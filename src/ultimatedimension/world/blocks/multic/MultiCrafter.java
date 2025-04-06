package ultimatedimension.world.blocks.multic;

import arc.Events;
import arc.math.Mathf;
import arc.math.geom.Point2;
import arc.scene.ui.Button;
import arc.scene.ui.Image;
import arc.scene.ui.Label;
import arc.scene.ui.layout.Table;
import arc.struct.EnumSet;
import arc.struct.IntSet;
import arc.struct.Seq;
import arc.util.Nullable;
import arc.util.Time;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.game.EventType;
import mindustry.gen.Building;
import mindustry.gen.Icon;
import mindustry.gen.Sounds;
import mindustry.gen.Tex;
import mindustry.graphics.Pal;
import mindustry.type.Item;
import mindustry.type.ItemStack;
import mindustry.type.Liquid;
import mindustry.type.LiquidStack;
import mindustry.ui.ItemImage;
import mindustry.ui.dialogs.BaseDialog;
import mindustry.world.blocks.heat.HeatBlock;
import mindustry.world.consumers.ConsumePowerDynamic;
import mindustry.world.meta.BlockFlag;
import mindustry.world.meta.Stat;
import ultimatedimension.ui.FluidImage;
import ultimatedimension.world.blocks.UDBlock;

public class MultiCrafter extends UDBlock {
    public Seq<Recipe> recipes = new Seq<>();
    public int lastRecipeIndex = 0;
    //public int recipeIndex = 0;
    public float powerCapacity = 0f;
    public float warmupRate = 0.15f;
    public boolean dumpExtraFluid = true;
    public boolean hasHeat = false;
    public float warmupSpeed = 0.019f;

    protected boolean isInputItem = false;
    protected boolean isOutputItem = false;
    protected boolean isInputLiquid = false;
    protected boolean isOutputLiquid = false;
    protected boolean isInputPower = false;
    protected boolean isOutputPower = false;
    protected boolean isInputHeat = false;
    protected boolean isOutputHeat = false;
    public boolean ignoreLiquidFullness = false;

    // 核心构造逻辑
    public MultiCrafter(String name) {
        super(name);
        update = true;
        configurable = true;
        saveConfig = true;
        solid = true;
        hasItems = true;
        hasPower = true;
        hasLiquids = false;
        consumesPower = true;
        outputsPower = false;
        ambientSound = Sounds.machine;
        sync = true;
        flags = EnumSet.of(BlockFlag.factory);
    }

    public void init() {
        //recipeIndex = Mathf.clamp(recipeIndex, 0, recipes.size - 1);
        Events.on(EventType.WorldLoadEvent.class, e -> {
            lastRecipeIndex = 0;
        });
        decorateRecipes();
        setupBlockByRecipes();
        setupConsumers();
        super.init();
    }





    // 配方建造工具（内部类）
    public class RecipeBuilder {
        private final Recipe recipe;

        public RecipeBuilder(float craftTime) {
            this.recipe = new Recipe(craftTime);
            MultiCrafter.this.recipes.add(recipe); // 自动注册到工厂
        }

        public RecipeBuilder name(String name) {
            recipe.name = name;
            return this;
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
            recipe.powerConsume = power;
            return this;
        }

        public RecipeBuilder producePower(float power) {
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

    //定义新配方
    public RecipeBuilder recipe(float craftTime) {
        return new RecipeBuilder(craftTime);
    }


    @Override
    public void setStats() {
        super.setStats();
        //stats.remove(Stat.powerUse);
        //stats.remove(Stat.);
        stats.add(Stat.output, table -> {
            table.row();
            //table.table(s -> {
                //s.row();
//                s.background(Tex.whiteui);
//                s.setColor(Pal.darkestGray);
                //s.add("可用配方:").left().padBottom(10);

                for (Recipe recipe : recipes.select(Recipe::isValid)) {
                    Table t = new Table();
                    t.background(Tex.whiteui);
                    t.setColor(Pal.darkerGray);
                    //s.row();

                    t.table(r -> {
                        //r.left(); // 左对齐
                        // 显示输入
                        for (ItemStack input : recipe.iInputs) {
                            r.add(new ItemImage(input.item.uiIcon, input.amount)).left().padLeft(4);
                        }
                        for (LiquidStack liquidInput : recipe.lInputs) {
                            r.add(new FluidImage(liquidInput.liquid.uiIcon, liquidInput.amount)).left().padLeft(4);
                        }
                        // 箭头分隔符
                        r.add("->").color(Pal.accent).center().pad(4);
                        // 显示输出
                        for (ItemStack output : recipe.iOutputs) {
                            r.add(new ItemImage(output.item.uiIcon, output.amount)).right().padRight(4);
                        }
                        for (LiquidStack liquidOutput : recipe.lOutputs) {
                            r.add(new FluidImage(liquidOutput.liquid.uiIcon, liquidOutput.amount)).right().padRight(4);
                        }
                        r.row();
                        r.add("电力消耗: "+recipe.powerConsume * 60);
                        r.row();
                        r.add("生产时间: "+String.format("%.2f",recipe.craftTime / 60) + "/s");
                    }).left().padBottom(8);
                    table.add(t).pad(10f).grow();
                    table.row();
                }
                table.row();
                table.defaults().grow();
            //});
        });
    }



    public class MultiCrafterBuild extends UDBuilding {
        public @Nullable Recipe currentRecipe;
        public float progress;
        public float heat;
        public float warmup;
        public float craftingTime;
        public float totalProgress;
        public float[] sideHeat = new float[4];
        public int recipeIndex = 0;


        public Recipe getRecipe() {
            //recipeIndex = Mathf.clamp(recipeIndex, 0, recipes.size - 1);
            //return recipes.get(recipes.indexOf(currentRecipe));
            if (currentRecipe == null || !recipes.contains(currentRecipe)) {
                // 默认选择第一个有效配方，避免崩溃
                currentRecipe = recipes.find(Recipe::isValid);
                if (currentRecipe == null && !recipes.isEmpty()) {
                    currentRecipe = recipes.first();
                }
            }
            return currentRecipe;
        }

        @Override
        public boolean acceptItem(Building source, Item item) {
            return hasItems &&
                    getRecipe().itemsUnique.contains(item) &&
                    items.get(item) < getMaximumAccepted(item);
        }

        @Override
        public boolean acceptLiquid(Building source, Liquid liquid) {
            return hasLiquids &&
                    getRecipe().liquidsUnique.contains(liquid) &&
                    liquids.get(liquid) < liquidCapacity;
        }

        @Override
        public void created() {
            super.created();
            MultiCrafter crafter = (MultiCrafter) block;
            if (crafter != null && crafter.recipes.size > 0) {
                if (lastRecipeIndex >= 0 && lastRecipeIndex < crafter.recipes.size) {
                    currentRecipe = crafter.recipes.get(lastRecipeIndex);
                } else {
                    currentRecipe = crafter.recipes.find(Recipe::isValid);
                }
            }
        }

        public float getPower() {
            if (power == null)
                return 0f;
            return power.status * powerCapacity;
        }

        public void setPower(float sPower) {
            if (power == null)
                return;
            power.status = Mathf.clamp(sPower / powerCapacity);
        }

        @Override
        public float edelta() {
            Recipe rec = getRecipe();
            if (rec.powerConsume > 0f)
                return this.efficiency *
                        Mathf.clamp(getPower() / rec.powerConsume) *
                        this.delta();
            else
                return this.efficiency * this.delta();
        }

        //TODO wait to do heat
        @Override
        public void updateTile() {
            Recipe rec = getRecipe();
            float craftTimeNeed = rec.craftTime;
             //As HeatConsumer
            if (rec.isInputHeat())
                heat = calculateHeat(sideHeat);
            if (rec.isOutputHeat()) {
                float heatProduce = rec.heatProduce;
                heat = Mathf.approachDelta(heat, heatProduce * efficiency, warmupRate * edelta());
            }
            // cool down
            //TODO
            if (efficiency > 0 && (!hasPower || getPower() >= rec.powerConsume)) {
                // if <= 0, instantly produced
                if (craftTimeNeed > 0f)
                    craftingTime += edelta();
                warmup = Mathf.approachDelta(warmup, warmupTarget(), warmupSpeed);
                if (hasPower) {
                    float powerChange = (rec.powerProduce - rec.powerConsume) * delta();
                    if (!Mathf.zero(powerChange))
                        setPower((getPower() + powerChange));
                }

                // continuously output fluid based on efficiency
                if (rec.isOutputLiquid()) {
                    float increment = getProgressIncrease(1f);
                    for (LiquidStack output : rec.lOutputs) {
                        Liquid fluid = output.liquid;
                        handleLiquid(this, fluid, Math.min(output.amount * increment, liquidCapacity - liquids.get(fluid)));
                    }
                }
                // particle fx
//                if (wasVisible && Mathf.chanceDelta(updateEffectChance))
//                    updateEffect.at(x + Mathf.range(size * 4f), y + Mathf.range(size * 4));
            } else
                warmup = Mathf.approachDelta(warmup, 0f, warmupSpeed);
            totalProgress += warmup * Time.delta;

//            if (moveInPayload()) {
//                yeetPayload(payload);
//                payload = null;
//            }

            if (craftTimeNeed <= 0f) {
                if (efficiency > 0f)
                    craft();
            } else if (craftingTime >= craftTimeNeed)
                craft();

            //updateBars();
            dumpOutputs();
        }

        @Override
        public float calculateHeat(float[] sideHeat) {
            Point2[] edges = this.block.getEdges();
            int length = edges.length;
            for (int i = 0; i < length; ++i) {
                Point2 edge = edges[i];
                Building build = this.nearby(edge.x, edge.y);
                if (build != null && build.team == this.team && build instanceof HeatBlock) {
                    HeatBlock heater = (HeatBlock) build;
                    // Only calculate heat if the block is a heater or a multicrafter heat output
                    if (heater instanceof MultiCrafterBuild) {
                        MultiCrafterBuild multi = (MultiCrafterBuild) heater;
                        if (getRecipe().isOutputHeat())
                            return this.calculateHeat(sideHeat, (IntSet) null);
                    } else
                        return this.calculateHeat(sideHeat, (IntSet) null);
                }
            }

            return 0.0f;
        }

        public float warmupTarget() {
            Recipe rec = getRecipe();
            // When As HeatConsumer
            if (isInputHeat && rec.isInputHeat())
                return Mathf.clamp(heat / rec.heatConsume);
            else
                return 1f;
        }

        @Override
        public boolean shouldConsume() {
            Recipe rec = getRecipe();
            if (hasItems)
                for (ItemStack output : rec.iOutputs)
                    if (items.get(output.item) + output.amount > itemCapacity)
                        return false;

            if (hasLiquids)
                if (rec.isOutputLiquid() && !ignoreLiquidFullness) {
                    boolean allFull = true;
                    for (LiquidStack output : rec.lOutputs)
                        if (liquids.get(output.liquid) >= liquidCapacity - 0.001f) {
                            if (!dumpExtraFluid)
                                return false;
                        } else
                            allFull = false; // if there's still space left, it's not full for all fluids

                    // if there is no space left for any fluid, it can't reproduce
                    if (allFull)
                        return false;
                }
//            if (hasPayloads)
//                for (PayloadStack output : cur.output.payloads)
//                    if (payloads.get(output.item) + output.amount > payloadCapacity)
//                        return false;
            return enabled;
        }

        public void dumpOutputs() {
            Recipe rec = getRecipe();
            if (timer(timerDump, dumpTime / timeScale)) {
                if (rec.isOutputItem())
                    for (ItemStack output : rec.iOutputs)
                        dump(output.item);

                // TODO fix infinite output
//                if (cur.isOutputPayload()) {
//                    for (PayloadStack output : cur.output.payloads) {
//                        Payload payloadOutput = null;
//                        if (output.item instanceof Block)
//                            payloadOutput = new BuildPayload((Block) output.item, this.team);
//                        else if (output.item instanceof UnitType)
//                            payloadOutput = new UnitPayload(((UnitType) output.item).create(this.team));
//
//                        if (payloadOutput != null)
//                            dumpPayload(payloadOutput);
//                    }
//                }
            }

            if (rec.isOutputLiquid()) {
                //Seq<LiquidStack> liquids = rec.lOutputs;
//                for (int i = 0; i < liquids.size; i++) {
//                    int dir = fluidOutputDirections.length > i ? fluidOutputDirections[i] : -1;
//                    dumpLiquid(liquids[i].liquid, 2f, dir);
//                }
                for (LiquidStack liquids : rec.lOutputs) {
                    dumpLiquid(liquids.liquid, 2f);
                }
            }
        }

        public void craft() {
            consume();
            Recipe rec = getRecipe();
            if (rec.isOutputItem())
                for (ItemStack output : rec.iOutputs)
                    for (int i = 0; i < output.amount; i++)
                        offload(output.item);

            if (rec.craftTime > 0f)
                craftingTime %= rec.craftTime;
            else
                craftingTime = 0f;
        }

        @Override
        public Object config() {
            return recipes.indexOf(currentRecipe);
        }


        @Override
        public void write(Writes write) {
            super.write(write);
            write.i(recipes.indexOf(currentRecipe));
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            int index = read.i();
            if (index >= 0 && index < recipes.size) {
                currentRecipe = recipes.get(index);
            } else {
                currentRecipe = null;
            }
        }

        @Override
        public float warmup() {
            return warmup;
        }

        @Override
        public float progress() {
            Recipe rec = getRecipe();
            return Mathf.clamp(rec.craftTime > 0f ? craftingTime / rec.craftTime : 1f);
        }

        @Override
        public float totalProgress() {
            return totalProgress;
        }


        @Override
        public void buildConfiguration(Table table) {
            Table main = new Table(Tex.pane);
            main.margin(12);

            // 配方选择下拉菜单
            Table selector = new Table();
            selector.add("当前配方: ").left();
            Button button = new Button();
            button.add(new Label(() -> currentRecipe != null ? "选择" : "无配方")).pad(8);
            button.clicked(() -> {
                // 显示配方列表
                showRecipeSelector();
            });
            selector.add(button).growX().row();
            main.add(selector).growX().row();

            main.row();
            main.table(this::rebuildIOTable).update(t -> {
                t.clear(); // 清空旧内容
                rebuildIOTable(t); // 重新构建
            }).grow();
            table.add(main);



            // 其他UI元素不变...
        }

        private void showRecipeSelector() {
            BaseDialog dialog = new BaseDialog("选择配方");
            dialog.cont.pane(p -> {
                MultiCrafter crafter = (MultiCrafter) block;
                for (Recipe recipe : crafter.recipes.select(Recipe::isValid)) {
                    p.button(b -> {
                        b.table(desc -> {
                            //desc.left();
                            // 输入
                            Table inputTable = new Table().left();
                            recipe.iInputs.each(stack -> inputTable.add(new ItemImage(stack.item.uiIcon, stack.amount)).padLeft(4));
                            recipe.lInputs.each(stack -> inputTable.add(new FluidImage(stack.liquid.uiIcon, stack.amount)).padLeft(4));
                            inputTable.add().growX();

                            Table arrow = new Table().center();
                            arrow.add("->").color(Pal.accent);
                            // 输出
                            Table outputTable = new Table().right();
                            recipe.iOutputs.each(stack -> outputTable.add(new ItemImage(stack.item.uiIcon, stack.amount)).padRight(4));
                            recipe.lOutputs.each(stack -> outputTable.add(new FluidImage(stack.liquid.uiIcon, stack.amount)).padRight(4));

                            desc.add(inputTable).left();
                            desc.add(arrow).center();
                            desc.add(outputTable).right().row();

                            //desc.add("[lightgray]|[]").padLeft(8); // 分隔线
                            if(recipe.isInputPower() || recipe.isOutputPower()) {
                                if (recipe.isInputPower()) {
                                    desc.row();
                                    desc.add("[gray]消耗" + "[] " + (recipe.powerConsume * 60) + " 电力/s").left().padLeft(4);
                                }
                                if (recipe.isOutputPower()) {
                                    desc.row();
                                    desc.add("[gray]生产" + "[] " + (recipe.powerProduce * 60) + " 电力/s").left().padLeft(4);
                                }
                            }

                            //desc.row();
                            desc.add("[red]时间[] " + recipe.craftTime + "秒").left().padLeft(4);
                        }).left();
                    }, () -> {
                        currentRecipe = recipe;
                        lastRecipeIndex = crafter.recipes.indexOf(recipe);
                        configure(null);
                        dialog.hide();
                    }).growX().pad(4).row();
                }
            });
            dialog.addCloseButton();
            dialog.show();
        }

        // 动态构建输入输出显示
        //展示界面
        private void rebuildIOTable(Table table) {
            table.clear();

            // 输入区
            Table inputs = new Table();
            inputs.left();
            if(currentRecipe != null){
                // 物品输入
                for(ItemStack stack : currentRecipe.iInputs){
                    inputs.add(new ItemImage(stack.item.uiIcon, stack.amount)).size(48).pad(2);
                }
                // 液体输入
                for(LiquidStack stack : currentRecipe.lInputs){
                    inputs.add(new FluidImage(stack.liquid.uiIcon, stack.amount)).size(48).pad(2);
                }
                inputs.add().grow(); // 占位
            }

            // 箭头分隔符
            Table arrow = new Table();
            arrow.add(new Image(Icon.right)).size(32).center();

            // 输出区
            Table outputs = new Table();
            outputs.right();
            if(currentRecipe != null){
                // 物品输出
                for(ItemStack stack : currentRecipe.iOutputs){
                    outputs.add(new ItemImage(stack.item.uiIcon, stack.amount)).size(48).pad(2);
                }
                // 液体输出
                for(LiquidStack stack : currentRecipe.lOutputs){
                    outputs.add(new FluidImage(stack.liquid.uiIcon, stack.amount)).size(48).pad(2);
                }
                outputs.add().grow();
            }

            // 组合布局
            table.add(inputs).grow().top().left();
            table.add(arrow).growY().center();
            table.add(outputs).grow().top().right();

            table.row(); // 新行
            table.table(params -> {
                params.defaults().padTop(8).left();
                // 电力消耗
                //params.label(() ->
//                    if(currentRecipe.isInputPower() || currentRecipe.isOutputPower()) {
//                        if (currentRecipe.isInputPower()) {
//                            params.label(() -> "[red]消耗" + "[] " + (currentRecipe.powerConsume * 60) + " 电力/s").padRight(12);
//                            params.row();
//                        }
//                        if (currentRecipe.isOutputPower()) {
//                            params.label(() -> "[red]生产" + "[] " + (currentRecipe.powerProduce * 60) + " 电力/s").padRight(12);
//                            params.row();
//                        }
//                    }
                if(currentRecipe.isInputPower() || currentRecipe.isOutputPower()) {
                    if (currentRecipe.isInputPower()) {
                        params.label(() -> "[red]消耗" + "[] " + (currentRecipe.powerConsume * 60) + " 电力/s").left().row();
                        //params.row();
                    }
                    if (currentRecipe.isOutputPower()) {
                        params.label(() -> "[red]生产" + "[] " + (currentRecipe.powerProduce * 60) + " 电力/s").left().row();
                        //params.row();
                    }
                }
                        //currentRecipe != null ? "[gray]" + "消耗" + "[] " + Math.abs(currentRecipe.powerConsume * 60) + " 电力/s" : ""
                //).padRight(12);
                // 生产时间
                params.label(() ->
                        currentRecipe != null ? "[red]时间[] " + currentRecipe.craftTime + "秒" : ""
                ).left();
            }).left().padTop(8).fillX();
        }
    }

    protected void decorateRecipes() {
        for (Recipe recipe : recipes) {
            recipe.cacheUnique(); // 填充 itemsUnique 和 fluidsUnique
        }
    }

    public void setupBlockByRecipes() {
        int maxItemAmount = 0;
        float maxFluidAmount = 0f;
        float maxPower = 0f;
        float maxHeat = 0f;

        for (Recipe recipe : recipes) {
            hasItems |= recipe.hasItems();
            hasLiquids |= recipe.hasLiquids();
            conductivePower = hasPower |= recipe.hasPower();
            hasHeat |= recipe.hasHeat();

            maxItemAmount = Math.max(recipe.maxItemAmount(), maxItemAmount);
            maxFluidAmount = Math.max(recipe.maxFluidAmount(), maxFluidAmount);
            maxPower = Math.max(recipe.maxPower(), maxPower);
            maxHeat = Math.max(recipe.maxHeat(), maxHeat);
//            maxPayloadAmount = Math.max(recipe.maxPayloadAmount(), maxPayloadAmount);

            isOutputItem |= recipe.isOutputItem();
            acceptsItems = isInputItem |= recipe.isInputItem();
            outputsLiquid = isOutputLiquid |= recipe.isOutputLiquid();
            isInputLiquid |= recipe.isInputLiquid();
            outputsPower = isOutputPower |= recipe.isOutputPower();
            consumesPower = isInputPower |= recipe.isInputPower();
            isOutputHeat |= recipe.isOutputHeat();
            isInputHeat |= recipe.isInputHeat();
//            outputsPayload = isOutputPayload |= recipe.isOutputPayload();
//            acceptsPayload = isConsumePayload |= recipe.isConsumePayload();
        }
        itemCapacity = Math.max((int) (maxItemAmount * itemCapacityMultiplier), itemCapacity);
        liquidCapacity = Math.max((int) (maxFluidAmount * 60f * fluidCapacityMultiplier), liquidCapacity);
        powerCapacity = Math.max(maxPower * 60f * powerCapacityMultiplier, powerCapacity);
        payloadCapacity = Math.max((int) (maxPayloadAmount * payloadCapacityMultiplier), payloadCapacity);
    }

    protected void setupConsumers() {
        if (isInputItem)
            consume(new ConsumeItemDynamic(
                    (MultiCrafterBuild b) -> b.getRecipe().iInputs));
        if (isInputLiquid)
            consume(new ConsumeLiquidDynamic(
                    (MultiCrafterBuild b) -> b.getRecipe().lOutputs));
        if (isInputPower)
            consume(new ConsumePowerDynamic(b -> ((MultiCrafterBuild) b).getRecipe().powerConsume));
//        if (isConsumePayload)
//            consume(new CustomConsumePayloadDynamic(
//                    (ultimatedimension.world.blocks.multi.MultiCrafter.MultiCrafterBuild b) -> b.getCurRecipe().input.payloads));
    }

}
