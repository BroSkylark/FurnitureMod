package yafm.Handler;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import yafm.GUI.ContainerBag;
import yafm.GUI.GuiBag;
import yafm.Library.GuiIDs;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{
    private static final Map<Integer, Class<? extends Container>> containers = new HashMap<Integer, Class<? extends Container>>();
    private static final Map<Integer, Class<? extends GuiContainer>> guis = new HashMap<Integer, Class<? extends GuiContainer>>();

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer entityplayer, World world, int x, int y, int z)
    {
        try
        {
            if(containers.containsKey(ID))
            {
                Class<? extends Container> cc = containers.get(ID);

                Object o = instantiate(cc, entityplayer, world, x, y, z);
                
                if(o == null) o = fromTE(cc, entityplayer, world, x, y, z);
                if(o == null) o = fromSimpleConstructor(cc);
                
                if(o != null && o instanceof Container)
                {
                    return o;
                }
                else
                {
                    (new RuntimeException("ERR: Couldn't find an valid initializer for Container with ID " + ID))
                        .printStackTrace();
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer entityplayer, World world, int x, int y, int z)
    {
        try
        {
            if(guis.containsKey(ID))
            {
                Class<? extends GuiContainer> cg = guis.get(ID);

                Object o = instantiate(cg, entityplayer, world, x, y, z);
                if(o == null) o = fromTE(cg, entityplayer, world, x, y, z);
                if(o == null) o = fromSimpleConstructor(cg);
                
                if(o != null && o instanceof GuiContainer)
                {
                    return o;
                }
                else
                {
                    (new RuntimeException("ERR: Couldn't find an valid initializer for GUI with ID " + ID))
                        .printStackTrace();
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    private Object instantiate(Class<?> c, EntityPlayer entityplayer, World world, int x, int y, int z)
    {
        try
        {
            Method init = c.getMethod("initializeInstance", new Class<?>[] 
                    {EntityPlayer.class, World.class, int.class, int.class, int.class});
    
            if(init != null)
            {
                return init.invoke(null, new Object[] {entityplayer, world, x, y, z});
            }
        }
        catch(Exception e) {}

        return null;
    }
    
    private Object fromTE(Class<?> cc, EntityPlayer entityplayer, World world, int x, int y, int z)
    {
        try
        {
            TileEntity te = world.getBlockTileEntity(x, y, z);
            
            if(te != null && te instanceof IInventory)
            {
                try
                {
                    Constructor<?> c = cc.getConstructor(new Class<?>[] {IInventory.class, te.getClass()});
                    
                    if(c != null) return c.newInstance(new Object[] {entityplayer.inventory, te});
                }
                catch(Exception e) {}
            }
        }
        catch(Exception e) {}
        
        return null;
    }
    
    private Object fromSimpleConstructor(Class<?> c)
    {
        try
        {
            return c.newInstance();
        }
        catch(Exception e) {}
        
        return null;
    }

    private static void addMapping(int id, Class<? extends Container> cc, Class<? extends GuiContainer> gc)
    {
        containers.put(Integer.valueOf(id), cc);
        guis.put(Integer.valueOf(id), gc);
    }

    static
    {
        addMapping(GuiIDs.GUI_BAG_ID, ContainerBag.class, GuiBag.class);
    }
}
