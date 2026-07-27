package com.blockmark.mod.registry;

import com.blockmark.mod.BlockMarkMod;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModItems {
    // Logo item
    public static final Item SUPERMARKET_LOGO = register("supermarket_logo", new Item(new Item.Settings()));

    // BlockItems
    public static final Item WALL_SHELF = registerBlockItem(ModBlocks.WALL_SHELF);
    public static final Item DOUBLE_SIDED_SHELF = registerBlockItem(ModBlocks.DOUBLE_SIDED_SHELF);
    public static final Item END_CAP_SHELF = registerBlockItem(ModBlocks.END_CAP_SHELF);
    public static final Item WALL_MOUNT_SHELF = registerBlockItem(ModBlocks.WALL_MOUNT_SHELF);
    public static final Item PRODUCE_DISPLAY = registerBlockItem(ModBlocks.PRODUCE_DISPLAY);
    public static final Item HEAVY_DUTY_SHELF = registerBlockItem(ModBlocks.HEAVY_DUTY_SHELF);
    public static final Item GLASS_CABINET = registerBlockItem(ModBlocks.GLASS_CABINET);
    public static final Item BEVERAGE_COOLER = registerBlockItem(ModBlocks.BEVERAGE_COOLER);
    public static final Item FREEZER_CHEST = registerBlockItem(ModBlocks.FREEZER_CHEST);
    public static final Item FRIDGE = registerBlockItem(ModBlocks.FRIDGE);
    public static final Item OPEN_COOLER = registerBlockItem(ModBlocks.OPEN_COOLER);
    public static final Item CHECKOUT_COUNTER = registerBlockItem(ModBlocks.CHECKOUT_COUNTER);
    public static final Item BARCODE_SCANNER = registerBlockItem(ModBlocks.BARCODE_SCANNER);
    public static final Item SELF_CHECKOUT = registerBlockItem(ModBlocks.SELF_CHECKOUT);
    public static final Item PRICE_TAG = registerBlockItem(ModBlocks.PRICE_TAG);
    public static final Item AD_SCREEN = registerBlockItem(ModBlocks.AD_SCREEN);
    public static final Item SHOPPING_BASKET_BLOCK = registerBlockItem(ModBlocks.SHOPPING_BASKET);
    public static final Item STORAGE_CRATE = registerBlockItem(ModBlocks.STORAGE_CRATE);
    public static final Item PALLET = registerBlockItem(ModBlocks.PALLET);
    public static final Item VENDING_MACHINE = registerBlockItem(ModBlocks.VENDING_MACHINE);
    public static final Item TRASH_CAN = registerBlockItem(ModBlocks.TRASH_CAN);
    public static final Item BENCH_TABLE = registerBlockItem(ModBlocks.BENCH_TABLE);

    // Category 1: Fresh Produce (12 items)
    public static final Item BANANA = registerFood("banana", 4, 3.0f);
    public static final Item GRAPE = registerFood("grape", 2, 1.5f);
    public static final Item BLUEBERRY = registerFood("blueberry", 2, 1.0f);
    public static final Item STRAWBERRY = registerFood("strawberry", 3, 2.0f);
    public static final Item ORANGE = registerFood("orange", 4, 3.5f);
    public static final Item TOMATO = registerFood("tomato", 3, 2.5f);
    public static final Item CUCUMBER = registerFood("cucumber", 2, 2.0f);
    public static final Item LETTUCE = registerFood("lettuce", 2, 1.5f);
    public static final Item CORN = registerFood("corn", 3, 3.0f);
    public static final Item GREEN_PEPPER = registerFood("green_pepper", 3, 2.0f);
    public static final Item ONION = registerFood("onion", 2, 1.5f);
    public static final Item MUSHROOM_BOX = registerFood("mushroom_box", 5, 4.0f);

    // Category 2: Meat & Seafood (5 items)
    public static final Item BACON = registerFood("bacon", 4, 5.0f);
    public static final Item SAUSAGE = registerFood("sausage", 5, 5.5f);
    public static final Item SHRIMP = registerFood("shrimp", 3, 3.5f);
    public static final Item CRAB = registerFood("crab", 5, 6.0f);
    public static final Item FISH_FILLET = registerFood("fish_fillet", 4, 4.5f);

    // Category 3: Bakery (8 items)
    public static final Item TOAST = registerFood("toast", 5, 6.0f);
    public static final Item CROISSANT = registerFood("croissant", 5, 6.5f);
    public static final Item DONUT = registerFood("donut", 4, 5.0f);
    public static final Item PIZZA = registerFood("pizza", 8, 10.0f);
    public static final Item COOKIE_BOX = registerFood("cookie_box", 6, 4.0f);
    public static final Item NOODLES = registerFood("noodles", 5, 6.0f);
    public static final Item RICE = registerFood("rice", 6, 7.0f);
    public static final Item DUMPLING = registerFood("dumpling", 6, 7.5f);

    // Category 4: Snacks (9 items)
    public static final Item POTATO_CHIPS = registerFood("potato_chips", 3, 3.0f);
    public static final Item CHOCOLATE = registerFood("chocolate", 4, 4.5f);
    public static final Item CANDY = registerFood("candy", 2, 2.0f);
    public static final Item POPCORN = registerFood("popcorn", 3, 2.5f);
    public static final Item NUTS = registerFood("nuts", 3, 3.5f);
    public static final Item JELLY = registerFood("jelly", 2, 2.0f);
    public static final Item WAFER = registerFood("wafer", 3, 3.0f);
    public static final Item SPICY_STRIPS = registerFood("spicy_strips", 2, 2.5f);
    public static final Item CANNED_FRUIT = registerFood("canned_fruit", 4, 4.0f);

    // Category 5: Beverages (9 items)
    public static final Item BOTTLED_WATER = registerDrink("bottled_water", 1, 1.0f);
    public static final Item COLA = registerDrink("cola", 3, 2.5f);
    public static final Item JUICE = registerDrink("juice", 4, 3.0f);
    public static final Item MILK_TEA = registerDrink("milk_tea", 5, 4.0f);
    public static final Item COFFEE = registerDrink("coffee", 4, 3.5f);
    public static final Item BEER = registerDrink("beer", 3, 2.0f);
    public static final Item YOGURT = registerFood("yogurt", 4, 3.5f);
    public static final Item ICE_CREAM = registerFood("ice_cream", 4, 3.0f);
    public static final Item POPSICLE = registerFood("popsicle", 3, 2.0f);

    // Category 6: Groceries & Condiments (7 items)
    public static final Item FLOUR = register("flour", new Item(new Item.Settings()));
    public static final Item COOKING_OIL = register("cooking_oil", new Item(new Item.Settings()));
    public static final Item SALT = register("salt", new Item(new Item.Settings()));
    public static final Item SAUCE = register("sauce", new Item(new Item.Settings()));
    public static final Item SOY_SAUCE = register("soy_sauce", new Item(new Item.Settings()));
    public static final Item VINEGAR = register("vinegar", new Item(new Item.Settings()));
    public static final Item BAGGED_RICE = register("bagged_rice", new Item(new Item.Settings()));

    // Category 7: Daily Supplies (6 items)
    public static final Item TOWEL = register("towel", new Item(new Item.Settings()));
    public static final Item SOAP = register("soap", new Item(new Item.Settings()));
    public static final Item GARBAGE_BAG = register("garbage_bag", new Item(new Item.Settings()));
    public static final Item FLASHLIGHT = register("flashlight", new Item(new Item.Settings()));
    public static final Item STORAGE_BAG = register("storage_bag", new Item(new Item.Settings()));
    public static final Item CLEANER = register("cleaner", new Item(new Item.Settings()));

    // Category 8: Garden (3 items)
    public static final Item SHRUB_SEEDS = register("shrub_seeds", new Item(new Item.Settings()));
    public static final Item POTTED_SEEDLING = register("potted_seedling", new Item(new Item.Settings()));
    public static final Item FERTILIZER_BAG = register("fertilizer_bag", new Item(new Item.Settings()));

    // Category 12: Health (3 items)
    public static final Item FIRST_AID_KIT = register("first_aid_kit", new Item(new Item.Settings()));
    public static final Item BANDAGE = register("bandage", new Item(new Item.Settings()));
    public static final Item ENERGY_DRINK = registerDrink("energy_drink", 3, 3.0f);

    // Category 15: Pet (2 items)
    public static final Item PET_COLLAR = register("pet_collar", new Item(new Item.Settings()));
    public static final Item PET_FOOD = register("pet_food", new Item(new Item.Settings()));

    // Category 17: Seasonal (2 items)
    public static final Item GIFT_BOX = register("gift_box", new Item(new Item.Settings()));
    public static final Item RIBBON = register("ribbon", new Item(new Item.Settings()));

    private static Item register(String name, Item item) {
        return Registry.register(Registries.ITEM, BlockMarkMod.id(name), item);
    }

    private static Item registerFood(String name, int hunger, float saturation) {
        return Registry.register(Registries.ITEM, BlockMarkMod.id(name),
                new Item(new Item.Settings().food(new net.minecraft.item.FoodComponent.Builder()
                        .hunger(hunger).saturationModifier(saturation).build())));
    }

    private static Item registerDrink(String name, int hunger, float saturation) {
        return Registry.register(Registries.ITEM, BlockMarkMod.id(name),
                new Item(new Item.Settings().food(new net.minecraft.item.FoodComponent.Builder()
                        .hunger(hunger).saturationModifier(saturation).build())
                        .maxCount(16)));
    }

    public static void register() {
    }

    private static Item registerBlockItem(net.minecraft.block.Block block) {
        return Registry.register(Registries.ITEM, Registries.BLOCK.getId(block),
                new BlockItem(block, new Item.Settings()));
    }
}
