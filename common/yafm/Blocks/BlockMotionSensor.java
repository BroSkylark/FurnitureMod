package yafm.Blocks;

import java.util.List;
import java.util.Random;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import yafm.FurnitureMod;
import yafm.Library.Reference;
import yafm.Library.RenderIDs;
import yafm.TileEntities.TileEntityDirectional;
import yafm.TileEntities.TileEntityMotionSensor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMotionSensor extends BlockContainer
{
    public BlockMotionSensor(int id)
    {
        super(id, Material.circuits);
        this.setHardness(1F);
        this.setCreativeTab(FurnitureMod.tabFurniture);
        this.setStepSound(soundStoneFootstep);
        this.setUnlocalizedName(Reference.BLOCK_MOTIONSENSOR_NAME);
        //        this.setTickRandomly(true);
    }

    @Override
    public int tickRate(World world)
    {
        return 4;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving entityliving, ItemStack itemstack)
    {
        if(entityliving instanceof EntityPlayer)
        {
            world.setBlockMetadataWithNotify(x, y, z, TileEntityDirectional.determineOrientation(world, x, y, z, 
                   (EntityPlayer) entityliving), Reference.FLAG_BLOCKSET_UPDATEBLOCK | Reference.FLAG_BLOCKSET_UPDATECLIENTS);
        }

        super.onBlockPlacedBy(world, x, y, z, entityliving, itemstack);
        world.scheduleBlockUpdate(x, y, z, blockID, tickRate(world));
    }

    protected boolean getState(int i)
    {
        return ((i >> 3) & 1) == 1;
    }

    protected int setState(int i, boolean b)
    {
        if(b)
        {
            return i | 8;
        } else
        {
            return i & (~8);
        }
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, int bID, int m)
    {
        world.setBlockMetadataWithNotify(x, y, z, setState(world.getBlockMetadata(x, y, z), false), 
                Reference.FLAG_BLOCKSET_NONE);
        world.notifyBlocksOfNeighborChange(x + 1, y, z, blockID);
        world.notifyBlocksOfNeighborChange(x - 1, y, z, blockID);
        world.notifyBlocksOfNeighborChange(x, y + 1, z, blockID);
        world.notifyBlocksOfNeighborChange(x, y - 1, z, blockID);
        world.notifyBlocksOfNeighborChange(x, y, z + 1, blockID);
        world.notifyBlocksOfNeighborChange(x, y, z - 1, blockID);
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random random)
    {
        int meta = world.getBlockMetadata(x, y, z);
        int facing = getOrientation(meta);
        int nx = x, ny = y, nz = z;

        if(facing == 0 || facing == 1)
        {
            int max = (facing == 0 ? (y <= 50 ? y - 1 : 50) : (y >= 78 ? 126 - y : 50));
            ny = findNextBlockIn(world, x, y, z, facing, max);

            if(ny == y || (Math.abs(ny - y) > 5 && world.getBlockId(x, ny, z) != blockID))
            {
                ny = y + (facing == 0 ? -5 : 5);
            }
            nx++;
            nz++;
        }
        else if(facing == 2 || facing == 3)
        {
            nz = findNextBlockIn(world, x, y, z, facing, 50);

            if(nz == z || (Math.abs(nz - z) > 5 && world.getBlockId(x, y, nz) != blockID))
            {
                nz = z + (facing == 2 ? -5 : 5);
            }
            if(y < 127)
                ny++;
            nx++;
        } 
        else if(facing == 4 || facing == 5)
        {
            nx = findNextBlockIn(world, x, y, z, facing, 50);

            if(nx == x || (max(nx, x) - min(nx, x) > 5 && world.getBlockId(nx, y, z) != blockID))
            {
                nx = x + (facing == 4 ? -5 : 5);
            }

            if(y < 127)
                ny++;
            nz++;
        }
        else
            return;

        AxisAlignedBB tmp = AxisAlignedBB.getBoundingBox(min(x, nx), min(y, ny), min(z, nz), max(x, nx), max(y, ny), 
                max(z, nz));
        List<?> list = world.getEntitiesWithinAABB(EntityLiving.class, tmp);

        int id = world.getBlockId(x + (facing == 4 ? -1 : (facing == 5 ? 1 : 0)), 
                y + (facing == 0 ? -1 : (facing == 1 ? 1 : 0)),
                z + (facing == 2 ? -1 : (facing == 3 ? 1 : 0)));
        boolean isActivated = (list.size() > 0 || (id != 0 && Block.blocksList[id].blockMaterial == Material.plants));

        world.setBlockMetadataWithNotify(x, y, z, setState(meta, isActivated), Reference.FLAG_BLOCKSET_UPDATEBLOCK |
                Reference.FLAG_BLOCKSET_UPDATECLIENTS);

        if(isActivated && !getState(meta) || !isActivated && getState(meta))
        {
            world.notifyBlocksOfNeighborChange(x + 1, y, z, blockID);
            world.notifyBlocksOfNeighborChange(x - 1, y, z, blockID);
            world.notifyBlocksOfNeighborChange(x, y + 1, z, blockID);
            world.notifyBlocksOfNeighborChange(x, y - 1, z, blockID);
            world.notifyBlocksOfNeighborChange(x, y, z + 1, blockID);
            world.notifyBlocksOfNeighborChange(x, y, z - 1, blockID);
        }

        world.scheduleBlockUpdate(x, y, z, blockID, tickRate(world) + (isActivated ? 6 : 0));
        super.updateTick(world, x, y, z, random);
    }

    public static int getOrientation(int i)
    {
        return i & 7;
    }

    private int max(int i, int j)
    {
        return (i > j ? i : j);
    }

    private int min(int i, int j)
    {
        return (i < j ? i : j);
    }

    private int findNextBlockIn(World world, int x, int y, int z, int l, int max)
    {
        if(l == 0 || l == 1)
        {
            int oy = 0;
            boolean flag = false;

            for(int i = 1 ; i <= max ; i++)
            {
                oy = (l == 0 ? -i : i);
                int id = world.getBlockId(x, y + oy, z);
                Material m = world.getBlockMaterial(x, y + oy, z);

                if(id == blockID || (id != 0 && Block.blocksList[id].blockMaterial == Material.plants) || 
                        (id != 0 && (Block.blocksList[id].isOpaqueCube() && m != Material.glass && m != Material.circuits)))
                {
                    flag = true;
                    break;
                }
            }

            if(!flag)
                return y;

            return oy + y;
        }
        else if(l == 2 || l == 3)
        {
            int oz = 0;
            boolean flag = false;

            for(int i = 1 ; i <= max ; i++)
            {
                oz = (l == 2 ? -i : i);
                int id = world.getBlockId(x, y, z + oz);
                Material m = world.getBlockMaterial(x, y, z + oz);

                if(id == blockID || (id != 0 && Block.blocksList[id].blockMaterial == Material.plants) || 
                        (id != 0 && (Block.blocksList[id].isOpaqueCube() && m != Material.glass && m != Material.circuits)))
                {
                    flag = true;
                    break;
                }
            }

            if(!flag)
                return z;

            return oz + z;
        }
        else if(l == 4 || l == 5)
        {
            int ox = 0;
            boolean flag = false;

            for(int i = 1 ; i <= max ; i++)
            {
                ox = (l == 4 ? -i : i);
                int id = world.getBlockId(x + ox, y, z);
                Material m = world.getBlockMaterial(x + ox, y, z);

                if(id == blockID || (id != 0 && Block.blocksList[id].blockMaterial == Material.plants) || 
                        (id != 0 && (Block.blocksList[id].isOpaqueCube() && m != Material.glass && m != Material.circuits)))
                {
                    flag = true;
                    break;
                }
            }

            if(!flag)
                return x;

            return ox + x;
        }

        return 0;
    }

    @Override
    public boolean canProvidePower()
    {
        return true;
    }

    @Override
    public int isProvidingStrongPower(IBlockAccess iblockaccess, int i, int j, int k, int l)
    {
        int meta = iblockaccess.getBlockMetadata(i, j, k);
        int facing = getOrientation(meta);

        if(!getState(meta))
            return 0;

        if(facing < 2 && l > 1)
        {
            return 15;
        }
        if(l > 1 && facing > 1)
        {
            return 15;
        }

        return 0;
    }

    @Override
    public int isProvidingWeakPower(IBlockAccess iblockaccess, int i, int j, int k, int l)
    {
        int meta = iblockaccess.getBlockMetadata(i, j, k);
        int facing = getOrientation(meta);

        if(!getState(meta))
            return 0;

        return facing == l ? 15 : 0;
    }

    @Override
    public int getRenderType()
    {
        return RenderIDs.BLOCK_MOTIONSENSOR_RENDER_ID;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister iconregister)
    {
    }
    
    @Override
    public void setBlockBoundsForItemRender()
    {
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public void addCollisionBoxesToList(World world, int i, int j, int k, AxisAlignedBB axisalignedbb, 
            @SuppressWarnings("rawtypes") List arraylist, Entity entity)
    {
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        super.addCollisionBoxesToList(world, i, j, k, axisalignedbb, arraylist, entity);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess iblockaccess, int i, int j, int k)
    {
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }
    
    @Override
    public Icon getIcon(int side, int meta)
    {
        return Block.pistonBase.getIcon(side, getOrientation(meta));
    }

    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new TileEntityMotionSensor();
    }
}
