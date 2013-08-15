package yafm.TileEntities;

import java.util.ArrayList;
import java.util.List;
import yafm.Handler.BlockHandler;
import core.utils.Math.AnalyticGeometry.Line;
import core.utils.Math.AnalyticGeometry.Plane;
import core.utils.Math.AnalyticGeometry.Point;
import core.utils.Math.AnalyticGeometry.Vector;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;

public class TileEntityPotionStand extends TileEntityDirectional
{
    private ItemStack potions[];
    private Plane plane;

    public TileEntityPotionStand()
    {
        super(true);
        potions = new ItemStack[ROWS * COLS];
        plane = null;
    }

    public boolean click(EntityPlayer player)
    {
        if(plane == null) setDirection(getDirection());
        boolean r = player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().itemID == Item.potion.itemID &&
                ItemPotion.isSplash(player.getCurrentEquippedItem().getItemDamage());
        
        Point i = plane.intersects(new Line(Point.getPlayerEyeLocation(player), 
                Vector.getVecFromPitchAndYaw(player.rotationPitch, player.rotationYaw)));
        ForgeDirection d = getDirection();
        int dX = d.offsetX != 0 ? 0 : -1, dZ = d.offsetX != 0 ? 1 : 0;

        if(i == null)
        {
            (new RuntimeException("ERR: Didn't click potionstand.")).printStackTrace(System.err);
            return r;
        }

        double dx = (d.offsetX != 0 ? 1D - (i.Z() - zCoord) : i.X() - xCoord) * 16D;
        double dy = (i.Y() - yCoord) * 16D;
        int c = -1;

        if(dy <= SIZE_H * 0.5d + 0.5d)
        {
            if(isAcceptable(xCoord, yCoord - 1, zCoord))
            {
                return (((TileEntityPotionStand) worldObj.getBlockTileEntity(xCoord, yCoord - 1, zCoord)).click(player) || r);
            }
        }
        else if(dy > SIZE_H * 0.5d + 0.5d && dy <= SIZE_H * 1.5d + 1.5d)
        {
            c = 0 * COLS;
        }
        else if(dy > SIZE_H * 1.5d + 1.5d && dy <= SIZE_H * 2.5d + 2.5d)
        {
            c = 1 * COLS;
        }
        else if(dy > SIZE_H * 2.5d + 2.5d)
        {
            if(isAcceptable(xCoord, yCoord + 1, zCoord))
            {
                c = 2 * COLS;
            }
        }
        
        if(c < 0) return r;

        if(dx <= SIZE_W * 0.5d + 0.5d)
        {
            if(isAcceptable(xCoord + dX, yCoord, zCoord + dZ))
            {
                return (((TileEntityPotionStand) worldObj.getBlockTileEntity(xCoord + dX, yCoord, zCoord + dZ)).
                        click(player) || r);
            }
            else
            {
                c = -1;
            }
        }
        else if(dx > SIZE_W * 0.5d + 0.5d && dx <= SIZE_W * 1.5d + 1.5d)
        {
            c += 0;
        }
        else if(dx > SIZE_W * 1.5d + 1.5d && dx <= SIZE_W * 2.5d + 2.5d)
        {
            c += 1;
        }
        else if(dx > SIZE_W * 2.5d + 2.5d && dx <= SIZE_W * 3.5d + 3.5d)
        {
            c += 2;
        }
        else if(dx > SIZE_W * 3.5d + 3.5d)
        {
            if(isAcceptable(xCoord - dX, yCoord, zCoord - dZ))
            {
                c += 3;
            }
            else
            {
                c = -1;
            }
        }

        return c >= 0 ? (clickCompartiment(c, player) || r) : r;
    }

    private boolean clickCompartiment(int i, EntityPlayer player)
    {
        System.out.println("CLICKED COMP. " + i);
        
        if(potions[i] != null)
        {
            ItemStack is = player.getCurrentEquippedItem();

            if(is != null && is.stackSize >= 1 && is.itemID == Item.potion.itemID &&
                    is.getItemDamage() == potions[i].getItemDamage())
            {
                if(potions[i].stackSize < 3)
                {
                    potions[i].stackSize++;
                    player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
                }
                else
                {
                    return false;
                }
            }
            else
            {
                is = potions[i].splitStack(1);

                if(!tryToAddToInventory(player, is))
                {
                    is.stackSize++;
                    return false;
                }

                if(potions[i].stackSize == 0) potions[i] = null;
            }
        }
        else
        {
            ItemStack is = player.getCurrentEquippedItem();

            if(is == null || is.stackSize <= 0 || is.itemID != Item.potion.itemID) return false;

            potions[i] = is;
            player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
        }

        player.inventoryContainer.detectAndSendChanges();
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);

        return true;
    }
    
    private boolean isAcceptable(int x, int y, int z)
    {
        return worldObj.getBlockId(x, y, z) == BlockHandler.potionStand.blockID && 
                ((TileEntityPotionStand) worldObj.getBlockTileEntity(x, y, z)).getDirection().ordinal() == getDirection().ordinal();
    }
    
    public ItemStack getPotions(int i)
    {
        return potions[i];
    }
    
    public ItemStack[] getNowInvalidPotions()
    {
        int dx = getDirection().offsetX != 0 ? 0 : 1, dz = getDirection().offsetZ != 0 ? 0 : 1;
        List<ItemStack> l = new ArrayList<ItemStack>(12);
        
        if(worldObj.getBlockId(xCoord, yCoord + 1, zCoord) != BlockHandler.potionStand.blockID)
        {
            for(int i = 2 * COLS ; i < 2 * COLS + 4 ; i++)
            {
                if(potions[i] != null)
                {
                    l.add(potions[i]);
                    potions[i] = null;
                }
            }
        }
        
        if(worldObj.getBlockId(xCoord + dx, yCoord, zCoord + dz) != BlockHandler.potionStand.blockID)
        {
            for(int i = 0 ; i < 3 ; i++)
            {
                int j = i * COLS + 3;
                if(potions[j] != null)
                {
                    l.add(potions[j]);
                    potions[j] = null;
                }
            }
        }
        
        return l.toArray(new ItemStack[l.size()]);
    }

    @Override
    public TileEntityDirectional setDirection(ForgeDirection dir)
    {
        plane = (new Plane(new Point(xCoord + 0.5d, yCoord, zCoord + 0.5d), Vector.eY, 
                (dir.offsetX != 0 ? Vector.eZ : Vector.eX)))
                .apply(new Vector(dir.offsetX * PLANE_OFFSET, 0, dir.offsetZ * PLANE_OFFSET));

        return super.setDirection(dir);
    }
    
    @Override
    public void saveInNBT(NBTTagCompound nbttagcompound)
    {
        nbttagcompound.setCompoundTag(TAG_POTIONS, saveItemStackInList(potions));
    }
    
    @Override
    public void restoreFromNBT(NBTTagCompound nbttagcompound)
    {
        potions = restoreItemStackFromList(nbttagcompound.getCompoundTag(TAG_POTIONS));
        setDirection(getDirection());
    }

    public static final int ROWS = 3, COLS = 4;
    private static final float PLANE_OFFSET = 0.5f * (1F - (2F / 16F));
    private static final double SIZE_W = (16D - 4D) / 4D, SIZE_H = (16D - 3D) / 3D;
    private static final String TAG_POTIONS = "potions";
}
