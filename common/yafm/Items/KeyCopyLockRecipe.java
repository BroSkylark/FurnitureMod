package yafm.Items;

import yafm.Handler.ItemHandler;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;

public class KeyCopyLockRecipe extends ShapedRecipes
{
    public KeyCopyLockRecipe()
    {
        super(3, 2, new ItemStack[] {
                new ItemStack(Item.ingotIron), new ItemStack(ItemHandler.key), new ItemStack(Item.ingotIron),
                null, new ItemStack(Item.ingotIron), null
        }, new ItemStack(ItemHandler.lockNKey));
        this.func_92100_c();
    }
}
