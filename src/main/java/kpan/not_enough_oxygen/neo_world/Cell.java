package kpan.not_enough_oxygen.neo_world;

import java.util.EnumSet;
import kpan.not_enough_oxygen.neo_world.ElementData.ElementState;

public final class Cell {

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
