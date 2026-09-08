package net.mebahel.mixin;

import net.mebahel.accessor.ReanimatedFlagAccessor;
import net.mebahel.accessor.SpawnedFromSpawnerAccessor;
import net.mebahel.entity.SkeletonHeadModEntities;
import net.mebahel.entity.SkeletonHeadEntity;
import net.mebahel.entity.variant.SkeletonHeadVariant;
import net.mebahel.util.config.SkeletonHeadModConfig;
import net.mebahel.util.config.SkullEntityListConfig;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class SkeletonHeadLivingEntityMixin {

    @Inject(method = "onDeath", at = @At("TAIL"))
    private void spawnSkullOnDeath(DamageSource source, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity)(Object)this;

        if (!entity.getWorld().isClient && entity.getWorld() instanceof ServerWorld serverWorld) {
            if (!SkeletonHeadModConfig.reanimatedEntitiesCanSpawnHeads
                    && entity instanceof ReanimatedFlagAccessor reanimated
                    && reanimated.isReanimated()) return;
            if (!SkeletonHeadModConfig.canSpawnFromSpawner)
                if (entity instanceof SpawnedFromSpawnerAccessor spawner && spawner.isFromSpawner()) return;

            Identifier entityId = Registries.ENTITY_TYPE.getId(entity.getType());
            SkeletonHeadVariant variant = null;
            float chance = 0f;

            if (SkullEntityListConfig.isSkeletonHeadEntity(entityId)) {
                variant = SkeletonHeadVariant.SKELETON;
                chance = SkeletonHeadModConfig.skeletonHeadSpawnRate / 100f;
            } else if (SkullEntityListConfig.isWitherSkeletonHeadEntity(entityId)) {
                variant = SkeletonHeadVariant.WITHER_SKELETON;
                chance = SkeletonHeadModConfig.witherSkeletonHeadSpawnRate / 100f;
            }

            if (variant != null && serverWorld.getRandom().nextFloat() < chance) {
                SkeletonHeadEntity skull = SkeletonHeadModEntities.SKELETON_HEAD.create(serverWorld);
                if (skull != null) {
                    skull.setPosition(entity.getX(), entity.getY(), entity.getZ());
                    skull.setVariant(variant);
                    skull.setEntityToRespawn(entityId);
                    skull.setHealthFromOriginal(entity.getMaxHealth());

                    if (entity.hasCustomName()) {
                        skull.setCustomName(entity.getCustomName());
                        skull.setCustomNameVisible(entity.isCustomNameVisible());
                    }

                    for (StatusEffectInstance effect : entity.getStatusEffects()) {
                        skull.addStatusEffect(new StatusEffectInstance(effect));
                    }

                    ItemStack headStack = entity.getEquippedStack(EquipmentSlot.HEAD);
                    if (!headStack.isEmpty()) {
                        skull.equipStack(EquipmentSlot.HEAD, headStack.copy());
                    }

                    serverWorld.spawnEntity(skull);
                }
            }
        }
    }
}
