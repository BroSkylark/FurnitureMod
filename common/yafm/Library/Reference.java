package yafm.Library;

public abstract class Reference
{
    public static final String MOD_ID = "yafm";
    public static final String MOD_NAME = "Yet Another Furniture Mod";
    public static final String MOD_VERSION = "a0.5";
    public static final String MOD_DEPENDENCIES = "required-after:Forge@[7.8.0.703,);after:Thaumcraft";
    public static final String MOD_CHANNEL = MOD_ID;

    public static final String PROXY_SERVER_CLASS = "yafm.Proxy.CommonProxy";
    public static final String PROXY_CLIENT_CLASS = "yafm.Proxy.ClientProxy";
    
    public static final String ADDON_TC_ID = "Thaumcraft";
    
    public static final String ITEM_KEY_NAME = "Key";
    public static final String ITEM_KEYTEMPLATE_NAME = "KeyTemplate";
    public static final String ITEM_LOCK_NAME = "Lock";
    public static final String ITEM_LOCKNKEY_NAME = "LockNKey";
    
    public static final String BLOCK_TOOLRACK_NAME = "ToolRack";
    public static final String BLOCK_POTIONSTAND_NAME = "PotionStand";
    public static final String BLOCK_MOTIONSENSOR_NAME = "MotionSensor";
    public static final String BLOCK_TRICKGLASS_NAME = "TrickGlass";
    public static final String BLOCK_PASSABLEGLASS_NAME = "PassableGlass";
    public static final String BLOCK_SPIKESNORMAL_NAME = "Spikes_Normal";
    public static final String BLOCK_SPIKESPOISON_NAME = "Spikes_Poison";
    public static final String BLOCK_SPIKESDIAMOND_NAME = "Spikes_Diamond";
    
    private static final String TE = "TE_";
    public static final String TE_POTIONSTAND_NAME = TE + BLOCK_POTIONSTAND_NAME;
    public static final String TE_TOOLRACK_NAME = TE + BLOCK_TOOLRACK_NAME;
    public static final String TE_MOTIONSENSOR_NAME = TE + BLOCK_MOTIONSENSOR_NAME;
    public static final String TE_TRICKGLASS_NAME = TE + BLOCK_TRICKGLASS_NAME;

    public static final int FLAG_BLOCKSET_NONE = 0;
    public static final int FLAG_BLOCKSET_UPDATEBLOCK = 1;
    public static final int FLAG_BLOCKSET_UPDATECLIENTS = 2;
    public static final int FLAG_BLOCKSET_PREVENTRERENDER = 4;
    
    public static final String getIconID(String blockName) { return MOD_ID.toLowerCase() + ":" + blockName.toLowerCase(); }
}
