package yafm.Blocks;

import java.util.List;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import yafm.FurnitureMod;
import yafm.Handler.BlockHandler;
import yafm.Library.Reference;
import yafm.Library.RenderIDs;
import yafm.TileEntities.TileEntityPotionStand;
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
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class BlockPotionStand extends BlockContainer
{
    public BlockPotionStand(int id)
    {
        super(id, Material.wood);
        this.setUnlocalizedName(Reference.BLOCK_POTIONSTAND_NAME);
        this.setHardness(1.5F);
        this.setCreativeTab(FurnitureMod.tabFurniture);
        this.setStepSound(soundWoodFootstep);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7,
            float par8, float par9)
    {
        if(player.isSneaking()) return false;
        
        return ((TileEntityPotionStand) world.getBlockTileEntity(x, y, z)).click(player);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister)
    {
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int side, int meta)
    {
        return Block.planks.getIcon(side, 0);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess iblockaccess, int x, int y, int z)
    {
        int m = iblockaccess.getBlockMetadata(x, y, z);
        float f = 5F / 16F;
        
        setBlockBounds(m == 5 ? 1 - f : 0, 0, m == 3 ? 1 - f : 0, m == 4 ? f : 1, 1, m == 2 ? f : 1);
    }

    @Override
    public void setBlockBoundsForItemRender()
    {
        setBlockBounds(1F - 5F / 16F, 0, 0, 1, 1, 1);
    }

    @Override
    public void addCollisionBoxesToList(World world, int i, int j, int k, AxisAlignedBB axisalignedbb, 
            @SuppressWarnings("rawtypes") List arraylist, Entity entity)
    {
        setBlockBoundsBasedOnState(world, i, j, k);
        super.addCollisionBoxesToList(world, i, j, k, axisalignedbb, arraylist, entity);
    }

    @Override
    public boolean isBlockSolidOnSide(World world, int x, int y, int z, ForgeDirection side)
    {
        return side == ForgeDirection.getOrientation(world.getBlockMetadata(x, y, z));
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving entityliving, ItemStack itemstack)
    {
        int d = getDirection(entityliving) ^ 1;
        world.setBlockMetadataWithNotify(x, y, z, d, Reference.FLAG_BLOCKSET_UPDATEBLOCK | Reference.FLAG_BLOCKSET_UPDATECLIENTS);
        ((TileEntityPotionStand)  world.getBlockTileEntity(x, y, z)).setDirection(d);
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public int getRenderType()
    {
        return RenderIDs.BLOCK_POTIONSTAND_RENDER_ID;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, int id, int meta)
    {
        TileEntityPotionStand teps = (TileEntityPotionStand) world.getBlockTileEntity(x, y, z);
        
        for(int i = 0 ; i < TileEntityPotionStand.COLS * TileEntityPotionStand.ROWS ; i++)
        {
            ItemStack is = teps.getPotions(i);
            if(is == null) continue;
            
            while(is.stackSize > 0)
            {
                dropBlockAsItem_do(world, x, y, z, is.splitStack(1));
            }
        }
        
        world.notifyBlocksOfNeighborChange(x, y, z, BlockHandler.potionStand.blockID);
        super.breakBlock(world, x, y, z, id, meta);
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int side)
    {
        TileEntityPotionStand teps = (TileEntityPotionStand) world.getBlockTileEntity(x, y, z);
        ItemStack d[] = teps.getNowInvalidPotions();
        
        for(int i = 0 ; i < d.length ; i++)
        {
            if(d[i] != null)
            {
                while(d[i].stackSize > 0)
                {
                    dropBlockAsItem_do(world, x, y, z, d[i].splitStack(1));
                }
            }
        }
        
        world.markBlockForUpdate(x, y, z);
    }
    
    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new TileEntityPotionStand();
    }
    
    public static int getDirection(EntityLiving entityliving)
    {
        int direction = 0;
        int facing = MathHelper.floor_double(entityliving.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
        if(facing == 0)
        {
            direction = ForgeDirection.NORTH.ordinal();
        }
        else if(facing == 1)
        {
            direction = ForgeDirection.EAST.ordinal();
        }
        else if(facing == 2)
        {
            direction = ForgeDirection.SOUTH.ordinal();
        }
        else if(facing == 3)
        {
            direction = ForgeDirection.WEST.ordinal();
        }
        
        return direction;
    }
}
