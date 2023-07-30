package ultimatedimension.world.blocks;

import mindustry.world.Block;
import universecore.annotations.Annotations;

@Annotations.ImplEntries
public class UDBlock extends Block {

	public UDBlock(String name){
		super(name);
		update = true;
	}
}