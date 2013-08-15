package yafm.GUI;

import yafm.Handler.ItemHandler;
import yafm.Library.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;


public class InventoryBag implements IInventory
{
    private ItemStack items[];
    private boolean isBig;

    private InventoryBag(boolean big)
    {
        isBig = big;
        items = new ItemStack[getSizeInventory()];
    }
    
    public boolean isBig()
    {
        return isBig;
    }

    @Override
    public int getSizeInventory()
    {
        return isBig ? 27 : 5;
    }

    @Override
    public ItemStack getStackInSlot(int i)
    {
        return items[i];
    }

    @Override
    public ItemStack decrStackSize(int i, int j)
    {
        ItemStack stack = getStackInSlot(i);
        if(stack != null)
        {
            if(stack.stackSize <= j)
            {
                setInventorySlotContents(i, null);
            }
            else
            {
                stack = stack.splitStack(j);
                if(getStackInSlot(i).stackSize == 0)
                {
                    setInventorySlotContents(i, null);
                }
            }
        }
        return stack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i)
    {
        ItemStack stack = getStackInSlot(i);
        if (stack != null)
        {
                setInventorySlotContents(i, null);
        }
        return stack;
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack)
    {
        items[i] = itemstack;
        if(items[i] != null && items[i].stackSize > getInventoryStackLimit())
        {
            items[i].stackSize = getInventoryStackLimit();
        }
    }

    @Override
    public String getInvName()
    {
        return Reference.MOD_ID + "." + Reference.ITEM_BAG_NAME + "." + (isBig ? "Big" : "Small");
    }

    @Override
    public boolean isInvNameLocalized()
    {
        return false;
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
    public void onInventoryChanged()
    {
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer)
    {
        return true;
    }

    @Override
    public void openChest()
    {
    }

    @Override
    public void closeChest()
    {
    }

    @Override
    public boolean isStackValidForSlot(int i, ItemStack itemstack)
    {
        return itemstack == null || itemstack.itemID != ItemHandler.bag.itemID;
    }
    
    private InventoryBag readFromNBT(NBTTagCompound nbt)
    {
        nbt.setBoolean(TAG_LOCK, true);
        
        if(!nbt.hasKey(TAG_ITEMS)) return this;
        
        items = new ItemStack[getSizeInventory()];
        NBTTagList list = nbt.getTagList(TAG_ITEMS);
        for(int i = 0 ; i < list.tagCount() ; i++)
        {
            NBTTagCompound nbttagcompound = (NBTTagCompound) list.tagAt(i);
            items[nbttagcompound.getInteger(TAG_POS)] = ItemStack.loadItemStackFromNBT(nbttagcompound.getCompoundTag(TAG_DATA));
        }
        
        return this;
    }
    
    public static void closeBag(ItemStack itemstack)
    {
        if(isOpenBag(itemstack))
        {
            itemstack.stackTagCompound.removeTag(TAG_LOCK);
        }
    }
    
    public void writeToNBT(EntityPlayer player)
    {
        NBTTagCompound nbt = null;
        
        for(int i = 0 ; i < player.inventory.getSizeInventory() ; i++)
        {
            ItemStack is = player.inventory.getStackInSlot(i);
            if(isOpenBag(is))
            {
                nbt = is.stackTagCompound;
                closeBag(is);
                break;
            }
        }
        
        if(nbt != null)
        {
            writeToNBT(nbt);
        }
    }
    
    public void writeToNBT(NBTTagCompound nbt)
    {
        NBTTagList list = new NBTTagList();
        for(int i = 0 ; i < items.length ; i++)
        {
            if(items[i] == null) continue;
            
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setInteger(TAG_POS, i);
            nbttagcompound.setCompoundTag(TAG_DATA, items[i].writeToNBT(new NBTTagCompound()));
            list.appendTag(nbttagcompound);
        }
        
        nbt.setTag(TAG_ITEMS, list);
    }
    
    public static boolean isOpenBag(ItemStack itemstack)
    {
        return itemstack != null && itemstack.itemID == ItemHandler.bag.itemID && itemstack.stackSize > 0 && 
                itemstack.hasTagCompound() && itemstack.stackTagCompound.hasKey(TAG_LOCK);
    }
    
    public static InventoryBag getInventory(ItemStack itemstack)
    {
        if(itemstack == null || itemstack.stackSize <= 0 || itemstack.itemID != ItemHandler.bag.itemID) return null;
        
        if(itemstack.stackTagCompound == null) itemstack.stackTagCompound = new NBTTagCompound();
        
        InventoryBag bag = new InventoryBag(itemstack.getItemDamage() == 1);
        bag.readFromNBT(itemstack.stackTagCompound);
        
        return bag;
    }

    private static final String TAG_ITEMS = "items";
    private static final String TAG_POS = "pos";
    private static final String TAG_DATA = "data";
    private static final String TAG_LOCK = "writing";
}
