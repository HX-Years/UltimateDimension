package ultimatedimension.world.blocks.multic;

import arc.func.Func;
import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import mindustry.gen.Building;
import mindustry.type.LiquidStack;
import mindustry.ui.ReqImage;
import mindustry.world.Block;
import mindustry.world.consumers.Consume;
import mindustry.world.modules.LiquidModule;
import ultimatedimension.ui.FluidImage;

public class ConsumeLiquidDynamic extends Consume {
    public final Func<Building, Seq<LiquidStack>> liquids;

    @SuppressWarnings("unchecked")
    public <T extends Building> ConsumeLiquidDynamic(Func<T, Seq<LiquidStack>> fluids) {
        this.liquids = (Func<Building, Seq<LiquidStack>>) fluids;
    }

    @Override
    public void apply(Block block) {
        block.hasLiquids = true;
    }

    @Override
    public void update(Building build) {
        Seq<LiquidStack> liquids = this.liquids.get(build);
        remove(build.liquids, liquids, build.edelta());
    }

//    @Override
//    public void build(Building build, Table table) {
//        final LiquidStack[][] current = { liquids.get(build) };
//
//        table.table(cont -> {
//            table.update(() -> {
//                Seq<LiquidStack> newLiquids = liquids.get(build);
//                if (current[0] != newLiquids) {
//                    rebuild(build, cont);
//                    current[0] = newLiquids;
//                }
//            });
//
//            rebuild(build, cont);
//        });
//    }
    @Override
    public void build(Building build, Table table) {
        final Seq<LiquidStack>[] current = new Seq[]{ liquids.get(build) };

        table.table(cont -> {
            table.update(() -> {
                Seq<LiquidStack> newLiquids = liquids.get(build);
                if (current[0] != newLiquids) {
                    rebuild(build, cont);
                    current[0] = newLiquids;
                }
            });

            rebuild(build, cont);
        });
    }

//    private void rebuild(Building tile, Table table) {
//        table.clear();
//        int i = 0;
//
//        LiquidStack[] fluids = this.liquids.get(tile);
//        for (LiquidStack stack : fluids) {
//            table.add(new ReqImage(new FluidImage(stack.liquid.uiIcon),
//                    () -> tile.liquids != null && tile.liquids.get(stack.liquid) >= stack.amount)).padRight(8).left();
//            if (++i % 4 == 0)
//                table.row();
//        }
//    }
    private void rebuild(Building tile, Table table) {
        table.clear();
        int i = 0;

        Seq<LiquidStack> liquids = this.liquids.get(tile);
        for (LiquidStack stack : liquids) {
            table.add(new ReqImage(new FluidImage(stack.liquid.uiIcon),
                    () -> tile.liquids != null && tile.liquids.get(stack.liquid) >= stack.amount)).padRight(8).left();
            if (++i % 4 == 0)
                table.row();
        }
    }

//    @Override
//    public float efficiency(Building build) {
//        LiquidStack[] fluids = this.liquids.get(build);
//        return build.consumeTriggerValid() || has(build.liquids, fluids) ? 1f : 0f;
//    }
    @Override
    public float efficiency(Building build) {
        Seq<LiquidStack> fluids = this.liquids.get(build);
        return build.consumeTriggerValid() || has(build.liquids, fluids) ? 1f : 0f;
    }

    public static boolean has(LiquidModule liquids, Seq<LiquidStack> reqs) {
        for (LiquidStack req : reqs) {
            if (liquids.get(req.liquid) < req.amount)
                return false;
        }
        return true;
    }

    public static void remove(LiquidModule fluids, Seq<LiquidStack> reqs, float multiplier) {
        for (LiquidStack req : reqs) {
            fluids.remove(req.liquid, req.amount * multiplier);
        }
    }
}
