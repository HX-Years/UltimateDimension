package ultimatedimension.content;

import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import mindustry.world.blocks.defense.Wall;

public class UDDefenseBlocks implements ContentList{

	public static Block tungstenSteelWall,//钨钢墙
	tungstenSteelWallLarge,//大型钨钢墙
	nanoAlloyWall,//纳米合金墙
	nanoAlloyWallLarge;//大型纳米合金墙

	@Override
	public void load(){
		tungstenSteelWall = new Wall("tungsten_steel_wall"){{
			requirements(Category.defense, ItemStack.with(UDItems.tungstenSteel, 10));
			health = 1500;
			size = 1;
		}};

		tungstenSteelWallLarge = new Wall("tungsten_steel_wall_large"){{
			requirements(Category.defense, ItemStack.with(UDItems.tungstenSteel, 40));
			health = 1500*4;
			size = 2;
		}};

		nanoAlloyWall = new ultimatedimension.world.blocks.defense.UDWall("nano_alloy_wall"){{
			requirements(Category.defense, ItemStack.with(UDItems.nanoAlloy, 10));
			health = 1200;
			size = 1;
			//components.add(selfHealing);
		}};

		nanoAlloyWallLarge = new ultimatedimension.world.blocks.defense.UDWall("nano_alloy_wall_large"){{
			requirements(Category.defense, ItemStack.with(UDItems.nanoAlloy, 40));
			health = 1200*4;
			size = 2;
			//components.add(selfHealing);
		}};
	}
}