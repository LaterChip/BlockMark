package com.blockmark.mod.block.entity;

import com.blockmark.mod.registry.ModBlockEntities;
import com.blockmark.mod.registry.ModScreenHandlers;
import com.blockmark.mod.screen.WallShelfScreenHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

/**
 * Block entity shared by all shelf, cooler, display, and vending machine blocks.
 * <p>
 * Manages a 27-slot inventory and dynamically selects the correct screen handler
 * type based on the parent block's translation key. Supports standard NBT-based
 * inventory persistence, comparator output for redstone, and full chunk data
 * synchronization for networked clients.
 * </p>
 */
public class WallShelfBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, net.minecraft.inventory.Inventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(27, ItemStack.EMPTY);

    public WallShelfBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.WALL_SHELF_ENTITY, pos, state);
    }

    private ScreenHandlerType<?> getScreenHandlerType() {
        Block block = this.getCachedState().getBlock();
        String name = block.getTranslationKey();
        if (name.contains("wall_mount_shelf")) return ModScreenHandlers.WALL_MOUNT_SHELF_SCREEN_HANDLER;
        if (name.contains("produce_display")) return ModScreenHandlers.PRODUCE_DISPLAY_SCREEN_HANDLER;
        if (name.contains("glass_cabinet")) return ModScreenHandlers.GLASS_CABINET_SCREEN_HANDLER;
        if (name.contains("open_cooler")) return ModScreenHandlers.OPEN_COOLER_SCREEN_HANDLER;
        if (name.contains("storage_crate")) return ModScreenHandlers.STORAGE_CRATE_SCREEN_HANDLER;
        if (name.contains("vending_machine")) return ModScreenHandlers.VENDING_MACHINE_SCREEN_HANDLER;
        if (name.contains("double_sided_shelf")) return ModScreenHandlers.DOUBLE_SIDED_SHELF_SCREEN_HANDLER;
        if (name.contains("end_cap_shelf")) return ModScreenHandlers.END_CAP_SHELF_SCREEN_HANDLER;
        if (name.contains("heavy_duty_shelf")) return ModScreenHandlers.HEAVY_DUTY_SHELF_SCREEN_HANDLER;
        if (name.contains("beverage_cooler")) return ModScreenHandlers.BEVERAGE_COOLER_SCREEN_HANDLER;
        if (name.contains("freezer_chest")) return ModScreenHandlers.FREEZER_CHEST_SCREEN_HANDLER;
        if (name.contains("fridge")) return ModScreenHandlers.FRIDGE_SCREEN_HANDLER;
        return ModScreenHandlers.WALL_SHELF_SCREEN_HANDLER;
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new WallShelfScreenHandler(getScreenHandlerType(), syncId, playerInventory, this);
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("container.blockmark.wall_shelf");
    }

    @Override
    public boolean isEmpty() { return inventory.stream().allMatch(ItemStack::isEmpty); }

    @Override
    public ItemStack getStack(int slot) { return inventory.get(slot); }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        ItemStack result = Inventories.splitStack(inventory, slot, amount);
        if (!result.isEmpty()) markDirty();
        return result;
    }

    @Override
    public ItemStack removeStack(int slot) {
        ItemStack result = Inventories.removeStack(inventory, slot);
        if (!result.isEmpty()) markDirty();
        return result;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        inventory.set(slot, stack);
        if (stack.getCount() > getMaxCountPerStack()) stack.setCount(getMaxCountPerStack());
        markDirty();
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return pos.isWithinDistance(player.getPos(), 8.0);
    }

    @Override
    public void clear() { inventory.clear(); markDirty(); }

    @Override
    public int size() { return 27; }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
    }

    /**
     * Creates the update packet for block entity data synchronization
     * to players tracking this chunk.
     *
     * @return a BlockEntityUpdateS2CPacket with full NBT data
     */
    @Override
    public net.minecraft.network.packet.Packet<net.minecraft.network.listener.ClientPlayPacketListener> toUpdatePacket() {
        return net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket.create(this);
    }

    /**
     * Provides the initial chunk data NBT for new chunk loads.
     * Delegates to {@link #createNbt()} for the full serialized state.
     *
     * @return the full NBT compound for initial chunk sync
     */
    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }
}

