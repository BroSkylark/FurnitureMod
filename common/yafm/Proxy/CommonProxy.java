package yafm.Proxy;

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
}
