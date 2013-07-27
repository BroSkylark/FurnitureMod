package yafm.Handler;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import yafm.Blocks.BlockMotionSensor;
import yafm.Blocks.BlockPotionStand;
import yafm.Blocks.BlockSpikes;
import yafm.Blocks.BlockToolRack;
import yafm.Blocks.BlockTrickGlass;
import yafm.Library.BlockIDs;
import yafm.Library.Reference;
import yafm.Library.RenderIDs;
import yafm.Renderers.RendererMotionSensor;
import yafm.Renderers.RendererPotionStand;
import yafm.Renderers.RendererSpikes;
import yafm.Renderers.RendererToolRack;
import yafm.TileEntities.TileEntityMotionSensor;
import yafm.TileEntities.TileEntityPotionStand;
import yafm.TileEntities.TileEntityToolRack;
import yafm.TileEntities.TileEntityTrickGlass;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BlockHandler
{
    public static Block toolRack;
    public static Block potionStand;
    public static Block motionSensor;
    public static Block trickGlass;
    public static Block passableGlass;
    public static Block spikesNormal;
    public static Block spikesPoison;
    public static Block spikesDiamond;
    
    public static void initBlocks()
    {
        toolRack = new BlockToolRack(BlockIDs.BLOCK_TOOLRACK_ID);
        potionStand = new BlockPotionStand(BlockIDs.BLOCK_POTIONSTAND_ID);
        motionSensor = new BlockMotionSensor(BlockIDs.BLOCK_MOTIONSENSOR_ID);
        trickGlass = new BlockTrickGlass(BlockIDs.BLOCK_TRICKGLASS_ID, false);
        passableGlass = new BlockTrickGlass(BlockIDs.BLOCK_PASSABLEGLASS_ID, true);
        spikesNormal = new BlockSpikes(BlockIDs.BLOCK_SPIKESNORMAL_ID, 0);
        spikesPoison = new BlockSpikes(BlockIDs.BLOCK_SPIKESPOISON_ID, 1);
        spikesDiamond = new BlockSpikes(BlockIDs.BLOCK_SPIKESDIAMOND_ID, 2);
    }
    
    public static void registerBlocks()
    {
        GameRegistry.registerBlock(toolRack, Reference.BLOCK_TOOLRACK_NAME);
        GameRegistry.registerBlock(potionStand, Reference.BLOCK_POTIONSTAND_NAME);
        GameRegistry.registerBlock(motionSensor, Reference.BLOCK_MOTIONSENSOR_NAME);
        GameRegistry.registerBlock(trickGlass, Reference.BLOCK_TRICKGLASS_NAME);
        GameRegistry.registerBlock(passableGlass, Reference.BLOCK_PASSABLEGLASS_NAME);
        GameRegistry.registerBlock(spikesNormal, Reference.BLOCK_SPIKESNORMAL_NAME);
        GameRegistry.registerBlock(spikesPoison, Reference.BLOCK_SPIKESPOISON_NAME);
        GameRegistry.registerBlock(spikesDiamond, Reference.BLOCK_SPIKESDIAMOND_NAME);
    }

    public static void registerTileEntities()
    {
        GameRegistry.registerTileEntity(TileEntityToolRack.class, Reference.TE_TOOLRACK_NAME);
        GameRegistry.registerTileEntity(TileEntityPotionStand.class, Reference.TE_POTIONSTAND_NAME);
        GameRegistry.registerTileEntity(TileEntityMotionSensor.class, Reference.TE_MOTIONSENSOR_NAME);
        GameRegistry.registerTileEntity(TileEntityTrickGlass.class, Reference.TE_TRICKGLASS_NAME);
    }
    
    public static void registerRecipes()
    {
        GameRegistry.addRecipe(new ItemStack(toolRack, 2), new Object[] {
           "###", " $ ", "###", '#', Item.stick, '$', Item.ingotIron 
        });
        GameRegistry.addRecipe(new ItemStack(potionStand, 2), new Object[] {
           "#$#", "$$$", "#$#", '#', Block.planks, '$', Item.stick 
        });
        GameRegistry.addRecipe(new ItemStack(motionSensor, 1), new Object[] {
            "###", "$@$", "$@$", '#', Item.netherQuartz, '$', Block.cobblestone, '@', Item.redstone
        });
        for(int i = 1 ; i < TileEntityTrickGlass.usedBlocks.length ; i++)
        {
            GameRegistry.addRecipe(new ItemStack(trickGlass, 6, i), new Object[] {
               "###", "$$$", "$$$", '#', TileEntityTrickGlass.usedBlocks[i], '$', Block.glass 
            });
            GameRegistry.addRecipe(new ItemStack(passableGlass, 6, i), new Object[] {
               "###", "$$$", "$$$", '#', TileEntityTrickGlass.usedBlocks[i], '$', new ItemStack(passableGlass, 1, 0) 
            });
        }
        GameRegistry.addRecipe(new ItemStack(spikesNormal, 4), new Object[] {
           " # ", "###", '#', Item.ingotIron 
        });
        GameRegistry.addShapelessRecipe(new ItemStack(spikesPoison), new Object[] {
           spikesNormal, Item.spiderEye 
        });
        GameRegistry.addShapelessRecipe(new ItemStack(spikesDiamond), new Object[] {
           spikesNormal, Item.diamond
        });
    }
    
    public static void registerRenderingIDs()
    {
        RenderIDs.BLOCK_TOOLRACK_RENDER_ID = RenderingRegistry.getNextAvailableRenderId();
        RenderIDs.BLOCK_POTIONSTAND_RENDER_ID = RenderingRegistry.getNextAvailableRenderId();
        RenderIDs.BLOCK_MOTIONSENSOR_RENDER_ID = RenderingRegistry.getNextAvailableRenderId();
        RenderIDs.BLOCK_TRICKGLASS_RENDER_ID = RenderingRegistry.getNextAvailableRenderId();
        RenderIDs.BLOCK_SPIKES_RENDER_ID = RenderingRegistry.getNextAvailableRenderId();
    }
    
    public static void registerBlockRenderers()
    {
        RenderingRegistry.registerBlockHandler(new RendererToolRack());
        RenderingRegistry.registerBlockHandler(new RendererPotionStand());
        RenderingRegistry.registerBlockHandler(new RendererMotionSensor());
        RenderingRegistry.registerBlockHandler(new RendererSpikes());
    }
    
    public static void registerTileEntitySpecialRenderers()
    {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityToolRack.class, new RendererToolRack());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPotionStand.class, new RendererPotionStand());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMotionSensor.class, new RendererMotionSensor());
    }
}
