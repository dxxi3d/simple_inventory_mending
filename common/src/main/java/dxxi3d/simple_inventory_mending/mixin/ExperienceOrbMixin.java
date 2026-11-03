package dxxi3d.simple_inventory_mending.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dxxi3d.simple_inventory_mending.MendingInventorySelector;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;
import java.util.function.Predicate;

@Mixin(ExperienceOrb.class)
public abstract class ExperienceOrbMixin {

    @Unique
    private static final MendingInventorySelector INVENTORY_SELECTOR = new MendingInventorySelector();

    @WrapOperation(
        method = "repairPlayerItems",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper;getRandomItemWith(Lnet/minecraft/core/component/DataComponentType;Lnet/minecraft/world/entity/LivingEntity;Ljava/util/function/Predicate;)Ljava/util/Optional;"
        ),
        require = 0
    )
    private Optional<EnchantedItemInUse> prioritizeEquippedThenInventoryMending(
        DataComponentType<?> componentType,
        LivingEntity entity,
        Predicate<ItemStack> filter,
        Operation<Optional<EnchantedItemInUse>> original
    ) {
        Optional<EnchantedItemInUse> equippedTarget = original.call(componentType, entity, filter);
        if (equippedTarget.isPresent()) {
            return equippedTarget;
        }

        if (!(entity instanceof ServerPlayer player)) {
            return Optional.empty();
        }

        return INVENTORY_SELECTOR.selectRepairTarget(player, componentType, filter);
    }
}
