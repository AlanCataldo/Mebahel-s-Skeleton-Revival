package net.mebahel.mixin;

import net.mebahel.accessor.ReanimatedFlagAccessor;
import net.mebahel.util.config.SkeletonHeadModConfig;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class SkeletonHeadNoLootMixin {

    @Inject(method = "dropLoot", at = @At("HEAD"), cancellable = true)
    private void skipLoot(DamageSource source, boolean causedByPlayer, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity)(Object)this;
        if (entity instanceof ReanimatedFlagAccessor reanimated && reanimated.isReanimated()) {
            System.out.println("[DEBUG] dropLoot called on " + this);
            if (!SkeletonHeadModConfig.respawnedEntityShouldDropItem) {
                ci.cancel();
            }
        }
    }
}
