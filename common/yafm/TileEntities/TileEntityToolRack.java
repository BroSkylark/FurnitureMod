package yafm.TileEntities;

import org.lwjgl.input.Keyboard;
import core.utils.Math.AnalyticGeometry.Line;
import core.utils.Math.AnalyticGeometry.Plane;
import core.utils.Math.AnalyticGeometry.Point;
import core.utils.Math.AnalyticGeometry.Vector;
import yafm.API.ToolRackRegistry;
import yafm.Handler.PacketHandler;
import yafm.Packets.PacketToolRack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
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
            double d = getDirection().offsetX == 0 ? p.X() - xCoord : p.Z() - zCoord;
            if(getDirection().offsetX < 0 || getDirection().offsetZ > 0) d = 1 - d;
            d = d * 0.33333d + 0.33333d;
            boolean r = clickCompartiment(player, ((p.Y() < (yCoord + d)) ? 0 : 1));

            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);

            return r;
        }

        return false;
    }

    private boolean clickCompartiment(EntityPlayer player, int c)
    {
        if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL))
        {
            if(!isSuitable(player.getCurrentEquippedItem())) return false;
            
            ItemStack is = player.getCurrentEquippedItem();
            
            if(!worldObj.isRemote)
            {
                player.inventory.setInventorySlotContents(player.inventory.currentItem, tools[c]);
                tools[c] = is;
            }
            
            if(tools[c] == null && player.getCurrentEquippedItem() == null) return false;
        }
        else
        {
            if(tools[c] == null)
            {
                if(!isSuitable(player.getCurrentEquippedItem())) return false;
                if(player.getCurrentEquippedItem() == null) return false;
                
                if(!worldObj.isRemote)
                {
                    tools[c] = player.getCurrentEquippedItem();
                    player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
                }
            }
            else
            {
                if(!worldObj.isRemote)
                {
                    if(!tryToAddToInventory(player, tools[c])) return false;
    
                    tools[c] = null;
                }
            }
        }

        if(!worldObj.isRemote)
        {
            player.inventoryContainer.detectAndSendChanges();
        }

        return true;
    }

    public ItemStack getToolInSlot(int i)
    {
        return tools[i];
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

    private static boolean tryToAddToInventory(EntityPlayer p, ItemStack is)
    {
        if(p.getCurrentEquippedItem() == null)
        {
            p.inventory.setInventorySlotContents(p.inventory.currentItem, is);
            return true;
        }
        else
        {
            return p.inventory.addItemStackToInventory(is);
        }
    }
    
    private static boolean isSuitable(ItemStack is)
    {
        return is == null || ToolRackRegistry.isAcceptable(is.itemID) || is.getItem() instanceof ItemTool || 
                is.getItem() instanceof ItemSword || is.getItem() instanceof ItemHoe;
    }

    private static final String TAG_TOOLS = "Tools_";
    private static final float PLANE_OFFSET = 0.5f * (1F - (3F / 16F));
    public static final int TOOL_COUNT = 2;
}
