package yafm.TileEntities;

import core.utils.Math.AnalyticGeometry.Line;
import core.utils.Math.AnalyticGeometry.Plane;
import core.utils.Math.AnalyticGeometry.Point;
import core.utils.Math.AnalyticGeometry.Vector;
import yafm.Handler.PacketHandler;
import yafm.Packets.PacketToolRack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraftforge.common.ForgeDirection;

public class TileEntityToolRack extends TileEntityDirectional
{
    public ItemStack tools[];
    private Plane plane;

    public TileEntityToolRack()
    {
        tools = new ItemStack[TOOL_COUNT];
        plane = null;
    }

    public boolean clickRack(EntityPlayer player)
    {
        if(plane == null) setDirection(getDirection());
        
        Point p = plane.intersects(new Line(Point.getPlayerEyeLocation(player),
                        Vector.getVecFromPitchAndYaw(player.rotationPitch, player.rotationYaw)));
        
        if(p == null) 
            (new RuntimeException("ERR: Apparently Player didn't click the ToolRack!?!")).printStackTrace(System.err);
        else
        {
            return clickCompartiment(player, ((p.Y() < (yCoord + 0.5d)) ? 0 : 1));
        }

        return false;
    }
    
    private boolean clickCompartiment(EntityPlayer player, int c)
    {
        if(player.worldObj.isRemote)
        {
            return tools[c] != null || player.getCurrentEquippedItem() != null;
        }
        
        if(tools[c] == null)
        {
            if((tools[c] = player.getCurrentEquippedItem()) == null) return false;

            player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            player.inventoryContainer.detectAndSendChanges();
        }
        else
        {
            if(!player.inventory.addItemStackToInventory(tools[c])) return false;

            tools[c] = null;
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            player.inventoryContainer.detectAndSendChanges();
        }

        return true;
    }

    @Override
    public Packet getDescriptionPacket()
    {
        return PacketHandler.populatePacket((new PacketToolRack()).setData(this));
    }

    @Override
    public void saveInNBT(NBTTagCompound nbttagcompound)
    {
        for(int i = 0 ; i < TOOL_COUNT ; i++)
        {
            if(tools[i] != null) 
            {
                nbttagcompound.setCompoundTag(TAG_TOOLS + i, tools[i].writeToNBT(new NBTTagCompound()));
            }
        }
    }

    @Override
    public void restoreFromNBT(NBTTagCompound nbttagcompound)
    {
        for(int i = 0 ; i < TOOL_COUNT ; i++)
        {
            if(nbttagcompound.hasKey(TAG_TOOLS + i))
            {
                tools[i] = ItemStack.loadItemStackFromNBT(nbttagcompound.getCompoundTag(TAG_TOOLS + i));
            }
            else
            {
                tools[i] = null;
            }
        }
    }
    
    @Override
    public TileEntityDirectional setDirection(ForgeDirection dir)
    {
        plane = (new Plane(new Point(xCoord + 0.5d, yCoord, zCoord + 0.5d), Vector.eY, 
                    (dir.offsetX != 0 ? Vector.eZ : Vector.eX)))
                    .apply(new Vector(dir.offsetX * PLANE_OFFSET, 0, dir.offsetZ * PLANE_OFFSET));
        
        return super.setDirection(dir);
    }

    private static final String TAG_TOOLS = "Tools_";
    private static final int TOOL_COUNT = 2;
    private static final float PLANE_OFFSET = 0.5f * (1F - (3F / 16F));
}
