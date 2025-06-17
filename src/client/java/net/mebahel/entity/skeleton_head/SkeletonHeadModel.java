package net.mebahel.entity.skeleton_head;

import com.google.common.collect.Maps;
import net.mebahel.MebahelsSkullRevival;
import net.mebahel.entity.SkeletonHeadEntity;
import net.mebahel.entity.variant.SkeletonHeadVariant;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import software.bernie.geckolib.model.GeoModel;

import java.util.Map;

public class SkeletonHeadModel extends GeoModel<SkeletonHeadEntity> {
    public static final Map<SkeletonHeadVariant, Identifier> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(SkeletonHeadVariant.class), (map) -> {
                map.put(SkeletonHeadVariant.SKELETON,
                        new Identifier("minecraft", "textures/entity/skeleton/skeleton.png"));
                map.put(SkeletonHeadVariant.WITHER_SKELETON,
                        new Identifier("minecraft", "textures/entity/skeleton/wither_skeleton.png"));
            });

    @Override
    public Identifier getModelResource(SkeletonHeadEntity object) {
        return new Identifier(MebahelsSkullRevival.MOD_ID, "geo/skeleton_head.geo.json");
    }

    @Override
    public Identifier getTextureResource(SkeletonHeadEntity animatable) {
        return LOCATION_BY_VARIANT.get(animatable.getVariant());
    }
    @Override
    public Identifier getAnimationResource(SkeletonHeadEntity animatable) {
        return new Identifier(MebahelsSkullRevival.MOD_ID, "animations/skeleton_head.animation.json");
    }
}