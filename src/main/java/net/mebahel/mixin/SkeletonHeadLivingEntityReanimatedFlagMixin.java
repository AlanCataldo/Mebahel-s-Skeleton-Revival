package net.mebahel.mixin;

import net.mebahel.accessor.ReanimatedFlagAccessor;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class SkeletonHeadLivingEntityReanimatedFlagMixin implements ReanimatedFlagAccessor {

    @Unique
    private boolean mebahel$reanimated = false;

    @Override
    public boolean isReanimated() {
        return mebahel$reanimated;
    }

    @Override
    public void setReanimated(boolean value) {
        this.mebahel$reanimated = value;
    }

    // ✅ Sauvegarde dans le NBT
    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void onWriteNbt(NbtCompound nbt, CallbackInfo ci) {
        nbt.putBoolean("mebahel$reanimated", mebahel$reanimated);
    }

    // ✅ Chargement depuis le NBT
    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void onReadNbt(NbtCompound nbt, CallbackInfo ci) {
        this.mebahel$reanimated = nbt.getBoolean("mebahel$reanimated");
    }
}