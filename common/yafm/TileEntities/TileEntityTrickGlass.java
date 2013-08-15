package yafm.TileEntities;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityTrickGlass extends TileEntityDirectional
{
    public static final Block usedBlocks[] = new Block[] { 
        Block.glass, Block.dirt, Block.stone, Block.cobblestone, Block.brick, Block.planks, Block.blockClay, Block.blockDiamond, 
        Block.blockEmerald, Block.blockGold,
        Block.blockIron, Block.blockLapis, Block.blockNetherQuartz, Block.blockRedstone, Block.bookShelf, Block.cobblestoneMossy,
        Block.glowStone, Block.gravel, Block.netherBrick, Block.netherrack, Block.obsidian, Block.oreCoal,
        Block.oreDiamond, Block.oreEmerald, Block.oreGold, Block.oreIron, Block.oreLapis, Block.oreNetherQuartz,
        Block.sand, Block.slowSand, Block.snow, Block.sponge, Block.stoneBrick, Block.whiteStone
        };
    private int blockID;
    
    public TileEntityTrickGlass()
    {
        super(true);
        blockID = -1;
    }
    
    public Block getBlock()
    {
        return usedBlocks[blockID];
    }
    
    public int getBlockID()
    {
        return blockID;
    }
    
    public TileEntityTrickGlass setBlock(int bID)
    {
        blockID = bID;
        return this;
    }
    
    @Override
    public void saveInNBT(NBTTagCompound nbttagcompound)
    {
        nbttagcompound.setInteger(TAG_BID, blockID);
    }
    
    @Override
    public void restoreFromNBT(NBTTagCompound nbttagcompound)
    {
        blockID = nbttagcompound.getInteger(TAG_BID);
    }
    
    private static final String TAG_BID = "blockID";
}
