package yafm.Handler;

import yafm.GUI.InventoryBag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.item.ItemTossEvent;

public class ForgeHooksHandler
{
    @ForgeSubscribe
    public void onItemToss(ItemTossEvent event)
    {
        EntityPlayer player = event.player;
        ItemStack is = event.entityItem.getEntityItem();
        
        if(InventoryBag.isOpenBag(is))
        {
            player.closeScreen();
            event.setCanceled(true);
        }
    }
}
