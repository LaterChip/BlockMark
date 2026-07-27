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
 * Screen handler for the Checkout Counter block.
 * <p>
 * Provides a 9x3 grid (27 slots) for the block inventory plus the standard
 * player inventory (3 rows hotbar + 3 rows main). Supports shift-click
 * quick transfer between inventory areas.
 * </p>
 *
 * <h3>Slot Layout</h3>
 * <ul>
 *   <li>Slots 0-26: Block inventory (9 columns x 3 rows, y=18)</li>
 *   <li>Slots 27-53: Player main inventory (9 columns x 3 rows, y=84)</li>
 *   <li>Slots 54-62: Player hotbar (9 columns, y=142)</li>
 * </ul>
 */
public class CheckoutCounterScreenHandler extends ScreenHandler {
    private final Inventory inventory;

    // --- Client-side constructor (fallback) ---

    /**
     * Client-side constructor used by the screen handler type.
     * Creates a handler with a temporary SimpleInventory.
     *
     * @param syncId          the synchronization ID
     * @param playerInventory the player's inventory
     */
    public CheckoutCounterScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(27));
    }

    // --- Server-side constructor ---

    /**
     * Server-side constructor with a real block entity inventory.
     *
     * @param syncId          the synchronization ID
     * @param playerInventory the player's inventory
     * @param inventory       the block entity's inventory (27 slots)
     */
    public CheckoutCounterScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
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
    public CheckoutCounterScreenHandler(ScreenHandlerType<?> type, int syncId,
                                         PlayerInventory playerInventory, Inventory inventory) {
        super(type, syncId);
        this.inventory = inventory;
        checkSize(inventory, 27);
        inventory.onOpen(playerInventory.player);
        buildSlots(playerInventory);
    }

    /**
     * Builds all slots for this screen handler layout.
     * <p>
     * Layout:
     * <ul>
     *   <li>Block inventory: 9x3 grid starting at (8, 18)</li>
     *   <li>Player inventory: 9x3 grid starting at (8, 84)</li>
     *   <li>Player hotbar: 9x1 row at (8, 142)</li>
     * </ul>
     *
     * @param playerInventory the player's inventory for slot construction
     */
    private void buildSlots(PlayerInventory playerInventory) {
        // Block inventory (27 slots, 9x3)
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(inventory, col + row * 9, 8 + col * 18, 18 + row * 18));
            }
        }

        // Player main inventory (27 slots, 9x3)
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }

        // Player hotbar (9 slots)
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
        }
    }

    /**
     * Handles shift-click quick transfer between the block inventory
     * and the player inventory.
     * <ul>
     *   <li>From block inventory (0-26): move to player inventory (27-62)</li>
     *   <li>From player inventory (27-62): move to block inventory (0-26)</li>
     * </ul>
     *
     * @param player the player performing the transfer
     * @param invSlot the slot index being shift-clicked
     * @return a copy of the transferred item stack, or EMPTY on failure
     */
    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                // Moving from block inventory to player inventory
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                // Moving from player inventory to block inventory
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

    /**
     * Checks whether the player can still use this screen handler.
     *
     * @param player the player to check
     * @return true if the player is within interaction range of the block
     */
    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    /**
     * Returns the block entity inventory backing this screen handler.
     *
     * @return the inventory instance
     */
    public Inventory getInventory() {
        return inventory;
    }
}
