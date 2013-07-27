package yafm.Blocks;

import java.util.List;
import yafm.FurnitureMod;
import yafm.Handler.BlockHandler;
import yafm.Library.Reference;
import yafm.Library.RenderIDs;
import yafm.TileEntities.TileEntityToolRack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;


public class BlockToolRack extends BlockContainer
{
    public BlockToolRack(int id)
    {
        super(id, Material.wood);
        this.setUnlocalizedName(Reference.BLOCK_TOOLRACK_NAME);
        this.setHardness(1.5F);
        this.setCreativeTab(FurnitureMod.tabFurniture);
        this.setStepSound(soundWoodFootstep);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess iblockaccess, int x, int y, int z)
    {
        int m = iblockaccess.getBlockMetadata(x, y, z);
        float f = 0.1875f;
        
        setBlockBounds(m == 5 ? 1 - f : 0, 0, m == 3 ? 1 - f : 0, m == 4 ? f : 1, 1, m == 2 ? f : 1);
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, int id, int meta)
    {
        TileEntityToolRack tetr = (TileEntityToolRack) world.getBlockTileEntity(x, y, z);
        
        for(int i = 0 ; tetr != null && i < TileEntityToolRack.TOOL_COUNT ; i++)
        {
            ItemStack is = tetr.getToolInSlot(i);
            
            if(is != null) dropBlockAsItem_do(world, x, y, z, is);
        }
        
        super.breakBlock(world, x, y, z, id, meta);
    }
    
    @Override
    public void setBlockBoundsForItemRender()
    {
        setBlockBounds(1F - 0.1875f, 0, 0, 1, 1, 1);
    }
    
    @Override
    public void addCollisionBoxesToList(World world, int i, int j, int k, AxisAlignedBB axisalignedbb, 
            @SuppressWarnings("rawtypes") List arraylist, Entity entity)
    {
        setBlockBoundsBasedOnState(world, i, j, k);
        super.addCollisionBoxesToList(world, i, j, k, axisalignedbb, arraylist, entity);
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
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
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7,
            float par8, float par9)
    {
        if(player.isSneaking()) return false;
        
        return ((TileEntityToolRack) world.getBlockTileEntity(x, y, z)).clickRack(player);
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
    public int getRenderType()
    {
        return RenderIDs.BLOCK_TOOLRACK_RENDER_ID;
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int side)
    {
        if(!canBlockStay(world, x, y, z))
        {
            dropBlockAsItem_do(world, x, y, z, new ItemStack(BlockHandler.toolRack));
            breakBlock(world, x, y, z, BlockHandler.toolRack.blockID, world.getBlockMetadata(x, y, z));
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
    
    @Override
    public void onPostBlockPlaced(World world, int x, int y, int z, int metadata)
    {
        ((TileEntityToolRack) world.getBlockTileEntity(x, y, z)).setDirection(metadata);
    }

    private boolean canBlockStayOnBlock(World world, int x, int y, int z, int side)
    {
        return side >= 2 && world.isBlockOpaqueCube(x, y, z);
    }

    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new TileEntityToolRack();
    }
}
