package net.mebahel.entity.skeleton_head;

import net.mebahel.entity.SkeletonHeadEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.ItemArmorGeoLayer;

public class SkeletonHeadArmorLayer extends ItemArmorGeoLayer<SkeletonHeadEntity> {

    public SkeletonHeadArmorLayer(GeoRenderer<SkeletonHeadEntity> renderer) {
        super(renderer);
    }

    @Override
    protected ItemStack getArmorItemForBone(GeoBone bone, SkeletonHeadEntity animatable) {
        if (bone.getName().equals("Head")) {
            return animatable.getEquippedStack(EquipmentSlot.HEAD);
        }
        return super.getArmorItemForBone(bone, animatable);
    }

    @Override
    protected @NotNull EquipmentSlot getEquipmentSlotForBone(GeoBone bone, ItemStack stack, SkeletonHeadEntity animatable) {
        if (bone.getName().equals("Head")) {
            return EquipmentSlot.HEAD;
        }
        return super.getEquipmentSlotForBone(bone, stack, animatable);
    }

    protected @NotNull ModelPart getModelPartForBone(GeoBone bone, EquipmentSlot slot, ItemStack stack, SkeletonHeadEntity animatable, BipedEntityModel<?> baseModel) {
        if (bone.getName().equals("Head")) {
            return baseModel.head;
        }
        return super.getModelPartForBone(bone, slot, stack, animatable, baseModel);
    }
}
