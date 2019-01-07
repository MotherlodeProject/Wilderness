package io.github.motherlodeproject.wilderness.registry;

import io.github.motherlodeproject.commons.block.HangingClimbableBlock;
import io.github.motherlodeproject.wilderness.Wilderness;
import io.github.prospector.silk.block.SilkStairsBlock;
import net.fabricmc.fabric.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.block.BlockItem;
import net.minecraft.util.Identifier;

public class WildernessBlocks {

	public static final Block ROPE = add("rope", new HangingClimbableBlock(FabricBlockSettings.of(Material.WOOL).breakInstantly().build()), ItemGroup.MISC);

	public static void addWithStairsAndSlab(String name, Block.Settings settings, ItemGroup tab) {
		Block baseBlock = add(name, new Block(settings), tab);
		add(name + "_slab", new SlabBlock(settings), tab);
		add(name + "_stairs", new SilkStairsBlock(baseBlock.getDefaultState(), settings), tab);
	}

	public static void addWithStairsSlabAndWall(String name, Block.Settings settings, ItemGroup tab) {
		Block baseBlock = add(name, new Block(settings), tab);
		add(name + "_slab", new SlabBlock(settings), tab);
		add(name + "_stairs", new SilkStairsBlock(baseBlock.getDefaultState(), settings), tab);
		add(name + "_wall", new WallBlock(settings), tab);
	}

	public static Block add(String name, Block block, ItemGroup tab) {
		return add(name, block, new BlockItem(block, new Item.Settings().itemGroup(tab)));
	}

	public static Block add(String name, Block block, BlockItem item) {
		WildernessRegistry.BLOCKS.put(new Identifier(Wilderness.MOD_ID, name), block);
		if (item != null) {
			item.registerBlockItemMap(Item.BLOCK_ITEM_MAP, item);
			WildernessItems.add(name, item);
		}
		return block;
	}

	public static void loadClass() {}
}
