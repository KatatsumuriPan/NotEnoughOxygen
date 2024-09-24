package kpan.not_enough_oxygen.world;

import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class NEOWorldRegisterer {
    public static final String DIMENSION_NAME = "neoxygen";
    public static final int DIMENSION_ID = findFreeDimensionID();
    public static final DimensionType DIMENSION_TYPE = DimensionType.register(DIMENSION_NAME, "_" + DIMENSION_NAME, DIMENSION_ID, NEOWorldProvider.class, true);
    @SuppressWarnings("unused")
    public static final NEOWorldType NEO_WORLD_TYPE = new NEOWorldType();

    public static void registerDimensions() {
        DimensionManager.registerDimension(DIMENSION_TYPE.getId(), DIMENSION_TYPE);
    }

    private static int findFreeDimensionID() {
        for (int i = 91; i < Integer.MAX_VALUE; i++) {
            if (!DimensionManager.isDimensionRegistered(i)) {
                return i;
            }
        }
        throw new IllegalStateException("ERROR: Could not find free dimension ID");
    }

}
