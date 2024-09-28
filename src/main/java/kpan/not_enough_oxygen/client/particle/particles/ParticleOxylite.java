package kpan.not_enough_oxygen.client.particle.particles;

import net.minecraft.client.particle.Particle;
import net.minecraft.world.World;

public class ParticleOxylite extends Particle {

    public ParticleOxylite(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
        this.particleRed = 1.0F;
        this.particleGreen = 1.0F;
        this.particleBlue = 1.0F;
        this.setParticleTextureIndex(32);
        this.setSize(0.02F, 0.02F);
        this.particleScale *= this.rand.nextFloat() * 0.5F + 0.2F;
        this.motionX = xSpeedIn * 0.2 + (Math.random() * 2.0D - 1.0D) * 0.05;
        this.motionY = ySpeedIn * 0.2 + (Math.random() * 2.0D - 1.0D) * 0.05;
        this.motionZ = zSpeedIn * 0.2 + (Math.random() * 2.0D - 1.0D) * 0.05;
        this.particleMaxAge = (int) (8 + Math.random() * 8);
        canCollide = false;
    }

    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY += 0.03;
        this.move(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.85;
        this.motionY *= 0.85;
        this.motionZ *= 0.85;

        if (this.particleMaxAge-- <= 0) {
            this.setExpired();
        }
    }

}
