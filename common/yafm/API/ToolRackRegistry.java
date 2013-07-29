package yafm.API;

import java.util.ArrayList;
import java.util.List;
import yafm.Handler.ItemHandler;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;

public abstract class ToolRackRegistry
{
    private static final List<Integer> acceptableItems = new ArrayList<Integer>();
    private static final List<Integer> additionalTools = new ArrayList<Integer>();
    
    public static void registerItem(int id)
    {
        acceptableItems.add(Integer.valueOf(id));
    }
    
    public static void registerTool(int id)
    {
        additionalTools.add(Integer.valueOf(id));
    }
    
    public static boolean isAcceptable(int id)
    {
        return acceptableItems.contains(Integer.valueOf(id)) || additionalTools.contains(Integer.valueOf(id));
    }
    
    public static boolean isTool(int id)
    {
        Item i = Item.itemsList[id];
        
        if(i != null && (i instanceof ItemTool || i instanceof ItemHoe || i instanceof ItemSword)) return true;
        
        return additionalTools.contains(Integer.valueOf(id));
    }
    
    static
    {
        additionalTools.add(Item.bow.itemID);
        acceptableItems.add(Item.shears.itemID);
        acceptableItems.add(Item.flintAndSteel.itemID);
        acceptableItems.add(ItemHandler.bag.itemID);
    }
}
