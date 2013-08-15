package yafm.Items;

import yafm.Handler.ItemHandler;
import yafm.Library.Keys.KeyReference;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class KeyNLockSplitRecipe implements IRecipe
{
    @Override
    public boolean matches(InventoryCrafting inventorycrafting, World world)
    {
        return getLockNKeyFromValidRecipe(inventorycrafting) != null;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inventorycrafting)
    {
        ItemStack r = new ItemStack(ItemHandler.key);
        r.setTagCompound(new NBTTagCompound());
        r.getTagCompound().setLong(KeyReference.TAG_UID, 
                getLockNKeyFromValidRecipe(inventorycrafting).getTagCompound().getLong(KeyReference.TAG_UID));
        
        return r;
    }
    
    private ItemStack getLockNKeyFromValidRecipe(InventoryCrafting inventorycrafting)
    {
        ItemStack locknkey = null;
        
        for(int i = 0 ; i < inventorycrafting.getSizeInventory() ; i++)
        {
            ItemStack is = inventorycrafting.getStackInSlot(i);
            if(is != null)
            {
                if(locknkey != null) return null;
                
                if(is.itemID == ItemHandler.lockNKey.itemID && is.hasTagCompound() && 
                        is.getTagCompound().hasKey(KeyReference.TAG_UID))
                {
                    locknkey = is;
                }
                else
                {
                    return null;
                }
            }
        }
        
        return locknkey;
    }

    @Override
    public int getRecipeSize()
    {
        return 1;
    }

    @Override
    public ItemStack getRecipeOutput()
    {
        return null;
    }
}
