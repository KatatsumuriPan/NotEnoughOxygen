package kpan.not_enough_oxygen.neo_world;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import kpan.not_enough_oxygen.neo_world.ElementData.EnumLightEmittion;
import kpan.not_enough_oxygen.neo_world.ElementSolid.EnumHardness;

public class Elements {

    public static final Map<String, ElementData<?>> ELEMENTS = new HashMap<>();

    public static final ElementData<?> VACUUM = ElementData.VACUUM;
    public static final ElementSolid NEUTRONIUM = solid("neutronium", Color.black, 0, Float.POSITIVE_INFINITY, EnumHardness.UNBREAKABLE).radiationAbsorption(0.9F);
    public static final ElementSolid CREATURE = solid("creature", new Color(0xFF_FF_FF), 0.6, 3.47, EnumHardness.SOFT).radiationAbsorption(0.25F);

    //----------solid----------
    // rock
    public static final ElementSolid ABYSSARITE = solid("abissarite", new Color(0xFF_FF_FF), 0.00001, 4, EnumHardness.VERY_HARD).radiationAbsorption(0.9F).rock(0);
    public static final ElementSolid GRANITE = solid("granite", new Color(0xFF_FF_FF), 3.39, 0.79, EnumHardness.HARD).rock(0);
    public static final ElementSolid IGNEOUS_ROCK = solid("igneous_rock", new Color(0xFF_FF_FF), 2, 1, EnumHardness.NORMAL).rock(0);
    public static final ElementSolid OBSIDIAN = solid("obsidian", new Color(0xFF_FF_FF), 2, 0.2, EnumHardness.HARDEST).rock(0);
    public static final ElementSolid SANDSTONE = solid("sandstone", new Color(0xFF_FF_FF), 2.9, 0.8, EnumHardness.SOFT).rock(0);
    // naural
    public static final ElementSolid ALGAE = solid("algae", new Color(0xFF_FF_FF), 2, 0.2, EnumHardness.VERY_SOFT);
    public static final ElementSolid COAL = solid("coal", new Color(0xFF_FF_FF), 1.25, 0.71, EnumHardness.VERY_SOFT).radiationAbsorption(0.84F);
    public static final ElementSolid DIRT = solid("dirt", new Color(0xFF_FF_FF), 2, 1.48, EnumHardness.VERY_SOFT);
    public static final ElementSolid FERTILIZER = solid("fertilizer", new Color(0xFF_FF_FF), 2, 0.83, EnumHardness.VERY_SOFT);
    public static final ElementSolid ICE = solid("ice", new Color(0xFF_FF_FF), 2.18, 2.05, EnumHardness.NORMAL).lightAbsorption(0.33F).radiationAbsorption(0.8F);
    public static final ElementSolid OXYLITE = solid("oxylite", new Color(0xFF_FF_FF), 4, 1, EnumHardness.SOFT);
    public static final ElementSolid SAND = solid("sand", new Color(0xFF_FF_FF), 2, 0.83, EnumHardness.SOFT).fallable();
    // metal_ore
    public static final ElementSolid COPPER_ORE = solid("copper_ore", new Color(0xFF_FF_FF), 4.5, 0.386, EnumHardness.NORMAL).radiationAbsorption(0.56F).metalOre();
    // metal
    // public static final ElementSolid COPPER = solid("copper", new Color(0xFF_FF_FF), 60, 0.385, EnumHardness.NORMAL).radiationAbsorption(0.61F).metal();
    // chemical
    public static final ElementSolid GLASS = solid("glass", new Color(0xFF_FF_FF), 1.11, 0.84, EnumHardness.SOFT).lightAbsorption(0.1F);
    // freezed

    // others
    //	public static final ElementSolid AEROGEL = solid("aerogel", new Color(0xFF_FF_FF), 0.003, 1, EnumHardness.VERY_SOFT).lightAbsorption(0.25F).radiationAbsorption(0.1F);
    //	public static final ElementSolid ALUMINUM = solid("aluminum", new Color(0xFF_FF_FF), 205, 0.91, EnumHardness.NORMAL).radiationAbsorption(0.77F);
    //	public static final ElementSolid ALUMINUM_ORE = solid("aluminum_ore", new Color(0xFF_FF_FF), 20.5, 0.91, EnumHardness.NORMAL).radiationAbsorption(0.72F);
    //	public static final ElementSolid BITUMEN = solid("bitumen", new Color(0xFF_FF_FF), 0.17, 1.76, EnumHardness.VERY_SOFT).radiationAbsorption(0.85F);
    //	public static final ElementSolid BLEACH_STONE = solid("bleach_stone", new Color(0xFF_FF_FF), 4, 0.5, EnumHardness.HARD).radiationAbsorption(0.73F);
    //	public static final ElementSolid BRICK = solid("brick", new Color(0xFF_FF_FF), 0.62, 0.84, EnumHardness.HARD).radiationAbsorption(0.8F);
    //	public static final ElementSolid BRINE_ICE = solid("brine_ice", new Color(0xFF_FF_FF), 2.18, 3.4, EnumHardness.NORMAL).lightAbsorption(0.33333F).radiationAbsorption(0.8F);
    //	public static final ElementSolid MILK_ICE = solid("milk_ice", new Color(0xFF_FF_FF), 2.18, 3.4, EnumHardness.NORMAL).lightAbsorption(0.33333F).radiationAbsorption(0.8F);
    //	public static final ElementSolid CARBON_FIBRE = solid("carbon_fibre", new Color(0xFF_FF_FF), 0, 0.52, EnumHardness.HARDEST).radiationAbsorption(0.84F);
    //	public static final ElementSolid CEMENT = solid("cement", new Color(0xFF_FF_FF), 8, 1.55, EnumHardness.HARDEST).radiationAbsorption(0.8F);
    //	public static final ElementSolid CEMENT_MIX = solid("cement_mix", new Color(0xFF_FF_FF), 8, 0.52, EnumHardness.SOFT).radiationAbsorption(0.8F);
    //	public static final ElementSolid CERAMIC = solid("ceramic", new Color(0xFF_FF_FF), 0.62, 0.84, EnumHardness.HARD);
    //	public static final ElementSolid CLAY = solid("clay", new Color(0xFF_FF_FF), 2, 0.92, EnumHardness.VERY_SOFT);
    //	public static final ElementSolid CRUSHED_ICE = solid("crushed_ice", new Color(0xFF_FF_FF), 2.18, 2.05, EnumHardness.SOFT).lightAbsorption(0.5F);
    //	public static final ElementSolid CRUSHED_ROCK = solid("crushed_rock", new Color(0xFF_FF_FF), 2, 0.2, EnumHardness.SOFT);
    //	public static final ElementSolid DEPLETED_URANIUM = solid("depleted_uranium", new Color(0xFF_FF_FF), 20, 1, EnumHardness.HARDEST).radiation(50F).radiationAbsorption(0.85F);
    //	public static final ElementSolid DIAMOND = solid("diamond", new Color(0xFF_FF_FF), 80, 0.516, EnumHardness.HARDEST).lightAbsorption(0.1F).radiationAbsorption(0.8F);
    //	public static final ElementSolid DIRTY_ICE = solid("dirty_ice", new Color(0xFF_FF_FF), 1, 3.05, EnumHardness.SOFT).lightAbsorption(0.8F);
    //	public static final ElementSolid ELECTRUM = solid("electrum", new Color(0xFF_FF_FF), 2, 0.15, EnumHardness.VERY_SOFT).radiationAbsorption(0.35F);
    //	public static final ElementSolid ENRICHED_URANIUM = solid("enriched_uranium", new Color(0xFF_FF_FF), 20, 1, EnumHardness.HARDEST).radiation(250F).radiationAbsorption(0.3F);
    //	public static final ElementSolid FOOLS_GOLD = solid("fools_gold", new Color(0xFF_FF_FF), 4.5, 0.386, EnumHardness.NORMAL);
    //	public static final ElementSolid FOSSIL = solid("fossil", new Color(0xFF_FF_FF), 2, 0.91, EnumHardness.HARD);
    //	public static final ElementSolid FULLERENE = solid("fullerene", new Color(0xFF_FF_FF), 50, 0.95, EnumHardness.HARDEST).radiationAbsorption(0.6F);
    //	public static final ElementSolid GOLD = solid("gold", new Color(0xFF_FF_FF), 60, 0.129, EnumHardness.VERY_SOFT).radiationAbsorption(0.35F);
    //	public static final ElementSolid GOLD_AMALGAM = solid("gold_amalgam", new Color(0xFF_FF_FF), 2, 0.15, EnumHardness.VERY_SOFT).radiationAbsorption(0.3F);
    //	public static final ElementSolid IRON = solid("iron", new Color(0xFF_FF_FF), 55, 0.449, EnumHardness.HARD).radiationAbsorption(0.66F);
    //	public static final ElementSolid IRON_ORE = solid("iron_ore", new Color(0xFF_FF_FF), 4, 0.449, EnumHardness.NORMAL).radiationAbsorption(0.61F);
    //	public static final ElementSolid COBALT = solid("cobalt", new Color(0xFF_FF_FF), 100, 0.42, EnumHardness.HARD).radiationAbsorption(0.63F);
    //	public static final ElementSolid COBALTITE = solid("cobaltite", new Color(0xFF_FF_FF), 4, 0.42, EnumHardness.NORMAL).radiationAbsorption(0.58F);
    //	public static final ElementSolid ISORESIN = solid("isoresin", new Color(0xFF_FF_FF), 0.17, 1.3, EnumHardness.SOFT);
    //	public static final ElementSolid LEAD = solid("lead", new Color(0xFF_FF_FF), 35, 0.128, EnumHardness.SOFT).radiationAbsorption(0.85F);
    //	public static final ElementSolid LIME = solid("lime", new Color(0xFF_FF_FF), 2, 0.834, EnumHardness.HARD);
    //	public static final ElementSolid MILK_FAT = solid("milk_fat", new Color(0xFF_FF_FF), 0.15, 1.92, EnumHardness.SOFTEST).radiationAbsorption(0.85F);
    //	public static final ElementSolid MAFIC_ROCK = solid("mafic_rock", new Color(0xFF_FF_FF), 1, 0.2, EnumHardness.VERY_SOFT);
    //	public static final ElementSolid NIOBIUM = solid("niobium", new Color(0xFF_FF_FF), 54, 0.265, EnumHardness.HARD).radiationAbsorption(0.49F);
    //	public static final ElementSolid CORIUM = solid("corium", new Color(0xFF_FF_FF), 6, 7.44, EnumHardness.HARDEST).radiationAbsorption(0.3F);
    //	public static final ElementSolid PHOSPHATE_NODULES = solid("phosphate_nodules", new Color(0xFF_FF_FF), 2, 0.15, EnumHardness.NORMAL);
    //	public static final ElementSolid PHOSPHORITE = solid("phosphorite", new Color(0xFF_FF_FF), 2, 0.15, EnumHardness.NORMAL);
    //	public static final ElementSolid PHOSPHORUS = solid("phosphorus", new Color(0xFF_FF_FF), 0.236, 0.7697, EnumHardness.SOFTEST);
    //	public static final ElementSolid HARD_POLYPROPYLENE = solid("hard_polypropylene", new Color(0xFF_FF_FF), 0.25, 1.5, EnumHardness.SOFTEST).radiationAbsorption(0.85F);
    //	public static final ElementSolid POLYPROPYLENE = solid("polypropylene", new Color(0xFF_FF_FF), 0.15, 1.92, EnumHardness.SOFTEST).radiationAbsorption(0.85F);
    //	public static final ElementSolid RADIUM = solid("radium", new Color(0xFF_FF_FF), 20, 1, EnumHardness.HARDEST).radiationAbsorption(0.25F);
    //	public static final ElementSolid REFINED_CARBON = solid("refined_carbon", new Color(0xFF_FF_FF), 3.1, 1.74, EnumHardness.VERY_SOFT).radiationAbsorption(0.84F);
    //	public static final ElementSolid REGOLITH = solid("regolith", new Color(0xFF_FF_FF), 1, 0.2, EnumHardness.VERY_SOFT).radiationAbsorption(0.6F);
    //	public static final ElementSolid RUST = solid("rust", new Color(0xFF_FF_FF), 4, 0.449, EnumHardness.NORMAL);
    //	public static final ElementSolid SALT = solid("salt", new Color(0xFF_FF_FF), 0.444, 0.7, EnumHardness.VERY_SOFT);
    //	public static final ElementSolid SAND_CEMENT = solid("sand_cement", new Color(0xFF_FF_FF), 8, 1.5, EnumHardness.HARD);
    //	public static final ElementSolid SEDIMENTARY_ROCK = solid("sedimentary_rock", new Color(0xFF_FF_FF), 2, 0.2, EnumHardness.VERY_SOFT);
    //	public static final ElementSolid SLABS = solid("slabs", new Color(0xFF_FF_FF), 8, 0.52, EnumHardness.HARD);
    //	public static final ElementSolid SLIME_MOLD = solid("slime_mold", new Color(0xFF_FF_FF), 2, 0.2, EnumHardness.VERY_SOFT);
    //	public static final ElementSolid SNOW = solid("snow", new Color(0xFF_FF_FF), 0.545, 2.05, EnumHardness.SOFT);
    //	public static final ElementSolid STABLE_SNOW = solid("stable_snow", new Color(0xFF_FF_FF), 0.545, 2.05, EnumHardness.SOFT);
    //	public static final ElementSolid SOLID_CARBON_DIOXIDE = solid("solid_carbon_dioxide", new Color(0xFF_FF_FF), 1.46, 0.846, EnumHardness.VERY_SOFT).radiationAbsorption(0.8F);
    //	public static final ElementSolid SOLID_CHLORINE = solid("solid_chlorine", new Color(0xFF_FF_FF), 0.75, 0.48, EnumHardness.NORMAL).radiationAbsorption(0.73F);
    //	public static final ElementSolid SOLID_CRUDE_OIL = solid("solid_crude_oil", new Color(0xFF_FF_FF), 2, 1.69, EnumHardness.VERY_SOFT).radiationAbsorption(0.8F);
    //	public static final ElementSolid SOLID_HYDROGEN = solid("solid_hydrogen", new Color(0xFF_FF_FF), 1, 2.4, EnumHardness.VERY_SOFT).lightAbsorption(0.75F).radiationAbsorption(0.9F);
    //	public static final ElementSolid SOLID_MERCURY = solid("solid_mercury", new Color(0xFF_FF_FF), 8.3, 0.14, EnumHardness.VERY_SOFT).radiationAbsorption(0.25F);
    //	public static final ElementSolid SOLID_METHANE = solid("solid_methane", new Color(0xFF_FF_FF), 0.03, 2.191, EnumHardness.VERY_SOFT);
    //	public static final ElementSolid SOLID_NAPHTHA = solid("solid_naphtha", new Color(0xFF_FF_FF), 0.2, 2.191, EnumHardness.VERY_SOFT).radiationAbsorption(0.6F);
    //	public static final ElementSolid SOLID_OXYGEN = solid("solid_oxygen", new Color(0xFF_FF_FF), 1, 1.01, EnumHardness.VERY_SOFT).lightAbsorption(0.75F).radiationAbsorption(0.82F);
    //	public static final ElementSolid SOLID_PETROLEUM = solid("solid_petroleum", new Color(0xFF_FF_FF), 2, 1.76, EnumHardness.VERY_SOFT).radiationAbsorption(0.8F);
    //	public static final ElementSolid SOLID_PROPANE = solid("solid_propane", new Color(0xFF_FF_FF), 1, 2.4, EnumHardness.SOFT);
    //	public static final ElementSolid SOLID_RESIN = solid("solid_resin", new Color(0xFF_FF_FF), 0.17, 1.3, EnumHardness.SOFT);
    //	public static final ElementSolid SOLID_SUPER_COOLANT = solid("solid_super_coolant", new Color(0xFF_FF_FF), 9.46, 8.44, EnumHardness.VERY_SOFT).radiationAbsorption(0.6F);
    //	public static final ElementSolid SOLID_VISCO_GEL = solid("solid_visco_gel", new Color(0xFF_FF_FF), 0.45, 1.55, EnumHardness.VERY_SOFT).lightAbsorption(0.2F).radiationAbsorption(0.6F);
    //	public static final ElementSolid STEEL = solid("steel", new Color(0xFF_FF_FF), 54, 0.49, EnumHardness.HARD).radiationAbsorption(0.74F);
    //	public static final ElementSolid SULFUR = solid("sulfur", new Color(0xFF_FF_FF), 0.2, 0.7, EnumHardness.VERY_SOFT).radiationAbsorption(0.74F);
    //	public static final ElementSolid SUPER_INSULATOR = solid("super_insulator", new Color(0xFF_FF_FF), 0.00001, 5.57, EnumHardness.HARDEST).radiationAbsorption(0.6F);
    //	public static final ElementSolid TEMP_CONDUCTOR_SOLID = solid("temp_conductor_solid", new Color(0xFF_FF_FF), 220, 0.622, EnumHardness.HARD).radiationAbsorption(0.6F);
    //	public static final ElementSolid TOXIC_SAND = solid("toxic_sand", new Color(0xFF_FF_FF), 2, 0.83, EnumHardness.SOFT);
    //	public static final ElementSolid TUNGSTEN = solid("tungsten", new Color(0xFF_FF_FF), 60, 0.134, EnumHardness.HARDEST).radiationAbsorption(0.35F);
    //	public static final ElementSolid URANIUM_ORE = solid("uranium_ore", new Color(0xFF_FF_FF), 20, 1, EnumHardness.VERY_HARD).radiation(150F).radiationAbsorption(0.3F);
    //	public static final ElementSolid WOLFRAMITE = solid("wolframite", new Color(0xFF_FF_FF), 15, 0.134, EnumHardness.VERY_HARD);
    //	public static final ElementSolid YELLOWCAKE = solid("yellowcake", new Color(0xFF_FF_FF), 20, 1, EnumHardness.HARDEST).radiationAbsorption(0.3F);
    //	public static final ElementSolid SOLID_ETHANOL = solid("solid_ethanol", new Color(0xFF_FF_FF), 20, 2.46, EnumHardness.HARDEST);
    //	public static final ElementSolid SOLID_SYNGAS = solid("solid_syngas", new Color(0xFF_FF_FF), 1, 2.4, EnumHardness.VERY_SOFT).lightAbsorption(0.75F);
    //	public static final ElementSolid TOXIC_MUD = solid("toxic_mud", new Color(0xFF_FF_FF), 2, 0.83, EnumHardness.SOFT);
    //	public static final ElementSolid MUD = solid("mud", new Color(0xFF_FF_FF), 2, 0.83, EnumHardness.SOFT);
    //	public static final ElementSolid SUCROSE = solid("sucrose", new Color(0xFF_FF_FF), 0.15, 1.255, EnumHardness.VERY_SOFT);
    //	public static final ElementSolid GRAPHITE = solid("graphite", new Color(0xFF_FF_FF), 8, 0.71, EnumHardness.SOFTEST).radiationAbsorption(0.85F);
    //	public static final ElementSolid SOLID_NUCLEAR_WASTE = solid("solid_nuclear_waste", new Color(0xFF_FF_FF), 6, 7.44, EnumHardness.NORMAL).radiation(150F).radiationAbsorption(0.3F);
    //	public static final ElementSolid CINNABAR = solid("cinnabar", new Color(0xFF_FF_FF), 4.5, 0.386, EnumHardness.NORMAL).radiationAbsorption(0.47F);
    //	public static final ElementSolid WOOD_LOG = solid("wood_log", new Color(0xFF_FF_FF), 0.22, 2.3, EnumHardness.SOFT).radiationAbsorption(0.6F);
    //	public static final ElementSolid TALLOW = solid("tallow", new Color(0xFF_FF_FF), 10, 2.19, EnumHardness.SOFT);

    //----------liquid----------
    // natural
    public static final ElementLiquid MILK = liquid("milk", new Color(0xFF_FF_FF), 0.609, 4.1, 23, 100, 0.01, 0.01, 1100).radiationAbsorption(0.8F);
    public static final ElementLiquid WATER = liquid("water", new Color(0xFF_FF_FF), 0.609, 4.179, 18.01528, 125, 0.01, 0.01, 1000).lightAbsorption(0.25F).radiationAbsorption(0.8F); // AnyWater
    public static final ElementLiquid MAGMA = liquid("magma", new Color(0xFF_FF_FF), 1, 1, 50, 60, 50, 20, 1840).radiationAbsorption(0.8F).light(EnumLightEmittion.HEAT_RELATED);
    // molten
    // public static final ElementLiquid MOLTEN_COPPER = liquid("molten_copper", new Color(0xFF_FF_FF), 12, 0.386, 63.546, 100, 20, 2, 3870)
    //         .radiationAbsorption(COPPER).light(EnumLightEmittion.HEAT_RELATED);
    public static final ElementLiquid MOLTEN_GLASS = liquid("molten_glass", new Color(0xFF_FF_FF), 1, 0.2, 50, 60, 50, 20, 1840).lightAbsorption(0.7F); // EmitsLight
    // condensed

    // others
    //	public static final ElementLiquid BRINE = liquid("brine", new Color(0xFF_FF_FF), 0.609, 3.4, 22, 100, 0.01, 0.01, 1200).lightAbsorption(0.25F).radiationAbsorption(0.8F);	// AnyWater
    //	public static final ElementLiquid CHLORINE = liquid("chlorine", new Color(0xFF_FF_FF), 0.0081, 0.48, 34.453, 180, 0.01, 0.01, 1000).radiationAbsorption(0.73F);
    //	public static final ElementLiquid CRUDE_OIL = liquid("crude_oil", new Color(0xFF_FF_FF), 2, 1.69, 500, 50, 0.1, 0.1, 870).radiationAbsorption(0.8F);
    //	public static final ElementLiquid DIRTY_WATER = liquid("dirty_water", new Color(0xFF_FF_FF), 0.58, 4.179, 20, 125, 0.01, 0.01, 1000).lightAbsorption(0.7F).radiationAbsorption(0.8F);	// "Mixture
    //	public static final ElementLiquid LIQUID_CARBON_DIOXIDE = liquid("liquid_carbon_dioxide", new Color(0xFF_FF_FF), 1.46, 0.846, 44.01, 125, 0.01, 0.01, 2000).radiationAbsorption(0.8F);
    //	public static final ElementLiquid LIQUID_HELIUM = liquid("liquid_helium", new Color(0xFF_FF_FF), 0.236, 0.2, 4, 100, 0.01, 0.01, 1000).lightAbsorption(0.8F).radiationAbsorption(0.89F);
    //	public static final ElementLiquid LIQUID_HYDROGEN = liquid("liquid_hydrogen", new Color(0xFF_FF_FF), 0.1, 2.4, 1.00794, 180, 0.01, 0.01, 1000).radiationAbsorption(0.9F);
    //	public static final ElementLiquid LIQUID_METHANE = liquid("liquid_methane", new Color(0xFF_FF_FF), 0.03, 2.191, 16.044, 180, 0.01, 0.01, 1000).lightAbsorption(0.6F).radiationAbsorption(0.75F);
    //	public static final ElementLiquid LIQUID_OXYGEN = liquid("liquid_oxygen", new Color(0xFF_FF_FF), 2, 1.01, 15.9994, 200, 0.01, 0.01, 500).radiationAbsorption(0.82F);	// Oxidizer
    //	public static final ElementLiquid LIQUID_PHOSPHORUS = liquid("liquid_phosphorus", new Color(0xFF_FF_FF), 0.236, 0.7697, 30.973762, 100, 2, 1, 1000).radiationAbsorption(0.75F);	// EmitsLight
    //	public static final ElementLiquid LIQUID_PROPANE = liquid("liquid_propane", new Color(0xFF_FF_FF), 0.1, 2.4, 44.1, 180, 0.01, 0.01, 1000).radiationAbsorption(0.75F);	// "HideFromSpawnTool
    //	public static final ElementLiquid LIQUID_SULFUR = liquid("liquid_sulfur", new Color(0xFF_FF_FF), 0.2, 0.7, 32, 50, 0.1, 0.1, 740).lightAbsorption(0.1F).radiationAbsorption(0.74F);
    //	public static final ElementLiquid MERCURY = liquid("mercury", new Color(0xFF_FF_FF), 8.3, 0.14, 200.59, 140, 0.01, 0.01, 1000).radiationAbsorption(0.25F);	// RefinedMetal
    //	public static final ElementLiquid MOLTEN_ALUMINUM = liquid("molten_aluminum", new Color(0xFF_FF_FF), 20.5, 0.91, 55.845, 100, 30, 3, 7870).radiationAbsorption(0.77F);	// "Metal
    //	public static final ElementLiquid MOLTEN_CARBON = liquid("molten_carbon", new Color(0xFF_FF_FF), 2, 0.71, 12.0107, 150, 0.01, 0.01, 4000).radiationAbsorption(0.84F);
    //	public static final ElementLiquid MOLTEN_GOLD = liquid("molten_gold", new Color(0xFF_FF_FF), 6, 0.1291, 196.966569, 100, 25, 1, 9970).radiationAbsorption(0.35F);	// "Metal
    //	public static final ElementLiquid MOLTEN_IRON = liquid("molten_iron", new Color(0xFF_FF_FF), 4, 0.449, 55.845, 100, 30, 3, 7870).radiationAbsorption(0.66F);	// "Metal
    //	public static final ElementLiquid MOLTEN_COBALT = liquid("molten_cobalt", new Color(0xFF_FF_FF), 4, 0.42, 58.9, 100, 30, 3, 7870).radiationAbsorption(0.63F);	// "Metal
    //	public static final ElementLiquid MOLTEN_LEAD = liquid("molten_lead", new Color(0xFF_FF_FF), 11, 0.128, 196.966569, 100, 25, 1, 9970).radiationAbsorption(0.85F);	// "Metal
    //	public static final ElementLiquid MOLTEN_NIOBIUM = liquid("molten_niobium", new Color(0xFF_FF_FF), 54, 0.265, 92.9, 100, 20, 10, 3870).radiationAbsorption(0.49F);	// "Metal
    //	public static final ElementLiquid MOLTEN_SALT = liquid("molten_salt", new Color(0xFF_FF_FF), 0.444, 0.7, 32, 50, 0.1, 0.1, 740).lightAbsorption(0.1F).radiationAbsorption(0.75F);
    //	public static final ElementLiquid MOLTEN_STEEL = liquid("molten_steel", new Color(0xFF_FF_FF), 80, 0.386, 63.546, 100, 20, 10, 3870).radiationAbsorption(0.74F);	// "Metal
    //	public static final ElementLiquid MOLTEN_TUNGSTEN = liquid("molten_tungsten", new Color(0xFF_FF_FF), 4, 0.134, 183.84, 100, 20, 10, 3870).lightAbsorption(0.7F).radiationAbsorption(0.35F);	// "Metal
    //	public static final ElementLiquid MOLTEN_URANIUM = liquid("molten_uranium", new Color(0xFF_FF_FF), 2, 1.69, 196.966569, 100, 25, 1, 9970).radiation(150F).radiationAbsorption(0.3F);	// "Metal
    //	public static final ElementLiquid NAPHTHA = liquid("naphtha", new Color(0xFF_FF_FF), 0.2, 2.191, 102.2, 30, 10, 10, 740).lightAbsorption(0.8F).radiationAbsorption(0.6F);	// Oil
    //	public static final ElementLiquid NUCLEAR_WASTE = liquid("nuclear_waste", new Color(0xFF_FF_FF), 6, 7.44, 196.966569, 100, 25, 1, 1000).radiation(150F).radiationAbsorption(0.3F);	// EmitsLight
    //	public static final ElementLiquid PETROLEUM = liquid("petroleum", new Color(0xFF_FF_FF), 2, 1.76, 82.2, 50, 0.1, 0.1, 740).lightAbsorption(0.8F).radiationAbsorption(0.8F);	// "CombustibleLiquid
    //	public static final ElementLiquid RESIN = liquid("resin", new Color(0xFF_FF_FF), 0.15, 1.11, 52.5, 1.1, 1.1, 0.01, 920).lightAbsorption(0.8F).radiationAbsorption(0.75F);
    //	public static final ElementLiquid SALT_WATER = liquid("salt_water", new Color(0xFF_FF_FF), 0.609, 4.1, 21, 100, 0.01, 0.01, 1100).lightAbsorption(0.25F).radiationAbsorption(0.8F);	// AnyWater
    //	public static final ElementLiquid SUGAR_WATER = liquid("sugar_water", new Color(0xFF_FF_FF), 0.609, 4.1, 21, 100, 0.01, 0.01, 1100).lightAbsorption(0.5F).radiationAbsorption(0.9F);	// PlastifiableLiquid
    //	public static final ElementLiquid SUPER_COOLANT = liquid("super_coolant", new Color(0xFF_FF_FF), 9.46, 8.44, 250, 150, 0.01, 0.01, 910).lightAbsorption(0.9F).radiationAbsorption(0.6F);
    //	public static final ElementLiquid VISCO_GEL = liquid("visco_gel", new Color(0xFF_FF_FF), 0.45, 1.55, 10, 1, 10, 10, 100).lightAbsorption(0.1F).radiationAbsorption(0.6F);
    //	public static final ElementLiquid ETHANOL = liquid("ethanol", new Color(0xFF_FF_FF), 0.171, 2.46, 46.07, 125, 0.01, 0.01, 1000).lightAbsorption(0.25F).radiationAbsorption(0.7F);	// CombustibleLiquid
    //	public static final ElementLiquid MOLTEN_SYNGAS = liquid("molten_syngas", new Color(0xFF_FF_FF), 0.1, 2.4, 1.00794, 180, 0.01, 0.01, 1000).radiationAbsorption(0.7F);
    //	public static final ElementLiquid MOLTEN_SUCROSE = liquid("molten_sucrose", new Color(0xFF_FF_FF), 0.15, 1.255, 32, 50, 0.1, 0.1, 740).lightAbsorption(0.1F).radiationAbsorption(0.7F);

    //----------gas----------
    // natural
    public static final ElementGas OXYGEN = gas("oxygen", new Color(0xFF_FF_FF), 0.024, 1.005, 15.9994, 0.12).lightAbsorption(0F);
    // evaporated
    public static final ElementGas ROCK_GAS = gas("rock_gas", new Color(0xFF_FF_FF), 0.1, 1, 50, 0.1).lightAbsorption(0.5F).light(EnumLightEmittion.STABLE_LV3);
    public static final ElementGas STEAM = gas("steam", new Color(0xFF_FF_FF), 0.184, 4.179, 18.01528, 0.1).lightAbsorption(0.1F);
    // others
    //	public static final ElementGas ALUMINUM_GAS = gas("aluminum_gas", new Color(0xFF_FF_FF), 2.5, 0.91, 63.546, 0.1).lightAbsorption(0.5F).radiationAbsorption(0.07F);	// "Metal
    //	public static final ElementGas CARBON_DIOXIDE = gas("carbon_dioxide", new Color(0xFF_FF_FF), 0.0146, 0.846, 44.01, 0.1).lightAbsorption(0.1F).radiationAbsorption(0.08F);
    //	public static final ElementGas CARBON_GAS = gas("carbon_gas", new Color(0xFF_FF_FF), 1.7, 0.71, 12.0107, 0.1).lightAbsorption(0.3F).radiationAbsorption(0.08F);	// EmitsLight
    //	public static final ElementGas CHLORINE_GAS = gas("chlorine_gas", new Color(0xFF_FF_FF), 0.0081, 0.48, 34.453, 0.1).lightAbsorption(0.2F).radiationAbsorption(0.07F);
    //	public static final ElementGas CONTAMINATED_OXYGEN = gas("contaminated_oxygen", new Color(0xFF_FF_FF), 0.024, 1.01, 15.9994, 0.12).lightAbsorption(0.1F).radiationAbsorption(0.08F);
    //	public static final ElementGas COPPER_GAS = gas("copper_gas", new Color(0xFF_FF_FF), 1, 0.386, 63.546, 0.1).lightAbsorption(0.5F).radiationAbsorption(0.06F);	// "Metal
    //	public static final ElementGas GOLD_GAS = gas("gold_gas", new Color(0xFF_FF_FF), 1, 0.1291, 196.966569, 0.1).lightAbsorption(0.5F).radiationAbsorption(0.03F);	// "Metal
    //	public static final ElementGas HELIUM = gas("helium", new Color(0xFF_FF_FF), 0.236, 0.14, 4, 0.1).lightAbsorption(0.1F).radiationAbsorption(0.09F);
    //	public static final ElementGas HYDROGEN = gas("hydrogen", new Color(0xFF_FF_FF), 0.168, 2.4, 1.00794, 0.1).lightAbsorption(0.1F).radiationAbsorption(0.09F);
    //	public static final ElementGas IRON_GAS = gas("iron_gas", new Color(0xFF_FF_FF), 1, 0.449, 55.845, 0.1).lightAbsorption(0.5F).radiationAbsorption(0.06F);	// "Metal
    //	public static final ElementGas COBALT_GAS = gas("cobalt_gas", new Color(0xFF_FF_FF), 1, 0.42, 58.9, 0.1).lightAbsorption(0.5F).radiationAbsorption(0.06F);	// "Metal
    //	public static final ElementGas LEAD_GAS = gas("lead_gas", new Color(0xFF_FF_FF), 3.5, 0.128, 196.966569, 0.1).lightAbsorption(0.5F).radiationAbsorption(0.08F);	// "Metal
    //	public static final ElementGas MERCURY_GAS = gas("mercury_gas", new Color(0xFF_FF_FF), 8.3, 0.14, 200.59, 0.1).lightAbsorption(0.5F).radiationAbsorption(0.02F);	// RefinedMetal
    //	public static final ElementGas METHANE = gas("methane", new Color(0xFF_FF_FF), 0.035, 2.191, 16.044, 0.1).lightAbsorption(0.25F).radiationAbsorption(0.07F);	// CombustibleGas
    //	public static final ElementGas NIOBIUM_GAS = gas("niobium_gas", new Color(0xFF_FF_FF), 1, 0.265, 92.9, 0.1).lightAbsorption(0.5F).radiationAbsorption(0.05F);	// "Metal
    //	public static final ElementGas FALLOUT = gas("fallout", new Color(0xFF_FF_FF), 1, 0.265, 92.9, 0.1).lightAbsorption(0.5F).radiation(15F).radiationAbsorption(0.03F);	// EmitsLight
    //	public static final ElementGas PHOSPHORUS_GAS = gas("phosphorus_gas", new Color(0xFF_FF_FF), 0.236, 0.7697, 30.973762, 0.1).lightAbsorption(0.5F).radiationAbsorption(0.07F); // EmitsLight
    //	public static final ElementGas PROPANE = gas("propane", new Color(0xFF_FF_FF), 0.015, 2.4, 44.1, 0.1).lightAbsorption(0.25F).radiationAbsorption(0.07F); // "HideFromSpawnTool
    //	public static final ElementGas SALT_GAS = gas("salt_gas", new Color(0xFF_FF_FF), 0.444, 0.88, 50, 0.1).lightAbsorption(0.1F).radiationAbsorption(0.07F);
    //	public static final ElementGas SOUR_GAS = gas("sour_gas", new Color(0xFF_FF_FF), 0.018, 1.898, 19.044, 0.1).lightAbsorption(0.25F).radiationAbsorption(0.05F);
    //	public static final ElementGas STEEL_GAS = gas("steel_gas", new Color(0xFF_FF_FF), 1, 0.49, 54.97, 0.1).lightAbsorption(0.5F).radiationAbsorption(0.07F); // "Metal
    //	public static final ElementGas SULFUR_GAS = gas("sulfur_gas", new Color(0xFF_FF_FF), 0.2, 0.7, 32, 0.1).lightAbsorption(0.1F).radiationAbsorption(0.07F);
    //	public static final ElementGas SUPER_COOLANT_GAS = gas("super_coolant_gas", new Color(0xFF_FF_FF), 1.2, 8.44, 190, 0.1).lightAbsorption(0.5F).radiationAbsorption(0.06F);
    //	public static final ElementGas TUNGSTEN_GAS = gas("tungsten_gas", new Color(0xFF_FF_FF), 1, 0.134, 183.84, 0.1).lightAbsorption(0.5F).radiationAbsorption(0.03F); // "Metal
    //	public static final ElementGas ETHANOL_GAS = gas("ethanol_gas", new Color(0xFF_FF_FF), 0.167, 2.148, 46.07, 0.1).lightAbsorption(0.5F).radiationAbsorption(0.07F);
    //	public static final ElementGas SYNGAS = gas("syngas", new Color(0xFF_FF_FF), 0.168, 2.4, 26.96, 0.1).lightAbsorption(0.1F).radiationAbsorption(0.07F); // CombustibleGas

    static {

        OXYLITE.gasEmittion(OXYGEN);

        // solid
        //		AEROGEL.highTemperature(10000F, ABYSSARITE);
        ALGAE.highTemperature(398.15F, DIRT);
        //		ALUMINUM.highTemperature(933.45F, MOLTEN_ALUMINUM);
        //		ALUMINUM_ORE.highTemperature(1357F, MOLTEN_ALUMINUM);
        //		BITUMEN.highTemperature(812F, CARBON_DIOXIDE);
        //		BLEACH_STONE.highTemperature(942F, CHLORINE);
        //		BRICK.highTemperature(1683F, MAGMA);
        //		BRINE_ICE.highTemperature(256.65F, BRINE);
        //		MILK_ICE.highTemperature(256.65F, MILK);
        //		CARBON.highTemperature(550F, REFINED_CARBON);
        //		CARBON_FIBRE.highTemperature(5000F, MOLTEN_CARBON);
        //		CEMENT.highTemperature(1683F, MAGMA);
        //		CEMENT_MIX.highTemperature(1683F, MAGMA);
        //		CERAMIC.highTemperature(2123F, MAGMA);
        //		CLAY.highTemperature(1200F, CERAMIC);
        // COPPER.highTemperature(1357F, MOLTEN_COPPER);
        CREATURE.highTemperature(10000F, CREATURE);
        //		CRUSHED_ICE.highTemperature(272.5F, WATER);
        //		CRUSHED_ROCK.highTemperature(1683F, MAGMA);
        //		CUPRITE.highTemperature(1357F, MOLTEN_COPPER);
        //		DEPLETED_URANIUM.highTemperature(406F, MOLTEN_URANIUM);
        //		DIAMOND.highTemperature(4200F, MOLTEN_CARBON);
        DIRT.highTemperature(600F, SAND);
        //		DIRTY_ICE.highTemperature(252.5F, DIRTY_WATER);
        //		ELECTRUM.highTemperature(1337F, MOLTEN_GOLD);
        //		ENRICHED_URANIUM.highTemperature(1132F, MOLTEN_URANIUM);
        FERTILIZER.highTemperature(398.15F, DIRT);
        //		FOOLS_GOLD.highTemperature(1357F, MOLTEN_IRON);
        //		FOSSIL.highTemperature(1612F, MAGMA);
        //		FULLERENE.highTemperature(4200F, MOLTEN_CARBON);
        GLASS.highTemperature(1700F, MOLTEN_GLASS);
        //		GOLD.highTemperature(1337F, MOLTEN_GOLD);
        //		GOLD_AMALGAM.highTemperature(1337F, MOLTEN_GOLD);
        GRANITE.highTemperature(942F, MAGMA);
        ICE.highTemperature(272.5F, WATER);
        IGNEOUS_ROCK.highTemperature(1683F, MAGMA);
        //		IRON.highTemperature(1808F, MOLTEN_IRON);
        //		IRON_ORE.highTemperature(1808F, MOLTEN_IRON);
        //		COBALT.highTemperature(1768F, MOLTEN_COBALT);
        //		COBALTITE.highTemperature(1768F, MOLTEN_COBALT);
        //		ISORESIN.highTemperature(473.15F, NAPHTHA);
        //		ABYSSARITE.highTemperature(3695F, MOLTEN_TUNGSTEN);
        //		LEAD.highTemperature(600.65F, MOLTEN_LEAD);
        //		LIME.highTemperature(1330F, MAGMA);
        //		MILK_FAT.highTemperature(433F, NAPHTHA);
        //		MAFIC_ROCK.highTemperature(1683F, MAGMA);
        //		NIOBIUM.highTemperature(2750F, MOLTEN_NIOBIUM);
        //		CORIUM.highTemperature(900F, FALLOUT);
        OBSIDIAN.highTemperature(3000F, MAGMA);
        OXYLITE.highTemperature(1683F, MAGMA);
        //		PHOSPHATE_NODULES.highTemperature(700F, LIQUID_PHOSPHORUS);
        //		PHOSPHORITE.highTemperature(517F, LIQUID_PHOSPHORUS);
        //		PHOSPHORUS.highTemperature(317.3F, LIQUID_PHOSPHORUS);
        //		HARD_POLYPROPYLENE.highTemperature(2100F, SOUR_GAS);
        //		POLYPROPYLENE.highTemperature(433F, NAPHTHA);
        //		RADIUM.highTemperature(1233F, MAGMA);
        //		REFINED_CARBON.highTemperature(4600F, MOLTEN_CARBON);
        //		REGOLITH.highTemperature(1683F, MAGMA);
        //		RUST.highTemperature(1808F, MOLTEN_IRON);
        //		SALT.highTemperature(1073F, MOLTEN_SALT);
        SAND.highTemperature(1986F, MOLTEN_GLASS);
        //		SAND_CEMENT.highTemperature(1683F, MAGMA);
        SANDSTONE.highTemperature(1200F, MAGMA);
        //		SEDIMENTARY_ROCK.highTemperature(1200F, MAGMA);
        //		SLABS.highTemperature(1683F, MAGMA);
        //		SLIME_MOLD.highTemperature(398.15F, DIRT);
        //		SNOW.highTemperature(272.5F, WATER);
        //		STABLE_SNOW.highTemperature(272.5F, WATER);
        //		SOLID_CARBON_DIOXIDE.highTemperature(216.6F, LIQUID_CARBON_DIOXIDE);
        //		SOLID_CHLORINE.highTemperature(172.17F, CHLORINE);
        //		SOLID_CRUDE_OIL.highTemperature(233F, CRUDE_OIL);
        //		SOLID_HYDROGEN.highTemperature(14F, LIQUID_HYDROGEN);
        //		SOLID_MERCURY.highTemperature(234.3F, MERCURY);
        //		SOLID_METHANE.highTemperature(90.55F, LIQUID_METHANE);
        //		SOLID_NAPHTHA.highTemperature(223F, NAPHTHA);
        //		SOLID_OXYGEN.highTemperature(54.36F, LIQUID_OXYGEN);
        //		SOLID_PETROLEUM.highTemperature(216F, PETROLEUM);
        //		SOLID_PROPANE.highTemperature(85F, LIQUID_PROPANE);
        //		SOLID_RESIN.highTemperature(293.15F, RESIN);
        //		SOLID_SUPER_COOLANT.highTemperature(2F, SUPER_COOLANT);
        //		SOLID_VISCO_GEL.highTemperature(242.5F, VISCO_GEL);
        //		STEEL.highTemperature(2700F, MOLTEN_STEEL);
        //		SULFUR.highTemperature(388.35F, LIQUID_SULFUR);
        //		SUPER_INSULATOR.highTemperature(3895F, MOLTEN_TUNGSTEN);
        //		TEMP_CONDUCTOR_SOLID.highTemperature(2950F, MOLTEN_NIOBIUM);
        //		TOXIC_SAND.highTemperature(1986F, MOLTEN_GLASS);
        //		TUNGSTEN.highTemperature(3695F, MOLTEN_TUNGSTEN);
        //		UNOBTANIUM.highTemperature(10000F, UNOBTANIUM);
        //		URANIUM_ORE.highTemperature(406F, MOLTEN_URANIUM);
        //		WOLFRAMITE.highTemperature(3200F, MOLTEN_TUNGSTEN);
        //		YELLOWCAKE.highTemperature(1132F, MOLTEN_URANIUM);
        //		SOLID_ETHANOL.highTemperature(159.1F, ETHANOL);
        //		SOLID_SYNGAS.highTemperature(14F, MOLTEN_SYNGAS);
        //		TOXIC_MUD.highTemperature(372.84F, STEAM);
        //		MUD.highTemperature(372.84F, STEAM);
        //		SUCROSE.highTemperature(459F, MOLTEN_SUCROSE);
        //		GRAPHITE.highTemperature(550F, REFINED_CARBON);
        //		SOLID_NUCLEAR_WASTE.highTemperature(300F, NUCLEAR_WASTE);
        //		CINNABAR.highTemperature(856.65F, MERCURY_GAS);
        //		WOOD_LOG.highTemperature(873.15F, CARBON_DIOXIDE);
        //		TALLOW.highTemperature(353.15F, CRUDE_OIL);

        // liquid
        //		BRINE.lowTemperature(250.65F, BRINE_ICE);
        //		BRINE.highTemperature(375.9F, STEAM).solute(0.3F, STEAM);
        //		CHLORINE.lowTemperature(172.17F, SOLID_CHLORINE);
        //		CHLORINE.highTemperature(238.55F, CHLORINE_GAS);
        //		CRUDE_OIL.lowTemperature(233F, SOLID_CRUDE_OIL);
        //		CRUDE_OIL.highTemperature(673F, PETROLEUM);
        //		DIRTY_WATER.lowTemperature(252.5F, DIRTY_ICE);
        //		DIRTY_WATER.highTemperature(392.5F, STEAM).solute(0.01F, STEAM);
        //		LIQUID_CARBON_DIOXIDE.lowTemperature(216.6F, SOLID_CARBON_DIOXIDE);
        //		LIQUID_CARBON_DIOXIDE.highTemperature(225F, CARBON_DIOXIDE);
        //		LIQUID_HELIUM.highTemperature(4.22F, HELIUM);
        //		LIQUID_HYDROGEN.lowTemperature(14F, SOLID_HYDROGEN);
        //		LIQUID_HYDROGEN.highTemperature(21F, HYDROGEN);
        //		LIQUID_METHANE.lowTemperature(90.55F, SOLID_METHANE);
        //		LIQUID_METHANE.highTemperature(111.65F, METHANE);
        //		LIQUID_OXYGEN.lowTemperature(54.36F, SOLID_OXYGEN);
        //		LIQUID_OXYGEN.highTemperature(90.19F, OXYGEN);
        //		LIQUID_PHOSPHORUS.lowTemperature(317.3F, PHOSPHORUS);
        //		LIQUID_PHOSPHORUS.highTemperature(553.6F, PHOSPHORUS_GAS);
        //		LIQUID_PROPANE.lowTemperature(85F, SOLID_PROPANE);
        //		LIQUID_PROPANE.highTemperature(231F, PROPANE);
        //		LIQUID_SULFUR.lowTemperature(388.35F, SULFUR);
        //		LIQUID_SULFUR.highTemperature(610.15F, SULFUR_GAS);
        //		MILK.lowTemperature(256.65F, MILK_ICE);
        //		MILK.highTemperature(353.15F, BRINE).solute(0.1F, BRINE);
        MAGMA.lowTemperature(1683F, IGNEOUS_ROCK);
        MAGMA.highTemperature(2630F, ROCK_GAS);
        //		MERCURY.lowTemperature(234.3F, SOLID_MERCURY);
        //		MERCURY.highTemperature(629.9F, MERCURY_GAS);
        //		MOLTEN_ALUMINUM.lowTemperature(933.45F, ALUMINUM);
        //		MOLTEN_ALUMINUM.highTemperature(2743.15F, ALUMINUM_GAS);
        //		MOLTEN_CARBON.lowTemperature(3825F, REFINED_CARBON);
        //		MOLTEN_CARBON.highTemperature(5100F, CARBON_GAS);
        // MOLTEN_COPPER.lowTemperature(1357F, COPPER);
        //		MOLTEN_COPPER.highTemperature(2834F, COPPER_GAS);
        MOLTEN_GLASS.lowTemperature(1400F, GLASS);
        MOLTEN_GLASS.highTemperature(2630F, ROCK_GAS);
        //		MOLTEN_GOLD.lowTemperature(1337F, GOLD);
        //		MOLTEN_GOLD.highTemperature(3129F, GOLD_GAS);
        //		MOLTEN_IRON.lowTemperature(1808F, IRON);
        //		MOLTEN_IRON.highTemperature(3023F, IRON_GAS);
        //		MOLTEN_COBALT.lowTemperature(1768F, COBALT);
        //		MOLTEN_COBALT.highTemperature(3200F, COBALT_GAS);
        //		MOLTEN_LEAD.lowTemperature(600.65F, LEAD);
        //		MOLTEN_LEAD.highTemperature(2022.15F, LEAD_GAS);
        //		MOLTEN_NIOBIUM.lowTemperature(2750F, NIOBIUM);
        //		MOLTEN_NIOBIUM.highTemperature(5017F, NIOBIUM_GAS);
        //		MOLTEN_SALT.lowTemperature(1073F, SALT);
        //		MOLTEN_SALT.highTemperature(1738F, SALT_GAS);
        //		MOLTEN_STEEL.lowTemperature(1357F, STEEL);
        //		MOLTEN_STEEL.highTemperature(4100F, STEEL_GAS);
        //		MOLTEN_TUNGSTEN.lowTemperature(3695F, TUNGSTEN);
        //		MOLTEN_TUNGSTEN.highTemperature(6203F, TUNGSTEN_GAS);
        //		MOLTEN_URANIUM.lowTemperature(406F, DEPLETED_URANIUM);
        //		MOLTEN_URANIUM.highTemperature(4405F, ROCK_GAS);
        //		NAPHTHA.lowTemperature(223F, SOLID_NAPHTHA);
        //		NAPHTHA.highTemperature(812F, SOUR_GAS);
        //		NUCLEAR_WASTE.lowTemperature(300F, SOLID_NUCLEAR_WASTE);
        //		NUCLEAR_WASTE.highTemperature(800F, FALLOUT);
        //		PETROLEUM.lowTemperature(216F, SOLID_PETROLEUM);
        //		PETROLEUM.highTemperature(812F, SOUR_GAS);
        //		RESIN.lowTemperature(293.15F, SOLID_RESIN);
        //		RESIN.highTemperature(398.15F, STEAM).solute(0.25F, STEAM);
        //		SALT_WATER.lowTemperature(265.65F, BRINE);
        //		SALT_WATER.highTemperature(372.84F, STEAM);
        //		SALT_WATER.solute(0.07F, STEAM);
        //		SUGAR_WATER.lowTemperature(190.65F, ICE);
        //		SUGAR_WATER.highTemperature(433.15F, STEAM).solute(0.77F, STEAM);
        //		SUPER_COOLANT.lowTemperature(2F, SOLID_SUPER_COOLANT);
        //		SUPER_COOLANT.highTemperature(710F, SUPER_COOLANT_GAS);
        //		VISCO_GEL.lowTemperature(242.5F, SOLID_VISCO_GEL);
        //		VISCO_GEL.highTemperature(753F, NAPHTHA);
        WATER.lowTemperature(272.5F, ICE);
        WATER.highTemperature(372.5F, STEAM);
        //		ETHANOL.lowTemperature(159.1F, SOLID_ETHANOL);
        //		ETHANOL.highTemperature(351.5F, ETHANOL_GAS);
        //		MOLTEN_SYNGAS.lowTemperature(14F, SOLID_SYNGAS);
        //		MOLTEN_SYNGAS.highTemperature(21F, SYNGAS);
        //		MOLTEN_SUCROSE.lowTemperature(459F, SUCROSE);
        //		MOLTEN_SUCROSE.highTemperature(503.15F, CARBON_DIOXIDE);

        // gas
        //		ALUMINUM_GAS.lowTemperature(2743.15F, MOLTEN_ALUMINUM);
        //		CARBON_DIOXIDE.lowTemperature(225F, LIQUID_CARBON_DIOXIDE);
        //		CARBON_GAS.lowTemperature(5100F, MOLTEN_CARBON);
        //		CHLORINE_GAS.lowTemperature(238.55F, CHLORINE);
        //		CONTAMINATED_OXYGEN.lowTemperature(90.19F, LIQUID_OXYGEN);
        //		COPPER_GAS.lowTemperature(2834F, MOLTEN_COPPER);
        //		GOLD_GAS.lowTemperature(3129F, MOLTEN_GOLD);
        //		HELIUM.lowTemperature(4.22F, LIQUID_HELIUM);
        //		HYDROGEN.lowTemperature(21F, LIQUID_HYDROGEN);
        //		IRON_GAS.lowTemperature(3023F, MOLTEN_IRON);
        //		COBALT_GAS.lowTemperature(3200F, MOLTEN_COBALT);
        //		LEAD_GAS.lowTemperature(2022.15F, MOLTEN_LEAD);
        //		MERCURY_GAS.lowTemperature(629.9F, MERCURY);
        //		METHANE.lowTemperature(111.65F, LIQUID_METHANE);
        //		NIOBIUM_GAS.lowTemperature(5017F, MOLTEN_NIOBIUM);
        //		FALLOUT.lowTemperature(340F, NUCLEAR_WASTE);
        //		OXYGEN.lowTemperature(90.19F, LIQUID_OXYGEN);
        //		PHOSPHORUS_GAS.lowTemperature(553.6F, LIQUID_PHOSPHORUS);
        //		PROPANE.lowTemperature(231F, LIQUID_PROPANE);
        ROCK_GAS.lowTemperature(2630F, MAGMA);
        //		SALT_GAS.lowTemperature(1738F, MOLTEN_SALT);
        //		SOUR_GAS.lowTemperature(111.65F, LIQUID_METHANE);
        STEAM.lowTemperature(372.5F, WATER);
        //		STEEL_GAS.lowTemperature(4100F, MOLTEN_STEEL);
        //		SULFUR_GAS.lowTemperature(610.15F, LIQUID_SULFUR);
        //		SUPER_COOLANT_GAS.lowTemperature(710F, SUPER_COOLANT);
        //		TUNGSTEN_GAS.lowTemperature(6203F, MOLTEN_TUNGSTEN);
        //		ETHANOL_GAS.lowTemperature(351.5F, ETHANOL);
        //		SYNGAS.lowTemperature(21F, MOLTEN_SYNGAS);

    }

    private static ElementSolid solid(String name, Color elementColor, double thermalConductivity, double specificHeatCapacity, EnumHardness hardness) {
        return new ElementSolid(name, elementColor, (float) thermalConductivity, (float) specificHeatCapacity, hardness);
    }

    private static ElementLiquid liquid(String name, Color elementColor, double thermalConductivity, double specificHeatCapacity, double molarMass, double viscocity, double minVerticalFlow,
                                        double minHorizontalFlow, double maxMass) {
        return new ElementLiquid(name, elementColor, (float) thermalConductivity, (float) specificHeatCapacity, (float) molarMass, (float) viscocity, (float) minVerticalFlow,
                (float) minHorizontalFlow, (float) maxMass);
    }

    private static ElementGas gas(String name, Color elementColor, double thermalConductivity, double specificHeatCapacity, double molarMass, double flowRate) {
        return new ElementGas(name, elementColor, (float) thermalConductivity, (float) specificHeatCapacity, (float) molarMass, (float) flowRate);
    }
}
