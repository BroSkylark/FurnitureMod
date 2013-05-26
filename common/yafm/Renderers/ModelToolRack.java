package yafm.Renderers;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelToolRack extends ModelBase
{
    private ModelRenderer bars;
    
    public ModelToolRack()
    {
        bars = (new ModelRenderer(this, 0, 0)).setTextureSize(64, 64);
        bars.addBox(-8F, -6F, -1F, 16, 2, 2);
        bars.addBox(-8F, -2F, -1F, 16, 2, 2);
        bars.addBox(-8F,  2F, -1F, 16, 2, 2);
        bars.addBox(-8F,  6F, -1F, 16, 2, 2);
        bars.rotationPointY = -1F;
    }
    
    public void renderAll() { render(null, 0, 0, 0, 0, 0, 0.0625f); }
    
    @Override
    public void render(Entity e, float f1, float f2, float f3, float f4, float f5, float f6)
    {
        setRotationAngles(f1, f2, f3, f4, f5, f6, e);
        bars.render(f6);
    }
    
    @Override
    public void setRotationAngles(float f1, float f2, float f3, float f4, float f5, float f6, Entity e)
    {
        bars.rotateAngleY = f1;
    }
}
