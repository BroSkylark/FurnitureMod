package yafm.Handler;

import cpw.mods.fml.common.registry.GameRegistry;
import yafm.Blocks.BlockBreaker;
import yafm.Blocks.BlockToolRack;
import yafm.TileEntities.TileEntityToolRack;
import yafm.Utility.BlockIDs;
import yafm.Utility.Reference;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BlockHandler
{
    public static Block toolRack;
    public static Block blockBreaker;
    
    public static void initBlocks()
    {
        toolRack = new BlockToolRack(BlockIDs.BLOCK_TOOLRACK_ID);
        blockBreaker = new BlockBreaker(BlockIDs.BLOCK_TOOLRACK_ID - 1);
    }
    
    public static void registerBlocks()
    {
        GameRegistry.registerBlock(toolRack, Reference.BLOCK_TOOLRACK_NAME);
        GameRegistry.registerBlock(blockBreaker, "breaker");
    }

    public static void registerTileEntities()
    {
        GameRegistry.registerTileEntity(TileEntityToolRack.class, Reference.TE_TOOLRACK_NAME);
    }
    
    public static void registerRecipes()
    {
        GameRegistry.addRecipe(new ItemStack(toolRack, 2), new Object[] {
           "###", " $ ", "###", '#', Item.stick, '$', Item.ingotIron 
        });
        GameRegistry.addRecipe(new ItemStack(blockBreaker), new Object[] {
           "SIS", "SPS", "SRS", 'S', Block.cobblestone, 'I', Item.ingotIron, 'P', Block.pistonBase, 'R', Item.redstone 
        });
    }
    
    public static void registerTileEntitySpecialRenderers()
    {
        
    }
}
