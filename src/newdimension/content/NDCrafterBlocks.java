package newdimension.content;

import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.production.*;
import newdimension.NewDimension;
import universecore.components.blockcomp.FactoryBuildComp;
import universecore.util.UncLiquidStack;
import universecore.world.consumers.BaseConsume;
import universecore.world.consumers.BaseConsumers;
import universecore.world.consumers.ConsumeItems;
import universecore.world.consumers.ConsumeType;
import universecore.world.lightnings.LightningContainer;
import universecore.world.lightnings.generator.CircleGenerator;
import universecore.world.lightnings.generator.VectorLightningGenerator;
import universecore.world.producers.ProduceType;

public class NDCrafterBlocks implements ContentList{

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
				NDItems.iron, 30
			));
			size = 2;
			health = 800;
			craftTime = 50f;
			hasItems = true;
			hasPower = true;
			itemCapacity = 20;
			consumeItem(NDItems.iron, 1);
			consumePower(2f);
			outputItem = new ItemStack(NDItems.steel, 1);
		}};

		electricCubeBaler = new GenericCrafter("electric_cube_baler"){{
			requirements(Category.crafting, ItemStack.with(
				Items.lead, 20,
				Items.tungsten, 30,
				NDItems.steel, 30));
			size = 2;
			health = 1000;
			craftTime = 80f;
			hasItems = true;
			hasPower = true;
			itemCapacity = 20;
			consumePower(30f);
			outputItem = new ItemStack(NDItems.electricCube, 2);
		}};

		superNuclearMachine = new GenericCrafter("super_nuclear_machine"){{
			requirements(Category.crafting, ItemStack.with(
				Items.tungsten, 30,
				NDItems.steel, 20,
				NDItems.iron, 30
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
			outputItem = new ItemStack(NDItems.hypernuclearCondensates, 2);
		}};

		tungstenSteelSmelter = new GenericCrafter("tungsten_steel_smelter"){{
			requirements(Category.crafting, ItemStack.with(
				NDItems.steel, 20,
				Items.silicon, 10
			));
			size = 3;
			health = 1500;
			craftTime = 75f;
			hasItems = true;
			hasPower = true;
			itemCapacity = 20;
			consumeItems(
				new ItemStack(NDItems.steel, 1),
				new ItemStack(Items.tungsten, 1)
			);
			consumePower(6f);
			outputItem = new ItemStack(NDItems.tungstenSteel, 2);
		}};

		researchCenter = new GenericCrafter("research_center"){{
			requirements(Category.crafting, ItemStack.with(
				Items.silicon, 300,
				NDItems.steel, 400
			));
			hasItems = true;
			hasPower = true;
			hasLiquids = true;
			size = 3;
			health = 1200;

			/*newConsume();
			consume.time(90f);
			consume.power(2.2f);
			consume.liquid(Liquids.water, 1f);
			newProduce();
			produce.item(NDItems.data_cube);

			newConsume();
			consume.time(90f);
			consume.power(3.6f);
			consume.item(NDItems.data_cube, 1);
			consume.liquid(Liquids.water, 1f);
			newProduce();
			produce.item(NDItems.supercomputing_cube);

			newConsume();
			consume.time(120f);
			consume.power(5.7f);
			consume.items(ItemStack.with(
				NDItems.data_cube, 1,
				NDItems.supercomputing_cube, 1
			));
			consume.liquid(Liquids.water, 1f);
			newProduce();
			produce.item(NDItems.model_cube);

			newConsume();
			consume.time(120f);
			consume.power(8f);
			consume.items(ItemStack.with(
				NDItems.data_cube, 1,
				NDItems.super_computing, 1,
				NDItems.model_cube, 1
			));
			consume.liquid(Liquids.water, 1f);
			newProduce();
			produce.item(NDItems.structure_cube);

			newConsume();
			consume.time(150f);
			consume.power(10f);
			consume.items(ItemStack.with(
				NDItems.data_cube, 1,
				NDItems.supercomputing, 1,
				NDItems.model_cube, 1,
				NDItems.structure, 1
			));
			consume.liquid(Liquids.water, 3f);
			newProduce();
			produce.item(NDItems.set_cube);*/
		}};
	}
}