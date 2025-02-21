package me.ciakid;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

public record ScaleAnimationPayload(int currentScaleAnimation) implements CustomPayload {
    public static final CustomPayload.Id<ScaleAnimationPayload> ID =
            new CustomPayload.Id<>(NetworkingConstants.SCALE_ANIMATION_PACKET_ID);

    public static final PacketCodec<RegistryByteBuf, ScaleAnimationPayload> CODEC =
            PacketCodec.tuple(PacketCodecs.INTEGER, ScaleAnimationPayload::currentScaleAnimation, ScaleAnimationPayload::new);

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return ID;
    }
}
