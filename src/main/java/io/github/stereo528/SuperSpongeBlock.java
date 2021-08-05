package io.github.stereo528;

import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.Tuple;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Material;

import java.util.Queue;

public class SuperSpongeBlock extends Block{

    protected SuperSpongeBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    public void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (!blockState2.is(blockState.getBlock())) {
            this.tryAbsorbWater(level, blockPos, blockState2);
        }
    }

    public void neighborChanged(BlockState blockState, Level level, BlockPos blockPos, Block block, BlockPos blockPos2, boolean bl) {
        this.tryAbsorbWater(level, blockPos, blockState);
        super.neighborChanged(blockState, level, blockPos, block, blockPos2, bl);
    }

    protected void tryAbsorbWater(Level level, BlockPos blockPos, BlockState blockState) {
        if (this.removeWaterBreadthFirstSearch(level, blockPos)) {
            if(blockState.getBlock().getName().getString() != "Lava") {
                level.setBlock(blockPos, SuperSpongeBlocks.SUPERWETSPONGE.defaultBlockState(), 2);
                level.levelEvent(2001, blockPos, Block.getId(Blocks.WATER.defaultBlockState()));
            }
            else {
                level.levelEvent(2001, blockPos, Block.getId(Blocks.LAVA.defaultBlockState()));
            }
        }

    }

    private boolean removeWaterBreadthFirstSearch(Level level, BlockPos blockPos) {
        Queue<Tuple<BlockPos, Integer>> queue = Lists.newLinkedList();
        queue.add(new Tuple(blockPos, 0));
        int i = 0;

        while(!queue.isEmpty()) {
            Tuple<BlockPos, Integer> tuple = (Tuple)queue.poll();
            BlockPos blockPos2 = (BlockPos)tuple.getA();
            int j = (Integer)tuple.getB();
            Direction[] var8 = Direction.values();
            int var9 = var8.length;

            for(int var10 = 0; var10 < var9; ++var10) {
                Direction direction = var8[var10];
                BlockPos blockPos3 = blockPos2.relative(direction);
                BlockState blockState = level.getBlockState(blockPos3);
                FluidState fluidState = level.getFluidState(blockPos3);
                Material material = blockState.getMaterial();
                for(Tag tag : FluidTags.getAllTags().getAllTags().values())
                    if (fluidState.is(tag)) {
                        if (blockState.getBlock() instanceof BucketPickup && !((BucketPickup)blockState.getBlock()).pickupBlock(level, blockPos3, blockState).isEmpty()) {
                            ++i;
                            if (j < 10) {
                                queue.add(new Tuple(blockPos3, j + 1));
                            }
                        } else if (blockState.getBlock() instanceof LiquidBlock) {
                            level.setBlock(blockPos3, Blocks.AIR.defaultBlockState(), 3);
                            ++i;
                            if (j < 10) {
                                queue.add(new Tuple(blockPos3, j + 1));
                            }
                        } else if (material == Material.WATER_PLANT || material == Material.REPLACEABLE_WATER_PLANT) {
                            BlockEntity blockEntity = blockState.hasBlockEntity() ? level.getBlockEntity(blockPos3) : null;
                            dropResources(blockState, level, blockPos3, blockEntity);
                            level.setBlock(blockPos3, Blocks.AIR.defaultBlockState(), 3);
                            ++i;
                            if (j < 10) {
                                queue.add(new Tuple(blockPos3, j + 1));
                            }
                        }
                    }

            }

            if (i > 64) {
                break;
            }
        }

        return i > 0;
    }
}
