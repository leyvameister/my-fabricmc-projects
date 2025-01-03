package me.ciakid;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;

public class BalloonBlockEntity extends BlockEntity implements GeoBlockEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    protected static final RawAnimation DEPLOY_ANIM = RawAnimation.begin().thenPlay("animation.balloon.new");


    public BalloonBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BALLOON_BLOCK_ENTITY, pos, state);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "swayController", 0, this::swayAnimation));

    }

    private PlayState swayAnimation(AnimationState<BalloonBlockEntity> balloonBlockEntityAnimationState) {
        return balloonBlockEntityAnimationState.setAndContinue(DEPLOY_ANIM);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

}
