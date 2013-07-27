package yafm.Proxy;

import net.minecraft.world.World;
import yafm.Handler.BlockHandler;
import yafm.Handler.ItemHandler;

public class CommonProxy
{
    public void handleBlocks()
    {
        BlockHandler.initBlocks();
        BlockHandler.registerBlocks();
        BlockHandler.registerTileEntities();
    }
    
    public void handleItems()
    {
        ItemHandler.initItem();
        ItemHandler.registerItem();
    }
    
    public void handleCrafting()
    {
        ItemHandler.registerRecipies();
        BlockHandler.registerRecipes();
    }
    
    public World getClientWorld()
    {
        return null;
    }
}
