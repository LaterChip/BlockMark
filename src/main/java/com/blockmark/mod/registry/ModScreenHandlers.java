package com.blockmark.mod.registry;

import com.blockmark.mod.BlockMarkMod;
import com.blockmark.mod.screen.CheckoutCounterScreenHandler;
import com.blockmark.mod.screen.WallShelfScreenHandler;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Registry for all screen handler types in the BlockMark mod.
 * <p>
 * Each shelf/cooler/display block gets its own screen handler type to allow
 * for separate GUI handling. The checkout counter has its own 27-slot handler.
 * This class provides utility methods for querying and grouping handler types.
 * </p>
 *
 * <h3>Handler Groups</h3>
 * <ul>
 *   <li><b>Shelf handlers:</b> Use WallShelfScreenHandler (14 types)</li>
 *   <li><b>Checkout handler:</b> Uses CheckoutCounterScreenHandler (1 type)</li>
 * </ul>
 */
public class ModScreenHandlers {

    // --- Shelf / Display handlers (WallShelfScreenHandler) ---

    public static final ScreenHandlerType<WallShelfScreenHandler> WALL_SHELF_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, BlockMarkMod.id("wall_shelf"),
                    new ScreenHandlerType<>(WallShelfScreenHandler::new, FeatureFlags.VANILLA_FEATURES));

    public static final ScreenHandlerType<WallShelfScreenHandler> DOUBLE_SIDED_SHELF_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, BlockMarkMod.id("double_sided_shelf"),
                    new ScreenHandlerType<>(WallShelfScreenHandler::new, FeatureFlags.VANILLA_FEATURES));

    public static final ScreenHandlerType<WallShelfScreenHandler> END_CAP_SHELF_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, BlockMarkMod.id("end_cap_shelf"),
                    new ScreenHandlerType<>(WallShelfScreenHandler::new, FeatureFlags.VANILLA_FEATURES));

    public static final ScreenHandlerType<WallShelfScreenHandler> HEAVY_DUTY_SHELF_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, BlockMarkMod.id("heavy_duty_shelf"),
                    new ScreenHandlerType<>(WallShelfScreenHandler::new, FeatureFlags.VANILLA_FEATURES));

    public static final ScreenHandlerType<WallShelfScreenHandler> BEVERAGE_COOLER_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, BlockMarkMod.id("beverage_cooler"),
                    new ScreenHandlerType<>(WallShelfScreenHandler::new, FeatureFlags.VANILLA_FEATURES));

    public static final ScreenHandlerType<WallShelfScreenHandler> FREEZER_CHEST_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, BlockMarkMod.id("freezer_chest"),
                    new ScreenHandlerType<>(WallShelfScreenHandler::new, FeatureFlags.VANILLA_FEATURES));

    public static final ScreenHandlerType<WallShelfScreenHandler> FRIDGE_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, BlockMarkMod.id("fridge"),
                    new ScreenHandlerType<>(WallShelfScreenHandler::new, FeatureFlags.VANILLA_FEATURES));

    public static final ScreenHandlerType<CheckoutCounterScreenHandler> CHECKOUT_COUNTER_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, BlockMarkMod.id("checkout_counter"),
                    new ScreenHandlerType<>(CheckoutCounterScreenHandler::new, FeatureFlags.VANILLA_FEATURES));

    public static final ScreenHandlerType<WallShelfScreenHandler> WALL_MOUNT_SHELF_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, BlockMarkMod.id("wall_mount_shelf"),
                    new ScreenHandlerType<>(WallShelfScreenHandler::new, FeatureFlags.VANILLA_FEATURES));

    public static final ScreenHandlerType<WallShelfScreenHandler> PRODUCE_DISPLAY_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, BlockMarkMod.id("produce_display"),
                    new ScreenHandlerType<>(WallShelfScreenHandler::new, FeatureFlags.VANILLA_FEATURES));

    public static final ScreenHandlerType<WallShelfScreenHandler> GLASS_CABINET_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, BlockMarkMod.id("glass_cabinet"),
                    new ScreenHandlerType<>(WallShelfScreenHandler::new, FeatureFlags.VANILLA_FEATURES));

    public static final ScreenHandlerType<WallShelfScreenHandler> OPEN_COOLER_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, BlockMarkMod.id("open_cooler"),
                    new ScreenHandlerType<>(WallShelfScreenHandler::new, FeatureFlags.VANILLA_FEATURES));

    public static final ScreenHandlerType<WallShelfScreenHandler> STORAGE_CRATE_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, BlockMarkMod.id("storage_crate"),
                    new ScreenHandlerType<>(WallShelfScreenHandler::new, FeatureFlags.VANILLA_FEATURES));

    public static final ScreenHandlerType<WallShelfScreenHandler> VENDING_MACHINE_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, BlockMarkMod.id("vending_machine"),
                    new ScreenHandlerType<>(WallShelfScreenHandler::new, FeatureFlags.VANILLA_FEATURES));

    /**
     * Returns all registered screen handler types in a read-only map.
     * Keys are the registration IDs, values are the type instances.
     *
     * @return an unmodifiable map of all handler types
     */
    public static Map<String, ScreenHandlerType<?>> getAllTypes() {
        Map<String, ScreenHandlerType<?>> map = new LinkedHashMap<>();
        map.put("wall_shelf", WALL_SHELF_SCREEN_HANDLER);
        map.put("double_sided_shelf", DOUBLE_SIDED_SHELF_SCREEN_HANDLER);
        map.put("end_cap_shelf", END_CAP_SHELF_SCREEN_HANDLER);
        map.put("heavy_duty_shelf", HEAVY_DUTY_SHELF_SCREEN_HANDLER);
        map.put("beverage_cooler", BEVERAGE_COOLER_SCREEN_HANDLER);
        map.put("freezer_chest", FREEZER_CHEST_SCREEN_HANDLER);
        map.put("fridge", FRIDGE_SCREEN_HANDLER);
        map.put("checkout_counter", CHECKOUT_COUNTER_SCREEN_HANDLER);
        map.put("wall_mount_shelf", WALL_MOUNT_SHELF_SCREEN_HANDLER);
        map.put("produce_display", PRODUCE_DISPLAY_SCREEN_HANDLER);
        map.put("glass_cabinet", GLASS_CABINET_SCREEN_HANDLER);
        map.put("open_cooler", OPEN_COOLER_SCREEN_HANDLER);
        map.put("storage_crate", STORAGE_CRATE_SCREEN_HANDLER);
        map.put("vending_machine", VENDING_MACHINE_SCREEN_HANDLER);
        return Collections.unmodifiableMap(map);
    }

    /**
     * Returns the total number of registered screen handler types.
     *
     * @return the count of registered handler types
     */
    public static int getHandlerCount() {
        return getAllTypes().size();
    }

    /**
     * Called during mod initialization to trigger static class loading
     * and ensure all screen handler types are registered.
     */
    public static void register() {
        // Static fields are initialized on class load; this method exists
        // as an explicit registration entry point called by BlockMarkMod
    }
}
