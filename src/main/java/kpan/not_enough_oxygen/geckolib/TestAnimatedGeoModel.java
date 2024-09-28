package kpan.not_enough_oxygen.geckolib;

import kpan.not_enough_oxygen.ModTagsGenerated;
import kpan.not_enough_oxygen.block.tileentity.TileEntityCoalGenerator;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class TestAnimatedGeoModel extends AnimatedGeoModel<TileEntityCoalGenerator> {
    @Override
    public ResourceLocation getModelLocation(TileEntityCoalGenerator tileentity) {
        return new ResourceLocation(ModTagsGenerated.MODID, "geo/coal_generator.geo.json");
    }
    @Override
    public ResourceLocation getTextureLocation(TileEntityCoalGenerator tileentity) {
        return new ResourceLocation(ModTagsGenerated.MODID, "textures/blocks/geckolib/coal_generator.png");
    }
    @Override
    public ResourceLocation getAnimationFileLocation(TileEntityCoalGenerator tileentity) {
        return new ResourceLocation(ModTagsGenerated.MODID, "animations/coal_generator.animation.json");
    }
}
