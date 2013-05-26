package yafm;
import java.io.File;
import net.minecraft.creativetab.CreativeTabs;
import yafm.CreativeTabs.CreativeTabFurniture;
import yafm.Handler.ConfigurationHandler;
import yafm.Handler.AddonHandler;
import yafm.Handler.LocalizationHandler;
import yafm.Handler.PacketHandler;
import yafm.Library.Reference;
import yafm.Proxy.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(   modid = Reference.MOD_ID,
        name = Reference.MOD_NAME,
        version = Reference.MOD_VERSION,
        dependencies = Reference.MOD_DEPENDENCIES)
@NetworkMod(channels = {Reference.MOD_CHANNEL}, 
            clientSideRequired = true, 
            serverSideRequired = false, 
            packetHandler = PacketHandler.class)
public class FurnitureMod
{
    @Instance(Reference.MOD_ID)
    public static FurnitureMod instance;
    
    @SidedProxy(clientSide = Reference.PROXY_CLIENT_CLASS, serverSide = Reference.PROXY_SERVER_CLASS)
    public static CommonProxy proxy;
    
    public static final CreativeTabs tabFurniture = new CreativeTabFurniture(CreativeTabs.getNextID(), 
            Reference.MOD_ID);
    
    @PreInit
    public void preInit(FMLPreInitializationEvent event)
    {
        LocalizationHandler.execute();
        
        ConfigurationHandler.execute(new File(event.getModConfigurationDirectory().getAbsolutePath() + File.separator
                + Reference.MOD_ID + File.separator + Reference.MOD_ID + ".cfg"));
        
        proxy.handleBlocks();
    }
    
    @Init
    public void init(FMLInitializationEvent event)
    {
    }
    
    @PostInit
    public void postInit(FMLPostInitializationEvent event)
    {
        AddonHandler.execute();
    }
}
