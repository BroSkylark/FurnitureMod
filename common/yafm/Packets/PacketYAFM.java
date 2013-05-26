package yafm.Packets;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.network.INetworkManager;
import cpw.mods.fml.common.network.Player;

public abstract class PacketYAFM
{
    private static final Map<Integer, Class<? extends PacketYAFM>> nameToClass;
    private static final Map<Class<? extends PacketYAFM>, Integer> classToName;
    public final boolean isChunkDataPacket;

    public PacketYAFM() { this(false); }
    public PacketYAFM(boolean isChunkDataPacket)
    {
        this.isChunkDataPacket = isChunkDataPacket;
    }
    
    public abstract void execute(INetworkManager manager, Player player);

    public byte[] populate()
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);

        try
        {
            dos.writeByte(getID(this));
            
            writeData(dos);
        }
        catch (IOException e)
        {
            e.printStackTrace(System.err);
        }

        return bos.toByteArray();
    }

    public static PacketYAFM reconstruct(byte[] data)
    {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        DataInputStream dis = null;
        PacketYAFM p = null;
        
        try
        {
            p = getInstance(bais.read());
            dis = new DataInputStream(bais);
            
            p.readData(dis);
        }
        catch(IOException e)
        {
            e.printStackTrace(System.err);
        }
        finally
        {
            if(dis != null)
            {
                try {dis.close();} catch(Exception e) {}
            }
        }
        
        return p;
    }

    public void readData(DataInputStream data) throws IOException
    {
    }

    public void writeData(DataOutputStream dos) throws IOException
    {
    }

    @SuppressWarnings("unused")
    private static void addMapping(Class<? extends PacketYAFM> c, int i)
    {
        nameToClass.put(Integer.valueOf(i), c);
        classToName.put(c, Integer.valueOf(i));
    }

    private static <T extends PacketYAFM> int getID(T t) { return getID(t.getClass()); }
    public static int getID(Class<? extends PacketYAFM> c)
    { 
        if(classToName.containsKey(c))
        {
            return classToName.get(c).intValue();
        }
        else
        {
            (new IllegalArgumentException("ERR: Not registered PacketYAFM-class!")).printStackTrace(System.err);

            return -1;
        }
    }

    public static PacketYAFM getInstance(int id)
    {
        try
        {
            return getClass(id).newInstance();
        }
        catch(Exception e)
        {
            e.printStackTrace(System.err);
        }

        return null;
    }

    public static Class<? extends PacketYAFM> getClass(int id)
    {
        if(nameToClass.containsKey(Integer.valueOf(id)))
        {
            return nameToClass.get(Integer.valueOf(id));
        }
        else
        {
            (new IllegalArgumentException("ERR: Invalid PacketYAFM-ID! (" + id + ")")).printStackTrace(System.err);
            return null;
        }
    }

    static
    {
        nameToClass = new HashMap<Integer, Class<? extends PacketYAFM>>();
        classToName = new HashMap<Class<? extends PacketYAFM>, Integer>();
    }
}
