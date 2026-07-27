package com.blockmark.mod.block;

import com.blockmark.mod.block.entity.WallShelfBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import com.mojang.serialization.MapCodec;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

/**
 * Wall Shelf
 * <p>
 * This block provides a storage inventory accessible via right-click interaction.
 * It supports horizontal facing for placement orientation, waterlogging for
 * submerged placement, custom collision and outline shapes, and automatic
 * inventory item scattering when the block is destroyed.
 * </p>
 *
 * <ul>
 *   <li><b>Facing:</b> Determined by player placement direction (opposite)</li>
 *   <li><b>Waterlogging:</b> Supported via WATERLOGGED property</li>
 *   <li><b>Render Type:</b> MODEL</li>
 *   <li><b>Inventory:</b> 27 slots, dropped on removal</li>
 * </ul>
 *
 * @see WallShelfBlockEntity
 */
public class WallShelfBlock extends BlockWithEntity {
    /** The direction this block faces horizontally. */
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

    /** Whether this block is waterlogged. */
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    /** The collision and outline bounding shape for this block (full cube). */
    private static final VoxelShape SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 16.0);

    /**
     * Constructs a new WallShelfBlock with the given settings.
     * Default state: facing NORTH, not waterlogged.
     *
     * @param settings the block settings (material, hardness, etc.)
     */
    public WallShelfBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState()
                .with(FACING, Direction.NORTH)
                .with(WATERLOGGED, false));
    }

    /**
     * Creates the block entity for this block.
     * All shelf and display blocks share the WallShelfBlockEntity.
     *
     * @param pos   the position of the block
     * @param state the current block state
     * @return a new WallShelfBlockEntity instance
     */
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new WallShelfBlockEntity(pos, state);
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
     * Handles right-click interaction. Opens the block's screen handler
     * on the server side for inventory access.
     *
     * @param state  the current block state
     * @param world  the world instance
     * @param pos    the position of the block
     * @param player the player who interacted
     * @param hand   the hand used for interaction
     * @param hit    the hit result details
     * @return SUCCESS on both client and server
     */
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos,
                               PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            NamedScreenHandlerFactory factory = state.createScreenHandlerFactory(world, pos);
            if (factory != null) {
                player.openHandledScreen(factory);
            }
        }
        return ActionResult.SUCCESS;
    }

    /**
     * Called when the block state is replaced. Scatters inventory contents
     * if the block type changes (e.g., destroyed).
     *
     * @param state    the old block state
     * @param world    the world instance
     * @param pos      the position of the block
     * @param newState the new block state replacing this one
     * @param moved    whether the block was moved by a piston
     */
    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos,
                                BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity be = world.getBlockEntity(pos);
            if (be instanceof Inventory) {
                ItemScatterer.spawn(world, pos, (Inventory) be);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    /**
     * Determines the block state when placed, based on the placement context.
     * The block faces opposite to the player's horizontal facing direction.
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
     *
     * @param state   the current block state
     * @param world   the block view
     * @param pos     the position
     * @param context the shape context
     * @return a full-cube VoxelShape
     */
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    /**
     * Returns the collision shape used for entity physics.
     *
     * @param state   the current block state
     * @param world   the block view
     * @param pos     the position
     * @param context the shape context
     * @return a full-cube VoxelShape
     */
    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    /**
     * Appends block state properties to the state manager builder.
     * Subclasses should call super.appendProperties first.
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
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return createCodec(WallShelfBlock::new);
    }
}
