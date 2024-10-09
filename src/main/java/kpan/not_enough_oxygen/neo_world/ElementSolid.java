package kpan.not_enough_oxygen.neo_world;

import java.awt.Color;

public class ElementSolid extends ElementData<ElementSolid> {

    public final EnumHardness hardness;// 採掘にかかる時間の基準

    private byte miningLevel = 0;// 採掘レベル
    private boolean isFallable = false;// 落下ブロックか
    private float facilityHeatResistanceBonus = 0;// オーバーヒート耐性
    private float slippness = 0;// 滑りやすさ
    private short decorationModifier = 0;// 装飾値（10->+10%）
    private ElementGas emittingGas = null;

    public ElementSolid(String name, Color elementColor, float thermalConductivity, float specificHeatCapacity, EnumHardness hardness) {
        super(name, ElementState.SOLID, elementColor, thermalConductivity, specificHeatCapacity);
        this.hardness = hardness;
    }

    // setter

    public ElementSolid fallable() {
        isFallable = true;
        return this;
    }

    public ElementSolid rock(int decorationModifier) {
        properties.add(ElementProperty.ROCK);
        this.decorationModifier = (short) decorationModifier;
        return this;
    }

    public ElementSolid metalOre() {
        properties.add(ElementProperty.METAL_ORE);
        return this;
    }

    public ElementSolid metal() {
        properties.add(ElementProperty.REFINED_METAL);
        return this;
    }

    public ElementSolid gasEmittion(ElementGas gas) {
        emittingGas = gas;
        return this;
    }

    // getter

    public byte getMiningLevel() {
        return miningLevel;
    }

    public boolean isFallable() {
        return isFallable;
    }

    public float getFacilityHeatResistanceBonus() {
        return facilityHeatResistanceBonus;
    }

    public float getSlippness() {
        return slippness;
    }

    public short getDecorationModifier() {
        return decorationModifier;
    }

    public ElementGas getEmittingGas() {
        return emittingGas;
    }

    public enum EnumHardness {
        SOFTEST, // 0,1
        VERY_SOFT, // 2~7
        SOFT, // 8~18
        NORMAL, // 19~39
        HARD, // 41~99
        VERY_HARD, // 100~179
        HARDEST, // 180~
        UNBREAKABLE,
        ;

        public static EnumHardness fromInt(int value) {
            if (value <= 1)
                return SOFTEST;
            if (value <= 7)
                return VERY_SOFT;
            if (value <= 18)
                return SOFT;
            if (value <= 39)
                return NORMAL;
            if (value <= 99)
                return HARD;
            if (value <= 179)
                return VERY_HARD;
            if (value <= 254)
                return HARDEST;
            return UNBREAKABLE;
        }
    }

}
