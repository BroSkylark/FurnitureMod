package FurnitureMod;

import thaumcraft.api.EnumTag;
import thaumcraft.api.ItemApi;
import thaumcraft.api.ThaumcraftApi;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagByte;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import Utility.Reference;

@Mod(   modid = Reference.MOD_ID,
        name = Reference.MOD_NAME,
        version = Reference.MOD_VERSION,
        dependencies = "required-after:Thaumcraft")
public class FurnitureMod
{
    @Instance(Reference.MOD_ID)
    public static FurnitureMod instance;
    
    @PreInit
    public void preInit(FMLPreInitializationEvent fmlpreinitializationevent)
    {
    }
    
    @Init
    public void init(FMLInitializationEvent fmlinitializationevent)
    {
        ItemStack is = ItemApi.getItem("itemJarFilled", 0); 
        is.setTagInfo("tag", new NBTTagByte("tag", (byte) EnumTag.CRYSTAL.id)); 
        is.setTagInfo("amount", new NBTTagByte("amount", (byte) 64));
        ThaumcraftApi.addShapelessArcaneCraftingRecipe("THEJARISNOWDIAMONDS", 50, new ItemStack(Item.diamond, 64, 0), 
                                 new Object[] { is });
    }
    
    @PostInit
    public void postInit(FMLPostInitializationEvent fmlpostinitializationevent)
    {
    }
}
