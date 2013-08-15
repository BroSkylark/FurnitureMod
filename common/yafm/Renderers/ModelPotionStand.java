package yafm.Renderers;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelPotionStand extends ModelBase
{
    private ModelRenderer verticalBars, horizontalBars;
    
    public ModelPotionStand()
    {
        verticalBars = (new ModelRenderer(this, 1, 1)).setTextureSize(64, 64);
        verticalBars.addBox(-6.5f, -8F, -2F, 1, 16, 4);
        verticalBars.addBox(-2.5f, -8F, -2F, 1, 16, 4);
        verticalBars.addBox( 1.5f, -8F, -2F, 1, 16, 4);
        verticalBars.addBox( 5.5f, -8F, -2F, 1, 16, 4);
        
        horizontalBars = (new ModelRenderer(this, 0, 0)).setTextureSize(64, 64);
        horizontalBars.addBox(-5.8333f, -8F, -2.5f, 1, 16, 5);
        horizontalBars.addBox(   -0.5f, -8F, -2.5f, 1, 16, 5);
        horizontalBars.addBox( 4.8333f, -8F, -2.5f, 1, 16, 5);
        horizontalBars.rotateAngleZ = (float) (Math.PI * 0.5d);
    }
    
    public void renderAll()
    {
        render(null, 0, 0, 0, 0, 0, 0.0625f);
    }
    
    @Override
    public void render(Entity e, float f1, float f2, float f3, float f4, float f5, float f6)
    {
        setRotationAngles(f1, f2, f3, f4, f5, f6, e);
        verticalBars.render(f6);
        horizontalBars.render(f6);
    }
    
    @Override
    public void setRotationAngles(float f1, float f2, float f3, float f4, float f5, float f6, Entity e)
    {
        verticalBars.rotateAngleY = f1;
        horizontalBars.rotateAngleY = f1;
    }
}
