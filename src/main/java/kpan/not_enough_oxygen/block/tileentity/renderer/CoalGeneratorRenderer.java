package kpan.not_enough_oxygen.block.tileentity.renderer;

import kpan.not_enough_oxygen.ModTagsGenerated;
import kpan.not_enough_oxygen.block.tileentity.TileEntityCoalGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

@SideOnly(Side.CLIENT)
public class CoalGeneratorRenderer extends GeoBlockRenderer<TileEntityCoalGenerator> {
    public CoalGeneratorRenderer() {
        super(new Model());
    }

    private static class Model extends AnimatedGeoModel<TileEntityCoalGenerator> {
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

}
