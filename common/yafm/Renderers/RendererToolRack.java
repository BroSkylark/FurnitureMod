package yafm.Renderers;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import yafm.API.ToolRackRegistry;
import yafm.Library.RenderIDs;
import yafm.Library.Textures;
import yafm.TileEntities.TileEntityToolRack;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.ForgeDirection;

public class RendererToolRack extends TileEntitySpecialRenderer implements ISimpleBlockRenderingHandler
{
    private static final ModelToolRack model = new ModelToolRack();

    @Override
    public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float tick)
    {
        TileEntityToolRack tetr = (TileEntityToolRack) tileentity;
        ForgeDirection d = tetr.getDirection();
        boolean isX = d.offsetX != 0;
        float f = 1.5f / 16F;

        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5d, y + 0.5d, z + 0.5d);
        GL11.glTranslated(d.offsetX * D_ORI, 0, d.offsetZ * D_ORI);

        if(isX) GL11.glRotatef(90F, 0, 1, 0);

        FMLClientHandler.instance().getClient().renderEngine.bindTexture(Textures.TEXT_MODEL_TOOLRACK);
        model.renderAll(0F);

        for(int i = 0 ; i < 2 ; i++)
        {
            ItemStack tool = tetr.getToolInSlot(i);
            if(tool == null) continue;

            GL11.glPushMatrix();

            GL11.glTranslatef(i == 0 ?  f *  0.5f : -f * 0.5f, 
                              i == 0 ? -f * 0.25f :  f * 0.25f, 
                             -(d.offsetZ + d.offsetX) * f);
            GL11.glTranslated(0, i == 0 ? -0.25d : 0.25d, 0);
            
            if((isX ? d.offsetX : d.offsetZ) < 0)
                GL11.glRotatef(180, 0, 1, 0); 
            else
                GL11.glTranslated(i == 0 ? -f : f, 0, 0);

            GL11.glTranslated(0, i == 0 ? f / 2 : -f / 2, 0);
            if(!(tool.getItem() instanceof ItemSword)) GL11.glTranslated(0, i == 0 ? f / 3 : -f / 3, 0);
            if(tool.getItem() instanceof ItemBow) GL11.glRotatef(180, 0, 0, 1);
            
            if(ToolRackRegistry.isTool(tool.itemID))
            {
                GL11.glRotatef(-45, 0, 0, 1);
                GL11.glRotatef(i== 0 ? 67.5f : 67.5f - 180, 0, 0, 1);
            }
            else
            {
                GL11.glTranslated(i == 0 ? -f * 2 : f * 2, 0, 0);
            }
            
            renderItemStack2D(tool);
            GL11.glPopMatrix();
        }

        GL11.glPopMatrix();
    }

    private static void renderItemStack2D(ItemStack is)
    {
        float f = 0.75f;
        
        if(!ToolRackRegistry.isTool(is.itemID))
        {
            f *= 0.625f;
        }
        
        Icon i = is.getIconIndex();
        FMLClientHandler.instance().getClient().renderEngine.bindTexture("/gui/items.png");

        GL11.glPushMatrix();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glTranslated(-0.5d * f, -0.5d * f, 0.0625d * 0.5d);
        GL11.glScalef(f, f, f);

        ItemRenderer.renderItemIn2D(Tessellator.instance, i.getMaxU(), i.getMinV(), i.getMinU(), i.getMaxV(), 
                i.getSheetWidth(), i.getSheetHeight(), 0.0625F);
        
        if(is.hasEffect()) renderSpecialOverlay();
        
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
    }
    
    private static void renderSpecialOverlay()
    {
        Tessellator tessellator = Tessellator.instance;
        GL11.glDepthFunc(GL11.GL_EQUAL);
        GL11.glDisable(GL11.GL_LIGHTING);
        FMLClientHandler.instance().getClient().renderEngine.bindTexture("%blur%/misc/glint.png");
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
        float f7 = 0.76F;
        GL11.glColor4f(0.5F * f7, 0.25F * f7, 0.8F * f7, 1.0F);
        GL11.glMatrixMode(GL11.GL_TEXTURE);
        GL11.glPushMatrix();
        float f8 = 0.125F;
        GL11.glScalef(f8, f8, f8);
        float f9 = (float)(Minecraft.getSystemTime() % 3000L) / 3000.0F * 8.0F;
        GL11.glTranslatef(f9, 0.0F, 0.0F);
        GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
        ItemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glScalef(f8, f8, f8);
        f9 = (float)(Minecraft.getSystemTime() % 4873L) / 4873.0F * 8.0F;
        GL11.glTranslatef(-f9, 0.0F, 0.0F);
        GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
        ItemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
        GL11.glPopMatrix();
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDepthFunc(GL11.GL_LEQUAL);
    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
    {
        TileEntityRenderer.instance.renderTileEntityAt(new TileEntityToolRack(), 0.0D, 0.0D, 0.0D, 0.0F);
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
        return RenderIDs.TE_TOOLRACK_ID;
    }

    private static final float D_ORI = 7F / 16F;
}
