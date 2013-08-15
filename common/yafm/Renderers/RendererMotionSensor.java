package yafm.Renderers;

import org.lwjgl.opengl.GL11;
import yafm.Library.RenderIDs;
import yafm.Library.Textures;
import yafm.TileEntities.TileEntityMotionSensor;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RendererMotionSensor extends TileEntitySpecialRenderer implements ISimpleBlockRenderingHandler
{
    private static final ModelMotionSensor model = new ModelMotionSensor();

    @Override
    public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float tick)
    {
        GL11.glPushMatrix();
        
        GL11.glTranslated(x + 0.5d, y + 0.5d, z + 0.5d);
        
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(Textures.TEXT_MODEL_MOTIONSENSOR);
        model.renderAll(tileentity.getBlockMetadata() & 7);
        
        GL11.glPopMatrix();
    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
    {
        TileEntityRenderer.instance.renderTileEntityAt(new TileEntityMotionSensor(3), 0.0D, -0.05D, 0.0D, 0.0F);
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess iblockaccess, int x, int y, int z, Block block, int modelId,
            RenderBlocks renderer)
    {
        return false;
    }

    @Override
    public boolean shouldRender3DInInventory()
    {
        return true;
    }

    @Override
    public int getRenderId()
    {
        return RenderIDs.BLOCK_MOTIONSENSOR_RENDER_ID;
    }
}
