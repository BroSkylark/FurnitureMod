package yafm.Renderers;

import org.lwjgl.opengl.GL11;
import yafm.Library.RenderIDs;
import yafm.Library.Textures;
import yafm.TileEntities.TileEntityPotionStand;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.ForgeDirection;

public class RendererPotionStand extends TileEntitySpecialRenderer implements ISimpleBlockRenderingHandler
{
    private static final ModelPotionStand model = new ModelPotionStand();
    
    @Override
    public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float tick)
    {
        TileEntityPotionStand teps = (TileEntityPotionStand) tileentity;
        ForgeDirection d = teps.getDirection();
        boolean isX = d.offsetX != 0;
        
        GL11.glPushMatrix();
        
        GL11.glTranslated(x + 0.5d, y + 0.5d, z + 0.5d);
        GL11.glTranslated(d.offsetX * D_ORI, 0, d.offsetZ * D_ORI);
        if(isX) GL11.glRotatef(90F, 0, 1, 0);
        
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(Textures.TEXT_MODEL_POTIONSTAND);
        model.renderAll();
        
        for(int i = 0 ; i < TileEntityPotionStand.ROWS ; i++)
        {
            for(int j = 0 ; j < TileEntityPotionStand.COLS ; j++)
            {
                ItemStack p = teps.getPotions(j + i * TileEntityPotionStand.COLS);
                if(p == null) continue;
                
                float w = (16F - 4F) / 4F / 16F, h = (16F - 3F) / 3F / 16F, b = 0.0625f;
                float s = d.offsetX + d.offsetZ;
                
                GL11.glPushMatrix();
                
                GL11.glTranslated(-0.5d + (0.25d / 16D), -0.5d, s * 0.125d);
                GL11.glTranslatef(w + b + j * (w + b), h + b + i * (h + b), 0);
                
                for(int k = 0 ; k < p.stackSize ; k++)
                {
                    GL11.glTranslated(0, 0, s * (-0.0625d * 1.25d));
                    RendererToolRack.renderItemStack2D(p, SCALE);
                }
                
                GL11.glPopMatrix();
            }
        }
        
        GL11.glPopMatrix();
    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
    {
        TileEntityRenderer.instance.renderTileEntityAt(new TileEntityPotionStand(), 0.0D, 0.0D, 0.0D, 0.0F);
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
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
        return RenderIDs.BLOCK_POTIONSTAND_RENDER_ID;
    }
    
    private static final float D_ORI = 5.5f / 16F;
    private static final float SCALE = 0.32f;
}
