package yafm.GUI;

import yafm.Handler.ItemHandler;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotBag extends Slot
{
    private boolean allowInsert;
    
    public SlotBag(InventoryBag bag, int id, int x, int y)
    {
        super(bag, id, x, y);
        allowInsert = bag.isBig();
    }
    
    @Override
    public boolean isItemValid(ItemStack itemstack)
    {
        if(itemstack == null) return true;
        
        return itemstack.itemID != ItemHandler.bag.itemID || (allowInsert && !InventoryBag.isOpenBag(itemstack));
    }
}
