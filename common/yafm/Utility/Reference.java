package yafm.Utility;

public abstract class Reference
{
    public static final String MOD_ID = "yafm";
    public static final String MOD_NAME = "Yet Another Furniture Mod";
    public static final String MOD_VERSION = "a0.1";
    public static final String MOD_DEPENDENCIES = "required-after:Forge@[7.8.0.703,);after:Thaumcraft";
    
    public static final String ADDON_TC_ID = "Thaumcraft";
    
    public static final String BLOCK_TOOLRACK_NAME = "ToolRack";
    public static final String BLOCK_TOOLRACK_ICONID = MOD_ID + ":" + BLOCK_TOOLRACK_NAME;

    public static final int FLAG_BLOCKSET_NONE = 0;
    public static final int FLAG_BLOCKSET_UPDATEBLOCK = 1;
    public static final int FLAG_BLOCKSET_UPDATECLIENTS = 2;
    public static final int FLAG_BLOCKSET_PREVENTRERENDER = 4;
}
