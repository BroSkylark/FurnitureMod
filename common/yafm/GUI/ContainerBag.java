package yafm.GUI;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;


public class ContainerBag extends ContainerYAFM
{
    private InventoryBag bag;

    public ContainerBag(InventoryBag inventorybag, InventoryPlayer inventoryplayer)
    {
        bag = inventorybag;

        if(bag.isBig())
        {
            for(int i = 0 ; i < 3 ; i++)
            {
                for(int j = 0 ; j < 9 ; j++)
                {
                    this.addSlotToContainer(new SlotBag(bag, i * 9 + j, 8 + j * 18, 17 + i * 18));
                }
            }

            this.registerPlayerSlots(inventoryplayer);
        }
        else
        {
            for(int i = 0 ; i < bag.getSizeInventory() ; i++)
            {
                this.addSlotToContainer(new SlotBag(bag, i, 44 + i * 18, 20));
            }

            this.registerPlayerSlots(inventoryplayer, 51);
        }
    }

    @Override
    public void onCraftGuiClosed(EntityPlayer entityplayer)
    {
        ItemStack is = entityplayer.inventory.getItemStack();
        if(is != null && InventoryBag.isOpenBag(is))
        {
            bag.writeToNBT(is.stackTagCompound);
            InventoryBag.closeBag(is);
        }
        else
        {
            bag.writeToNBT(entityplayer);
        }
        
        super.onCraftGuiClosed(entityplayer);
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer)
    {
        return bag.isUseableByPlayer(entityplayer);
    }

    public static ContainerBag initializeInstance(EntityPlayer entityplayer, World world, int x, int y, int z)
    {
        InventoryBag bag = InventoryBag.getInventory(entityplayer.getHeldItem());

        return bag == null ? null : new ContainerBag(bag, entityplayer.inventory);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot)
    {
        ItemStack stack = null;
        Slot slotObject = (Slot) inventorySlots.get(slot);
        int s = bag.getSizeInventory();

        if(slotObject != null && slotObject.getHasStack())
        {
            ItemStack stackInSlot = slotObject.getStack();
            stack = stackInSlot.copy();

            if(slot < s)
            {
                if(!this.mergeItemStack(stackInSlot, s, s + 36, true))
                {
                    return null;
                }
            }
            else if(InventoryBag.isOpenBag(stackInSlot))
            {
                if(slot < s + 27)
                {
                    if(!this.mergeItemStack(stackInSlot, s + 27, s + 36, false))
                    {
                        return null;
                    }
                }
                else if(!this.mergeItemStack(stackInSlot, s, s + 9, true))
                {
                    return null;
                }
            }
            else if(!this.mergeItemStack(stackInSlot, 0, s, false))
            {
                return null;
            }

            if(stackInSlot.stackSize == 0)
            {
                slotObject.putStack(null);
            }
            else
            {
                slotObject.onSlotChanged();
            }
            
            if(stackInSlot.stackSize == stack.stackSize)
            {
                return null;
            }
            
            slotObject.onPickupFromSlot(player, stackInSlot);
        }
        
        return stack;
    }
}
