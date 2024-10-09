package kpan.not_enough_oxygen.neo_world;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kpan.not_enough_oxygen.neo_world.ElementData.ElementState;
import kpan.not_enough_oxygen.world.NEOWorldProvider;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import org.apache.commons.lang3.NotImplementedException;

public final class SimulationFrame {
    public final long simulationTick;
    public final AreaData areaData;
    public final EntityData entityData = null;

    private SimulationFrame(long simulationTick, AreaData areaData) {
        this.simulationTick = simulationTick;
        this.areaData = areaData;
    }

    public static final class AreaData {
        public final int sizeX;
        public final int sizeZ;
        private final Cell[] cells;
        private final boolean[] explored;

        private final Map<Integer, Cell> updatedCells = new HashMap<>();
        private final Set<Integer> newlyExplored = new HashSet<>();

        public AreaData(int sizeX, int sizeZ) {
            this(sizeX, sizeZ, new Cell[sizeX * NEOWorldProvider.WORLD_HEIGHT * sizeZ]);
        }

        public AreaData(int sizeX, int sizeZ, Cell[] cells) {
            this.sizeX = sizeX;
            this.sizeZ = sizeZ;
            this.cells = cells;
            explored = new boolean[cells.length];
        }

        public boolean isExplored(int cellIdx) {
            return explored[cellIdx];
        }

        public Cell getCellReadOnly(int idx) {
            if (idx < 0 || idx >= cells.length)
                return Cell.INVALID;
            return cells[idx];
        }

        public Cell getCellForGeneration(int idx) {
            if (idx < 0 || idx >= cells.length)
                return Cell.INVALID.clone();
            return cells[idx];
        }

        // 実際に移動したmass
        public float moveMass(int srcIdx, int dstIdx, float massToMove, ElementData<?> elementCondition) {
            if (massToMove < 0)
                throw new IllegalArgumentException("massToMove must not be negative");
            // dst判定
            Cell dstCell = updatedCells.get(dstIdx);
            if (dstCell == null) {
                Cell c = cells[dstIdx];
                if (elementCondition != c.element && !c.isVacuum())
                    return 0;
            } else {
                if (elementCondition != dstCell.element && !dstCell.isVacuum())
                    return 0;
            }
            // src判定+updatedCellsのセット
            Cell srcCell = updatedCells.get(srcIdx);
            if (srcCell == null) {
                Cell c = cells[srcIdx];
                if (elementCondition != c.element)
                    return 0;
                srcCell = c.clone();
                updatedCells.put(srcIdx, srcCell);
            } else {
                if (elementCondition != srcCell.element)
                    return 0;
            }
            // dstのupdatedCellsのセット
            if (dstCell == null) {
                dstCell = cells[dstIdx].clone();
                updatedCells.put(dstIdx, dstCell);
            }

            // 移動量設定
            massToMove = Math.min(massToMove, srcCell.mass);

            // 移動先更新
            if (dstCell.isVacuum()) {
                dstCell.element = elementCondition;
                dstCell.mass = massToMove;
                dstCell.temperature = srcCell.temperature;
            } else {
                float newTemperature = (dstCell.mass * dstCell.temperature + massToMove * srcCell.temperature) / (dstCell.mass + massToMove);
                dstCell.mass += massToMove;
                dstCell.temperature = newTemperature;
            }

            // 移動元更新
            srcCell.mass -= massToMove;
            if (srcCell.mass <= 0) {
                srcCell.setAsVacuum();
            }

            return massToMove;
        }

        public boolean changeToFalling(int cellIdx, ElementLiquid elementCondition, int spawnCellIdx) {
            // src判定+updatedCellsのセット
            Cell cell = updatedCells.get(cellIdx);
            if (cell == null) {
                Cell c = cells[cellIdx];
                if (elementCondition != c.element)
                    return false;
                cell = c.clone();
                updatedCells.put(cellIdx, cell);
            } else {
                if (elementCondition != cell.element)
                    return false;
            }

            spawnFalling(cell, spawnCellIdx);
            cell.setAsVacuum();
            return true;
        }

        public void spawnFalling(Cell cell, int spawnCellIdx) {
            // TODO:spawnFalling実装
            throw new NotImplementedException();
        }

        public float moveLiquid(int srcIdx, int dstIdx, float massToMove, ElementLiquid elementCondition) {
            if (massToMove < 0)
                throw new IllegalArgumentException("massToMove must not be negative");
            // src判定
            Cell srcCell = updatedCells.get(srcIdx);
            if (srcCell == null) {
                Cell c = cells[srcIdx];
                if (elementCondition != c.element)
                    return 0;
            } else {
                if (elementCondition != srcCell.element)
                    return 0;
            }
            // dst取得
            Cell dstCellReadOnly = updatedCells.get(dstIdx);
            if (dstCellReadOnly == null)
                dstCellReadOnly = cells[dstIdx];

            if (dstCellReadOnly.element == elementCondition || dstCellReadOnly.isVacuum()) {
                // そのまま移動で問題ない
                return moveMass(srcIdx, dstIdx, massToMove, elementCondition);
            }

            if (dstCellReadOnly.element.state != ElementState.GAS) {
                // 気体以外は押しだせない
                return 0;
            }
            if (!tryPushOutGas(dstIdx))
                return 0;

            // 押し出しに成功したのでそのまま移動で問題ない
            return moveMass(srcIdx, dstIdx, massToMove, elementCondition);
        }

        public boolean swapToGas(int srcIdx, int dstIdx, ElementLiquid elementCondition) {
            // dst判定
            Cell dstCell = updatedCells.get(dstIdx);
            if (dstCell == null) {
                Cell c = cells[dstIdx];
                if (!c.isVacuum() && c.element.state != ElementState.GAS)
                    return false;
            } else {
                if (!dstCell.isVacuum() && dstCell.element.state != ElementState.GAS)
                    return false;
            }
            // src判定+クローン
            Cell srcCell = updatedCells.get(srcIdx);
            if (srcCell == null) {
                Cell c = cells[srcIdx];
                if (elementCondition != c.element)
                    return false;
                srcCell = c.clone();
            } else {
                if (elementCondition != srcCell.element)
                    return false;
            }
            // dstのクローン
            if (dstCell == null) {
                dstCell = cells[dstIdx].clone();
            }

            // swap
            updatedCells.put(dstIdx, srcCell);
            updatedCells.put(srcIdx, dstCell);
            return true;
        }

        public boolean tryPushOutGas(int cellIdx) {
            Cell cellReadOnly = updatedCells.get(cellIdx);
            if (cellReadOnly == null)
                cellReadOnly = cells[cellIdx];
            if (cellReadOnly.isVacuum())
                return true;
            if (cellReadOnly.element.state != ElementState.GAS)
                return false;

            for (EnumFacing facing : EnumFacing.VALUES) {
                int facingIdx = getOffsetCellIdx(cellIdx, facing);
                Cell facingCellReadOnly = updatedCells.get(facingIdx);
                if (facingCellReadOnly == null)
                    facingCellReadOnly = cells[facingIdx];
                if (!facingCellReadOnly.isVacuum() && facingCellReadOnly.element != cellReadOnly.element)
                    continue;
                if (moveMass(cellIdx, facingIdx, cellReadOnly.mass, cellReadOnly.element) > 0)// 全量移動なので0以外なら成功
                    return true;
            }
            return false;
        }

        public int getOffsetCellIdx(int cellIdx, EnumFacing facing) {
            return switch (facing) {
                case DOWN -> cellIdx - 1;
                case EAST -> cellIdx + sizeZ * NEOWorldProvider.WORLD_HEIGHT;
                case NORTH -> cellIdx - NEOWorldProvider.WORLD_HEIGHT;
                case SOUTH -> cellIdx + NEOWorldProvider.WORLD_HEIGHT;
                case UP -> cellIdx + 1;
                case WEST -> cellIdx - sizeZ * NEOWorldProvider.WORLD_HEIGHT;
            };
        }

        public int toCellIdx(int x, int y, int z) {
            return (x * sizeZ + z) * NEOWorldProvider.WORLD_HEIGHT + y;
        }

    }

    public static class BuildingData {

    }

    public static class SolidPipeNet {

    }

    public static class LiquidPipeNet {

    }

    public static class GasPipeNet {

    }

    public static class EntityData {

        List<NEOEntity> entities = new LinkedList<>();

        public void spawn(Critter critter, BlockPos pos) {
            entities.add(new NEOEntityCritter(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, critter));
        }
    }

}
