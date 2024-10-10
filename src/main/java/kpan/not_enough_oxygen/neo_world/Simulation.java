package kpan.not_enough_oxygen.neo_world;

import kpan.not_enough_oxygen.neo_world.ElementData.ElementState;
import kpan.not_enough_oxygen.neo_world.SimulationFrame.AreaData;
import kpan.not_enough_oxygen.util.MyNBTUtil;
import kpan.not_enough_oxygen.util.MyNBTUtil.NBTException;
import kpan.not_enough_oxygen.world.NEOWorldProvider;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

public class Simulation {

    private State state = State.INITIALIZED;// アクセス状況によってlock必要
    private SimulationFrame currentFrame;
    private int queuedTicks = 0;// 読み書きにはlock必須
    private final Object lockObj = new Object();

    public static Simulation fromNBT(NBTTagCompound nbt) {
        Simulation simulation = new Simulation();
        try {
            simulation.state = MyNBTUtil.readEnum(nbt, "state", State.class);
            simulation.currentFrame = SimulationFrame.fromNBT(nbt.getCompoundTag("currentFrame"));
            simulation.queuedTicks = MyNBTUtil.readNumberInt(nbt, "queuedTicks");
            return simulation;
        } catch (NBTException e) {
            throw new RuntimeException(e);
        }
    }

    public State getState() {
        return state;
    }

    public SimulationFrame getCurrentFrame() {
        return currentFrame;
    }

    public void queueProcess(int ticks) {
        if (ticks < 0)
            throw new IllegalArgumentException("ticks can't be negative");
        synchronized (lockObj) {
            if (state == State.INITIALIZED)
                throw new IllegalStateException("Simulation is not running!");
            if (state == State.READY)
                state = State.QUEUED;
            queuedTicks += ticks;
        }
        if (queuedTicks < 0)
            throw new IllegalStateException("ticks overflowed");
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        synchronized (lockObj) {
            if (state != State.INITIALIZED)
                return;
            state = State.READY;
        }
        try {
            while (true) {
                switch (state) {
                    case READY:
                        // やること無し
                        Thread.sleep(50);
                        break;
                    case QUEUED:
                        state = State.PROCESSING;
                        while (true) {
                            simulateNextFrame();
                            synchronized (lockObj) {
                                applyNextFrame();
                                queuedTicks--;
                                if (queuedTicks <= 0) {
                                    state = State.READY;
                                    break;
                                }
                            }
                        }
                        Thread.sleep(20);
                        break;
                    default:
                        throw new IllegalStateException("INVALID STATE:" + state);
                }
            }
        } catch (InterruptedException e) {
            state = State.INITIALIZED;
        }
    }

    public NBTTagCompound toNBT() {
        synchronized (lockObj) {
            NBTTagCompound nbt = new NBTTagCompound();
            MyNBTUtil.write(nbt, "state", state);
            nbt.setTag("currentFrame", currentFrame.toNBT());
            nbt.setInteger("queuedTicks", queuedTicks);
            return nbt;
        }
    }

    private void simulateNextFrame() {

        simulateArea(currentFrame);

    }

    private void applyNextFrame() {
        currentFrame.areaData.applyUpdate();
    }

    private static void simulateArea(SimulationFrame currentFrame) {

        AreaData areaData = currentFrame.areaData;
        int beginCellIdx = 0;
        int endCellIdxExc = areaData.sizeX * NEOWorldProvider.WORLD_HEIGHT * areaData.sizeZ;
        for (int cellIdx = beginCellIdx; cellIdx < endCellIdxExc; cellIdx++) {
            if (!areaData.isExplored(cellIdx))
                continue;
            Cell cellReadOnly = areaData.getCellReadOnly(cellIdx);
            ElementData<?> element = cellReadOnly.element;
            switch (element.state) {
                case VACUUM -> {
                    // やること無し
                }
                case SOLID -> {
                    // TODO:まだやることない
                }
                case LIQUID -> SimulateLiquid.sim(cellReadOnly, (ElementLiquid) element, cellIdx, areaData);
                case GAS -> {
                    /*
                    // 上
                    int upCellIdx = areaData.getOffsetCellIdx(cellIdx, EnumFacing.UP);
                    Cell upCell = areaData.getCellReadOnly(upCellIdx);
                    if (upCell.canGasExist()) {
                        if (upCell.isVacuum() || upCell.element == element && upCell.mass < remainCellMass) {
                            // 上に移動
                            float massDiff = remainCellMass - upCell.mass;
                            float massToMove = massDiff * ((ElementGas) element).flowRate;
                            remainCellMass -= areaData.moveMass(cellIdx, upCellIdx, massToMove, element);
                        }
                        if (remainCellMass == 0)
                            break;
                    }
                    // 水平
                    for (EnumFacing facing : EnumFacing.HORIZONTALS) {
                        int nextCellIdx = areaData.getOffsetCellIdx(cellIdx, facing);
                        Cell nextCell = areaData.getCellReadOnly(nextCellIdx);
                        if (nextCell.isVacuum() || nextCell.element == element && nextCell.mass < remainCellMass) {
                            // そのマスに移動
                            float massDiff = remainCellMass - nextCell.mass;
                            float massToMove = massDiff * ((ElementGas) element).flowRate;
                            remainCellMass -= areaData.moveMass(cellIdx, nextCellIdx, massToMove, element);
                        }
                        if (remainCellMass == 0)
                            break;
                    }
                    if (remainCellMass == 0)
                        break;
                    // 下
                    int downCellIdx = areaData.getOffsetCellIdx(cellIdx, EnumFacing.UP);
                    Cell downCell = areaData.getCellReadOnly(downCellIdx);
                    if (downCell.isVacuum() || downCell.element == element && downCell.mass < remainCellMass) {
                        // 右に移動
                        float massDiff = remainCellMass - downCell.mass;
                        float massToMove = massDiff * element.getGasFlowRate();
                        remainCellMass -= areaData.moveMass(cellIdx, downCellIdx, massToMove, element);
                    }
                    if (remainCellMass == 0)
                        break;
*/
                }
            }
        }
    }

    //

    public enum State {
        INITIALIZED, // run前
        READY, // sim準備完了
        QUEUED, // sim依頼
        PROCESSING, // sim中
    }

    private static class SimulateLiquid {

        public static void sim(Cell cellReadOnly, ElementLiquid element, int cellIdx, AreaData areaData) {
            if (!cellReadOnly.canLiquidExist())
                return;// 気体透過ブロックに液体を埋め込むバグテクニックを再現する

            float remainCellMass = cellReadOnly.mass;

            remainCellMass = moveToDown(element, cellIdx, areaData, remainCellMass);
            if (remainCellMass <= 0)
                return;
            remainCellMass = moveToHorizontal(element, cellIdx, areaData, remainCellMass);
            if (remainCellMass <= 0)
                return;
            remainCellMass = moveToUp(element, cellIdx, areaData, remainCellMass);
            if (remainCellMass <= 0)
                return;
        }

        private static float moveToDown(ElementLiquid element, int cellIdx, AreaData areaData, float remainCellMass) {
            int downCellIdx = areaData.getOffsetCellIdx(cellIdx, EnumFacing.DOWN);
            Cell downCell = areaData.getCellReadOnly(downCellIdx);
            if (!downCell.canLiquidExist())
                return remainCellMass;

            if (downCell.element == element) {
                float compressedCellMass = Math.min(remainCellMass * 1.01F, element.maxMass);
                float massDifference = compressedCellMass - downCell.mass;
                if (massDifference > 0) {
                    float amountToMove = Math.min(massDifference * 0.5F, element.viscosity);

                    if (remainCellMass <= element.minVerticalFlow || amountToMove >= element.minVerticalFlow) {
                        remainCellMass -= areaData.moveMass(cellIdx, downCellIdx, amountToMove, element);
                    }
                }
            } else if (downCell.isVacuum() || downCell.element.state == ElementState.GAS) {
                boolean isEdge = false;
                for (EnumFacing facing : EnumFacing.HORIZONTALS) {
                    int idx = areaData.getOffsetCellIdx(cellIdx, facing);
                    if (!areaData.getCellReadOnly(idx).canLiquidExist())
                        continue;
                    if (areaData.getCellReadOnly(areaData.getOffsetCellIdx(idx, EnumFacing.DOWN)).canLiquidExist())
                        continue;
                    isEdge = true;
                    break;
                }
                if (isEdge) {
                    areaData.changeToFalling(cellIdx, element, cellIdx);
                } else {
                    areaData.swapToGas(cellIdx, downCellIdx, element);
                }
            }
            return remainCellMass;
        }

        private static float moveToHorizontal(ElementLiquid element, int cellIdx, AreaData areaData, float remainCellMass) {
            for (EnumFacing facing : EnumFacing.HORIZONTALS) {
                int facingCellIdx = areaData.getOffsetCellIdx(cellIdx, facing);
                Cell facingCell = areaData.getCellReadOnly(facingCellIdx);
                if (!facingCell.canLiquidExist())
                    continue;
                if (facingCell.element != element && !facingCell.isVacuum() && facingCell.element.state != ElementState.GAS)
                    continue;

                float amountToMove = Math.min((remainCellMass - facingCell.mass) * 0.25F, element.viscosity);
                if (amountToMove <= 0)
                    continue;
                if (amountToMove < element.minHorizontalFlow)
                    continue;
                if (!areaData.getCellReadOnly(areaData.getOffsetCellIdx(cellIdx, EnumFacing.DOWN)).canLiquidExist()
                        && areaData.getCellReadOnly(areaData.getOffsetCellIdx(facingCellIdx, EnumFacing.DOWN)).canLiquidExist()) {
                    if (areaData.changeToFalling(cellIdx, element, facingCellIdx))
                        return 0;
                } else {
                    remainCellMass -= areaData.moveLiquid(cellIdx, facingCellIdx, amountToMove, element);
                    if (remainCellMass <= 0)
                        return 0;
                }
            }
            return remainCellMass;
        }

        private static float moveToUp(ElementLiquid element, int cellIdx, AreaData areaData, float remainCellMass) {
            int upCellIdx = areaData.getOffsetCellIdx(cellIdx, EnumFacing.UP);
            Cell upCell = areaData.getCellReadOnly(upCellIdx);
            if (!upCell.canLiquidExist())
                return remainCellMass;
            if (upCell.element != element && !upCell.isVacuum() && upCell.element.state != ElementState.GAS)
                return remainCellMass;

            float expectedCellMass = Math.max(upCell.mass * 1.01F, element.maxMass);
            float massDifferece = remainCellMass - expectedCellMass;
            if (massDifferece <= 0.0F)
                return remainCellMass;

            float amountToMove = massDifferece * 0.5F;

            // 圧縮状態の2倍未満でなければ移動量に制限がない
            //→上方向にはありえない質量の移動が可能
            if (remainCellMass < expectedCellMass + expectedCellMass) {
                amountToMove = Math.min(amountToMove, element.viscosity);
            }

            // amountToMove>0.01なら移動
            if (amountToMove > 0.01) {
                remainCellMass -= areaData.moveLiquid(cellIdx, upCellIdx, amountToMove, element);
                if (remainCellMass <= 0)
                    return 0;
            }
            return remainCellMass;
        }

    }
}
