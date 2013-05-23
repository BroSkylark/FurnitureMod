package yafm.Packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import yafm.FurnitureMod;
import yafm.TileEntities.TileEntityToolRack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.network.Player;

public class PacketToolRack extends PacketYAFM
{
    private NBTTagCompound data;
    
    public PacketToolRack()
    {
        super(true);
        data = new NBTTagCompound();
    }
    
    public PacketToolRack setData(TileEntityToolRack tetr) { tetr.writeToNBT(data); return this; }
    
    @Override
    public void execute(INetworkManager manager, Player player)
    {
        int x = data.getInteger("x"), y = data.getInteger("y"), z = data.getInteger("z");
        TileEntity tetr = FurnitureMod.proxy.getClientWorld().getBlockTileEntity(x, y, z);
        
        if(tetr != null)
        {
            tetr.readFromNBT(data);
        }
    }
    
    @Override
    public void writeData(DataOutputStream dos) throws IOException
    {
        NBTTagCompound nbt = new NBTTagCompound("Tools");
        nbt.setCompoundTag(TAG_TOOLS, data);
        NBTBase.writeNamedTag(nbt, dos);
    }
    
    @Override
    public void readData(DataInputStream dis) throws IOException
    {
        NBTTagCompound nbt = (NBTTagCompound) NBTBase.readNamedTag(dis);
        data = nbt.getCompoundTag(TAG_TOOLS);
        
        if(data == null)
        {
            (new RuntimeException("ERR: Couldn't read data from ToolRack packet!")).printStackTrace(System.err);
            data = new NBTTagCompound();
        }
    }
    
    private static final String TAG_TOOLS = "data";
}
