package yafm.TileEntities;

import net.minecraft.tileentity.TileEntity;

public class TileEntityMotionSensor extends TileEntity
{
    public TileEntityMotionSensor() { this(0); }
    public TileEntityMotionSensor(int d)
    {
        blockMetadata = d;
    }
}
