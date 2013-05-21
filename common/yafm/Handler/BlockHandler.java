package yafm.Handler;

import cpw.mods.fml.common.registry.GameRegistry;
import yafm.Blocks.BlockToolRack;
import yafm.Utility.BlockIDs;
import yafm.Utility.Reference;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BlockHandler
{
    public static Block toolRack;
    
    public static void execute()
    {
        initBlocks();
        registerBlocks();
        registerRecipes();
    }
    
    private static void initBlocks()
    {
        toolRack = new BlockToolRack(BlockIDs.BLOCK_TOOLRACK_ID);
    }
    
    private static void registerBlocks()
    {
        GameRegistry.registerBlock(toolRack, Reference.BLOCK_TOOLRACK_NAME);
    }
    
    private static void registerRecipes()
    {
        GameRegistry.addRecipe(new ItemStack(toolRack, 2), new Object[] {
           "###", " $ ", "###", '#', Item.stick, '$', Item.ingotIron 
        });
    }
}
