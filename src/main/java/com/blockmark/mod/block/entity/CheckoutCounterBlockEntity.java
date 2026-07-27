package com.blockmark.mod.block.entity;

import com.blockmark.mod.registry.ModBlockEntities;
import com.blockmark.mod.registry.ModScreenHandlers;
import com.blockmark.mod.screen.CheckoutCounterScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

/**
 * Block entity for the Checkout Counter block.
 * <p>
 * Manages a 27-slot inventory with standard NBT persistence.
 * Implements both NamedScreenHandlerFactory and Inventory so the
 * block can be opened via right-click and retain items across
 * chunk loads. Also provides comparator output for redstone
 * integration.
 * </p>
 */
public class CheckoutCounterBlockEntity extends BlockEntity
        implements NamedScreenHandlerFactory, net.minecraft.inventory.Inventory {

    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(27, ItemStack.EMPTY);

    /**
     * Constructs a new CheckoutCounterBlockEntity.
     *
     * @param pos   the block position in the world
     * @param state the current block state
     */
    public CheckoutCounterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CHECKOUT_COUNTER_ENTITY, pos, state);
    }

    /**
     * Creates the screen handler for this block entity.
     *
     * @param syncId          the synchronization ID
     * @param playerInventory the player's inventory
     * @param player          the player opening the screen
     * @return a new CheckoutCounterScreenHandler
     */
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new CheckoutCounterScreenHandler(
                ModScreenHandlers.CHECKOUT_COUNTER_SCREEN_HANDLER,
                syncId, playerInventory, this);
    }

    /**
     * Returns the display name shown in the screen title.
     *
     * @return the translatable display name for the checkout counter
     */
    @Override
    public Text getDisplayName() {
        return Text.translatable("container.blockmark.checkout_counter");
    }

    @Override
    public boolean isEmpty() {
        return inventory.stream().allMatch(ItemStack::isEmpty);
    }

    @Override
    public ItemStack getStack(int slot) {
        return inventory.get(slot);
    }

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
        if (stack.getCount() > getMaxCountPerStack()) {
            stack.setCount(getMaxCountPerStack());
        }
        markDirty();
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return pos.isWithinDistance(player.getPos(), 8.0);
    }

    @Override
    public void clear() {
        inventory.clear();
        markDirty();
    }

    @Override
    public int size() {
        return 27;
    }

    /**
     * Reads block entity data from NBT, including inventory contents.
     *
     * @param nbt the NBT compound to read from
     */
    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
    }

    /**
     * Writes block entity data to NBT, including inventory contents.
     *
     * @param nbt the NBT compound to write to
     */
    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
    }

    /**
     * Creates the update packet for synchronizing block entity data
     * to players tracking this chunk.
     *
     * @return a block entity update packet with full NBT data
     */
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    /**
     * Creates the initial chunk data NBT for chunk synchronization.
     * Delegates to createNbt() which includes inventory contents.
     *
     * @return the full NBT compound for initial chunk sync
     */
    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }
}
