package kpan.not_enough_oxygen.neo_world;

import java.awt.Color;
import java.util.EnumSet;

public class ElementData<E extends ElementData<E>> {
    public static final ElementData<?> VACUUM = new ElementData<>("vacuum", ElementState.VACUUM, Color.black, 0, Float.POSITIVE_INFINITY);

    // 基本特性
    public final String name;
    public final ElementState state;
    public final Color elementColor;
    public final float thermalConductivity;
    public final float specificHeatCapacity;
    protected EnumSet<ElementProperty> properties = EnumSet.noneOf(ElementProperty.class);

    // 追加特性
    private float highTemperature = Float.POSITIVE_INFINITY;// 温度による状態変化
    private ElementData<?> highTemperatureElement = null;
    private float lowTemperature = Float.NEGATIVE_INFINITY;
    private ElementData<?> lowTemperatureElement = null;
    private EnumLightEmittion lightEmittion = EnumLightEmittion.NONE;
    private float lightAbsorptionFactor = 1.0F;
    private float radiationEmittion = 0;
    private float radiationAbsorptionFactor = 0.7F;

    protected ElementData(String name, ElementState state, Color elementColor, float thermalConductivity, float specificHeatCapacity) {
        this.name = name;
        this.state = state;
        this.elementColor = elementColor;
        this.thermalConductivity = thermalConductivity;
        this.specificHeatCapacity = specificHeatCapacity;
        Elements.ELEMENTS.add(this);
    }

    // setter
    @SuppressWarnings("unchecked")
    public E highTemperature(float temperature, ElementData<?> element) {
        highTemperature = temperature;
        highTemperatureElement = element;
        return (E) this;
    }

    @SuppressWarnings("unchecked")
    public E lowTemperature(float temperature, ElementData<?> element) {
        lowTemperature = temperature;
        lowTemperatureElement = element;
        return (E) this;
    }

    @SuppressWarnings("unchecked")
    public E light(EnumLightEmittion emittion) {
        lightEmittion = emittion;
        return (E) this;
    }

    @SuppressWarnings("unchecked")
    public E lightAbsorption(float factor) {
        lightAbsorptionFactor = factor;
        return (E) this;
    }

    @SuppressWarnings("unchecked")
    public E radiation(float emittion) {
        radiationEmittion = emittion;
        return (E) this;
    }

    @SuppressWarnings("unchecked")
    public E radiationAbsorption(float factor) {
        radiationAbsorptionFactor = factor;
        return (E) this;
    }

    @SuppressWarnings("unchecked")
    public E radiationAbsorption(ElementData<?> baseElement) {
        radiationAbsorptionFactor = baseElement.getRadiationAbsorptionFactor();
        return (E) this;
    }

    // getter

    public EnumSet<ElementProperty> getProperties() {
        return properties;
    }

    public float getHighTemperature() {
        return highTemperature;
    }

    public ElementData<?> getHighTemperatureElement() {
        return highTemperatureElement;
    }

    public float getLowTemperature() {
        return lowTemperature;
    }

    public ElementData<?> getLowTemperatureElement() {
        return lowTemperatureElement;
    }

    public EnumLightEmittion getLightEmittion() {
        return lightEmittion;
    }

    public float getLightAbsorptionFactior() {
        return lightAbsorptionFactor;
    }

    public float getRadiationEmittion() {
        return radiationEmittion;
    }

    public float getRadiationAbsorptionFactor() {
        return radiationAbsorptionFactor;
    }

    public enum ElementState {
        VACUUM, SOLID, LIQUID, GAS,
    }

    public enum ElementProperty {
        // solid
        ROCK, METAL_ORE, REFINED_METAL,
        //
    }

    public enum EnumLightEmittion {
        NONE, STABLE_LV1, STABLE_LV2, STABLE_LV3, HEAT_RELATED,
    }
}