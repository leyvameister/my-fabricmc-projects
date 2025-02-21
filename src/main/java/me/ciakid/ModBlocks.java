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
import net.minecraft.util.Pair;

public class ModBlocks {
    public static final Pair<Block, BlockItem> BALLOON = register("balloon", new BalloonBlock(Block.Settings.create().nonOpaque()));

    private static <T extends Block> Pair<T, BlockItem> register(String name, T block) {
        Identifier id = Identifier.of(Main.MOD_ID, name);

        Registry.register(Registries.BLOCK, id, block);
        BlockItem blockItem = new BlockItem(block, new Item.Settings());
        Registry.register(Registries.ITEM, id, blockItem);

        return new Pair<>(block, blockItem);

    }

    public static void initialize() {

    }
}

