package com.blockmark.mod.registry;

import com.blockmark.mod.BlockMarkMod;
import com.blockmark.mod.block.*;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

/**
 * Central registry for all custom blocks in the BlockMark mod.
 * <p>
 * Blocks are organized into functional categories:
 * <ul>
 *   <li><b>Shelving:</b> WallShelf, DoubleSidedShelf, EndCapShelf, WallMountShelf,
 *       HeavyDutyShelf, GlassCabinet, ProduceDisplay</li>
 *   <li><b>Refrigeration:</b> BeverageCooler, FreezerChest, Fridge, OpenCooler</li>
 *   <li><b>Checkout:</b> CheckoutCounter, BarcodeScanner, SelfCheckout, PriceTag, AdScreen</li>
 *   <li><b>Facilities:</b> ShoppingBasket, StorageCrate, Pallet, VendingMachine,
 *       TrashCan, BenchTable</li>
 * </ul>
 * Each block is registered with {@link Registry#BLOCK} using a namespaced ID.
 */
public class ModBlocks {
    // Shelving - Wall Shelf (27 slots, single-sided)
    public static final Block WALL_SHELF = register("wall_shelf",
            new WallShelfBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).nonOpaque()));

    // Shelving - Double Sided Shelf (36 slots, accessible from both sides)
    public static final Block DOUBLE_SIDED_SHELF = register("double_sided_shelf",
            new DoubleSidedShelfBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).nonOpaque()));

    // Shelving - End Cap Shelf (promo, lower height)
    public static final Block END_CAP_SHELF = register("end_cap_shelf",
            new EndCapShelfBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).nonOpaque()));

    // Shelving - Wall Mount Shelf (wall-mounted, 9 slots)
    public static final Block WALL_MOUNT_SHELF = register("wall_mount_shelf",
            new WallMountShelfBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).nonOpaque()));

    // Produce Display (open display for fruits/vegetables, 18 slots)
    public static final Block PRODUCE_DISPLAY = register("produce_display",
            new ProduceDisplayBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).nonOpaque()));

    // Heavy Duty Shelf (metal, large capacity, 54 slots)
    public static final Block HEAVY_DUTY_SHELF = register("heavy_duty_shelf",
            new HeavyDutyShelfBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).nonOpaque()));

    // Glass Cabinet (potion/rare items, 27 slots, glass front)
    public static final Block GLASS_CABINET = register("glass_cabinet",
            new GlassCabinetBlock(FabricBlockSettings.copyOf(Blocks.GLASS).nonOpaque().luminance(s -> 3)));

    // Refrigeration - Beverage Cooler (glass door, lit, 18 slots)
    public static final Block BEVERAGE_COOLER = register("beverage_cooler",
            new BeverageCoolerBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).nonOpaque().luminance(s -> 12)));

    // Refrigeration - Freezer Chest (chest-style, 27 slots)
    public static final Block FREEZER_CHEST = register("freezer_chest",
            new FreezerChestBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).nonOpaque()));

    // Refrigeration - Fridge (double door, 54 slots)
    public static final Block FRIDGE = register("fridge",
            new FridgeBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).nonOpaque().luminance(s -> 8)));

    // Refrigeration - Open Cooler (no glass, 18 slots)
    public static final Block OPEN_COOLER = register("open_cooler",
            new OpenCoolerBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).nonOpaque()));

    // Checkout - Counter (main GUI for shopping, 27 slots)
    public static final Block CHECKOUT_COUNTER = register("checkout_counter",
            new CheckoutCounterBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).nonOpaque()));

    // Checkout - Barcode Scanner (decorative + interactive)
    public static final Block BARCODE_SCANNER = register("barcode_scanner",
            new Block(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).nonOpaque()));

    // Checkout - Self Checkout
    public static final Block SELF_CHECKOUT = register("self_checkout",
            new Block(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).nonOpaque()));

    // Checkout - Price Tag (text display stand)
    public static final Block PRICE_TAG = register("price_tag",
            new PriceTagBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).nonOpaque()));

    // Checkout - Ad Screen
    public static final Block AD_SCREEN = register("ad_screen",
            new Block(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).nonOpaque().luminance(s -> 10)));

    // Facilities - Shopping Basket (placeable)
    public static final Block SHOPPING_BASKET = register("shopping_basket",
            new Block(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).nonOpaque()));

    // Facilities - Storage Crate (small storage, 9 slots)
    public static final Block STORAGE_CRATE = register("storage_crate",
            new StorageCrateBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).nonOpaque()));

    // Facilities - Pallet
    public static final Block PALLET = register("pallet",
            new Block(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).nonOpaque()));

    // Facilities - Vending Machine
    public static final Block VENDING_MACHINE = register("vending_machine",
            new VendingMachineBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).nonOpaque().luminance(s -> 8)));

    // Facilities - Trash Can
    public static final Block TRASH_CAN = register("trash_can",
            new Block(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).nonOpaque()));

    // Facilities - Bench Table
    public static final Block BENCH_TABLE = register("bench_table",
            new Block(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).nonOpaque()));

    private static Block register(String name, Block block) {
        return Registry.register(Registries.BLOCK, BlockMarkMod.id(name), block);
    }

    /**
     * Returns a list of all registered blocks for iteration or bulk operations.
     *
     * @return an unmodifiable list of all registered Block instances
     */
    public static java.util.List<Block> getAllBlocks() {
        return java.util.List.of(
                WALL_SHELF, DOUBLE_SIDED_SHELF, END_CAP_SHELF, WALL_MOUNT_SHELF,
                PRODUCE_DISPLAY, HEAVY_DUTY_SHELF, GLASS_CABINET,
                BEVERAGE_COOLER, FREEZER_CHEST, FRIDGE, OPEN_COOLER,
                CHECKOUT_COUNTER, BARCODE_SCANNER, SELF_CHECKOUT, PRICE_TAG, AD_SCREEN,
                SHOPPING_BASKET, STORAGE_CRATE, PALLET, VENDING_MACHINE,
                TRASH_CAN, BENCH_TABLE
        );
    }

    /**
     * Returns the total number of registered blocks.
     *
     * @return the block count
     */
    public static int getBlockCount() {
        return getAllBlocks().size();
    }

    /**
     * Returns all blocks that implement a specific type (e.g., BlockWithEntity).
     * Useful for programmatic iteration over shelf-like blocks.
     *
     * @param clazz  the class to filter by
     * @param <T>    the block type
     * @return a list of blocks assignable to the given class
     */
    @SuppressWarnings("unchecked")
    public static <T extends Block> java.util.List<T> getBlocksOfType(Class<T> clazz) {
        return getAllBlocks().stream()
                .filter(clazz::isInstance)
                .map(b -> (T) b)
                .collect(java.util.stream.Collectors.toList());
    }

    public static void register() {
    }
}
