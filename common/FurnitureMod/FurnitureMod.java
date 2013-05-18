package FurnitureMod;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import Utility.Reference;

@Mod(   modid = Reference.MOD_ID,
        name = Reference.MOD_NAME,
        version = Reference.MOD_VERSION)
public class FurnitureMod
{
    @Instance(Reference.MOD_ID)
    public static FurnitureMod instance;
    
    @PreInit
    public void preInit(FMLPreInitializationEvent fmlpreinitializationevent)
    {
    }
    
    @Init
    public void init(FMLInitializationEvent fmlinitializationevent)
    {
    }
    
    @PostInit
    public void postInit(FMLPostInitializationEvent fmlpostinitializationevent)
    {
    }
}
