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


    public float powerConsume = 0;
    public float powerProduce = 0;
    public float heatProduce = 0;
    public float heatConsume = 0;


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
        //TODO will to join heat
        return (hasInputs() || hasEnergyInput()) &&
                (hasOutputs() || hasEnergyOutput());
    }

    private boolean hasInputs() {
        return !iInputs.isEmpty() || !lInputs.isEmpty();
    }

    private boolean hasOutputs() {
        return !iOutputs.isEmpty() || !lOutputs.isEmpty();
    }

    private boolean hasEnergyInput() {
        return (powerConsume > 0) || (heatConsume > 0);
    }

    private boolean hasEnergyOutput() {
        return (powerProduce > 0) || (heatProduce > 0);
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
        int max;
        int max1 = 0;
        int max2 = 0;
        for (ItemStack item : iInputs) {
            max1 = Math.max(item.amount, max1);
        }
        for (ItemStack item : iOutputs) {
            max2 = Math.max(item.amount, max2);
        }
        max = Math.max(max1, max2);
        return max;
    }

    public float maxLiquidAmount() {
        float max;
        float max1 = 0;
        float max2 = 0;
        for (LiquidStack liquid : lInputs) {
            max1 = Math.max(liquid.amount, max1);
        }
        for (LiquidStack liquid : lOutputs) {
            max2 = Math.max(liquid.amount, max2);
        }
        max = Math.max(max1, max2);
        return max;
    }
//    public int maxItemAmount() {
//        return Math.max(iInputs.maxItemAmount(), iOutput.maxItemAmount());
//    }
//
//    public float maxLiquidAmount() {
//        return Math.max(lInput.maxLiquidAmount(), lOutput.maxLiquidAmount());
//    }

    public float maxPower() {
        return Math.max(powerConsume, powerProduce);
    }

    public float maxHeat() {
        return Math.max(heatConsume, heatProduce);
    }
}