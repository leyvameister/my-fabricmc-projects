package me.ciakid;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib.GeckoLibClient;
import software.bernie.geckolib.cache.GeckoLibCache;
import software.bernie.geckolib.network.GeckoLibNetworkingFabric;
import software.bernie.geckolib.platform.GeckoLibClientFabric;
import software.bernie.geckolib.platform.GeckoLibFabric;
import software.bernie.geckolib.service.GeckoLibNetworking;
import software.bernie.geckolib.util.GeckoLibUtil;

import static net.minecraft.server.command.CommandManager.literal;


public class Main implements ModInitializer {
    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("ciakid");
    public static final String MOD_ID = "ciakid";

    @Override
    public void onInitialize() {
        ModBlocks.initialize();
        ModBlockEntities.initialize();
        ModSounds.registerSounds();

        ItemGroups.initialize();

        PayloadTypeRegistry.playC2S().register(ScaleAnimationPayload.ID, ScaleAnimationPayload.CODEC);

        registerCommands();
    }

    private void registerCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("inflate")
                    .executes(context -> {
                        ServerCommandSource source = context.getSource();
                        Vec3i pos = new Vec3i(0, 5, 0);
                        BlockPos blockPos = new BlockPos(pos);
                        ServerWorld world = source.getWorld();
                        world.getBlockEntity(blockPos, ModBlockEntities.BALLOON_BLOCK_ENTITY)
                                .ifPresent(balloon -> {
                                    balloon.toggleInflate();

                                    source.sendFeedback(() -> Text.literal(balloon.isInflating() ? "inflating" : "stopped"), false);

                                });
                        return 1;
                    })
            );
        });
    }
}