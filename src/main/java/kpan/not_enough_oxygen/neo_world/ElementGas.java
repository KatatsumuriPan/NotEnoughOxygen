package kpan.not_enough_oxygen.neo_world;

import java.awt.Color;

public class ElementGas extends ElementData<ElementGas> {

    public final float molarMass;// g/mol
    public final float flowRate;

    public ElementGas(String name, Color elementColor, float thermalConductivity, float specificHeatCapacity, float molarMass, float flowRate) {
        super(name, ElementState.GAS, elementColor, thermalConductivity, specificHeatCapacity);
        this.molarMass = molarMass;
        this.flowRate = flowRate;
        lightAbsorption(0.2F);
        radiationAbsorption(0.07F);
    }

}