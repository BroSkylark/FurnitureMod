package yafm.Handler;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import yafm.Blocks.BlockPotionStand;
import yafm.Blocks.BlockToolRack;
import yafm.Library.BlockIDs;
import yafm.Library.Reference;
import yafm.Library.RenderIDs;
import yafm.Renderers.RendererPotionStand;
import yafm.Renderers.RendererToolRack;
import yafm.TileEntities.TileEntityPotionStand;
import yafm.TileEntities.TileEntityToolRack;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BlockHandler
{
    public static Block toolRack;
    public static Block potionStand;
    
    public static void initBlocks()
    {
        toolRack = new BlockToolRack(BlockIDs.BLOCK_TOOLRACK_ID);
        potionStand = new BlockPotionStand(BlockIDs.BLOCK_POTIONSTAND_ID);
    }
    
    public static void registerBlocks()
    {
        GameRegistry.registerBlock(toolRack, Reference.BLOCK_TOOLRACK_NAME);
        GameRegistry.registerBlock(potionStand, Reference.BLOCK_POTIONSTAND_NAME);
    }

    public static void registerTileEntities()
    {
        GameRegistry.registerTileEntity(TileEntityToolRack.class, Reference.TE_TOOLRACK_NAME);
        GameRegistry.registerTileEntity(TileEntityPotionStand.class, Reference.TE_POTIONSTAND_NAME);
    }
    
    public static void registerRecipes()
    {
        GameRegistry.addRecipe(new ItemStack(toolRack, 2), new Object[] {
           "###", " $ ", "###", '#', Item.stick, '$', Item.ingotIron 
        });
        GameRegistry.addRecipe(new ItemStack(potionStand, 2), new Object[] {
           "#$#", "$$$", "#$#", '#', Block.planks, '$', Item.stick 
        });
    }
    
    public static void registerRenderingIDs()
    {
        RenderIDs.TE_TOOLRACK_ID = RenderingRegistry.getNextAvailableRenderId();
        RenderIDs.TE_POTIONSTAND_ID = RenderingRegistry.getNextAvailableRenderId();
    }
    
    public static void registerBlockRenderers()
    {
        RenderingRegistry.registerBlockHandler(new RendererToolRack());
        RenderingRegistry.registerBlockHandler(new RendererPotionStand());
    }
    
    public static void registerTileEntitySpecialRenderers()
    {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityToolRack.class, new RendererToolRack());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPotionStand.class, new RendererPotionStand());
    }
}
