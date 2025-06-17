package net.mebahel.mixin;

import net.mebahel.accessor.ReanimatedFlagAccessor;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(AbstractSkeletonEntity.class)
public class AbstractSkeletonEntityMixin implements ReanimatedFlagAccessor {

    @Unique
    private boolean mebahel$reanimated = false;

    @Override
    public boolean isReanimated() {
        return mebahel$reanimated;
    }

    @Override
    public void setReanimated(boolean reanimated) {
        this.mebahel$reanimated = reanimated;
    }
}
