package yafm.Handler.Addons;

import java.lang.reflect.Field;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import thaumcraft.api.EnumTag;
import thaumcraft.api.ItemApi;
import thaumcraft.api.ObjectTags;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchList;
import thaumcraft.common.Config;
import thaumcraft.common.items.wands.ItemWandCastingAdept;
import thaumcraft.common.items.wands.ItemWandCastingApprentice;
import thaumcraft.common.items.wands.ItemWandCastingMage;
import thaumcraft.common.tiles.TileAlembic;
import thaumcraft.common.tiles.TileCrucible;
import yafm.API.ToolRackRegistry;
import yafm.Handler.BlockHandler;
import yafm.Handler.ItemHandler;
import yafm.Library.Addons.ThaumcraftReference;

public final class ThaumcraftHandler extends AddonHandler
{
    protected static final AddonHandler instance = new ThaumcraftHandler();

    @Override
    public void handlePreInit()
    {
        ThaumcraftApi.registerResearchXML(ThaumcraftReference.RESEARCH_PATH);
    }

    @Override
    public void handlePostInit()
    {
        (new ResearchItem(ThaumcraftReference.RESEARCH_PASSABLEGLASS_NAME, (new ObjectTags())
                .add(EnumTag.ELDRITCH, 24).add(EnumTag.CONTROL, 4).add(EnumTag.CRYSTAL, 32).add(EnumTag.EXCHANGE, 8)
                .add(EnumTag.MOTION, 4).add(EnumTag.SPIRIT, 12).add(EnumTag.VISION, 16), 
                3, 9, new ItemStack(Block.glass)))
                .setParents(new ResearchItem[] {ResearchList.getResearch("PORTABLEHOLE")})
                .setParentsHidden(new ResearchItem[] {ResearchList.getResearch("CRYSTALCLUSTER")})
                .setHidden().registerResearchItem();
        (new ResearchItem(ThaumcraftReference.RESEARCH_BAG_NAME, (new ObjectTags())
                .add(EnumTag.ELDRITCH, 16).add(EnumTag.VOID, 32).add(EnumTag.MOTION, 4).add(EnumTag.EXCHANGE, 4),
                1, 9, new ItemStack(ItemHandler.bag)))
                .setParents(new ResearchItem[] {ResearchList.getResearch("HUNGRYCHEST")})
                .setParentsHidden(new ResearchItem[] {ResearchList.getResearch("CRYSTALCLUSTER")})
                .setHidden().registerResearchItem();

        ThaumcraftApi.addInfusionCraftingRecipe(ThaumcraftReference.RESEARCH_PASSABLEGLASS_NAME, 
                ThaumcraftReference.CRAFTING_PASSABLEGLASS_NAME, 90, (new ObjectTags())
                .add(EnumTag.ELDRITCH, 8).add(EnumTag.MOTION, 12).add(EnumTag.VISION, 24),
                new ItemStack(BlockHandler.passableGlass, 9), new Object[] {
            "###", "###", "###", '#', Block.glass
        });
        ThaumcraftApi.addInfusionCraftingRecipe(ThaumcraftReference.RESEARCH_BAG_NAME, 
                ThaumcraftReference.CRAFTING_BAG_NAME, 100, (new ObjectTags())
                .add(EnumTag.VOID, 24).add(EnumTag.ELDRITCH, 4).add(EnumTag.TOOL, 8), 
                new ItemStack(ItemHandler.bag, 1, 1), new Object[] {
            "# #", "# #", "$#$", '#', Item.leather, '$', Item.silk
        });

        ThaumcraftApi.registerObjectTag(ItemHandler.bag.itemID, 0, 
                (new ObjectTags()).add(EnumTag.BEAST, 2).add(EnumTag.TOOL, 4).add(EnumTag.VOID, 4));
        ThaumcraftApi.registerObjectTag(ItemHandler.bag.itemID, 1, 
                (new ObjectTags()).add(EnumTag.BEAST, 2).add(EnumTag.TOOL, 4).add(EnumTag.VOID, 12)
                .add(EnumTag.ELDRITCH, 4).add(EnumTag.MAGIC, 4));
        ThaumcraftApi.registerObjectTag(BlockHandler.toolRack.blockID, -1, 
                (new ObjectTags()).add(EnumTag.METAL, 2).add(EnumTag.WOOD, 4).add(EnumTag.TOOL, 2));
        ThaumcraftApi.registerObjectTag(BlockHandler.potionStand.blockID, -1, 
                (new ObjectTags()).add(EnumTag.WOOD, 2).add(EnumTag.TOOL, 2));
        ThaumcraftApi.registerObjectTag(BlockHandler.motionSensor.blockID, -1, 
                (new ObjectTags()).add(EnumTag.ROCK, 4).add(EnumTag.CRYSTAL, 3).add(EnumTag.VISION, 4).add(EnumTag.CONTROL, 2).
                add(EnumTag.POWER, 2));
        ThaumcraftApi.registerObjectTag(BlockHandler.trickGlass.blockID, -1, 
                (new ObjectTags()).add(EnumTag.CRYSTAL, 4).add(EnumTag.VISION, 2));
        ThaumcraftApi.registerObjectTag(BlockHandler.passableGlass.blockID, -1, 
                (new ObjectTags()).add(EnumTag.CRYSTAL, 4).add(EnumTag.VISION, 2).add(EnumTag.MAGIC, 2).add(EnumTag.ELDRITCH, 1));
        ThaumcraftApi.registerObjectTag(ItemHandler.keyTemplate.itemID, -1, 
                (new ObjectTags()).add(EnumTag.METAL, 3).add(EnumTag.MECHANISM, 1).add(EnumTag.TOOL, 1));
        ThaumcraftApi.registerObjectTag(ItemHandler.key.itemID, -1, 
                (new ObjectTags()).add(EnumTag.METAL, 2).add(EnumTag.MECHANISM, 2).add(EnumTag.TOOL, 2));
        ThaumcraftApi.registerObjectTag(ItemHandler.lock.itemID, -1, 
                (new ObjectTags()).add(EnumTag.METAL, 12).add(EnumTag.MECHANISM, 4).add(EnumTag.TOOL, 2));
        ThaumcraftApi.registerObjectTag(ItemHandler.lockNKey.itemID, -1, 
                (new ObjectTags()).add(EnumTag.METAL, 14).add(EnumTag.MECHANISM, 6).add(EnumTag.TOOL, 4));

        ToolRackRegistry.registerTool(ItemApi.getItem("itemWandCastingApprentice", 0).itemID);
        ToolRackRegistry.registerTool(ItemApi.getItem("itemWandCastingAdept", 0).itemID);
        ToolRackRegistry.registerTool(ItemApi.getItem("itemWandCastingMage", 0).itemID);
        ToolRackRegistry.registerTool(ItemApi.getItem("itemWandFire", 0).itemID);
        ToolRackRegistry.registerTool(ItemApi.getItem("itemWandLightning", 0).itemID);
        ToolRackRegistry.registerTool(ItemApi.getItem("itemWandFrost", 0).itemID);
        ToolRackRegistry.registerTool(ItemApi.getItem("itemWandExcavation", 0).itemID);
        ToolRackRegistry.registerTool(ItemApi.getItem("itemWandTrade", 0).itemID);
        ToolRackRegistry.registerTool(ItemApi.getItem("itemHellrod", 0).itemID);
        ToolRackRegistry.registerItem(ItemApi.getItem("itemHandMirror", 0).itemID);

        injectWandAugmentation();
    }

    private void injectWandAugmentation()
    {
        try
        {
            Class<?> cc = Class.forName("thaumcraft.common.Config");
            Field wa = cc.getField("itemWandCastingApprentice");
            Field wd = cc.getField("itemWandCastingAdept");
            Field wm = cc.getField("itemWandCastingMage");
            wa.setAccessible(true);
            wd.setAccessible(true);
            wm.setAccessible(true);

            ItemWandCastingApprentice iwca = (ItemWandCastingApprentice) wa.get(null);
            ItemWandCastingAdept iwcd = (ItemWandCastingAdept) wd.get(null);
            ItemWandCastingMage iwcm = (ItemWandCastingMage) wm.get(null);

            wa.set(null, new TC_CastingWandApprentice(iwca));
            wd.set(null, new TC_CastingWandAdept(iwcd));
            wm.set(null, new TC_CastingWandMage(iwcm));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private static int onItemUseOverride(World world, EntityPlayer player, int x, int y, int z)
    {
        int bi = world.getBlockId(x, y, z);
        int md = world.getBlockMetadata(x, y, z);

        if((bi == Config.blockCrucible.blockID) && (md >= 1) && (md <= 4) && !player.isSneaking())
        {
            TileEntity te = world.getBlockTileEntity(x, y, z);
            if((te != null) && ((te instanceof TileAlembic)) && (((TileAlembic) te).amount <= 0))
            {
                int dir = world.getBlockMetadata(x, y, z) + 1;
                TileEntity tc = world.getBlockTileEntity(x + ForgeDirection.getOrientation(dir).getOpposite().offsetX, y
                        + ForgeDirection.getOrientation(dir).getOpposite().offsetY, z
                        + ForgeDirection.getOrientation(dir).getOpposite().offsetZ);
                if((tc != null) && ((tc instanceof TileCrucible)) && (((TileCrucible) tc).heat > 150)
                        && (((TileCrucible) tc).liquid))
                {
                    TileCrucible tcr = (TileCrucible) tc;
                    TileAlembic ta = (TileAlembic) te;
                    ObjectTags src = tcr.getSourceTags();
                    if(src.size() > 0)
                    {
                        EnumTag t = src.getAspectsSortedAmount()[0];
                        int a = src.getAmount(t);
                        if(a > 16)
                        {
                            src.reduceAmount(t, 16);
                            a = 16;
                        }
                        else
                        {
                            src.reduceAmount(t, a);
                        }

                        ta.addToSource(t, a);

                        if(world.isRemote)
                        {
                            player.swingItem();
                            world.playSound(x, y, z, "thaumcraft.bubble", 0.2F, 1.0F + world.rand.nextFloat() * 0.4F, false);
                            return 0;
                        }

                        return 1;
                    }
                }
            }
        }

        return -1;
    }

    private static class TC_CastingWandApprentice extends ItemWandCastingApprentice
    {
        public TC_CastingWandApprentice(ItemWandCastingApprentice iwca)
        {
            super(iwca.itemID - 256);
            this.setUnlocalizedName("WandCastingApprentice");
        }

        @Override
        public boolean onItemUseFirst(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side,
                float hitX, float hitY, float hitZ)
        {
            int r = onItemUseOverride(world, player, x, y, z);

            return r < 0 ? super.onItemUseFirst(itemstack, player, world, x, y, z, side, hitX, hitY, hitZ) : r == 1;
        }
    }

    private static class TC_CastingWandAdept extends ItemWandCastingAdept
    {
        public TC_CastingWandAdept(ItemWandCastingAdept iwca)
        {
            super(iwca.itemID - 256);
            this.setUnlocalizedName("WandCastingAdept");
        }

        @Override
        public boolean onItemUseFirst(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side,
                float hitX, float hitY, float hitZ)
        {
            int r = onItemUseOverride(world, player, x, y, z);

            return r < 0 ? super.onItemUseFirst(itemstack, player, world, x, y, z, side, hitX, hitY, hitZ) : r == 1;
        }
    }

    private static class TC_CastingWandMage extends ItemWandCastingMage
    {
        public TC_CastingWandMage(ItemWandCastingMage iwcm)
        {
            super(iwcm.itemID - 256);
            this.setUnlocalizedName("WandCastingMage");
        }

        @Override
        public boolean onItemUseFirst(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side,
                float hitX, float hitY, float hitZ)
        {
            int r = onItemUseOverride(world, player, x, y, z);

            return r < 0 ? super.onItemUseFirst(itemstack, player, world, x, y, z, side, hitX, hitY, hitZ) : r == 1;
        }
    }

    private ThaumcraftHandler() {}
}
