package me.ciakid;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.ConnectScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.text.Text;


public class MainClient implements ClientModInitializer {


    @Override
    public void onInitializeClient() {
        BlockEntityRendererFactories.register(ModBlockEntities.BALLOON_BLOCK_ENTITY, BalloonBlockEntityRenderer::new);


        // Registering an event to initialize the screen after the client is ready.
//        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
//            if (screen instanceof TitleScreen) {
//                client.setScreen(new TutorialScreen());
//
//            }
//        });
    }
}

@Environment(EnvType.CLIENT)
class TutorialScreen extends Screen {
    protected TutorialScreen() {
        super(Text.literal("My tutorial screen"));
    }

    public ButtonWidget button1;

    @Override
    protected void init() {
        button1 = ButtonWidget.builder(Text.literal("Play"), button -> {
                    connectToServer("mc.srolemine.com");
                })
                .dimensions((width - 200) / 2, (height - 20) / 2, 200, 20)
                //.tooltip(Tooltip.of(Text.literal("Click to join")))
                .build();


        addDrawableChild(button1);



    }

    private void connectToServer(String ip) {
        MinecraftClient client = MinecraftClient.getInstance();
        ServerAddress serverAddress = ServerAddress.parse(ip);
        ServerInfo serverInfo = new ServerInfo("Hypixel", ip, ServerInfo.ServerType.OTHER);
        ConnectScreen.connect(this, client, serverAddress, serverInfo, false, null);
    }
}