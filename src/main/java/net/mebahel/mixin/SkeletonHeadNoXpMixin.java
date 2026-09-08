package net.mebahel.mixin;

import net.mebahel.accessor.ReanimatedFlagAccessor;
import net.mebahel.util.config.SkeletonHeadModConfig;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEntity.class)
public abstract class SkeletonHeadNoXpMixin {

    @Inject(method = "getXpToDrop", at = @At("HEAD"), cancellable = true)
    private void skipXp(CallbackInfoReturnable<Integer> cir) {
        MobEntity entity = (MobEntity)(Object)this;
        if (entity instanceof ReanimatedFlagAccessor reanimated && reanimated.isReanimated()) {
            if (!SkeletonHeadModConfig.respawnedEntityShouldDropExperience) {
                System.out.println("[DEBUG] Cancelling XP drop on " + this);
                cir.setReturnValue(0);
            }
        }
    }
}

