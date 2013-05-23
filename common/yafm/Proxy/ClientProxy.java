package yafm.Proxy;

import net.minecraft.world.World;
import cpw.mods.fml.client.FMLClientHandler;
import yafm.Handler.BlockHandler;

public class ClientProxy extends CommonProxy
{
    @Override
    public void handleBlocks()
    {
        super.handleBlocks();
        BlockHandler.registerRenderingIDs();
        BlockHandler.registerBlockRenderers();
        BlockHandler.registerTileEntitySpecialRenderers();
    }
    
    @Override
    public World getClientWorld()
    {
        return FMLClientHandler.instance().getClient().theWorld;
    }
}
