package kpan.not_enough_oxygen.item;

import kpan.not_enough_oxygen.world.NEOWorldProvider;
import kpan.not_enough_oxygen.world.NEOWorldRegisterer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
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

        server.getPlayerList().transferPlayerToDimension((EntityPlayerMP) player, NEOWorldRegisterer.DIMENSION_ID, new ITeleporter() {
            @Override
            public void placeEntity(World world, Entity entity, float yaw) {
                if (entity instanceof EntityPlayer player) {
                    BlockPos spawnPos = ((NEOWorldProvider) world.provider).getSpawnPoint1();
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
}
