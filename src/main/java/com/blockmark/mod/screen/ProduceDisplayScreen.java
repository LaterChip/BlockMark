package com.blockmark.mod.screen;

import com.blockmark.mod.BlockMarkMod;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

/**
 * Handled screen for the Produce Display block - an open display unit
 * for fresh fruits and vegetables with 18 slots. Supports keyboard
 * hotbar shortcuts (number keys 1-9) and shift-scroll quick-move.
 */
public class ProduceDisplayScreen extends HandledScreen<WallShelfScreenHandler> {
    private static final Identifier TEXTURE = BlockMarkMod.id("textures/gui/produce_display.png");
    private static final int INVENTORY_ROWS = 3;
    private static final int INVENTORY_COLS = 9;

    public ProduceDisplayScreen(WallShelfScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.backgroundHeight = 166;
        this.playerInventoryTitleY = this.backgroundHeight - 93;
    }

    @Override
    protected void init() {
        super.init();
        this.titleX = (this.backgroundWidth - this.textRenderer.getWidth(this.title)) / 2;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        context.drawText(this.textRenderer, this.title, this.titleX, this.titleY, 0x404040, false);
        context.drawText(this.textRenderer, this.playerInventoryTitle, this.playerInventoryTitleX,
                this.playerInventoryTitleY, 0x404040, false);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE || this.client.player.shouldCancelInteraction()) {
            this.close();
            return true;
        }
        if (this.client.options.inventoryKey.matchesKey(keyCode, scanCode)) {
            this.close();
            return true;
        }
        int hotbarSlot = mapKeyToHotbarSlot(keyCode);
        if (hotbarSlot >= 0 && hotbarSlot <= 8) {
            if (this.handler.getCursorStack().isEmpty()) {
                handleHotbarKeyPress(hotbarSlot);
            }
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    private static int mapKeyToHotbarSlot(int keyCode) {
        if (keyCode >= GLFW.GLFW_KEY_1 && keyCode <= GLFW.GLFW_KEY_9) {
            return keyCode - GLFW.GLFW_KEY_1;
        }
        if (keyCode == GLFW.GLFW_KEY_0) {
            return 8;
        }
        return -1;
    }

    private void handleHotbarKeyPress(int hotbarSlot) {
        if (this.focusedSlot != null && this.focusedSlot.hasStack()) {
            int containerSlots = ((WallShelfScreenHandler) this.handler).getInventory().size();
            int targetSlot = containerSlots + INVENTORY_ROWS * INVENTORY_COLS + hotbarSlot;
            if (targetSlot < this.handler.slots.size()) {
                this.onMouseClick(this.focusedSlot, this.focusedSlot.id, hotbarSlot,
                        net.minecraft.screen.slot.SlotActionType.SWAP);
            }
        }
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if (this.focusedSlot != null && this.focusedSlot.hasStack() && hasShiftDown()) {
            int direction = verticalAmount > 0 ? 1 : -1;
            int newSlotId = this.focusedSlot.id + direction;
            int containerSize = ((WallShelfScreenHandler) this.handler).getInventory().size();
            if (newSlotId >= 0 && newSlotId < containerSize) {
                this.onMouseClick(this.focusedSlot, this.focusedSlot.id, 0,
                        net.minecraft.screen.slot.SlotActionType.QUICK_MOVE);
            }
            return true;
        }
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    @Override
    protected void drawMouseoverTooltip(DrawContext context, int x, int y) {
        super.drawMouseoverTooltip(context, x, y);
    }
}
