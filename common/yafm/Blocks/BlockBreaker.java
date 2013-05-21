package yafm.Blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import yafm.FurnitureMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.Facing;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockBreaker extends Block
{
    public BlockBreaker(int id)
    {
        super(id, Material.rock);
        this.setUnlocalizedName("breaker");
        this.setHardness(2F);
        this.setCreativeTab(FurnitureMod.tabFurniture);
        this.setStepSound(soundStoneFootstep);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int side, int meta)
    {
        if(side == (meta & 7)) return Block.bedrock.getIcon(side, 0);

        return Block.obsidian.getIcon(side, 0);
    }
    
    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int side)
    {
        super.onNeighborBlockChange(world, x, y, z, side);
        int m = world.getBlockMetadata(x, y, z);
        boolean b = world.isBlockIndirectlyGettingPowered(x, y, z);
        
        if(b && ((m & 8) == 0))
        {
            world.setBlockMetadataWithNotify(x, y, z, m | 8, 3);
            
            x += Facing.offsetsXForSide[m & 7];
            y += Facing.offsetsYForSide[m & 7];
            z += Facing.offsetsZForSide[m & 7];
            
            int bid = world.getBlockId(x, y, z);
            if(bid > 0)
            {
                Block.blocksList[bid].dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
                world.setBlockToAir(x, y, z);
            }
        }
        else if(!b && ((m & 8) != 0))
        {
            world.setBlockMetadataWithNotify(x, y, z, m & 7, 3);
        }
    }
    
    @Override
    public int onBlockPlaced(World world, int x, int y, int z, int side, float dx, float dy, float dz, int l)
    {
        return side ^ 1;
    }
}
