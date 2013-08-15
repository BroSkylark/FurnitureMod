package yafm.Items;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import yafm.Handler.ItemHandler;
import yafm.Library.Reference;
import yafm.Library.Keys.KeyReference;

public class ItemLock extends ItemTemplate
{
    public ItemLock(int id)
    {
        super(id, Reference.ITEM_LOCK_NAME);
        this.setHasSubtypes(true);
        this.setMaxDamage(1);
        this.setMaxStackSize(1);
    }
    
    public static boolean doesKeyFit(ItemStack lock, ItemStack key)
    {
        if(lock == null || key == null) return false;
        if(lock.itemID != ItemHandler.lock.itemID || key.itemID != ItemHandler.key.itemID) return false;
        if(!lock.hasTagCompound() || !key.hasTagCompound()) return false;
        
        NBTTagCompound nbtLock = lock.getTagCompound(), nbtKey = key.getTagCompound();
        
        if(!nbtLock.hasKey(KeyReference.TAG_UID) || !nbtKey.hasKey(KeyReference.TAG_UID)) return false;
        
        return nbtLock.getLong(KeyReference.TAG_UID) == nbtKey.getLong(KeyReference.TAG_UID);
    }
}
