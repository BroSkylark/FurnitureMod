package yafm.Renderers;

import org.lwjgl.opengl.GL11;
import yafm.Library.RenderIDs;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RendererSpikes implements ISimpleBlockRenderingHandler
{
    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
    {
        renderer.renderBlockAsItem(block, 0, 1);
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
    {
        Tessellator tessellator = Tessellator.instance;
        double dx = tessellator.xOffset, dy = tessellator.yOffset, dz = tessellator.zOffset;
        int d = world.getBlockMetadata(x, y, z) ^ 1;
        float aX = Facing.offsetsYForSide[d] == 1 ? 180F : Facing.offsetsZForSide[d] * -90F;
        float aZ = Facing.offsetsXForSide[d] * 90F;
        
        GL11.glPushMatrix();
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.xOffset = tessellator.yOffset = tessellator.zOffset = 0;
            GL11.glTranslated(x + 0.5d + dx, y + 0.5d + dy, z + 0.5d + dz);
            GL11.glRotatef(aX, 1, 0, 0);
            GL11.glRotatef(aZ, 0, 0, 1);
            tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
            tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
            renderer.renderBlockCropsImpl(block, 0, -0.5d, -0.0625F - 0.5d, -0.5d);
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.xOffset = dx;
            tessellator.yOffset = dy;
            tessellator.zOffset = dz;
        GL11.glPopMatrix();
        
        return true;
    }

    @Override
    public boolean shouldRender3DInInventory()
    {
        return false;
    }

    @Override
    public int getRenderId()
    {
        return RenderIDs.BLOCK_SPIKES_RENDER_ID;
    }
}
