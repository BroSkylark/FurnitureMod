package yafm.Blocks;

import yafm.Utility.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.Facing;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockToolRack extends Block
{
    public BlockToolRack(int id)
    {
        super(id, Material.wood);
        this.setUnlocalizedName(Reference.BLOCK_TOOLRACK_NAME);
        this.setHardness(2F);
        this.setCreativeTab(CreativeTabs.tabBlock);
        this.setStepSound(soundWoodFootstep);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon(Reference.BLOCK_TOOLRACK_ICONID);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int side, int meta)
    {
        if(side == meta) return Block.bedrock.getIcon(side, 0);

        return super.getIcon(side, meta);
    }
    
    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int side)
    {
        super.onNeighborBlockChange(world, x, y, z, side);
        
        if(!canBlockStay(world, x, y, z))
        {
            world.setBlockToAir(x, y, z);
        }
    }
    
    @Override
    public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side)
    {
        return canBlockStayOnBlock(world, x + Facing.offsetsXForSide[side ^ 1], y, 
                z + Facing.offsetsZForSide[side ^ 1], side);
    }
    
    @Override
    public boolean canBlockStay(World world, int x, int y, int z)
    {
        return canPlaceBlockOnSide(world, x, y, z, world.getBlockMetadata(x, y, z) ^ 1);
    }

    @Override
    public int onBlockPlaced(World world, int x, int y, int z, int side, float dx, float dy, float dz, int l)
    {
        return side ^ 1;
    }
    
    private boolean canBlockStayOnBlock(World world, int x, int y, int z, int side)
    {
        return side >= 2 && world.isBlockOpaqueCube(x, y, z);
    }
}
