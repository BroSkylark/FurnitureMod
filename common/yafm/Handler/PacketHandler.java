package yafm.Handler;

import yafm.Library.Reference;
import yafm.Packets.PacketYAFM;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler
{
    @Override
    public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player)
    {
        reconstructPacket(packet).execute(manager, player);
    }
    
    public static Packet populatePacket(PacketYAFM packet)
    {
        byte[] d = packet.populate();
        
        Packet250CustomPayload p = new Packet250CustomPayload();
        p.channel = Reference.MOD_CHANNEL;
        p.data = d;
        p.length = d.length;
        p.isChunkDataPacket = packet.isChunkDataPacket;
        
        return p;
    }
    
    public static PacketYAFM reconstructPacket(Packet250CustomPayload packet)
    {
        return PacketYAFM.reconstruct(packet.data);
    }
}
