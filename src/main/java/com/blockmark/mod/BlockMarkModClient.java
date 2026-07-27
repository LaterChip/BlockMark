package com.blockmark.mod;

import com.blockmark.mod.registry.ModScreenHandlers;
import com.blockmark.mod.screen.*;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.screen.ScreenHandlerType;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Client-side initializer for the BlockMark mod.
 * <p>
 * Registers all screen handler types with their corresponding screen
 * implementations. This mapping enables Fabric's screen API to open
 * the correct GUI when a player interacts with a block.
 * </p>
 *
 * <h3>Registration Convention</h3>
 * Each {@code HandledScreens.register(...)} call binds a
 * {@link ScreenHandlerType} to a screen constructor reference.
 * The constructor receives the screen handler and player inventory
 * as parameters.
 */
public class BlockMarkModClient implements ClientModInitializer {

    /**
     * Map of all registered screen handler types to their
     * screen implementation class names. Used for debugging
     * and inspection.
     */
    private static final Map<ScreenHandlerType<?>, String> SCREEN_REGISTRY;

    static {
        Map<ScreenHandlerType<?>, String> registry = new LinkedHashMap<>();
        registry.put(ModScreenHandlers.WALL_SHELF_SCREEN_HANDLER,
                WallShelfScreen.class.getSimpleName());
        registry.put(ModScreenHandlers.DOUBLE_SIDED_SHELF_SCREEN_HANDLER,
                DoubleSidedShelfScreen.class.getSimpleName());
        registry.put(ModScreenHandlers.END_CAP_SHELF_SCREEN_HANDLER,
                EndCapShelfScreen.class.getSimpleName());
        registry.put(ModScreenHandlers.HEAVY_DUTY_SHELF_SCREEN_HANDLER,
                HeavyDutyShelfScreen.class.getSimpleName());
        registry.put(ModScreenHandlers.BEVERAGE_COOLER_SCREEN_HANDLER,
                BeverageCoolerScreen.class.getSimpleName());
        registry.put(ModScreenHandlers.FREEZER_CHEST_SCREEN_HANDLER,
                FreezerChestScreen.class.getSimpleName());
        registry.put(ModScreenHandlers.FRIDGE_SCREEN_HANDLER,
                FridgeScreen.class.getSimpleName());
        registry.put(ModScreenHandlers.CHECKOUT_COUNTER_SCREEN_HANDLER,
                CheckoutCounterScreen.class.getSimpleName());
        registry.put(ModScreenHandlers.WALL_MOUNT_SHELF_SCREEN_HANDLER,
                WallMountShelfScreen.class.getSimpleName());
        registry.put(ModScreenHandlers.PRODUCE_DISPLAY_SCREEN_HANDLER,
                ProduceDisplayScreen.class.getSimpleName());
        registry.put(ModScreenHandlers.GLASS_CABINET_SCREEN_HANDLER,
                GlassCabinetScreen.class.getSimpleName());
        registry.put(ModScreenHandlers.OPEN_COOLER_SCREEN_HANDLER,
                OpenCoolerScreen.class.getSimpleName());
        registry.put(ModScreenHandlers.STORAGE_CRATE_SCREEN_HANDLER,
                StorageCrateScreen.class.getSimpleName());
        registry.put(ModScreenHandlers.VENDING_MACHINE_SCREEN_HANDLER,
                VendingMachineScreen.class.getSimpleName());
        SCREEN_REGISTRY = Collections.unmodifiableMap(registry);
    }

    /**
     * Initializes the client-side mod resources.
     * Called automatically by Fabric after all common initialization
     * has completed.
     */
    @Override
    public void onInitializeClient() {
        registerAllScreens();
        BlockMarkMod.LOGGER.info("BlockMark client initialized with {} screen types",
                SCREEN_REGISTRY.size());
    }

    /**
     * Registers all screen handler types with their corresponding
     * screen implementations via HandledScreens.
     */
    private void registerAllScreens() {
        HandledScreens.register(ModScreenHandlers.WALL_SHELF_SCREEN_HANDLER,
                WallShelfScreen::new);
        HandledScreens.register(ModScreenHandlers.DOUBLE_SIDED_SHELF_SCREEN_HANDLER,
                DoubleSidedShelfScreen::new);
        HandledScreens.register(ModScreenHandlers.END_CAP_SHELF_SCREEN_HANDLER,
                EndCapShelfScreen::new);
        HandledScreens.register(ModScreenHandlers.HEAVY_DUTY_SHELF_SCREEN_HANDLER,
                HeavyDutyShelfScreen::new);
        HandledScreens.register(ModScreenHandlers.BEVERAGE_COOLER_SCREEN_HANDLER,
                BeverageCoolerScreen::new);
        HandledScreens.register(ModScreenHandlers.FREEZER_CHEST_SCREEN_HANDLER,
                FreezerChestScreen::new);
        HandledScreens.register(ModScreenHandlers.FRIDGE_SCREEN_HANDLER,
                FridgeScreen::new);
        HandledScreens.register(ModScreenHandlers.CHECKOUT_COUNTER_SCREEN_HANDLER,
                CheckoutCounterScreen::new);
        HandledScreens.register(ModScreenHandlers.WALL_MOUNT_SHELF_SCREEN_HANDLER,
                WallMountShelfScreen::new);
        HandledScreens.register(ModScreenHandlers.PRODUCE_DISPLAY_SCREEN_HANDLER,
                ProduceDisplayScreen::new);
        HandledScreens.register(ModScreenHandlers.GLASS_CABINET_SCREEN_HANDLER,
                GlassCabinetScreen::new);
        HandledScreens.register(ModScreenHandlers.OPEN_COOLER_SCREEN_HANDLER,
                OpenCoolerScreen::new);
        HandledScreens.register(ModScreenHandlers.STORAGE_CRATE_SCREEN_HANDLER,
                StorageCrateScreen::new);
        HandledScreens.register(ModScreenHandlers.VENDING_MACHINE_SCREEN_HANDLER,
                VendingMachineScreen::new);
    }

    /**
     * Returns a read-only view of the registered screen handler-to-screen
     * class mapping.
     *
     * @return an unmodifiable map of handler types to screen class names
     */
    public static Map<ScreenHandlerType<?>, String> getScreenRegistry() {
        return SCREEN_REGISTRY;
    }

    /**
     * Returns the total number of screen types registered.
     *
     * @return the screen type count
     */
    public static int getScreenCount() {
        return SCREEN_REGISTRY.size();
    }
}
