package com.blockmark.mod.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;

/**
 * Screen handler shared by all shelf, cooler, display, and vending machine blocks.
 * <p>
 * Provides a 9x3 grid (27 slots) for block inventory plus the standard
 * player inventory (3 rows main + 1 row hotbar). Supports shift-click
 * quick transfer between inventory areas.
 * </p>
 *
 * <h3>Slot Layout</h3>
 * <ul>
 *   <li>Slots 0-26:  Block inventory (9 columns x 3 rows, y=18)</li>
 *   <li>Slots 27-53: Player main inventory (9 columns x 3 rows, y=84)</li>
 *   <li>Slots 54-62: Player hotbar (9 columns, y=142)</li>
 * </ul>
 */
public class WallShelfScreenHandler extends ScreenHandler {
    private final Inventory inventory;

    /**
     * Client-side fallback constructor with a temporary SimpleInventory.
     *
     * @param syncId          the synchronization ID
     * @param playerInventory the player's inventory
     */
    public WallShelfScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(27));
    }

    /**
     * Server-side constructor without explicit type.
     *
     * @param syncId          the synchronization ID
     * @param playerInventory the player's inventory
     * @param inventory       the block entity's inventory (27 slots)
     */
    public WallShelfScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(null, syncId);
        this.inventory = inventory;
        checkSize(inventory, 27);
        inventory.onOpen(playerInventory.player);
        buildSlots(playerInventory);
    }

    /**
     * Full constructor with explicit screen handler type.
     *
     * @param type            the registered screen handler type
     * @param syncId          the synchronization ID
     * @param playerInventory the player's inventory
     * @param inventory       the block entity's inventory (27 slots)
     */
    public WallShelfScreenHandler(ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(type, syncId);
        this.inventory = inventory;
        checkSize(inventory, 27);
        inventory.onOpen(playerInventory.player);
        buildSlots(playerInventory);
    }

    /**
     * Builds all slot components for the screen handler layout.
     * Includes block inventory (3x9), player inventory (3x9), and hotbar (9).
     *
     * @param playerInventory the player's inventory reference
     */
    private void buildSlots(PlayerInventory playerInventory) {
        // Block inventory: 3 rows of 9
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(inventory, col + row * 9, 8 + col * 18, 18 + row * 18));
            }
        }

        // Player inventory: 3 rows of 9
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }

        // Hotbar: 1 row of 9
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
        }
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                    return ItemStack.EMPTY;
                }
            }
            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }
        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    /**
     * Returns the backing inventory instance for this screen handler.
     *
     * @return the block entity inventory
     */
    public Inventory getInventory() {
        return inventory;
    }
}
