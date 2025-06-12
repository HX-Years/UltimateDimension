package ultimatedimension.world.blocks.multic;

import arc.struct.Seq;
import mindustry.type.Item;
import mindustry.type.ItemStack;
import mindustry.type.Liquid;
import mindustry.type.LiquidStack;

public class RecipeBuilder {
    public final Recipe recipe;

    public RecipeBuilder(float craftTime, Seq<Recipe> recipes) {
        this.recipe = new Recipe(craftTime);
        //MultiCrafter.this.recipes.add(recipe); // 自动注册到工厂
        recipes.add(recipe);
    }

    //物品
    public RecipeBuilder input(Item item, int amount) {
        recipe.iInputs.add(new ItemStack(item, amount));
        return this;
    }

    public RecipeBuilder output(Item item, int amount) {
        recipe.iOutputs.add(new ItemStack(item, amount));
        return this;
    }

    //液体
    public RecipeBuilder input(Liquid liquid, float amount) {
        recipe.lInputs.add(new LiquidStack(liquid, amount));
        return this;
    }

    public RecipeBuilder output(Liquid liquid, float amount) {
        recipe.lOutputs.add(new LiquidStack(liquid, amount));
        return this;
    }

    //能源一类
    public RecipeBuilder consumePower(float power) {
        if (power < 0) throw new IllegalArgumentException("Power consumption cannot be negative");
        recipe.powerConsume = power;
        return this;
    }

    public RecipeBuilder producePower(float power) {
        if (power < 0) throw new IllegalArgumentException("Power producption cannot be negative");
        recipe.powerProduce = power;
        return this;
    }

    public RecipeBuilder consumeHeat(float heat) {
        recipe.heatConsume = heat;
        return this;
    }

    public RecipeBuilder produceHeat(float heat) {
        recipe.heatProduce = heat;
        return this;
    }
}
