package com.blockmark.mod.registry;

import com.blockmark.mod.BlockMarkMod;
import com.blockmark.mod.block.entity.CheckoutCounterBlockEntity;
import com.blockmark.mod.block.entity.WallShelfBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Registry for all block entity types in the BlockMark mod.
 * <p>
 * Block entities are registered via the Fabric block entity type builder,
 * which links a block entity class to its valid block instances. This
 * registry provides lookup and validation utility methods in addition
 * to the standard Fabric registration.
 * </p>
 *
 * <h3>Registered Entities</h3>
 * <ul>
 *   <li><b>WALL_SHELF_ENTITY</b> — WallShelfBlockEntity, used by 14 shelf/display blocks</li>
 *   <li><b>CHECKOUT_COUNTER_ENTITY</b> — CheckoutCounterBlockEntity, used by the checkout counter</li>
 * </ul>
 *
 * @see WallShelfBlockEntity
 * @see CheckoutCounterBlockEntity
 */
public class ModBlockEntities {

    /** Maps each registered block entity type to its list of valid blocks (replaces access to private BlockEntityType.blocks). */
    private static final Map<BlockEntityType<?>, List<Block>> BLOCK_MAP = new LinkedHashMap<>();

    /** Wall shelf block entity type, shared by all shelf, cooler, display, and machine blocks. */
    public static final BlockEntityType<WallShelfBlockEntity> WALL_SHELF_ENTITY = registerType(
            "wall_shelf",
            WallShelfBlockEntity::new,
            ModBlocks.WALL_SHELF, ModBlocks.DOUBLE_SIDED_SHELF, ModBlocks.END_CAP_SHELF,
            ModBlocks.WALL_MOUNT_SHELF, ModBlocks.PRODUCE_DISPLAY, ModBlocks.HEAVY_DUTY_SHELF,
            ModBlocks.GLASS_CABINET, ModBlocks.BEVERAGE_COOLER, ModBlocks.FREEZER_CHEST,
            ModBlocks.FRIDGE, ModBlocks.OPEN_COOLER, ModBlocks.STORAGE_CRATE,
            ModBlocks.VENDING_MACHINE);

    /** Checkout counter block entity type, dedicated to the checkout counter block. */
    public static final BlockEntityType<CheckoutCounterBlockEntity> CHECKOUT_COUNTER_ENTITY = registerType(
            "checkout_counter",
            CheckoutCounterBlockEntity::new,
            ModBlocks.CHECKOUT_COUNTER);

    /**
     * Registers a block entity type and records its valid blocks in the local block map.
     *
     * @param id     the registration ID path (without namespace)
     * @param factory the block entity factory
     * @param blocks the blocks that support this block entity type
     * @param <T>    the block entity type
     * @return the registered BlockEntityType
     */
    @SafeVarargs
    private static <T extends net.minecraft.block.entity.BlockEntity> BlockEntityType<T> registerType(
            String id, FabricBlockEntityTypeBuilder.Factory<T> factory, Block... blocks) {
        BlockEntityType<T> type = Registry.register(
                Registries.BLOCK_ENTITY_TYPE, BlockMarkMod.id(id),
                FabricBlockEntityTypeBuilder.create(factory, blocks).build());
        BLOCK_MAP.put(type, Collections.unmodifiableList(new ArrayList<>(Arrays.asList(blocks))));
        return type;
    }

    /**
     * Returns all registered block entity types in a read-only map.
     * Keys are the registration IDs (e.g., "wall_shelf"), values are the type instances.
     *
     * @return an unmodifiable map of all registered block entity types
     */
    public static Map<String, BlockEntityType<?>> getAllTypes() {
        Map<String, BlockEntityType<?>> map = new LinkedHashMap<>();
        map.put("wall_shelf", WALL_SHELF_ENTITY);
        map.put("checkout_counter", CHECKOUT_COUNTER_ENTITY);
        return Collections.unmodifiableMap(map);
    }

    /**
     * Returns a list of all blocks that are valid for a given block entity type.
     * This is useful for programmatic block placement validation.
     *
     * @param type the block entity type to query
     * @return an unmodifiable list of valid block instances
     */
    public static List<Block> getAllowedBlocks(BlockEntityType<?> type) {
        List<Block> blocks = BLOCK_MAP.get(type);
        return blocks != null ? blocks : Collections.emptyList();
    }

    /**
     * Checks whether a given block is valid for a given block entity type.
     *
     * @param type  the block entity type to check against
     * @param block the block to validate
     * @return true if the block supports the given block entity type
     */
    public static boolean supportsBlock(BlockEntityType<?> type, Block block) {
        return type.supports(block.getDefaultState());
    }

    /**
     * Looks up a block entity type by its registration ID string.
     *
     * @param id the registration ID (e.g., "wall_shelf", "checkout_counter")
     * @return the matching BlockEntityType, or null if not found
     */
    public static BlockEntityType<?> getById(String id) {
        return getAllTypes().get(id);
    }

    /**
     * Counts the total number of block instances supported across all registered
     * block entity types. Useful for validation reports.
     *
     * @return the sum of all supported block counts
     */
    public static int getTotalSupportedBlocks() {
        return BLOCK_MAP.values().stream().mapToInt(List::size).sum();
    }

    /**
     * Returns the total number of registered block entity types.
     *
     * @return the count of registered types
     */
    public static int getTypeCount() {
        return getAllTypes().size();
    }

    /**
     * Called during mod initialization to trigger static class loading
     * and ensure all block entity types are registered.
     */
    public static void register() {
        // Static fields are initialized on class load; this method exists
        // as an explicit registration entry point called by BlockMarkMod
    }
}
