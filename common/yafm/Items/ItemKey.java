package yafm.Items;

import yafm.Library.Reference;

public class ItemKey extends ItemTemplate
{
    public ItemKey(int id)
    {
        super(id, Reference.ITEM_KEY_NAME);
        this.setHasSubtypes(true);
        this.setMaxDamage(1);
        this.setMaxStackSize(1);
    }
}
