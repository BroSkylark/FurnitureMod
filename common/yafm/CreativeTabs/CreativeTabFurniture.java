package yafm.CreativeTabs;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTabFurniture extends CreativeTabs
{
    public CreativeTabFurniture(int id, String name)
    {
        super(id, name);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getTabIconItemIndex()
    {
        return Item.blazePowder.itemID;
    }
}
