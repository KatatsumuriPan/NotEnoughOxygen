package kpan.not_enough_oxygen.client.particle.emitter_block;

import kpan.not_enough_oxygen.client.ClientTickHandler;
import kpan.not_enough_oxygen.client.particle.particles.ParticleOxylite;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class OxyliteParticleEmitter extends BlockParticleEmitter {

    private final BlockPos pos;
    public OxyliteParticleEmitter(BlockPos pos) {
        this.pos = pos;
    }

    @Override
    public boolean tick() {
        if (ClientTickHandler.tick % 25 >= 5)
            return true;
        WorldClient world = Minecraft.getMinecraft().world;
        for (EnumFacing face : EnumFacing.VALUES) {
            if (canEmit(world, face)) {
                Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleOxylite(world,
                        pos.getX() + 0.5 + 0.6 * face.getXOffset() + (Math.random() - 0.5) * (1 - Math.abs(face.getXOffset())),
                        pos.getY() + 0.5 + 0.6 * face.getYOffset() + (Math.random() - 0.5) * (1 - Math.abs(face.getYOffset())),
                        pos.getZ() + 0.5 + 0.6 * face.getZOffset() + (Math.random() - 0.5) * (1 - Math.abs(face.getZOffset())), face.getXOffset() * 0.5, face.getYOffset() * 0.5 - 0.5, face.getZOffset() * 0.5));
            }
        }
        return true;
    }

    private boolean canEmit(World world, EnumFacing face) {
        IBlockState state = world.getBlockState(pos.offset(face));
        return state.getBlock().isAir(state, world, pos);
    }
}
