package yafm.Handler;

import java.io.File;
import java.util.logging.Level;
import yafm.Utility.BlockIDs;
import yafm.Utility.Reference;
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
            BlockIDs.BLOCK_TOOLRACK_ID = configuration.getBlock(Reference.BLOCK_TOOLRACK_NAME, 
                    BlockIDs.BlOCK_TOOLRACK_ID_DEFAULT).getInt(BlockIDs.BlOCK_TOOLRACK_ID_DEFAULT);
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
