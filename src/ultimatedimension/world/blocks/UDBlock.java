package ultimatedimension.world.blocks;

import mindustry.world.Block;
import universecore.annotations.Annotations;
import universecore.components.blockcomp.ConsumerBlockComp;
import universecore.world.consumers.BaseConsumers;

@Annotations.ImplEntries
public class UDBlock extends Block implements ConsumerBlockComp {

    public UDBlock(String name) {
        super(name);
        update = true;
    }

    @Override
    public BaseConsumers newConsume() {
        return ConsumerBlockComp.super.newConsume();
    }
}
