package me.ciakid;


import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class BalloonBlockModel extends GeoModel<BalloonBlockEntity> {

    @Override
    public Identifier getModelResource(BalloonBlockEntity animatable) {
        return Identifier.of(Main.MOD_ID, "geo/balloon.geo.json");
    }

    @Override
    public Identifier getTextureResource(BalloonBlockEntity animatable) {
        return Identifier.of(Main.MOD_ID, "textures/balloon.png");
    }

    @Override
    public Identifier getAnimationResource(BalloonBlockEntity animatable) {
        return Identifier.of(Main.MOD_ID, "animations/balloon.animation.json");
    }
}
