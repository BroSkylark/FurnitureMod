package yafm.Library;

public abstract class Textures
{
    public static final String TEXT_DIR = "/mods/yafm/textures/";
    public static final String PNG = ".png";
    
    public static final String TEXT_MODEL = "models/";
    public static final String TEXT_MODEL_TOOLRACK = TEXT_DIR + TEXT_MODEL + Reference.BLOCK_TOOLRACK_NAME + PNG;
    public static final String TEXT_MODEL_POTIONSTAND = TEXT_DIR + TEXT_MODEL + Reference.BLOCK_POTIONSTAND_NAME + PNG;
    public static final String TEXT_MODEL_MOTIONSENSOR = TEXT_DIR + TEXT_MODEL + Reference.BLOCK_MOTIONSENSOR_NAME + PNG;
    
    public static final String TEXT_ITEM = "items/";
    public static final String TEXT_ITEM_KEYTEMPLATE = TEXT_DIR + TEXT_ITEM + Reference.ITEM_KEYTEMPLATE_NAME + PNG;
    public static final String TEXT_ITEM_KEY = TEXT_DIR + TEXT_ITEM + Reference.ITEM_KEY_NAME + PNG;
    public static final String TEXT_ITEM_BAG = TEXT_DIR + TEXT_ITEM + Reference.ITEM_BAG_NAME + PNG;
    
    public static final String TEXT_GUI = "gui/";
    public static final String TEXT_GUI_BAG_SMALL = TEXT_DIR + TEXT_GUI + Reference.ITEM_BAG_NAME + "_small" + PNG;
    public static final String TEXT_GUI_BAG_LARGE = TEXT_DIR + TEXT_GUI + Reference.ITEM_BAG_NAME + "_large" + PNG;
}
