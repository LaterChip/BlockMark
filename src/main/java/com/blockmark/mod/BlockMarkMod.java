package com.blockmark.mod;

import com.blockmark.mod.registry.ModBlocks;
import com.blockmark.mod.registry.ModItems;
import com.blockmark.mod.registry.ModBlockEntities;
import com.blockmark.mod.registry.ModScreenHandlers;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BlockMarkMod implements ModInitializer {
    public static final String MOD_ID = "blockmark";
    public static final Logger LOGGER = LoggerFactory.getLogger(BlockMarkMod.class);

    public static final RegistryKey<ItemGroup> BLOCKMARK_GROUP_KEY =
            RegistryKey.of(RegistryKeys.ITEM_GROUP, id("blockmark_tab"));

    @Override
    public void onInitialize() {
        ModItems.register();
        ModBlocks.register();
        ModBlockEntities.register();
        ModScreenHandlers.register();

        Registry.register(Registries.ITEM_GROUP, BLOCKMARK_GROUP_KEY,
                FabricItemGroup.builder()
                        .icon(() -> new ItemStack(ModItems.SUPERMARKET_LOGO))
                        .displayName(Text.translatable("itemGroup.blockmark.main"))
                        .entries((displayContext, entries) -> {
                            // Category 1: Fresh Produce
                            entries.add(ModItems.BANANA);
                            entries.add(ModItems.GRAPE);
                            entries.add(ModItems.BLUEBERRY);
                            entries.add(ModItems.STRAWBERRY);
                            entries.add(ModItems.ORANGE);
                            entries.add(ModItems.TOMATO);
                            entries.add(ModItems.CUCUMBER);
                            entries.add(ModItems.LETTUCE);
                            entries.add(ModItems.CORN);
                            entries.add(ModItems.GREEN_PEPPER);
                            entries.add(ModItems.ONION);
                            entries.add(ModItems.MUSHROOM_BOX);

                            // Category 2: Meat & Seafood
                            entries.add(ModItems.BACON);
                            entries.add(ModItems.SAUSAGE);
                            entries.add(ModItems.SHRIMP);
                            entries.add(ModItems.CRAB);
                            entries.add(ModItems.FISH_FILLET);

                            // Category 3: Bakery
                            entries.add(ModItems.TOAST);
                            entries.add(ModItems.CROISSANT);
                            entries.add(ModItems.DONUT);
                            entries.add(ModItems.PIZZA);
                            entries.add(ModItems.COOKIE_BOX);
                            entries.add(ModItems.NOODLES);
                            entries.add(ModItems.RICE);
                            entries.add(ModItems.DUMPLING);

                            // Category 4: Snacks
                            entries.add(ModItems.POTATO_CHIPS);
                            entries.add(ModItems.CHOCOLATE);
                            entries.add(ModItems.CANDY);
                            entries.add(ModItems.POPCORN);
                            entries.add(ModItems.NUTS);
                            entries.add(ModItems.JELLY);
                            entries.add(ModItems.WAFER);
                            entries.add(ModItems.SPICY_STRIPS);
                            entries.add(ModItems.CANNED_FRUIT);

                            // Category 5: Beverages
                            entries.add(ModItems.BOTTLED_WATER);
                            entries.add(ModItems.COLA);
                            entries.add(ModItems.JUICE);
                            entries.add(ModItems.MILK_TEA);
                            entries.add(ModItems.COFFEE);
                            entries.add(ModItems.BEER);
                            entries.add(ModItems.YOGURT);
                            entries.add(ModItems.ICE_CREAM);
                            entries.add(ModItems.POPSICLE);

                            // Category 6: Groceries & Condiments
                            entries.add(ModItems.FLOUR);
                            entries.add(ModItems.COOKING_OIL);
                            entries.add(ModItems.SALT);
                            entries.add(ModItems.SAUCE);
                            entries.add(ModItems.SOY_SAUCE);
                            entries.add(ModItems.VINEGAR);
                            entries.add(ModItems.BAGGED_RICE);

                            // Category 7: Daily Supplies
                            entries.add(ModItems.TOWEL);
                            entries.add(ModItems.SOAP);
                            entries.add(ModItems.GARBAGE_BAG);
                            entries.add(ModItems.FLASHLIGHT);
                            entries.add(ModItems.STORAGE_BAG);
                            entries.add(ModItems.CLEANER);

                            // Category 8: Garden Seeds
                            entries.add(ModItems.SHRUB_SEEDS);
                            entries.add(ModItems.POTTED_SEEDLING);
                            entries.add(ModItems.FERTILIZER_BAG);

                            // Category 13: Clothing
                            entries.add(ModItems.PET_COLLAR);
                            entries.add(ModItems.PET_FOOD);

                            // Category 17: Seasonal
                            entries.add(ModItems.GIFT_BOX);
                            entries.add(ModItems.RIBBON);

                            // Category 12: Health
                            entries.add(ModItems.FIRST_AID_KIT);
                            entries.add(ModItems.BANDAGE);
                            entries.add(ModItems.ENERGY_DRINK);

                            // Blocks - Shelves
                            entries.add(ModBlocks.WALL_SHELF);
                            entries.add(ModBlocks.DOUBLE_SIDED_SHELF);
                            entries.add(ModBlocks.END_CAP_SHELF);
                            entries.add(ModBlocks.WALL_MOUNT_SHELF);
                            entries.add(ModBlocks.PRODUCE_DISPLAY);
                            entries.add(ModBlocks.HEAVY_DUTY_SHELF);
                            entries.add(ModBlocks.GLASS_CABINET);

                            // Blocks - Refrigeration
                            entries.add(ModBlocks.BEVERAGE_COOLER);
                            entries.add(ModBlocks.FREEZER_CHEST);
                            entries.add(ModBlocks.FRIDGE);
                            entries.add(ModBlocks.OPEN_COOLER);

                            // Blocks - Checkout
                            entries.add(ModBlocks.CHECKOUT_COUNTER);
                            entries.add(ModBlocks.BARCODE_SCANNER);
                            entries.add(ModBlocks.SELF_CHECKOUT);
                            entries.add(ModBlocks.PRICE_TAG);
                            entries.add(ModBlocks.AD_SCREEN);

                            // Blocks - Facilities
                            entries.add(ModBlocks.SHOPPING_BASKET);
                            entries.add(ModBlocks.STORAGE_CRATE);
                            entries.add(ModBlocks.PALLET);
                            entries.add(ModBlocks.VENDING_MACHINE);
                            entries.add(ModBlocks.TRASH_CAN);
                            entries.add(ModBlocks.BENCH_TABLE);
                        })
                        .build());
    }

    public static Identifier id(String path) {
        return new Identifier(MOD_ID, path);
    }
}
