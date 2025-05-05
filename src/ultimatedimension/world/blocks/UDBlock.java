package ultimatedimension.world.blocks;

import arc.Core;
import arc.math.Mathf;
import arc.util.Log;
import arc.util.Nullable;
import arc.util.Strings;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.ctype.UnlockableContent;
import mindustry.gen.Building;
import mindustry.graphics.Pal;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.ui.Bar;
import mindustry.world.Block;
import mindustry.world.consumers.Consume;
import mindustry.world.meta.*;

import static mindustry.Vars.content;
import static mindustry.Vars.tilesize;

public class UDBlock extends Block {

    public boolean isBuildTime = true;

    public float udBuildTime = 60f;

    public @Nullable float powerProduction;

    public Stat generationType = Stat.basePowerGeneration;

    public UDBlock(String name) {
        super(name);
        update = true;
    }

    //TODO 该方法要移到子类
    @Override
    public void setStats() {
        super.setStats();
        if (outputsPower) {
            stats.add(generationType, powerProduction * 60.0f, StatUnit.powerSecond);
        }
    }

    @Override
    public void setBars() {
        super.setBars();

        //TODO too
        if (hasPower && outputsPower) {
            addBar("power", (UDBuilding entity) -> new Bar(() -> Core.bundle.format("bar.poweroutput",
                    Strings.fixed(entity.getPowerProduction() * 60 * entity.timeScale(), 1)),
                    () -> Pal.powerBar,
                    () -> entity.productionEfficiency));
        }
    }

    @Override
    public void init() {

        if (customShadow) {
            hasShadow = false;
        }

        if (fogRadius > 0) {
            flags = flags.with(BlockFlag.hasFogRadius);
        }

        if (health == -1) {
            boolean round = false;
            if (scaledHealth < 0) {
                scaledHealth = 40;

                float scaling = 1f;
                for (var stack : requirements) {
                    scaling += stack.item.healthScaling;
                }

                scaledHealth *= scaling;
                round = true;
            }

            health = round ? Mathf.round(size * size * scaledHealth, 5) : (int) (size * size * scaledHealth);
        }

        clipSize = Math.max(clipSize, size * tilesize);

        if (hasLiquids && drawLiquidLight) {
            clipSize = Math.max(size * 30f * 2f, clipSize);
        }

        if (emitLight) {
            clipSize = Math.max(clipSize, lightRadius * 2f);
        }

        if (group == BlockGroup.transportation || category == Category.distribution) {
            acceptsItems = true;
        }

        offset = ((size + 1) % 2) * tilesize / 2f;
        sizeOffset = -((size - 1) / 2);

        if (requirements.length > 0 && isBuildTime) {
            udBuildTime *= buildCostMultiplier;
            buildCost = 0f;
            buildCost = udBuildTime;
        } else if (requirements.length > 0 && !isBuildTime) {
            buildCost = 0f;
            for (ItemStack stack : requirements) {
                buildCost += stack.amount * stack.item.cost;
            }
            buildCost *= buildCostMultiplier;
        }

        consumers = consumeBuilder.toArray(Consume.class);
        optionalConsumers = consumeBuilder.select(consume -> consume.optional && !consume.ignore())
                .toArray(Consume.class);
        nonOptionalConsumers = consumeBuilder.select(consume -> !consume.optional && !consume.ignore())
                .toArray(Consume.class);
        updateConsumers = consumeBuilder.select(consume -> consume.update && !consume.ignore()).toArray(Consume.class);
        hasConsumers = consumers.length > 0;
        itemFilter = new boolean[content.items().size];
        liquidFilter = new boolean[content.liquids().size];

        for (Consume cons : consumers) {
            cons.apply(this);
        }

        setBars();

        stats.useCategories = true;

        if (!logicConfigurable) {
            configurations.each((key, val) -> {
                if (UnlockableContent.class.isAssignableFrom(key)) {
                    logicConfigurable = true;
                }
            });
        }

        if (!outputsPower && consPower != null && consPower.buffered) {
            Log.warn("Consumer using buffered power: @. Disabling buffered power.", name);
            consPower.buffered = false;
        }

        if (buildVisibility == BuildVisibility.sandboxOnly) {
            hideDetails = false;
        }

    }

    public class UDBuilding extends Building {

        public float generateTime;
        /** The efficiency of the producer. An efficiency of 1.0 means 100% */
        public float productionEfficiency = 0.0f;

        @Override
        public float warmup() {
            return productionEfficiency;
        }

        @Override
        public float ambientVolume() {
            return Mathf.clamp(productionEfficiency);
        }

        @Override
        public float getPowerProduction() {
            return powerProduction * productionEfficiency;
        }

        @Override
        public byte version() {
            return 1;
        }

        @Override
        public void write(Writes write) {
            super.write(write);
            write.f(productionEfficiency);
            write.f(generateTime);
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            productionEfficiency = read.f();
            if (revision >= 1) {
                generateTime = read.f();
            }
        }
    }
}
