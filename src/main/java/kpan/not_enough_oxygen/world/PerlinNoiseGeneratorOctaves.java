package kpan.not_enough_oxygen.world;

import java.util.Arrays;
import java.util.Random;
import net.minecraft.util.math.MathHelper;

public class PerlinNoiseGeneratorOctaves {
    /**
     * Collection of noise generation functions.  Output is combined to produce different octaves of noise.
     */
    private final PerlinNoiseGenerator[] generatorCollection;
    private final int octaves;

    public PerlinNoiseGeneratorOctaves(Random rand, int octavesIn) {
        octaves = octavesIn;
        generatorCollection = new PerlinNoiseGenerator[octavesIn];

        for (int i = 0; i < octavesIn; ++i) {
            generatorCollection[i] = new PerlinNoiseGenerator(rand);
        }
    }

    /**
     * pars:(par2,3,4=noiseOffset ; so that adjacent noise segments connect) (pars5,6,7=x,y,zArraySize),(pars8,10,12 =
     * x,y,z noiseScale)
     */
    public float[] generateNoiseOctaves(float[] noiseArray, int xOffset, int yOffset, int zOffset, int xSize, int ySize, int zSize, float xScale, float yScale, float zScale) {
        if (noiseArray == null) {
            noiseArray = new float[xSize * ySize * zSize];
        } else {
            Arrays.fill(noiseArray, 0.0F);
        }

        float d3 = 1.0F;

        for (int j = 0; j < octaves; ++j) {
            float d0 = (float) xOffset * d3 * xScale;
            float d1 = (float) yOffset * d3 * yScale;
            float d2 = (float) zOffset * d3 * zScale;
            long k = MathHelper.lfloor(d0);
            long l = MathHelper.lfloor(d2);
            d0 = d0 - (float) k;
            d2 = d2 - (float) l;
            k = k % 0x100_0000L;
            l = l % 0x100_0000L;
            d0 = d0 + (float) k;
            d2 = d2 + (float) l;
            generatorCollection[j].populateNoiseArray(noiseArray, d0, d1, d2, xSize, ySize, zSize, xScale * d3, yScale * d3, zScale * d3, d3);
            d3 /= 2.0F;
        }

        return noiseArray;
    }

}