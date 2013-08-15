package yafm.Library.Addons;

import yafm.Library.Reference;

public final class ThaumcraftReference
{
    private static final String PRE = "YAFM_";
    
    private static final String CRAFTING = PRE + "CRAFT_";
    public static final String CRAFTING_PASSABLEGLASS_NAME = CRAFTING + Reference.BLOCK_PASSABLEGLASS_NAME;
    public static final String CRAFTING_BAG_NAME = CRAFTING + Reference.ITEM_BAG_NAME;
    
    private static final String RESEARCH = PRE + "RESEARCH_";
    public static final String RESEARCH_PASSABLEGLASS_NAME = RESEARCH + Reference.BLOCK_PASSABLEGLASS_NAME;
    public static final String RESEARCH_BAG_NAME = RESEARCH + Reference.ITEM_BAG_NAME;
    
    public static final String RESEARCH_PATH = "/mods/yafm/lang/research.xml";
    
    private ThaumcraftReference() {}
}
