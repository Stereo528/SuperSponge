package io.github.stereo528;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import static io.github.stereo528.SuperSpongeMain.MOD_ID;

public class SuperSpongeBlocks {
    public static final Block SUPERSPONGE = register("supersponge", new SuperSpongeBlock(FabricBlockSettings.copy(Blocks.SPONGE)));
    public static final Block SUPERWETSPONGE = register("superwetsponge", new SuperWetSpongeBlock(FabricBlockSettings.copy(Blocks.WET_SPONGE)));

    public static void init() {}

    private static Block register(String name, Block block) {
        Registry.register(Registry.ITEM, new ResourceLocation(MOD_ID, name), new BlockItem(block, new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
        return Registry.register(Registry.BLOCK, new ResourceLocation(MOD_ID, name), block);
    }
}
