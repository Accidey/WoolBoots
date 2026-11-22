package com.xulai.woolboots.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.xulai.woolboots.WoolBootsComponents;
import java.util.List;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.NormalCraftingRecipe;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.ShapedCraftingRecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.Level;

public class WoolSoleRecipe extends NormalCraftingRecipe {

    public static final MapCodec<WoolSoleRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                    Recipe.CommonInfo.MAP_CODEC.forGetter(recipe -> recipe.commonInfo),
                    CraftingRecipe.CraftingBookInfo.MAP_CODEC.forGetter(recipe -> recipe.bookInfo),
                    ShapedRecipePattern.MAP_CODEC.forGetter(recipe -> recipe.pattern),
                    ItemStackTemplate.CODEC.fieldOf("result").forGetter(recipe -> recipe.displayResult))
            .apply(instance, WoolSoleRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, WoolSoleRecipe> STREAM_CODEC = StreamCodec.composite(
            Recipe.CommonInfo.STREAM_CODEC,
            recipe -> recipe.commonInfo,
            CraftingRecipe.CraftingBookInfo.STREAM_CODEC,
            recipe -> recipe.bookInfo,
            ShapedRecipePattern.STREAM_CODEC,
            recipe -> recipe.pattern,
            ItemStackTemplate.STREAM_CODEC,
            recipe -> recipe.displayResult,
            WoolSoleRecipe::new);

    public static final RecipeSerializer<WoolSoleRecipe> SERIALIZER = new RecipeSerializer<>(CODEC, STREAM_CODEC);

    private final ShapedRecipePattern pattern;
    private final ItemStackTemplate displayResult;

    public WoolSoleRecipe(Recipe.CommonInfo commonInfo, CraftingRecipe.CraftingBookInfo bookInfo, ShapedRecipePattern pattern, ItemStackTemplate displayResult) {
        super(commonInfo, bookInfo);
        this.pattern = pattern;
        this.displayResult = displayResult;
    }

    @Override
    public RecipeSerializer<WoolSoleRecipe> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        return this.pattern.matches(input);
    }

    @Override
    public ItemStack assemble(CraftingInput input) {
        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (UnsoledBootsIngredient.isUnsoledBoots(stack)) {
                ItemStack result = stack.copyWithCount(1);
                WoolBootsComponents.setWoolSole(result);
                return result;
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    protected PlacementInfo createPlacementInfo() {
        return PlacementInfo.createFromOptionals(this.pattern.ingredients());
    }

    @Override
    public List<RecipeDisplay> display() {
        return List.of(
                new ShapedCraftingRecipeDisplay(
                        this.pattern.width(),
                        this.pattern.height(),
                        this.pattern.ingredients().stream()
                                .map(ingredient -> ingredient.map(Ingredient::display).orElse(SlotDisplay.Empty.INSTANCE))
                                .toList(),
                        new SlotDisplay.ItemStackSlotDisplay(this.displayResult),
                        new SlotDisplay.ItemSlotDisplay(Items.CRAFTING_TABLE)));
    }
}