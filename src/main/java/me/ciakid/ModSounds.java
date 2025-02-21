package me.ciakid;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {

    public static final Identifier BALLOON_SOUND_ID = Identifier.of("ciakid:balloon_pop");
    public static SoundEvent BALLOON_SOUND_EVENT = SoundEvent.of(BALLOON_SOUND_ID);

    public static void registerSounds() {
        Registry.register(Registries.SOUND_EVENT, BALLOON_SOUND_ID, BALLOON_SOUND_EVENT);
    }

}
