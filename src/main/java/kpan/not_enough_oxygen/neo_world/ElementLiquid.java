package kpan.not_enough_oxygen.neo_world;

import java.awt.Color;

public class ElementLiquid extends ElementData<ElementLiquid> {

    public final float molarMass;// g/mol
    public final float viscosity;
    public final float minVerticalFlow;
    public final float minHorizontalFlow;
    public final float maxMass;
    private ElementGas emittingGas = null;
    private ElementData<?> soluteElement = null;
    private float soluteRatio = 0;

    public ElementLiquid(String name, Color elementColor, float thermalConductivity, float specificHeatCapacity, float molarMass, float viscosity, float minVerticalFlow, float minHorizontalFlow,
                         float maxMass) {
        super(name, ElementState.SOLID, elementColor, thermalConductivity, specificHeatCapacity);
        this.molarMass = molarMass;
        this.viscosity = viscosity;
        this.minVerticalFlow = minVerticalFlow;
        this.minHorizontalFlow = minHorizontalFlow;
        this.maxMass = maxMass;
    }

    // setter

    public ElementLiquid gasEmittion(ElementGas emittingGas) {
        this.emittingGas = emittingGas;
        return this;
    }

    public ElementLiquid solute(float soluteRatio, ElementData<?> soluteElement) {
        this.soluteElement = soluteElement;
        this.soluteRatio = soluteRatio;
        return this;
    }

    // getter

    public ElementGas getEmittingGas() {
        return emittingGas;
    }

    public ElementData<?> getSoluteElement() {
        return soluteElement;
    }

    public float getSoluteRatio() {
        return soluteRatio;
    }

}