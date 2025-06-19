package net.mebahel.entity;

import net.mebahel.accessor.ReanimatedFlagAccessor;
import net.mebahel.ai.FleeTargetGoal;
import net.mebahel.entity.variant.SkeletonHeadVariant;
import net.mebahel.util.config.ModConfig;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.animation.*;

import java.util.Objects;

public class SkeletonHeadEntity extends HostileEntity implements GeoEntity {
    public SkeletonHeadEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.ambientSoundChance = -this.getMinAmbientSoundDelay();
    }

    private int lifeTickCounter = 0;

    private boolean stoppedMoving = false;

    private final AnimatableInstanceCache factory = new SingletonAnimatableInstanceCache(this);

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }

    public static final TrackedData<Integer> DATA_ID_TYPE_VARIANT =
            DataTracker.registerData(SkeletonHeadEntity.class, TrackedDataHandlerRegistry.INTEGER);


    public static final TrackedData<Boolean> SHOULD_RESPAWN = DataTracker.registerData(SkeletonHeadEntity.class,
            TrackedDataHandlerRegistry.BOOLEAN);


    public static final TrackedData<Boolean> HAS_SPAWNED = DataTracker.registerData(SkeletonHeadEntity.class,
            TrackedDataHandlerRegistry.BOOLEAN);


    public boolean getHasSpawned() {
        return this.dataTracker.get(HAS_SPAWNED);
    }

    public void setHasSpawned(boolean bool) {
        this.dataTracker.set(HAS_SPAWNED, bool);
    }

    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(DATA_ID_TYPE_VARIANT, 0);
        builder.add(HAS_SPAWNED, false);
        builder.add(SHOULD_RESPAWN, false);
    }


    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new FleeTargetGoal(this, 0.38f));

        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, VillagerEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, IronGolemEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, RaiderEntity.class, true));
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return HostileEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 35)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.72f)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 8.0D)
                .add(EntityAttributes.GENERIC_ARMOR, 6f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5.0f)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.2f)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 0.5f);
    }

    private PlayState predicate(AnimationState animationState) {
        if (animationState.isMoving() && !this.stoppedMoving) {
            animationState.getController().setAnimation(RawAnimation.begin().then("walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        } else if (!animationState.isMoving() && !this.isAttacking() && !this.stoppedMoving) {
            animationState.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
        return PlayState.CONTINUE;
    }

    private PlayState spawnPredicate(AnimationState state) {
        if (!this.getHasSpawned()) {
            state.getController().setAnimation(RawAnimation.begin().then("spawn", Animation.LoopType.PLAY_ONCE));
            if (state.getController().getAnimationState() == AnimationController.State.STOPPED) {
                this.setHasSpawned(true);
            }
        }
        return PlayState.CONTINUE;
    }

    private PlayState respawnPredicate(AnimationState state) {
        if (this.stoppedMoving) {
            state.getController().setAnimation(RawAnimation.begin().then("respawn", Animation.LoopType.PLAY_ONCE));
        }
        return PlayState.CONTINUE;
    }


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController(this, "controller", 0, this::predicate));
        controllers.add(new AnimationController(this, "spawning", 0, this::spawnPredicate));
        controllers.add(new AnimationController(this, "respawning", 0, this::respawnPredicate));
    }

    protected SoundEvent getAmbientSound() {
        if (this.getVariant() == SkeletonHeadVariant.WITHER_SKELETON) {
            return SoundEvents.ENTITY_WITHER_SKELETON_AMBIENT;
        }
        return SoundEvents.ENTITY_SKELETON_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        if (this.getVariant() == SkeletonHeadVariant.WITHER_SKELETON) {
            return SoundEvents.ENTITY_WITHER_SKELETON_HURT;
        }
        return SoundEvents.ENTITY_SKELETON_HURT;
    }

    protected SoundEvent getDeathSound() {
        if (this.getVariant() == SkeletonHeadVariant.WITHER_SKELETON) {
            return SoundEvents.ENTITY_WITHER_SKELETON_DEATH;
        }
        return SoundEvents.ENTITY_SKELETON_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        if (this.getVariant() == SkeletonHeadVariant.WITHER_SKELETON) {
            this.playSound(SoundEvents.ENTITY_WITHER_SKELETON_STEP, 0.65f, 1.0f);
        }
        this.playSound(SoundEvents.ENTITY_SKELETON_STEP, 0.65f, 1.0f);
    }

    public boolean damage(DamageSource source, float amount) {
        if (source.isOf(DamageTypes.IN_FIRE) || source.isOf(DamageTypes.ON_FIRE)) {
            return super.damage(source, amount * 2);
        } else if (source.isOf(DamageTypes.FREEZE)) {
            return false;
        }
        return super.damage(source, amount);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("HasSpawned", true);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setHasSpawned(nbt.getBoolean("HasSpawned"));
    }

    @Override
    public void tick() {
        super.tick();
        lifeTickCounter++;

        if (lifeTickCounter == ModConfig.timeBeforeRevival - 15 && !stoppedMoving) {
            stoppedMoving = true;
            Objects.requireNonNull(this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED)).setBaseValue(0);
        }
        if (lifeTickCounter >= ModConfig.timeBeforeRevival) {
            spawnSkeletonHead(this.getWorld());
        }
    }

    private void spawnSkeletonHead(World world) {
        AbstractSkeletonEntity servant;

        if (this.getVariant() == SkeletonHeadVariant.WITHER_SKELETON) {
            servant = EntityType.WITHER_SKELETON.create(world);
        } else {
            servant = EntityType.SKELETON.create(world);
        }

        if (servant != null) {
            ((ReanimatedFlagAccessor) servant).setReanimated(true);
            servant.setPosition(this.getX(), this.getY(), this.getZ());
            world.spawnEntity(servant);
        }

        this.remove(RemovalReason.DISCARDED);
    }

    public SkeletonHeadVariant getVariant() {
        return SkeletonHeadVariant.byId(this.getTypeVariant() & 255);
    }

    public int getTypeVariant() {
        return this.dataTracker.get(DATA_ID_TYPE_VARIANT);
    }

    public void setVariant(SkeletonHeadVariant variant) {
        this.dataTracker.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
    }
}

