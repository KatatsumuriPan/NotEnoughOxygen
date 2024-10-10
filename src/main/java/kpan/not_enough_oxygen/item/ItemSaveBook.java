package kpan.not_enough_oxygen.item;

import java.util.Random;
import kpan.not_enough_oxygen.block.BlockElementSolid;
import kpan.not_enough_oxygen.neo_world.Elements;
import kpan.not_enough_oxygen.world.NEOChunkGenerator;
import kpan.not_enough_oxygen.world.NEOWorldProvider;
import kpan.not_enough_oxygen.world.NEOWorldRegisterer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.ITeleporter;

public class ItemSaveBook extends ItemBase {
    public ItemSaveBook() {
        super("save_book", CreativeTabs.MISC);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        goToNEOWorld(playerIn);
        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }


    public static void goToNEOWorld(EntityPlayer player) {
        if (player.world.isRemote)
            return;
        MinecraftServer server = player.getServer();
        Generator.generateWorld(server);
        server.getPlayerList().transferPlayerToDimension((EntityPlayerMP) player, NEOWorldRegisterer.DIMENSION_ID, new ITeleporter() {
            @Override
            public void placeEntity(World world, Entity entity, float yaw) {
                if (entity instanceof EntityPlayer player) {
                    BlockPos spawnPos = NEOWorldProvider.getSpawnPoint1();
                    entity.setLocationAndAngles(spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5, entity.rotationYaw, 0.0F);
                    entity.motionX = 0.0D;
                    entity.motionY = 0.0D;
                    entity.motionZ = 0.0D;
                    player.inventory.clear();
                } else {
                    entity.setDead();
                }
            }
        });
    }


    private static class Generator {

        private static MutableBlockPos mutableBlockPos;
        private static Random random;

        public static void generateWorld(MinecraftServer server) {
            WorldServer world = server.getWorld(NEOWorldRegisterer.DIMENSION_ID);
            if (world.isChunkGeneratedAt(0, 0))
                return;
            ((NEOChunkGenerator) world.getChunkProvider().chunkGenerator).generateBiome();
            for (int cx = 0; cx < NEOWorldProvider.WORLD_SIZE; cx++) {
                for (int cz = 0; cz < NEOWorldProvider.WORLD_SIZE; cz++) {
                    world.getChunk(cx, cz);
                }
            }
            mutableBlockPos = new MutableBlockPos();
            random = new Random(world.getSeed());

            SpawnBaseGenerator.generateSpawnBase(world);

            mutableBlockPos = null;

            for (int cx = 0; cx < NEOWorldProvider.WORLD_SIZE; cx++) {
                for (int cz = 0; cz < NEOWorldProvider.WORLD_SIZE; cz++) {
                    world.getChunk(cx, cz).markDirty();
                }
            }
        }

        private static boolean placeBlock(World world, int x, int y, int z, IBlockState state) {
            if (!NEOChunkGenerator.isInGame(x, y, z))
                return false;
            world.setBlockState(mutableBlockPos.setPos(x, y, z), state, 0);
            return true;
        }

        private static class SpawnBaseGenerator {

            private static void generateSpawnBase(WorldServer world) {
                BlockPos spawnPoint = NEOWorldProvider.getSpawnPoint1();
                int spawnX = spawnPoint.getX();
                int spawnY = spawnPoint.getY();
                int spawnZ = spawnPoint.getZ();

                // 床
                for (int x = -6; x <= 6; x++) {
                    for (int z = -6; z <= 6; z++) {
                        if (Math.max(Math.abs(x), Math.abs(z)) <= 3)
                            placeBlock(world, spawnX + x, spawnY - 1, spawnZ + z, Blocks.STONEBRICK.getDefaultState());
                        else
                            placeBlock(world, spawnX + x, spawnY - 1, spawnZ + z, Blocks.STONE.getDefaultState());
                    }
                }
                // 高さ0~2
                for (int x = -6; x <= 6; x++) {
                    for (int z = -6; z <= 6; z++) {
                        for (int y = 0; y <= 2; y++) {
                            if (Math.max(Math.abs(x), Math.abs(z)) <= 3)
                                placeBlock(world, spawnX + x, spawnY + y, spawnZ + z, Blocks.AIR.getDefaultState());
                            else
                                placeBlock(world, spawnX + x, spawnY + y, spawnZ + z, Blocks.STONE.getDefaultState());
                        }
                    }
                }
                // 高さ3
                for (int x = -5; x <= 5; x++) {
                    for (int z = -5; z <= 5; z++) {
                        if (Math.max(Math.abs(x), Math.abs(z)) <= 2)
                            placeBlock(world, spawnX + x, spawnY + 3, spawnZ + z, Blocks.AIR.getDefaultState());
                        else
                            placeBlock(world, spawnX + x, spawnY + 3, spawnZ + z, Blocks.STONE.getDefaultState());
                    }
                }
                // 高さ4~5
                for (int x = -4; x <= 4; x++) {
                    for (int z = -4; z <= 4; z++) {
                        placeBlock(world, spawnX + x, spawnY + 4, spawnZ + z, Blocks.STONE.getDefaultState());
                        if (Math.max(Math.abs(x), Math.abs(z)) <= 2)
                            placeBlock(world, spawnX + x, spawnY + 5, spawnZ + z, Blocks.STONE.getDefaultState());
                    }
                }

                // 石の置換
                placeBlockNearSpawn(world, Blocks.DIRT.getDefaultState(), spawnX, spawnY, spawnZ, 1.2, 3, random);
                placeBlockNearSpawn(world, Blocks.DIRT.getDefaultState(), spawnX, spawnY, spawnZ, 1.5, 5, random);
                placeBlockNearSpawn(world, Blocks.DIRT.getDefaultState(), spawnX, spawnY, spawnZ, 2, 3, random);
                placeBlockNearSpawn(world, Blocks.WOOL.getStateFromMeta(13), spawnX, spawnY, spawnZ, 1.2, 3, random);
                placeBlockNearSpawn(world, Blocks.WOOL.getStateFromMeta(13), spawnX, spawnY, spawnZ, 1.2, 3, random);
                placeBlockNearSpawn(world, Blocks.IRON_ORE.getDefaultState(), spawnX, spawnY, spawnZ, 1, 2.5, random);

                // オキシライト
                IBlockState oxylite = BlockElementSolid.get(Elements.OXYLITE).getDefaultState();
                placeBlock(world, spawnX - 1, spawnY + 3, spawnZ - 3, oxylite);
                placeBlock(world, spawnX - 0, spawnY + 3, spawnZ - 3, oxylite);
                placeBlock(world, spawnX - 2, spawnY + 4, spawnZ - 3, oxylite);
                placeBlock(world, spawnX - 1, spawnY + 4, spawnZ - 3, oxylite);
                placeBlock(world, spawnX - 0, spawnY + 4, spawnZ - 3, oxylite);
                placeBlock(world, spawnX - 2, spawnY + 4, spawnZ - 4, oxylite);
                placeBlock(world, spawnX - 1, spawnY + 4, spawnZ - 4, oxylite);
                placeBlock(world, spawnX - 0, spawnY + 4, spawnZ - 4, oxylite);
                placeBlock(world, spawnX - 1, spawnY + 5, spawnZ - 3, oxylite);
                placeBlock(world, spawnX - 0, spawnY + 5, spawnZ - 3, oxylite);

                // ゲート
                placeBlock(world, spawnX - 2, spawnY + 0, spawnZ, Blocks.QUARTZ_BLOCK.getDefaultState());
                placeBlock(world, spawnX + 2, spawnY + 0, spawnZ, Blocks.QUARTZ_BLOCK.getDefaultState());
                placeBlock(world, spawnX - 2, spawnY + 1, spawnZ, Blocks.QUARTZ_BLOCK.getDefaultState());
                placeBlock(world, spawnX + 2, spawnY + 1, spawnZ, Blocks.QUARTZ_BLOCK.getDefaultState());
                placeBlock(world, spawnX - 2, spawnY + 2, spawnZ, Blocks.QUARTZ_BLOCK.getDefaultState());
                placeBlock(world, spawnX + 2, spawnY + 2, spawnZ, Blocks.QUARTZ_BLOCK.getDefaultState());
                placeBlock(world, spawnX - 1, spawnY + 3, spawnZ, Blocks.QUARTZ_BLOCK.getDefaultState());
                placeBlock(world, spawnX + 1, spawnY + 3, spawnZ, Blocks.QUARTZ_BLOCK.getDefaultState());
                placeBlock(world, spawnX, spawnY + 3, spawnZ, Blocks.GLOWSTONE.getDefaultState());

                // チェスト
                placeBlock(world, spawnX - 2, spawnY, spawnZ + 1, Blocks.CHEST.getDefaultState());
                placeBlock(world, spawnX - 3, spawnY, spawnZ + 1, Blocks.CHEST.getDefaultState());
                if (world.getTileEntity(new BlockPos(spawnX - 3, spawnY, spawnZ + 1)) instanceof TileEntityChest tileentity) {
                    tileentity.setInventorySlotContents(0, new ItemStack(Items.BREAD, 10));
                }
            }

            private static void placeBlockNearSpawn(World world, IBlockState state, int spawnX, int spawnY, int spawnZ, double rMin, double rMax, Random random) {
                double x = spawnX + random.nextDouble() * 10 - 5;
                double y = spawnY + random.nextDouble() * 10 - 5;
                double z = spawnZ + random.nextDouble() * 10 - 5;
                double r = random.nextDouble() * (rMax - rMin) + rMin;
                for (int ix = (int) -r; ix <= (int) r; ix++) {
                    for (int iz = (int) -r; iz <= (int) r; iz++) {
                        for (int iy = (int) -r; iy <= (int) r; iy++) {
                            if (new Vec3d(x + ix, y + iy, z + iz).squareDistanceTo(new Vec3d(x, y, z)) - (Math.abs(ix) + Math.abs(iy) + Math.abs(iz)) * 0.3 > r * r)
                                continue;
                            if (world.getBlockState(new BlockPos((int) (x + ix), (int) (y + iy), (int) (z + iz))) == Blocks.STONE.getDefaultState()) {
                                placeBlock(world, (int) (x + ix), (int) (y + iy), (int) (z + iz), state);
                            }
                        }
                    }
                }
            }

        }

    }

}
