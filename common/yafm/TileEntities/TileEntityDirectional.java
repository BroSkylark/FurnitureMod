package yafm.TileEntities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

public class TileEntityDirectional extends TileEntity
{
    private ForgeDirection direction;
    
    public TileEntityDirectional()
    {
        direction = ForgeDirection.UNKNOWN;
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

    private static final String TAG_DATA = "_data";
    private static final String TAG_DIRECTION = "_dir";
}
