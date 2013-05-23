package yafm.Handler;

import cpw.mods.fml.common.registry.LanguageRegistry;
import yafm.Library.Reference;

public class LocalizationHandler
{
    private static final String LOCALIZATION_DIR = "/mods/" + Reference.MOD_ID + "/lang/";
    
    private static final String[] LOCALIZATIONS = new String[] {
        LOCALIZATION_DIR + "en_US.xml"
    };
    
    public static void execute()
    {
        for(String path : LOCALIZATIONS)
        {
            LanguageRegistry.instance().loadLocalization(path, getFileName(path), isXMLFile(path));
        }
    }
    
    private static String getFileName(String path)
    {
        return path.substring(path.lastIndexOf('/') + 1, path.lastIndexOf('.'));
    }
    
    private static boolean isXMLFile(String path)
    {
        return path.endsWith(".xml");
    }
}
