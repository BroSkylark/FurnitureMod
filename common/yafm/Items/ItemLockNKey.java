package yafm.Items;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import yafm.Handler.ItemHandler;
import yafm.Library.Reference;
import yafm.Library.Keys.KeyReference;

public class ItemLockNKey extends ItemTemplate
{
    public ItemLockNKey(int id)
    {
        super(id, Reference.ITEM_LOCKNKEY_NAME);
        this.setHasSubtypes(true);
        this.setMaxDamage(1);
        this.setMaxStackSize(1);
        this.setContainerItem(ItemHandler.lock);
    }
    
    @Override
    public ItemStack getContainerItemStack(ItemStack itemstack)
    {
        ItemStack r = new ItemStack(getContainerItem());
        r.setTagCompound(new NBTTagCompound());
        r.getTagCompound().setLong(KeyReference.TAG_UID, itemstack.getTagCompound().getLong(KeyReference.TAG_UID));
        return r;
    }
}
