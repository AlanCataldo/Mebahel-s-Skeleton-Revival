package net.mebahel.mixin;

import net.mebahel.accessor.ReanimatedFlagAccessor;
import net.mebahel.accessor.SpawnedFromSpawnerAccessor;
import net.mebahel.entity.ModEntities;
import net.mebahel.entity.SkeletonHeadEntity;
import net.mebahel.entity.variant.SkeletonHeadVariant;
import net.mebahel.util.config.ModConfig;
import net.mebahel.util.config.SkullEntityListConfig;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(method = "onDeath", at = @At("TAIL"))
    private void spawnSkullOnDeath(DamageSource source, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity)(Object)this;

        if (!entity.getWorld().isClient && entity.getWorld() instanceof ServerWorld serverWorld) {
            if (entity instanceof ReanimatedFlagAccessor reanimated && reanimated.isReanimated()) return;
            if (entity instanceof SpawnedFromSpawnerAccessor spawner && spawner.isFromSpawner()) return;

            Identifier entityId = Registries.ENTITY_TYPE.getId(entity.getType());
            SkeletonHeadVariant variant = null;
            float chance = 0f;

            if (SkullEntityListConfig.isSkeletonHeadEntity(entityId)) {
                variant = SkeletonHeadVariant.SKELETON;
                chance = ModConfig.skeletonHeadSpawnRate / 100f;
            } else if (SkullEntityListConfig.isWitherSkeletonHeadEntity(entityId)) {
                variant = SkeletonHeadVariant.WITHER_SKELETON;
                chance = ModConfig.witherSkeletonHeadSpawnRate / 100f;
            }

            if (variant != null && serverWorld.getRandom().nextFloat() < chance) {
                SkeletonHeadEntity skull = ModEntities.SKELETON_HEAD.create(serverWorld);
                if (skull != null) {
                    skull.setPosition(entity.getX(), entity.getY(), entity.getZ());
                    skull.setVariant(variant);

                    // ✅ Spécifie l'entité à réanimer
                    skull.setEntityToRespawn(entityId);

                    serverWorld.spawnEntity(skull);
                }
            }
        }
    }
}