package yafm.TileEntities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class TileEntityDirectional extends TileEntity
{
    private ForgeDirection direction;
    private boolean needsUpdates;
    
    public TileEntityDirectional() { this(false); }
    public TileEntityDirectional(boolean updates)
    {
        direction = ForgeDirection.UNKNOWN;
        needsUpdates = updates;
    }

    public TileEntityDirectional setDirection(int dir) { return setDirection(ForgeDirection.getOrientation(dir)); }
    public TileEntityDirectional setDirection(ForgeDirection fd)
    {
        direction = fd == null ? ForgeDirection.UNKNOWN : fd;
        
        return this;
    }
    
    public ForgeDirection getDirection()
    {
        return direction;
    }

    @Override
    public Packet getDescriptionPacket()
    {
        if(!needsUpdates) return null;
        
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        writeToNBT(nbttagcompound);
        return new Packet132TileEntityData(xCoord, yCoord, zCoord, -1, nbttagcompound);
    }
    
    @Override
    public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt)
    {
        readFromNBT(pkt.customParam1);
    }

    @Override
    public final void writeToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeToNBT(nbttagcompound);
        
        nbttagcompound.setInteger(TAG_DIRECTION, direction.ordinal());
        
        NBTTagCompound nbt = new NBTTagCompound();
        saveInNBT(nbt);
        nbttagcompound.setCompoundTag(TAG_DATA, nbt);
    }
    
    @Override
    public final void readFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readFromNBT(nbttagcompound);
        
        direction = ForgeDirection.getOrientation(nbttagcompound.getInteger(TAG_DIRECTION));
        
        if(nbttagcompound.hasKey(TAG_DATA))
        {
            restoreFromNBT(nbttagcompound.getCompoundTag(TAG_DATA));
        }
        else
        {
            (new RuntimeException("ERR: TileEntity with missing " + TAG_DATA + " tag.")).printStackTrace();
        }
    }
    
    public void saveInNBT(NBTTagCompound nbttagcompound)
    {
    }
    
    public void restoreFromNBT(NBTTagCompound nbttagcompound)
    {
    }
    
    @Override
    public String toString()
    {
        return String.format("TE_Dir(%d|%d|%d) Facing %s", xCoord, yCoord, zCoord, direction.toString());
    }

    public static boolean tryToAddToInventory(EntityPlayer p, ItemStack is)
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
    
    public static NBTTagCompound saveItemStackInList(ItemStack is[])
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        NBTTagList list = new NBTTagList();
        
        for(int i = 0 ; i < is.length ; i++)
        {
            if(is[i] == null) continue;
            
            NBTTagCompound nbt = new NBTTagCompound();
            nbt.setCompoundTag(TAG_IS_D, is[i].writeToNBT(new NBTTagCompound()));
            nbt.setInteger(TAG_IS_I, i);
            list.appendTag(nbt);
        }
        
        nbttagcompound.setInteger(TAG_IS_S, is.length);
        nbttagcompound.setTag(TAG_IS_L, list);
        
        return nbttagcompound;
    }
    
    public static ItemStack[] restoreItemStackFromList(NBTTagCompound nbttagcompound)
    {
        ItemStack is[] = new ItemStack[nbttagcompound.getInteger(TAG_IS_S)];
        NBTTagList list = nbttagcompound.getTagList(TAG_IS_L);
        
        if(list != null)
        {
            for(int i = 0 ; i < list.tagCount() ; i++)
            {
                NBTTagCompound nbt = (NBTTagCompound) list.tagAt(i);
                is[nbt.getInteger(TAG_IS_I)] = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag(TAG_IS_D));
            }
        }
        
        return is;
    }

    public static int determineOrientation(World world, int i, int j, int k, EntityPlayer entityplayer)
    {
        if(MathHelper.abs((float)entityplayer.posX - (float)i) < 2.0F && 
                MathHelper.abs((float)entityplayer.posZ - (float)k) < 2.0F)
        {
            double d = (entityplayer.posY + 1.82D) - (double)entityplayer.yOffset;
            if(d - (double)j > 2D)
            {
                return 1;
            }
            if((double)j - d > 0.0D)
            {
                return 0;
            }
        }

        int l = MathHelper.floor_double((double)((entityplayer.rotationYaw * 4F) / 360F) + 0.5D) & 3;

        if(l == 0)
        {
            return 2;
        }

        if(l == 1)
        {
            return 5;
        }

        if(l == 2)
        {
            return 3;
        }

        if(l == 3)
        {
            return 4;
        }

        return 0;
    }
    
    private static final String TAG_DATA = "_data";
    private static final String TAG_DIRECTION = "_dir";
    private static final String TAG_IS_L = "_list";
    private static final String TAG_IS_S = "_length";
    private static final String TAG_IS_I = "_index";
    private static final String TAG_IS_D = "_data";
}
