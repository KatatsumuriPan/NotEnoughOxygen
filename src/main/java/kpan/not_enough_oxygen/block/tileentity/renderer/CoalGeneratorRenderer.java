package kpan.not_enough_oxygen.block.tileentity.renderer;

import kpan.not_enough_oxygen.block.tileentity.TileEntityCoalGenerator;
import kpan.not_enough_oxygen.geckolib.TestAnimatedGeoModel;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

@SideOnly(Side.CLIENT)
public class CoalGeneratorRenderer extends GeoBlockRenderer<TileEntityCoalGenerator> {
    public CoalGeneratorRenderer() {
        super(new TestAnimatedGeoModel());
    }

}
