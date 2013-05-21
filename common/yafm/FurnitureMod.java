package yafm;
import java.io.File;
import yafm.Handler.BlockHandler;
import yafm.Handler.ConfigurationHandler;
import yafm.Handler.AddonHandler;
import yafm.Handler.LocalizationHandler;
import yafm.Utility.Reference;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(   modid = Reference.MOD_ID,
        name = Reference.MOD_NAME,
        version = Reference.MOD_VERSION,
        dependencies = Reference.MOD_DEPENDENCIES)
public class FurnitureMod
{
    @Instance(Reference.MOD_ID)
    public static FurnitureMod instance;
    
    @PreInit
    public void preInit(FMLPreInitializationEvent event)
    {
        LocalizationHandler.execute();
        
        ConfigurationHandler.execute(new File(event.getModConfigurationDirectory().getAbsolutePath() + File.separator
                + Reference.MOD_ID + File.separator + Reference.MOD_ID + ".cfg"));
        
        BlockHandler.execute();
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
