package yafm.Handler;

import cpw.mods.fml.common.registry.GameRegistry;
import yafm.Items.ItemBag;
import yafm.Items.ItemKey;
import yafm.Items.ItemLock;
import yafm.Items.ItemLockNKey;
import yafm.Items.ItemTemplate;
import yafm.Items.KeyCopyKeyRecipe;
import yafm.Items.KeyCopyLockRecipe;
import yafm.Items.KeyLockCreateRecipe;
import yafm.Items.KeyLockRecipe;
import yafm.Items.KeyNLockSplitRecipe;
import yafm.Library.ItemIDs;
import yafm.Library.Reference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public abstract class ItemHandler
{
    public static Item keyTemplate;
    public static Item key;
    public static Item lock;
    public static Item lockNKey;
    public static Item bag;
    
    public static void initItem()
    {
        keyTemplate = new ItemTemplate(ItemIDs.ITEM_KEYTEMPLATE_ID, Reference.ITEM_KEYTEMPLATE_NAME);
        key = new ItemKey(ItemIDs.ITEM_KEY_ID);
        lock = new ItemLock(ItemIDs.ITEM_LOCK_ID);
        lockNKey = new ItemLockNKey(ItemIDs.ITEM_LOCKNKEY_ID);
        bag = new ItemBag(ItemIDs.ITEM_BAG_ID);
    }
    
    public static void registerItem()
    {
        GameRegistry.registerItem(keyTemplate, Reference.ITEM_KEYTEMPLATE_NAME);
        GameRegistry.registerItem(key, Reference.ITEM_KEY_NAME);
        GameRegistry.registerItem(lock, Reference.ITEM_LOCK_NAME);
        GameRegistry.registerItem(lockNKey, Reference.ITEM_LOCKNKEY_NAME);
        GameRegistry.registerItem(bag, Reference.ITEM_BAG_NAME);
    }
    
    public static void registerRecipies()
    {
        GameRegistry.addRecipe(new ItemStack(bag), new Object[] {
            "# #", "# #", "$#$", '#', Item.leather, '$', Item.silk
        });
        GameRegistry.addRecipe(new ItemStack(keyTemplate, 6), new Object[] {
            "###", "  #", '#', Item.ingotIron
        });
        GameRegistry.addRecipe(new KeyLockCreateRecipe());
        GameRegistry.addRecipe(new KeyLockRecipe());
        GameRegistry.addRecipe(new KeyNLockSplitRecipe());
        GameRegistry.addRecipe(new KeyCopyKeyRecipe());
        GameRegistry.addRecipe(new KeyCopyLockRecipe());
    }
}
