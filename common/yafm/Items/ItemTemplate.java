package yafm.Items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import yafm.FurnitureMod;
import yafm.Library.Reference;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;

public class ItemTemplate extends Item
{
    private String iconName;
    
    public ItemTemplate(int id, String unlocalizedName)
    {
        super(id);
        this.setUnlocalizedName(unlocalizedName);
        this.setCreativeTab(FurnitureMod.tabFurniture);
        iconName = Reference.MOD_ID + ":" + unlocalizedName;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister reg)
    {
        this.itemIcon = reg.registerIcon(iconName);
    }
}
