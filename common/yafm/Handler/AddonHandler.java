package yafm.Handler;

import thaumcraft.api.EnumTag;
import thaumcraft.api.ItemApi;
import thaumcraft.api.ObjectTags;
import thaumcraft.api.ThaumcraftApi;
import yafm.API.ToolRackRegistry;
import yafm.Library.BlockIDs;
import yafm.Library.Reference;
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

        ToolRackRegistry.registerTool(ItemApi.getItem("itemWandCastingApprentice", 0).itemID);
        ToolRackRegistry.registerTool(ItemApi.getItem("itemWandCastingAdept", 0).itemID);
        ToolRackRegistry.registerTool(ItemApi.getItem("itemWandCastingMage", 0).itemID);
        ToolRackRegistry.registerTool(ItemApi.getItem("itemWandFire", 0).itemID);
        ToolRackRegistry.registerTool(ItemApi.getItem("itemWandLightning", 0).itemID);
        ToolRackRegistry.registerTool(ItemApi.getItem("itemWandFrost", 0).itemID);
        ToolRackRegistry.registerTool(ItemApi.getItem("itemWandExcavation", 0).itemID);
        ToolRackRegistry.registerTool(ItemApi.getItem("itemWandTrade", 0).itemID);
        ToolRackRegistry.registerTool(ItemApi.getItem("itemHellrod", 0).itemID);
        ToolRackRegistry.registerItem(ItemApi.getItem("itemHandMirror", 0).itemID);
    }
}
