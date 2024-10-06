package kpan.not_enough_oxygen.block.item.renderer;

import kpan.not_enough_oxygen.ModTagsGenerated;
import kpan.not_enough_oxygen.block.item.ItemBlockAnimating;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class ItemCoalGeneratorRenderer extends GeoItemRenderer<ItemBlockAnimating> {
    public ItemCoalGeneratorRenderer() {
        super(new Model());
    }

    private static class Model extends AnimatedGeoModel<ItemBlockAnimating> {
        @Override
        public ResourceLocation getModelLocation(ItemBlockAnimating item) {
            return new ResourceLocation(ModTagsGenerated.MODID, "geo/coal_generator.geo.json");
        }
        @Override
        public ResourceLocation getTextureLocation(ItemBlockAnimating item) {
            return new ResourceLocation(ModTagsGenerated.MODID, "textures/blocks/geckolib/coal_generator.png");
        }
        @Override
        public ResourceLocation getAnimationFileLocation(ItemBlockAnimating item) {
            return new ResourceLocation(ModTagsGenerated.MODID, "animations/coal_generator.animation.json");
        }
    }
}
