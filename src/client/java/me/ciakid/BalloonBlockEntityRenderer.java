package me.ciakid;

import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class BalloonBlockEntityRenderer extends GeoBlockRenderer<BalloonBlockEntity> {

    public BalloonBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
        super(new BalloonBlockModel());
    }

}
