package yafm.Renderers;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.Facing;

public class ModelMotionSensor extends ModelBase
{
    private ModelRenderer model;
    
    public ModelMotionSensor()
    {
        model = (new ModelRenderer(this, 0, 0)).setTextureSize(64, 64);
        model.addBox(-8F, -8F, -8F, 16, 16, 16);
    }
    
    public void renderAll(int d)
    {
        render(null, Facing.offsetsYForSide[d] == 1 ? 180F : Facing.offsetsZForSide[d] * -90F, 
                Facing.offsetsXForSide[d] * 90F, 0, 0, 0, 0.0625f);
    }
    
    @Override
    public void render(Entity e, float f1, float f2, float f3, float f4, float f5, float f6)
    {
        setRotationAngles(f1, f2, f3, f4, f5, f6, e);
        model.render(f6);
    }
    
    @Override
    public void setRotationAngles(float f1, float f2, float f3, float f4, float f5, float f6, Entity e)
    {
        model.rotateAngleX = (float) (f1 * Math.PI / 180D);
        model.rotateAngleZ = (float) (f2 * Math.PI / 180D);
    }
}
