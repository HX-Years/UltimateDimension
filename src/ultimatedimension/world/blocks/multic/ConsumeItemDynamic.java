package ultimatedimension.world.blocks.multic;

import arc.func.Func;
import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import mindustry.gen.Building;
import mindustry.type.ItemStack;
import mindustry.ui.ItemImage;
import mindustry.ui.ReqImage;
import mindustry.world.Block;
import mindustry.world.consumers.Consume;

public class ConsumeItemDynamic extends Consume {
    public final Func<Building, Seq<ItemStack>> items;

    @SuppressWarnings("unchecked")
    public <T extends Building> ConsumeItemDynamic(Func<T, Seq<ItemStack>> items){
        this.items = (Func<Building, Seq<ItemStack>>)items;
    }

    @Override
    public void apply(Block block){
        block.hasItems = true;
        block.acceptsItems = true;
    }


    @Override
    public void build(Building build, Table table){
        Seq<ItemStack>[] current = new Seq[] {items.get(build)};

        table.table(cont -> {
            table.update(() -> {
                Seq<ItemStack> newItems = items.get(build);
                if(current[0] != newItems){
                    rebuild(build, cont);
                    current[0] = newItems;
                }
            });

            rebuild(build, cont);
        });
    }

    private void rebuild(Building build, Table table){
        table.clear();
        int i = 0;

        for(ItemStack stack : items.get(build)){
            table.add(new ReqImage(new ItemImage(stack.item.uiIcon, Math.round(stack.amount * multiplier.get(build))),
                    () -> build.items != null && build.items.has(stack.item, Math.round(stack.amount * multiplier.get(build))))).padRight(8).left();
            if(++i % 4 == 0) table.row();
        }
    }

    @Override
    public void trigger(Building build){
        for(ItemStack stack : items.get(build)){
            build.items.remove(stack.item, Math.round(stack.amount * multiplier.get(build)));
        }
    }

    @Override
    public float efficiency(Building build){
        //return build.consumeTriggerValid() || build.items.has(items.get(build), multiplier.get(build)) ? 1f : 0f;
        if (build.consumeTriggerValid()) return 1f;
        Seq<ItemStack> required = items.get(build);
        for (ItemStack stack : required) {
            if (build.items.get(stack.item) < stack.amount * multiplier.get(build)) {
                return 0f;
            }
        }
        return 1f;
    }
}
