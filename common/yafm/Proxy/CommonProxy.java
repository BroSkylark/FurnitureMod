package yafm.Proxy;

import net.minecraft.world.World;
import yafm.Handler.BlockHandler;

public class CommonProxy
{
    public void handleBlocks()
    {
        BlockHandler.initBlocks();
        BlockHandler.registerBlocks();
        BlockHandler.registerTileEntities();
        BlockHandler.registerRecipes();
    }
    
    public World getClientWorld()
    {
        return null;
    }
}
