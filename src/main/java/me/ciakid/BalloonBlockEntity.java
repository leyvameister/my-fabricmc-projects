package me.ciakid;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;

public class BalloonBlockEntity extends BlockEntity implements GeoBlockEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private int currentInflateAnimation = 0;
    private boolean isInflating = false;
    private static final int MAX_INFLATION_STAGE = 15;
    private int tickCounter;

    public BalloonBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BALLOON_BLOCK_ENTITY, pos, state);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "swayController", 0, this::swayAnimation));
        controllers.add(new AnimationController<>(this, "inflateController", 0, this::inflateAnimation));
    }

    private PlayState swayAnimation(AnimationState<BalloonBlockEntity> state) {
        return state.setAndContinue(RawAnimation.begin().thenPlay("animation.balloon.sway"));
    }

    private PlayState inflateAnimation(AnimationState<BalloonBlockEntity> state) {
        return state.setAndContinue(RawAnimation.begin().thenPlayAndHold("animation.balloon.scale" + currentInflateAnimation));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        nbt.putBoolean("isInflating", isInflating);
        nbt.putInt("currentInflateAnimation", currentInflateAnimation);
    }

    @Override
    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        isInflating = nbt.getBoolean("isInflating");
        currentInflateAnimation = nbt.getInt("currentInflateAnimation");
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
//        NbtCompound nbt = super.toInitialChunkDataNbt(registryLookup);
//        nbt.putBoolean("isInflating", isInflating);
//        nbt.putInt("currentInflateAnimation", currentInflateAnimation);
//        return nbt;
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    private void syncWithClient() {
        if (world != null) {
            world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_ALL);
        }
    }

    public void toggleInflate() {
        isInflating = !isInflating;
        markDirty();
        syncWithClient();
    }


    public static <T extends BlockEntity> void tick(World world, BlockPos blockPos, BlockState blockState, BalloonBlockEntity blockEntity) {
        if (blockEntity.isInflating) {
            blockEntity.tickCounter++;
            if (blockEntity.tickCounter >= 20) {
                System.out.println(blockEntity.currentInflateAnimation);
                blockEntity.currentInflateAnimation++;
                if (blockEntity.currentInflateAnimation > MAX_INFLATION_STAGE) {
                    blockEntity.currentInflateAnimation = MAX_INFLATION_STAGE;
                    blockEntity.isInflating = false;

                    spawnExplosionParticles(world, blockPos);
                    destroyBalloon(world, blockPos);
                }
                blockEntity.tickCounter = 0;
            }
            blockEntity.syncWithClient();
            blockEntity.markDirty();
        }
    }

    private static void destroyBalloon(World world, BlockPos pos) {
        world.removeBlock(pos, false);
        world.playSound(null, pos, ModSounds.BALLOON_SOUND_EVENT, SoundCategory.BLOCKS, 1.0f, 1.0f);
    }

    private static void spawnExplosionParticles(World world, BlockPos pos) {
        if (world instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(ParticleTypes.POOF, pos.getX() + 0.5, pos.getY() + 2,
                    pos.getZ() + 0.5, 10, 0.3, 0.3, 0.3, 0.05);
        }
    }

    public boolean isInflating() {
        return isInflating;
    }


}
