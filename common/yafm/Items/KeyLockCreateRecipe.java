package yafm.Items;

import java.util.Random;
import yafm.Handler.ItemHandler;
import yafm.Library.Keys.KeyReference;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.nbt.NBTTagCompound;

public class KeyLockCreateRecipe extends ShapedRecipes
{
    private static Random rand = new Random(System.currentTimeMillis());
    
    public KeyLockCreateRecipe()
    {
        super(3, 2, new ItemStack[] {
                new ItemStack(Item.ingotIron), new ItemStack(ItemHandler.keyTemplate), new ItemStack(Item.ingotIron),
                null, new ItemStack(Item.ingotIron), null
        }, new ItemStack(ItemHandler.lockNKey));
    }
    
    @Override
    public ItemStack getCraftingResult(InventoryCrafting inventorycrafting)
    {
        ItemStack r = super.getCraftingResult(inventorycrafting);
        r.setTagCompound(new NBTTagCompound());
        r.getTagCompound().setLong(KeyReference.TAG_UID, rand.nextLong());
        return r;
    }
}
