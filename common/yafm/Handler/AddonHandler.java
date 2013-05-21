package yafm.Handler;

import thaumcraft.api.EnumTag;
import thaumcraft.api.ObjectTags;
import thaumcraft.api.ThaumcraftApi;
import yafm.Utility.BlockIDs;
import yafm.Utility.Reference;
import cpw.mods.fml.common.Loader;

public class AddonHandler
{
    public static void execute()
    {
        if(Loader.isModLoaded(Reference.ADDON_TC_ID)) handleThaumcraft();
    }
    
    private static void handleThaumcraft()
    {
        ThaumcraftApi.registerObjectTag(BlockIDs.BLOCK_TOOLRACK_ID, -1, 
                (new ObjectTags()).add(EnumTag.METAL, 2).add(EnumTag.WOOD, 4));
    }
}
