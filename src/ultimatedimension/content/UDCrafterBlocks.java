package ultimatedimension.content;

import mindustry.content.Items;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import mindustry.world.blocks.production.GenericCrafter;

public class UDCrafterBlocks implements ContentList{

	public static Block steelFactory,//钢厂
	electricCubeBaler,//电立方打包机
	superNuclearMachine,//超核机
	tungstenSteelSmelter,//钨钢冶炼厂
	nanoAlloyFactory,//纳米合金制造厂
	researchCenter;//研究中心

	@Override
	public void load(){
		steelFactory = new GenericCrafter("steel_factory"){{
			requirements(Category.crafting, ItemStack.with(
				Items.lead, 15,
				UDItems.iron, 30
			));
			size = 2;
			health = 800;
			craftTime = 50f;
			hasItems = true;
			hasPower = true;
			itemCapacity = 20;
			consumeItem(UDItems.iron, 1);
			consumePower(2f);
			outputItem = new ItemStack(UDItems.steel, 1);
		}};

		electricCubeBaler = new GenericCrafter("electric_cube_baler"){{
			requirements(Category.crafting, ItemStack.with(
				Items.lead, 20,
				Items.tungsten, 30,
				UDItems.steel, 30));
			size = 2;
			health = 1000;
			craftTime = 80f;
			hasItems = true;
			hasPower = true;
			itemCapacity = 20;
			consumePower(30f);
			outputItem = new ItemStack(UDItems.electricCube, 2);
		}};

		superNuclearMachine = new GenericCrafter("super_nuclear_machine"){{
			requirements(Category.crafting, ItemStack.with(
				Items.tungsten, 30,
				UDItems.steel, 20,
				UDItems.iron, 30
			));
			size = 3;
			health = 1500;
			craftTime = 85f;
			hasItems = true;
			hasPower = true;
			itemCapacity = 20;
			consumeItems(
				new ItemStack(Items.surgeAlloy, 1),
				new ItemStack(Items.thorium, 2)
			);
			consumePower(45f);
			outputItem = new ItemStack(UDItems.nanoAlloy, 2);
		}};

		tungstenSteelSmelter = new GenericCrafter("tungsten_steel_smelter"){{
			requirements(Category.crafting, ItemStack.with(
				UDItems.steel, 20,
				Items.silicon, 10
			));
			size = 3;
			health = 1500;
			craftTime = 75f;
			hasItems = true;
			hasPower = true;
			itemCapacity = 20;
			consumeItems(
				new ItemStack(UDItems.steel, 1),
				new ItemStack(Items.tungsten, 1)
			);
			consumePower(6f);
			outputItem = new ItemStack(UDItems.tungstenSteel, 2);
		}};

		researchCenter = new GenericCrafter("research_center"){{
			requirements(Category.crafting, ItemStack.with(
				Items.silicon, 300,
				UDItems.steel, 400
			));
			hasItems = true;
			hasPower = true;
			hasLiquids = true;
			size = 3;
			health = 1200;

		}};
	}
}