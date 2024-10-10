package kpan.not_enough_oxygen.neo_world;

import java.util.EnumSet;
import kpan.not_enough_oxygen.neo_world.ElementData.ElementState;
import kpan.not_enough_oxygen.util.MyNBTUtil;
import kpan.not_enough_oxygen.util.MyNBTUtil.NBTException;
import net.minecraft.nbt.NBTTagCompound;

public final class Cell {

    public static Cell fromNBT(NBTTagCompound nbt) throws NBTException {
        ElementData<?> element = Elements.ELEMENTS.get(MyNBTUtil.readString(nbt, "element"));
        if (element == null)
            throw new NBTException("element is not valid:" + MyNBTUtil.readString(nbt, "element"));
        float mass = MyNBTUtil.readNumberFloat(nbt, "mass");
        float temperature = MyNBTUtil.readNumberFloat(nbt, "temperature");
        Germ germ = Germs.GERMS.get(MyNBTUtil.readNumberByte(nbt, "germ"));

        float germAmount = MyNBTUtil.readNumberFloat(nbt, "germAmount");
        int effectsBits = MyNBTUtil.readNumberInt(nbt, "effects");
        EnumSet<EffectFromBuilding> effects = EnumSet.noneOf(EffectFromBuilding.class);
        for (int i = 0; i < 16; i++) {
            if ((effectsBits & (1 << i)) != 0)
                effects.add(EffectFromBuilding.values()[i]);
        }
        return new Cell(element, mass, temperature, germ, germAmount, effects);
    }

    public static final Cell INVALID = new Cell(Elements.NEUTRONIUM, Float.POSITIVE_INFINITY, 0);

    public ElementData<?> element;
    public float mass;
    public float temperature;
    public Germ germ;
    public float germAmount;
    public final EnumSet<EffectFromBuilding> effects;

    public Cell(ElementData<?> element, float mass, float temperature) {
        this(element, mass, temperature, null, 0, EnumSet.noneOf(EffectFromBuilding.class));
    }

    public Cell(ElementData<?> element, float mass, float temperature, Germ germ, float germAmount, EnumSet<EffectFromBuilding> states) {
        this.element = element;
        this.mass = mass;
        this.temperature = temperature;
        this.germ = germ;
        this.germAmount = germAmount;
        effects = states;
    }

    @Override
    public Cell clone() {
        return new Cell(element, mass, temperature, germ, germAmount, EnumSet.copyOf(effects));
    }

    public void copyTo(Cell cell) {
        cell.element = element;
        cell.mass = mass;
        cell.temperature = temperature;
        cell.germ = germ;
        cell.germAmount = germAmount;
        cell.effects.clear();
        cell.effects.addAll(effects);
    }

    public boolean canGasExist() {
        return element.state != ElementState.SOLID && !effects.contains(EffectFromBuilding.GAS_IMPERMEABLE);
    }

    public boolean canLiquidExist() {
        return element.state != ElementState.SOLID && !effects.contains(EffectFromBuilding.LIQUID_IMPERMEABLE);
    }

    public NBTTagCompound toNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setString("element", element.name);
        nbt.setFloat("mass", mass);
        nbt.setFloat("temperature", temperature);
        nbt.setByte("germ", germ.id);
        nbt.setFloat("germAmount", germAmount);
        nbt.setShort("effects", (short) effects.stream().mapToInt(Enum::ordinal).reduce(0, (sum, val) -> sum | 1 << val));
        return nbt;
    }

    public enum EffectFromBuilding {
        LIQUID_IMPERMEABLE, GAS_IMPERMEABLE
    }

    public boolean isVacuum() {
        return element.state == ElementState.VACUUM;
    }

    public void setAsVacuum() {
        element = Elements.VACUUM;
        mass = 0;
        temperature = 0;
        germ = null;
        germAmount = 0;
    }
}
