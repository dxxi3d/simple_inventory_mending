package dxxi3d.simple_inventory_mending;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Util;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public final class MendingInventorySelector {

    public Optional<EnchantedItemInUse> selectRepairTarget(
        ServerPlayer player,
        DataComponentType<?> componentType,
        Predicate<ItemStack> filter
    ) {
        List<ItemStack> candidates = collectCandidates(player, componentType, filter);
        if (candidates.isEmpty()) {
            return Optional.empty();
        }

        return Util.getRandomSafe(candidates, player.getRandom())
            .map(stack -> new EnchantedItemInUse(stack, EquipmentSlot.MAINHAND, player));
    }

    private List<ItemStack> collectCandidates(
        ServerPlayer player,
        DataComponentType<?> componentType,
        Predicate<ItemStack> filter
    ) {
        List<ItemStack> candidates = new ArrayList<>();
        for (ItemStack stack : player.getInventory().getNonEquipmentItems()) {
            if (!stack.isEmpty() && filter.test(stack) && EnchantmentHelper.has(stack, componentType)) {
                candidates.add(stack);
            }
        }
        return candidates;
    }
}
