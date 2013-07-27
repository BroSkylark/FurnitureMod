package yafm.Handler;

import java.io.File;
import java.util.logging.Level;
import yafm.Library.BlockIDs;
import yafm.Library.ItemIDs;
import yafm.Library.Reference;
import cpw.mods.fml.common.FMLLog;
import net.minecraftforge.common.Configuration;

public class ConfigurationHandler
{
    public static Configuration configuration;
    
    public static void execute(File file)
    {
        configuration = new Configuration(file);

        configuration.load();
        
        try
        {
            ItemIDs.ITEM_KEYTEMPLATE_ID = configuration.getItem(Reference.ITEM_KEYTEMPLATE_NAME, 
                    ItemIDs.ITEM_KEYTEMPLATE_ID_DEFAULT).getInt(ItemIDs.ITEM_KEYTEMPLATE_ID_DEFAULT);
            ItemIDs.ITEM_KEY_ID = configuration.getItem(Reference.ITEM_KEY_NAME, 
                    ItemIDs.ITEM_KEY_ID_DEFAULT).getInt(ItemIDs.ITEM_KEY_ID_DEFAULT);
            ItemIDs.ITEM_LOCK_ID = configuration.getItem(Reference.ITEM_LOCK_NAME, 
                    ItemIDs.ITEM_LOCK_ID_DEFAULT).getInt(ItemIDs.ITEM_LOCK_ID_DEFAULT);
            ItemIDs.ITEM_LOCKNKEY_ID = configuration.getItem(Reference.ITEM_LOCKNKEY_NAME, 
                    ItemIDs.ITEM_LOCKNKEY_ID_DEFAULT).getInt(ItemIDs.ITEM_LOCKNKEY_ID_DEFAULT);
            
            BlockIDs.BLOCK_TOOLRACK_ID = configuration.getBlock(Reference.BLOCK_TOOLRACK_NAME, 
                    BlockIDs.BlOCK_TOOLRACK_ID_DEFAULT).getInt(BlockIDs.BlOCK_TOOLRACK_ID_DEFAULT);
            BlockIDs.BLOCK_POTIONSTAND_ID = configuration.getBlock(Reference.BLOCK_POTIONSTAND_NAME, 
                    BlockIDs.BLOCK_POTIONSTAND_ID_DEFAULT).getInt(BlockIDs.BLOCK_POTIONSTAND_ID_DEFAULT);
            BlockIDs.BLOCK_MOTIONSENSOR_ID = configuration.getBlock(Reference.BLOCK_MOTIONSENSOR_NAME, 
                    BlockIDs.BLOCK_MOTIONSENSOR_ID_DEFAULT).getInt(BlockIDs.BLOCK_MOTIONSENSOR_ID_DEFAULT);
            BlockIDs.BLOCK_TRICKGLASS_ID = configuration.getBlock(Reference.BLOCK_TRICKGLASS_NAME, 
                    BlockIDs.BLOCK_TRICKGLASS_ID_DEFAULT).getInt(BlockIDs.BLOCK_TRICKGLASS_ID_DEFAULT);
            BlockIDs.BLOCK_PASSABLEGLASS_ID = configuration.getBlock(Reference.BLOCK_PASSABLEGLASS_NAME, 
                    BlockIDs.BLOCK_PASSABLEGLASS_ID_DEFAULT).getInt(BlockIDs.BLOCK_PASSABLEGLASS_ID_DEFAULT);
            BlockIDs.BLOCK_SPIKESNORMAL_ID = configuration.getBlock(Reference.BLOCK_SPIKESNORMAL_NAME, 
                    BlockIDs.BLOCK_SPIKESNORMAL_ID_DEFAULT).getInt(BlockIDs.BLOCK_SPIKESNORMAL_ID_DEFAULT);
            BlockIDs.BLOCK_SPIKESPOISON_ID = configuration.getBlock(Reference.BLOCK_SPIKESPOISON_NAME, 
                    BlockIDs.BLOCK_SPIKESPOISON_ID_DEFAULT).getInt(BlockIDs.BLOCK_SPIKESPOISON_ID_DEFAULT);
            BlockIDs.BLOCK_SPIKESDIAMOND_ID = configuration.getBlock(Reference.BLOCK_SPIKESDIAMOND_NAME, 
                    BlockIDs.BLOCK_SPIKESDIAMOND_ID_DEFAULT).getInt(BlockIDs.BLOCK_SPIKESDIAMOND_ID_DEFAULT);
        }
        catch(Exception e)
        {
            FMLLog.log(Level.SEVERE, e, "Mod '%s' experienced problems while loading its configuration.", Reference.MOD_ID);
        }
        finally
        {
            configuration.save();
        }
    }


    public static void set(String categoryName, String propertyName, String newValue)
    {
        configuration.load();
        if(configuration.getCategoryNames().contains(categoryName))
        {
            if(configuration.getCategory(categoryName).containsKey(propertyName))
            {
                configuration.getCategory(categoryName).get(propertyName).set(newValue);
            }
        }
        configuration.save();
    }
}
