package yafm.GUI;

import org.lwjgl.opengl.GL11;
import yafm.Library.Reference;
import yafm.Library.Textures;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class GuiBag extends GuiContainer
{
    private boolean isBig;
    
    public GuiBag(InventoryBag inventorybag, InventoryPlayer inventoryplayer)
    {
        super(new ContainerBag(inventorybag, inventoryplayer));
        isBig = inventorybag.isBig();
        
        if(!isBig) this.ySize = 133;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y)
    {
        fontRenderer.drawString(StatCollector.translateToLocal(Reference.CONTAINER_BAG_NAME + (isBig ? ".Big" : ".Small")), 8, 6, 0x404040);
        fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 0x404040);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(isBig ? Textures.TEXT_GUI_BAG_LARGE : Textures.TEXT_GUI_BAG_SMALL);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
    }
    
    public static GuiBag initializeInstance(EntityPlayer entityplayer, World world, int x, int y, int z)
    {
        InventoryBag bag = InventoryBag.getInventory(entityplayer.getHeldItem());
        
        return bag == null ? null : new GuiBag(bag, entityplayer.inventory);
    }
}
