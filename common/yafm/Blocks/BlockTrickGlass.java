package yafm.Blocks;

import java.util.List;
import java.util.Random;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import yafm.Library.Reference;
import yafm.TileEntities.TileEntityDirectional;
import yafm.TileEntities.TileEntityTrickGlass;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTrickGlass extends BlockContainer
{
    private boolean isPassable;
    
    public BlockTrickGlass(int id, boolean passable)
    {
        super(id, Material.glass);
        this.setUnlocalizedName(passable ? Reference.BLOCK_PASSABLEGLASS_NAME : Reference.BLOCK_TRICKGLASS_NAME);
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.setHardness(1F);
        this.setStepSound(soundGlassFootstep);
        
        isPassable = passable;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
    {
        if(isPassable && !world.isBlockIndirectlyGettingPowered(x, y, z))
            return null;
        else
            return super.getCollisionBoundingBoxFromPool(world, x, y, z);
    }
    
    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z)
    {
        return new ItemStack(blockID, 1, ((TileEntityTrickGlass) world.getBlockTileEntity(x, y, z)).getBlockID());
    }
    
    @Override
    public int idDropped(int i, Random random, int j)
    {
        return 0;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void getSubBlocks(int id, CreativeTabs creativeTab, @SuppressWarnings("rawtypes") List items)
    {
        if(isPassable && !Loader.isModLoaded(Reference.ADDON_TC_ID)) return;
        
        for(int i = 0 ; i < TileEntityTrickGlass.usedBlocks.length ; i++)
        {
            if(!isPassable && TileEntityTrickGlass.usedBlocks[i].blockID == Block.glass.blockID) continue;
            
            items.add(new ItemStack(id, 1, i));
        }
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int x, int y, int z, int side)
    {
        TileEntityTrickGlass tetg = (TileEntityTrickGlass) iblockaccess.getBlockTileEntity(
                x + Facing.offsetsXForSide[side ^ 1], y + Facing.offsetsYForSide[side ^ 1], z + Facing.offsetsZForSide[side ^ 1]);
        
        if(side == tetg.getDirection().ordinal())
        {
            return !iblockaccess.isBlockOpaqueCube(x, y, z);
        }
        else
        {
            int id = iblockaccess.getBlockId(x, y, z);
            return !iblockaccess.isBlockOpaqueCube(x, y, z) && id != Block.glass.blockID && id != blockID;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister)
    {
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getBlockTexture(IBlockAccess iblockaccess, int x, int y, int z, int side)
    {
        TileEntityTrickGlass tetg = (TileEntityTrickGlass) iblockaccess.getBlockTileEntity(x, y, z);
        
        return tetg.getDirection().ordinal() == side ? tetg.getBlock().getIcon(side, 0) : Block.glass.getIcon(side, 0);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int side, int meta)
    {
        meta = meta < 0 ? 0 : meta % TileEntityTrickGlass.usedBlocks.length;
        return side == 4 ? TileEntityTrickGlass.usedBlocks[meta].getIcon(side, 0) : Block.glass.getIcon(side, 0);
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving entityliving, ItemStack itemstack)
    {
        int d = TileEntityDirectional.determineOrientation(world, x, y, z, (EntityPlayer) entityliving);
        TileEntityTrickGlass tetg = (TileEntityTrickGlass) world.getBlockTileEntity(x, y, z);
        
        world.setBlockMetadataWithNotify(x, y, z, itemstack.getItemDamage(), Reference.FLAG_BLOCKSET_UPDATEBLOCK | 
                Reference.FLAG_BLOCKSET_UPDATECLIENTS);
        tetg.setBlock(itemstack.getItemDamage()).setDirection(d);
    }

    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new TileEntityTrickGlass();
    }
}
