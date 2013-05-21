package yafm.Proxy;

import yafm.Handler.BlockHandler;

public class ClientProxy extends CommonProxy
{
    @Override
    public void handleBlocks()
    {
        super.handleBlocks();
        BlockHandler.registerTileEntitySpecialRenderers();
    }
}
