package kpan.not_enough_oxygen.world;

import java.util.Random;

public class PerlinNoiseGenerator {
    /**
     * An int[512], where the first 256 elements are the numbers 0..255, in random shuffled order,
     * and the second half of the array is identical to the first half, apparently for convenience in wrapping lookups.
     * <p>
     * Effectively a shuffled 0..255 that wraps once.
     */
    private final int[] permutations;
    public float xCoord;
    public float yCoord;
    public float zCoord;
    private static final float[] GRAD_X = new float[]{1.0F, -1.0F, 1.0F, -1.0F, 1.0F, -1.0F, 1.0F, -1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, -1.0F, 0.0F};
    private static final float[] GRAD_Y = new float[]{1.0F, 1.0F, -1.0F, -1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, -1.0F, 1.0F, -1.0F, 1.0F, -1.0F, 1.0F, -1.0F};
    private static final float[] GRAD_Z = new float[]{0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, -1.0F, -1.0F, 1.0F, 1.0F, -1.0F, -1.0F, 0.0F, 1.0F, 0.0F, -1.0F};
    private static final float[] GRAD_2X = new float[]{1.0F, -1.0F, 1.0F, -1.0F, 1.0F, -1.0F, 1.0F, -1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, -1.0F, 0.0F};
    private static final float[] GRAD_2Z = new float[]{0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, -1.0F, -1.0F, 1.0F, 1.0F, -1.0F, -1.0F, 0.0F, 1.0F, 0.0F, -1.0F};

    public PerlinNoiseGenerator(Random rand) {
        permutations = new int[512];
        xCoord = rand.nextFloat() * 256.0F;
        yCoord = rand.nextFloat() * 256.0F;
        zCoord = rand.nextFloat() * 256.0F;

        for (int l = 0; l < 256; ++l) {
            int j = rand.nextInt(256 - l) + l;
            int k = permutations[l];
            permutations[l] = permutations[j];
            permutations[j] = k;
            permutations[l + 256] = permutations[l];
        }
    }

    public final float lerp(float p_76311_1_, float p_76311_3_, float p_76311_5_) {
        return p_76311_3_ + p_76311_1_ * (p_76311_5_ - p_76311_3_);
    }

    public final float grad2(int p_76309_1_, float p_76309_2_, float p_76309_4_) {
        int i = p_76309_1_ & 15;
        return GRAD_2X[i] * p_76309_2_ + GRAD_2Z[i] * p_76309_4_;
    }

    public final float grad(int p_76310_1_, float p_76310_2_, float p_76310_4_, float p_76310_6_) {
        int i = p_76310_1_ & 15;
        return GRAD_X[i] * p_76310_2_ + GRAD_Y[i] * p_76310_4_ + GRAD_Z[i] * p_76310_6_;
    }

    /**
     * noiseArray should be xSize*ySize*zSize in size
     */
    public void populateNoiseArray(float[] noiseArray, float xOffset, float yOffset, float zOffset, int xSize, int ySize, int zSize, float xScale, float yScale, float zScale, float noiseScale) {
        if (ySize == 1) {
            int i5 = 0;
            int j5 = 0;
            int j = 0;
            int k5 = 0;
            float d14 = 0.0F;
            float d15 = 0.0F;
            int l5 = 0;
            float d16 = 1.0F / noiseScale;

            for (int j2 = 0; j2 < xSize; ++j2) {
                float d17 = xOffset + (float) j2 * xScale + xCoord;
                int i6 = (int) d17;

                if (d17 < (float) i6) {
                    --i6;
                }

                int k2 = i6 & 255;
                d17 = d17 - (float) i6;
                float d18 = d17 * d17 * d17 * (d17 * (d17 * 6.0F - 15.0F) + 10.0F);

                for (int j6 = 0; j6 < zSize; ++j6) {
                    float d19 = zOffset + (float) j6 * zScale + zCoord;
                    int k6 = (int) d19;

                    if (d19 < (float) k6) {
                        --k6;
                    }

                    int l6 = k6 & 255;
                    d19 = d19 - (float) k6;
                    float d20 = d19 * d19 * d19 * (d19 * (d19 * 6.0F - 15.0F) + 10.0F);
                    i5 = permutations[k2] + 0;
                    j5 = permutations[i5] + l6;
                    j = permutations[k2 + 1] + 0;
                    k5 = permutations[j] + l6;
                    d14 = lerp(d18, grad2(permutations[j5], d17, d19), grad(permutations[k5], d17 - 1.0F, 0.0F, d19));
                    d15 = lerp(d18, grad(permutations[j5 + 1], d17, 0.0F, d19 - 1.0F), grad(permutations[k5 + 1], d17 - 1.0F, 0.0F, d19 - 1.0F));
                    float d21 = lerp(d20, d14, d15);
                    int i7 = l5++;
                    noiseArray[i7] += d21 * d16;
                }
            }
        } else {
            int i = 0;
            float d0 = 1.0F / noiseScale;
            int k = -1;
            int l = 0;
            int i1 = 0;
            int j1 = 0;
            int k1 = 0;
            int l1 = 0;
            int i2 = 0;
            float d1 = 0.0F;
            float d2 = 0.0F;
            float d3 = 0.0F;
            float d4 = 0.0F;

            for (int l2 = 0; l2 < xSize; ++l2) {
                float d5 = xOffset + (float) l2 * xScale + xCoord;
                int i3 = (int) d5;

                if (d5 < (float) i3) {
                    --i3;
                }

                int j3 = i3 & 255;
                d5 = d5 - (float) i3;
                float d6 = d5 * d5 * d5 * (d5 * (d5 * 6.0F - 15.0F) + 10.0F);

                for (int k3 = 0; k3 < zSize; ++k3) {
                    float d7 = zOffset + (float) k3 * zScale + zCoord;
                    int l3 = (int) d7;

                    if (d7 < (float) l3) {
                        --l3;
                    }

                    int i4 = l3 & 255;
                    d7 = d7 - (float) l3;
                    float d8 = d7 * d7 * d7 * (d7 * (d7 * 6.0F - 15.0F) + 10.0F);

                    for (int j4 = 0; j4 < ySize; ++j4) {
                        float d9 = yOffset + (float) j4 * yScale + yCoord;
                        int k4 = (int) d9;

                        if (d9 < (float) k4) {
                            --k4;
                        }

                        int l4 = k4 & 255;
                        d9 = d9 - (float) k4;
                        float d10 = d9 * d9 * d9 * (d9 * (d9 * 6.0F - 15.0F) + 10.0F);

                        if (j4 == 0 || l4 != k) {
                            k = l4;
                            l = permutations[j3] + l4;
                            i1 = permutations[l] + i4;
                            j1 = permutations[l + 1] + i4;
                            k1 = permutations[j3 + 1] + l4;
                            l1 = permutations[k1] + i4;
                            i2 = permutations[k1 + 1] + i4;
                            d1 = lerp(d6, grad(permutations[i1], d5, d9, d7), grad(permutations[l1], d5 - 1.0F, d9, d7));
                            d2 = lerp(d6, grad(permutations[j1], d5, d9 - 1.0F, d7), grad(permutations[i2], d5 - 1.0F, d9 - 1.0F, d7));
                            d3 = lerp(d6, grad(permutations[i1 + 1], d5, d9, d7 - 1.0F), grad(permutations[l1 + 1], d5 - 1.0F, d9, d7 - 1.0F));
                            d4 = lerp(d6, grad(permutations[j1 + 1], d5, d9 - 1.0F, d7 - 1.0F), grad(permutations[i2 + 1], d5 - 1.0F, d9 - 1.0F, d7 - 1.0F));
                        }

                        float d11 = lerp(d10, d1, d2);
                        float d12 = lerp(d10, d3, d4);
                        float d13 = lerp(d8, d11, d12);
                        int j7 = i++;
                        noiseArray[j7] += d13 * d0;
                    }
                }
            }
        }
    }
}