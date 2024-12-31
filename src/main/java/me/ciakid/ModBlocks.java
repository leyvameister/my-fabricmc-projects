package me.ciakid;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {
    public static final Block BALLOON = register("balloon", new BalloonBlock(Block.Settings.create()));

    private static <T extends Block> T register(String name, T block) {
        Registry.register(Registries.BLOCK, Identifier.of(Main.MOD_ID, name), block);
        Registry.register(Registries.ITEM, Identifier.of(Main.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
        return block;
    }

    public static void initialize() {
    }
}

