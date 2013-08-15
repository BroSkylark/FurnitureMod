package yafm.Handler.Addons;

import yafm.Library.Reference;
import cpw.mods.fml.common.Loader;

public abstract class AddonHandler
{
    protected AddonHandler() {}
    
    public abstract void handlePreInit();
    public abstract void handlePostInit();
    
    public static final void executePreInit()
    {
        if(Loader.isModLoaded(Reference.ADDON_TC_ID)) ThaumcraftHandler.instance.handlePreInit();
    }
    
    public static final void executePostInit()
    {
        if(Loader.isModLoaded(Reference.ADDON_TC_ID)) ThaumcraftHandler.instance.handlePostInit();
    }
}
