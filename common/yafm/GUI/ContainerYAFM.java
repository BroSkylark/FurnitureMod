package yafm.GUI;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public abstract class ContainerYAFM extends Container
{
    protected void registerPlayerSlots(InventoryPlayer inventoryplayer) { registerPlayerSlots(inventoryplayer, 84); }
    protected void registerPlayerSlots(InventoryPlayer inventoryplayer, int yOffset)
    {
        for(int i = 0 ; i < 3 ; i++)
        {
            for(int j = 0 ; j < 9 ; j++)
            {
                addSlotToContainer(new Slot(inventoryplayer, j + i * 9 + 9, 8 + j * 18, yOffset + i * 18));
            }
        }
        
        for(int i = 0 ; i < 9 ; i++)
        {
            addSlotToContainer(new Slot(inventoryplayer, i, 8 + i * 18, 58 + yOffset));
        }
    }
}
