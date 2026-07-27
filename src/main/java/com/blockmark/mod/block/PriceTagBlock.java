package com.blockmark.mod.block;

import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import com.mojang.serialization.MapCodec;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

/**
 * Price Tag - a small text display stand block for labeling
 * shelves and products.
 * <p>
 * This block is purely decorative and does not have a block entity
 * or inventory. It supports horizontal facing placement, waterlogging,
 * and a compact 8x8x8 collision/outline shape for realistic rendering.
 * </p>
 *
 * <ul>
 *   <li><b>Facing:</b> Determined by player placement direction (opposite)</li>
 *   <li><b>Waterlogging:</b> Supported via WATERLOGGED property</li>
 *   <li><b>Render Type:</b> MODEL</li>
 *   <li><b>Shape:</b> Compact 8x8x8 cube (centered)</li>
 * </ul>
 */
public class PriceTagBlock extends Block {
    /** The direction this block faces horizontally. */
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

    /** Whether this block is waterlogged. */
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    /** Compact collision/outline shape: 4,0,4 to 12,8,12 (centered 8x8x8). */
    private static final VoxelShape SHAPE = Block.createCuboidShape(4.0, 0.0, 4.0, 12.0, 8.0, 12.0);

    /**
     * Constructs a new PriceTagBlock with the given settings.
     * Default state: facing NORTH, not waterlogged.
     *
     * @param settings the block settings (material, hardness, etc.)
     */
    public PriceTagBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState()
                .with(FACING, Direction.NORTH)
                .with(WATERLOGGED, false));
    }

    /**
     * Returns the render type for this block.
     *
     * @param state the current block state (unused)
     * @return MODEL for JSON model rendering
     */
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    /**
     * Determines the block state when placed, based on the placement context.
     * The block faces opposite to the player so it appears to face outward.
     *
     * @param ctx the item placement context
     * @return the block state with appropriate facing and waterlogging
     */
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        return this.getDefaultState()
                .with(FACING, ctx.getHorizontalPlayerFacing().getOpposite())
                .with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }

    /**
     * Returns the fluid state for this block. If waterlogged, the block
     * contains water at its position.
     *
     * @param state the current block state
     * @return the fluid state (water if waterlogged, empty otherwise)
     */
    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    /**
     * Updates the block state when a neighboring block changes.
     * Handles waterlogging fluid updates.
     *
     * @param state         the current block state
     * @param direction     the direction of the neighbor update
     * @param neighborState the neighbor's block state
     * @param world         the world access instance
     * @param pos           the position of this block
     * @param neighborPos   the position of the neighbor that changed
     * @return the updated block state
     */
    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction,
                                                 BlockState neighborState, WorldAccess world,
                                                 BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    /**
     * Returns the visual outline shape for rendering selection boxes.
     * Uses a compact 8x8x8 bounding box to match the small size of a price tag stand.
     *
     * @param state   the current block state
     * @param world   the block view
     * @param pos     the position
     * @param context the shape context
     * @return a compact VoxelShape (4,0,4 to 12,8,12)
     */
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    /**
     * Returns the collision shape used for entity physics.
     * Uses the same compact 8x8x8 bounding box as the outline shape.
     *
     * @param state   the current block state
     * @param world   the block view
     * @param pos     the position
     * @param context the shape context
     * @return a compact VoxelShape (4,0,4 to 12,8,12)
     */
    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    /**
     * Appends block state properties to the state manager builder.
     * Includes facing and waterlogging.
     *
     * @param builder the state manager builder
     */
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
    }

    /**
     * Returns the MapCodec for serialization.
     *
     * @return the codec for this block type
     */
    @Override
    protected MapCodec<? extends Block> getCodec() {
        return createCodec(PriceTagBlock::new);
    }
}
