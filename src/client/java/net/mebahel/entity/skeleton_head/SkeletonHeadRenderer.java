package net.mebahel.entity.skeleton_head;

import net.mebahel.entity.SkeletonHeadEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SkeletonHeadRenderer extends GeoEntityRenderer<SkeletonHeadEntity> {
    public SkeletonHeadRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new SkeletonHeadModel());
        this.shadowRadius = 0.35f;
        addRenderLayer(new SkeletonHeadArmorLayer(this));
    }
    @Override
    public RenderLayer getRenderType(SkeletonHeadEntity animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }

    @Override
    public float getMotionAnimThreshold(SkeletonHeadEntity animatable) {
        return 0.008F;
    }
}
