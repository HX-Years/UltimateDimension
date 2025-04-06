package ultimatedimension.world.blocks.crafter;

import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.math.geom.Geometry;
import arc.struct.EnumSet;
import arc.struct.Seq;
import arc.util.Eachable;
import arc.util.Nullable;
import arc.util.Time;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.entities.units.BuildPlan;
import mindustry.gen.Sounds;
import mindustry.logic.LAccess;
import mindustry.type.Item;
import mindustry.type.ItemStack;
import mindustry.type.LiquidStack;
import mindustry.world.draw.DrawBlock;
import mindustry.world.draw.DrawDefault;
import mindustry.world.meta.BlockFlag;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;
import mindustry.world.meta.StatValues;
import ultimatedimension.world.blocks.UDBlock;

import static mindustry.Vars.tilesize;

public class UDGenericCrafter extends UDBlock {

    public @Nullable ItemStack outputItem;

    public @Nullable ItemStack[] outputItems;

    public @Nullable LiquidStack outputLiquid;

    public @Nullable LiquidStack[] outputLiquids;

    public int[] liquidOutputDirections = { -1 };

    public float itemDuration = 120f;

    public boolean dumpExtraLiquid = true;

    public boolean ignoreLiquidFullness = false;

    public float craftTime = 80;

    public Effect craftEffect = Fx.none;

    public Effect updateEffect = Fx.none;

    public float updateEffectChance = 0.04f;

    public float warmupSpeed = 0.019f;

    public boolean legacyReadWarmup = false;

    public DrawBlock drawer = new DrawDefault();

    public UDGenericCrafter(String name) {
        super(name);
        update = true;
        solid = true;
        hasItems = true;
        hasPower = true;
        hasLiquids = false;
        consumesPower = true;
        outputsPower = false;
        ambientSound = Sounds.machine;
        sync = true;
        ambientSoundVolume = 0.03f;
        flags = EnumSet.of(BlockFlag.factory);
        drawArrow = false;
    }

    @Override
    public void setStats() {
        stats.timePeriod = craftTime;
        super.setStats();
        if ((hasItems && itemCapacity > 0) || outputItems != null) {
            if (outputsPower) {
                stats.add(Stat.productionTime, 1f, StatUnit.seconds);
            } else {
                stats.add(Stat.productionTime, craftTime / 60f, StatUnit.seconds);
            }
        }

        if (outputItems != null) {
            stats.add(Stat.output, StatValues.items(craftTime, outputItems));
        }

        if (outputLiquids != null) {
            stats.add(Stat.output, StatValues.liquids(1f, outputLiquids));
        }
    }

    @Override
    public void setBars() {
        super.setBars();

        // set up liquid bars for liquid outputs
        if (outputLiquids != null && outputLiquids.length > 0) {
            // no need for dynamic liquid bar
            removeBar("liquid");

            // then display output buffer
            for (var stack : outputLiquids) {
                addLiquidBar(stack.liquid);
            }
        }
    }

    @Override
    public boolean rotatedOutput(int x, int y) {
        return false;
    }

    @Override
    public void load() {
        super.load();

        drawer.load(this);
    }

    @Override
    public void init() {
        if (outputItems == null && outputItem != null) {
            outputItems = new ItemStack[] { outputItem };
        }
        if (outputLiquids == null && outputLiquid != null) {
            outputLiquids = new LiquidStack[] { outputLiquid };
        }
        // write back to outputLiquid, as it helps with sensing
        if (outputLiquid == null && outputLiquids != null && outputLiquids.length > 0) {
            outputLiquid = outputLiquids[0];
        }
        outputsLiquid = outputLiquids != null;

        if (outputItems != null)
            hasItems = true;
        if (outputLiquids != null)
            hasLiquids = true;

        super.init();
    }

    @Override
    public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list) {
        drawer.drawPlan(this, plan, list);
    }

    @Override
    public TextureRegion[] icons() {
        return drawer.finalIcons(this);
    }

    @Override
    public boolean outputsItems() {
        return outputItems != null;
    }

    @Override
    public void getRegionsToOutline(Seq<TextureRegion> out) {
        drawer.getRegionsToOutline(this, out);
    }

    @Override
    public void drawOverlay(float x, float y, int rotation) {
        if (outputLiquids != null) {
            for (int i = 0; i < outputLiquids.length; i++) {
                int dir = liquidOutputDirections.length > i ? liquidOutputDirections[i] : -1;

                if (dir != -1) {
                    Draw.rect(
                            outputLiquids[i].liquid.fullIcon,
                            x + Geometry.d4x(dir + rotation) * (size * tilesize / 2f + 4),
                            y + Geometry.d4y(dir + rotation) * (size * tilesize / 2f + 4),
                            8f, 8f);
                }
            }
        }
    }

    public class UDGenericCrafterBuild extends UDBuilding {
        public float progress;
        public float totalProgress;
        public float warmup;
        public float totalTime;
        public float efficiencyMultiplier;

        @Override
        public void draw() {
            drawer.draw(this);
        }

        @Override
        public void drawLight() {
            super.drawLight();
            drawer.drawLight(this);
        }

        @Override
        public boolean shouldConsume() {
            if (outputItems != null) {
                for (var output : outputItems) {
                    if (items.get(output.item) + output.amount > itemCapacity) {
                        return false;
                    }
                }
            }
            if (outputLiquids != null && !ignoreLiquidFullness) {
                boolean allFull = true;
                for (var output : outputLiquids) {
                    if (liquids.get(output.liquid) >= liquidCapacity - 0.001f) {
                        if (!dumpExtraLiquid) {
                            return false;
                        }
                    } else {
                        allFull = false;
                    }
                }
                if (allFull) {
                    return false;
                }
            }

            return enabled;
        }

        @Override
        public void updateTile() {

            warmup = 1f;
            totalTime = 1f;
            efficiencyMultiplier = 1f;

            if (efficiency > 0) {

                progress += getProgressIncrease(craftTime);
                warmup = Mathf.approachDelta(warmup, warmupTarget(), warmupSpeed);

                // continuously output based on efficiency
                if (outputLiquids != null) {
                    float inc = getProgressIncrease(1f);
                    for (var output : outputLiquids) {
                        handleLiquid(this, output.liquid,
                                Math.min(output.amount * inc, liquidCapacity - liquids.get(output.liquid)));
                    }
                }

                if (wasVisible && Mathf.chanceDelta(updateEffectChance)) {
                    updateEffect.at(x + Mathf.range(size * 4f), y + Mathf.range(size * 4));
                }
            } else {
                warmup = Mathf.approachDelta(warmup, 0f, warmupSpeed);
            }

            totalProgress += warmup * Time.delta;

            if (progress >= 1f) {
                craft();
            }

            dumpOutputs();

            boolean valid = efficiency > 0;

            warmup = Mathf.lerpDelta(warmup, valid ? 1f : 0f, warmupSpeed);

            productionEfficiency = efficiency * efficiencyMultiplier;
            totalTime += warmup * Time.delta;

            if (hasItems && valid && generateTime <= 0f) {
                consume();
                generateTime = 1f;
            }

            generateTime -= delta() / itemDuration;
        }

        @Override
        public boolean consumeTriggerValid() {
            return generateTime > 0;
        }

        @Override
        public float getProgressIncrease(float baseTime) {
            if (ignoreLiquidFullness) {
                return super.getProgressIncrease(baseTime);
            }

            // limit progress increase by maximum amount of liquid it can produce
            float scaling = 1f, max = 1f;
            if (outputLiquids != null) {
                max = 0f;
                for (var s : outputLiquids) {
                    float value = (liquidCapacity - liquids.get(s.liquid)) / (s.amount * edelta());
                    scaling = Math.min(scaling, value);
                    max = Math.max(max, value);
                }
            }

            // when dumping excess take the maximum value instead of the minimum.
            return super.getProgressIncrease(baseTime) * (dumpExtraLiquid ? Math.min(max, 1f) : scaling);
        }

        public float warmupTarget() {
            return 1f;
        }

        @Override
        public float warmup() {
            return warmup;
        }

        @Override
        public float totalProgress() {
            return totalProgress;
        }

        public void craft() {
            consume();

            if (outputItems != null) {
                for (var output : outputItems) {
                    for (int i = 0; i < output.amount; i++) {
                        offload(output.item);
                    }
                }
            }

            if (wasVisible) {
                craftEffect.at(x, y);
            }
            progress %= 1f;
        }

        public void dumpOutputs() {
            if (outputItems != null && timer(timerDump, dumpTime / timeScale)) {
                for (ItemStack output : outputItems) {
                    dump(output.item);
                }
            }

            if (outputLiquids != null) {
                for (int i = 0; i < outputLiquids.length; i++) {
                    int dir = liquidOutputDirections.length > i ? liquidOutputDirections[i] : -1;

                    dumpLiquid(outputLiquids[i].liquid, 2f, dir);
                }
            }
        }

        @Override
        public double sense(LAccess sensor) {
            if (sensor == LAccess.progress)
                return progress();
            // attempt to prevent wild total liquid fluctuation, at least for crafters
            if (sensor == LAccess.totalLiquids && outputLiquid != null)
                return liquids.get(outputLiquid.liquid);
            return super.sense(sensor);
        }

        @Override
        public float progress() {
            return Mathf.clamp(progress);
        }

        @Override
        public int getMaximumAccepted(Item item) {
            return itemCapacity;
        }

        @Override
        public boolean shouldAmbientSound() {
            return efficiency > 0;
        }

        @Override
        public void write(Writes write) {
            super.write(write);
            write.f(progress);
            write.f(warmup);
            if (legacyReadWarmup)
                write.f(0f);
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            progress = read.f();
            warmup = read.f();
            if (legacyReadWarmup)
                read.f();
        }
    }
}
