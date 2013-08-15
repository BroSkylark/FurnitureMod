package yafm.Items;

import yafm.Handler.ItemHandler;
import yafm.Library.Keys.KeyReference;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class KeyLockRecipe implements IRecipe
{
    @Override
    public boolean matches(InventoryCrafting inventorycrafting, World world)
    {
        return getLockFromValidRecipe(inventorycrafting) != null;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inventorycrafting)
    {
        ItemStack lock = getLockFromValidRecipe(inventorycrafting);
        if(lock != null) 
        {
            ItemStack r = new ItemStack(ItemHandler.lockNKey);
            r.setTagCompound(new NBTTagCompound());
            r.getTagCompound().setLong(KeyReference.TAG_UID, lock.getTagCompound().getLong(KeyReference.TAG_UID));
            return r;
        }
        
        return null;
    }
    
    private ItemStack getLockFromValidRecipe(InventoryCrafting inventorycrafting)
    {
        ItemStack key = null, lock = null;
        
        for(int i = 0 ; i < inventorycrafting.getSizeInventory() ; i++)
        {
            ItemStack is = inventorycrafting.getStackInSlot(i);
            if(is == null) continue;
            
            if(is.itemID == ItemHandler.key.itemID)
            {
                if(key != null) return null;
                key = is;
            }
            else if(is.itemID == ItemHandler.lock.itemID)
            {
                if(lock != null) return null;
                lock = is;
            }
            else
            {
                return null;
            }
        }
        
        if(key != null && lock != null)
        {
            return ItemLock.doesKeyFit(lock, key) ? lock : null;
        }
        
        return null;
    }

    @Override
    public int getRecipeSize()
    {
        return 2;
    }

    @Override
    public ItemStack getRecipeOutput()
    {
        return null;
    }
}
