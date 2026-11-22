package com.xulai.woolboots.recipe;

import com.mojang.serialization.MapCodec;
import com.xulai.woolboots.WoolBootsComponents;
import com.xulai.woolboots.WoolBootsRecipes;
import java.util.stream.Stream;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.Equippable;
import net.neoforged.neoforge.common.crafting.ICustomIngredient;
import net.neoforged.neoforge.common.crafting.IngredientType;

public final class UnsoledBootsIngredient implements ICustomIngredient {

    public static final UnsoledBootsIngredient INSTANCE = new UnsoledBootsIngredient();

    public static final MapCodec<UnsoledBootsIngredient> CODEC = MapCodec.unit(INSTANCE);

    private UnsoledBootsIngredient() {
    }

    public static boolean isBoots(ItemStack stack) {
        Equippable equippable = stack.get(DataComponents.EQUIPPABLE);
        return equippable != null && equippable.slot() == EquipmentSlot.FEET;
    }

    public static boolean isUnsoledBoots(ItemStack stack) {
        return isBoots(stack) && !WoolBootsComponents.hasWoolSole(stack);
    }

    @Override
    public boolean test(ItemStack stack) {
        return isUnsoledBoots(stack);
    }

    @Override
    public Stream<Holder<Item>> items() {
        return BuiltInRegistries.ITEM.listElements()
                .filter(holder -> isBoots(new ItemStack(holder.value())))
                .map(holder -> (Holder<Item>) holder);
    }

    @Override
    public boolean isSimple() {
        return false;
    }

    @Override
    public IngredientType<?> getType() {
        return WoolBootsRecipes.UNSOLED_BOOTS.get();
    }
}