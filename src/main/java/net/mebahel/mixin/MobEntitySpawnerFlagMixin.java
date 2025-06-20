package net.mebahel.mixin;

import net.mebahel.accessor.SpawnedFromSpawnerAccessor;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEntity.class)
public class MobEntitySpawnerFlagMixin implements SpawnedFromSpawnerAccessor {

    @Unique
    private boolean mebahel$fromSpawner = false;

    @Override
    public boolean isFromSpawner() {
        return mebahel$fromSpawner;
    }

    @Override
    public void setFromSpawner(boolean fromSpawner) {
        this.mebahel$fromSpawner = fromSpawner;
    }

    @Inject(
            method = "initialize",
            at = @At("RETURN")
    )
    private void captureSpawnReason(
            ServerWorldAccess world,
            LocalDifficulty difficulty,
            SpawnReason spawnReason,
            @Nullable EntityData entityData,
            CallbackInfoReturnable<EntityData> cir
    ) {
        if (spawnReason == SpawnReason.SPAWNER) {
            this.mebahel$fromSpawner = true;
        }
    }
}
