package yafm.Items;

import java.util.List;
import core.utils.Math.AnalyticGeometry.Point;
import core.utils.Math.AnalyticGeometry.PointBlock;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import yafm.FurnitureMod;
import yafm.Library.GuiIDs;
import yafm.Library.Reference;

public class ItemBag extends ItemTemplate
{
    public ItemBag(int id)
    {
        super(id, Reference.ITEM_BAG_NAME);
        this.setCreativeTab(FurnitureMod.tabFurniture);
        this.setMaxStackSize(1);
    }
    
    @Override
    public String getUnlocalizedName(ItemStack itemstack)
    {
        return super.getUnlocalizedName() + (itemstack.getItemDamage() == 1 ? ".Big" : ".Small");
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void getSubItems(int id, CreativeTabs tab, @SuppressWarnings("rawtypes") List list)
    {
        list.add(new ItemStack(id, 1, 0));
        list.add(new ItemStack(id, 1, 1));
    }
    
    @Override
    public boolean hasEffect(ItemStack itemstack)
    {
        return itemstack.getItemDamage() == 1;
    }

    @Override
    public boolean getShareTag()
    {
        return true;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
        PointBlock p = Point.getPlayerEyeLocation(entityplayer).toBlock();
        entityplayer.openGui(FurnitureMod.instance, GuiIDs.GUI_BAG_ID, world, p.X(), p.Y(), p.Z());
        
        return itemstack;
    }
}
