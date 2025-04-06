package ultimatedimension.world.blocks.multic;

import arc.struct.Seq;
import mindustry.type.Item;
import mindustry.type.ItemStack;
import mindustry.type.Liquid;
import mindustry.type.LiquidStack;

public class Recipe {
    public float craftTime;
    public String name;
    public Seq<ItemStack> iInputs = new Seq<>();
    public Seq<LiquidStack> lInputs = new Seq<>();
    public Seq<ItemStack> iOutputs = new Seq<>();
    public Seq<LiquidStack> lOutputs = new Seq<>();
    public transient Seq<Item> itemsUnique = new Seq<>();
    public transient Seq<Liquid> liquidsUnique = new Seq<>();


    public float powerConsume;
    public float powerProduce;
    public float heatProduce;
    public float heatConsume;

    public Recipe(float craftTime) {
        this.craftTime = craftTime;
    }

    // 在初始化或加载配方时填充唯一集合
    public void cacheUnique() {
        itemsUnique.clear();
        for (ItemStack stack : iInputs) {
            itemsUnique.add(stack.item);
        }
        liquidsUnique.clear();
        for (LiquidStack stack : lInputs) {
            liquidsUnique.add(stack.liquid);
        }
    }

    public boolean isValid() {
        // 至少有一个输入和一个输出
//        return (!iInputs.isEmpty() || !lInputs.isEmpty())
//                && (!iOutputs.isEmpty() || !lOutputs.isEmpty());
        return (hasInputs() || hasPowerInput()) &&
                (hasOutputs() || hasPowerOutput());
    }

    private boolean hasInputs() {
        return !iInputs.isEmpty() || !lInputs.isEmpty();
    }

    private boolean hasOutputs() {
        return !iOutputs.isEmpty() || !lOutputs.isEmpty();
    }

    private boolean hasPowerInput() {
        return powerConsume > 0;
    }

    private boolean hasPowerOutput() {
        return powerProduce > 0;
    }

    //
    public boolean isInputItem() {
        return iInputs.size > 0;
    }

    public boolean isOutputItem() {
        return iOutputs.size > 0;
    }

    public boolean isInputLiquid() {
        return lInputs.size > 0;
    }

    public boolean isOutputLiquid() {
        return lOutputs.size > 0;
    }

    public boolean isInputPower() {
        return powerConsume > 0;
    }

    public boolean isOutputPower() {
        return powerProduce > 0;
    }

    public boolean isInputHeat() {
        return heatProduce > 0;
    }

    public boolean isOutputHeat() {
        return heatConsume > 0;
    }

    public boolean hasItems() {
        return isInputItem() || isOutputItem();
    }

    public boolean hasLiquids() {
        return isInputLiquid() || isOutputLiquid();
    }

    public boolean hasPower() {
        return isInputPower() || isOutputPower();
    }

    public boolean hasHeat() {
        return isInputHeat() || isOutputHeat();
    }

    public int maxItemAmount() {
        int max = 0;
        for (ItemStack item : items) {
            max = Math.max(item.amount, max);
        }
        return max;
    }

    public float maxFluidAmount() {
        float max = 0;
        for (LiquidStack fluid : fluids) {
            max = Math.max(fluid.amount, max);
        }
        return max;
    }
    public int maxItemAmount() {
        return Math.max(iInputs.maxItemAmount(), iOutput.maxItemAmount());
    }

    public float maxFluidAmount() {
        return Math.max(input.maxFluidAmount(), output.maxFluidAmount());
    }

    public float maxPower() {
        return Math.max(input.power, output.power);
    }

    public float maxHeat() {
        return Math.max(input.heat, output.heat);
    }
}