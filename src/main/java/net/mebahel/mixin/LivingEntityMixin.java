package net.mebahel.mixin;

import net.mebahel.accessor.ReanimatedFlagAccessor;
import net.mebahel.accessor.SpawnedFromSpawnerAccessor;
import net.mebahel.entity.ModEntities;
import net.mebahel.entity.SkeletonHeadEntity;
import net.mebahel.entity.variant.SkeletonHeadVariant;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.mebahel.util.config.ModConfig;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(method = "onDeath", at = @At("TAIL"))
    private void spawnSkullOnDeath(DamageSource source, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity)(Object)this;

        if (!entity.getWorld().isClient && entity.getWorld() instanceof ServerWorld serverWorld) {
            if ((entity instanceof SkeletonEntity || entity instanceof WitherSkeletonEntity)) {
                if (((ReanimatedFlagAccessor) entity).isReanimated()) return;
                if (((SpawnedFromSpawnerAccessor) entity).isFromSpawner()) return;

                float chance = (entity instanceof WitherSkeletonEntity)
                        ? ModConfig.witherSkeletonHeadSpawnRate / 100f
                        : ModConfig.skeletonHeadSpawnRate / 100f;

                if (serverWorld.getRandom().nextFloat() < chance) {
                    SkeletonHeadEntity skull = ModEntities.SKELETON_HEAD.create(serverWorld);
                    if (skull != null) {
                        skull.setPosition(entity.getX(), entity.getY(), entity.getZ());

                        skull.setVariant(entity instanceof WitherSkeletonEntity
                                ? SkeletonHeadVariant.WITHER_SKELETON
                                : SkeletonHeadVariant.SKELETON);

                        serverWorld.spawnEntity(skull);
                    }
                }
            }
        }
    }
}

