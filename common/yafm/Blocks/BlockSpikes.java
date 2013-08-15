package yafm.Blocks;

import java.util.Random;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import yafm.FurnitureMod;
import yafm.Library.Reference;
import yafm.Library.RenderIDs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Facing;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSpikes extends Block
{
    private static final String SPIKE_NAMES[] = new String[] {Reference.BLOCK_SPIKESNORMAL_NAME, 
        Reference.BLOCK_SPIKESPOISON_NAME, Reference.BLOCK_SPIKESDIAMOND_NAME};
    private Icon icons[];
    private int spikeType;
    
    public BlockSpikes(int id, int type)
    {
        super(id, Material.iron);
        this.setUnlocalizedName(SPIKE_NAMES[type]);
        this.setCreativeTab(FurnitureMod.tabFurniture);
        this.setHardness(8F);
        
        icons = new Icon[3];
        spikeType = type;
    }
    
    @Override
    public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta)
    {
        return side;
    }
    
    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
    {
        if(!(entity instanceof EntityLiving)) return;

        int effect = (int) Math.pow(2, world.difficultySetting);
        int duration = world.difficultySetting == 3 ? 5 : effect * 2;

        switch(spikeType)
        {
            case 0:
                entity.attackEntityFrom(DamageSource.cactus, effect);
                break;
            case 1:
                entity.attackEntityFrom(DamageSource.cactus, effect);
                ((EntityLiving)entity).addPotionEffect(new PotionEffect(Potion.poison.id, duration * 20, 
                        world.difficultySetting == 3 ? 1 : 0));
                break;
            case 2:
                entity.attackEntityFrom(DamageSource.cactus, effect * (6 - world.difficultySetting));
                break;
        }
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess iblockaccess, int x, int y, int z)
    {
        float bounds[] = getBlockBounds(iblockaccess.getBlockMetadata(x, y, z));

        setBlockBounds(bounds[0], bounds[1], bounds[2], bounds[3], bounds[4], bounds[5]);
    }

    @Override
    public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side)
    {
        return super.canPlaceBlockAt(world, x, y, z) && canBlockStay(world, x, y, z, side);
    }
    
    @Override
    public boolean canBlockStay(World world, int x, int y, int z)
    {
        return canBlockStay(world, x, y, z, world.getBlockMetadata(x, y, z));
    }
    
    private boolean canBlockStay(World world, int x, int y, int z, int meta)
    {
        int d = meta ^ 1;
        Block place = blocksList[world.getBlockId(x + Facing.offsetsXForSide[d], 
                y + Facing.offsetsYForSide[d], z + Facing.offsetsZForSide[d])];
        return place != null && place.isOpaqueCube();
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int meta)
    {
        super.onNeighborBlockChange(world, x, y, z, meta);
        this.checkPlaceChange(world, x, y, z);
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random par5Random)
    {
        this.checkPlaceChange(world, x, y, z);
    }
    
    protected final void checkPlaceChange(World world, int x, int y, int z)
    {
        if (!this.canBlockStay(world, x, y, z))
        {
            this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
            world.setBlockToAir(x, y, z);
        }
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
    {
        return null;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister iconregister)
    {
        icons[0] = iconregister.registerIcon(Reference.getIconID(Reference.BLOCK_SPIKESNORMAL_NAME));
        icons[1] = iconregister.registerIcon(Reference.getIconID(Reference.BLOCK_SPIKESPOISON_NAME));
        icons[2] = iconregister.registerIcon(Reference.getIconID(Reference.BLOCK_SPIKESDIAMOND_NAME));
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public Icon getIcon(int side, int meta)
    {
        return icons[spikeType];
    }
    
    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public int getRenderType()
    {
        return RenderIDs.BLOCK_SPIKES_RENDER_ID;
    }

    private float[] getBlockBounds(int m)
    {
        float ret[] = {0, 0, 0, 1, 1, 1};

        float f = 0.0625f;
        
        if((m & ~1) == 0)
        {
            ret[0] = f;
            ret[1] = (m == 0 ? 0.7f : 0);
            ret[2] = f;
            ret[3] = 1 - f;
            ret[4] = (m == 0 ? 1 : 0.3f);
            ret[5] = 1 - f;
        }
        else if((m & ~1) == 2)
        {
            ret[0] = f;
            ret[1] = f;
            ret[2] = (m == 2 ? 0.7f : 0);
            ret[3] = 1 - f;
            ret[4] = 1 - f;
            ret[5] = (m == 2 ? 1 : 0.3f);
        }
        else if((m & ~1) == 4)
        {
            ret[0] = (m == 4 ? 0.7f : 0);
            ret[1] = f;
            ret[2] = f;
            ret[3] = (m == 4 ? 1 : 0.3f);
            ret[4] = 1 - f;
            ret[5] = 1 - f;
        }
        
        return ret;
    }
}
