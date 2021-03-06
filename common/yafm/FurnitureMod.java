package yafm;
import java.io.File;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import yafm.CreativeTabs.CreativeTabFurniture;
import yafm.Handler.ConfigurationHandler;
import yafm.Handler.ForgeHooksHandler;
import yafm.Handler.GuiHandler;
import yafm.Handler.LocalizationHandler;
import yafm.Handler.PacketHandler;
import yafm.Handler.Addons.AddonHandler;
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
import cpw.mods.fml.common.network.NetworkRegistry;

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
        
        proxy.handleItems();
        proxy.handleBlocks();
        proxy.handleCrafting();
        
        AddonHandler.executePreInit();
    }
    
    @Init
    public void init(FMLInitializationEvent event)
    {
        NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler());
        
        MinecraftForge.EVENT_BUS.register(new ForgeHooksHandler());
    }
    
    @PostInit
    public void postInit(FMLPostInitializationEvent event)
    {
        AddonHandler.executePostInit();
    }
}
