package ultimatedimension.content;

import arc.struct.Seq;
import arc.util.Log;
import mindustry.content.*;
import mindustry.graphics.Pal;
import mindustry.mod.Mod;
import mindustry.type.LiquidStack;

import mindustry.content.Items;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.*;
import mindustry.world.blocks.power.*;
import mindustry.world.blocks.production.*;
import ultimatedimension.world.blocks.UDBlock;
import ultimatedimension.world.blocks.crafter.*;
import ultimatedimension.world.blocks.multi.*;
import ultimatedimension.ui.*;

public class UDCrafterBlocks implements ContentList {

	public static Block steelFactory, // 钢厂
			electricCubeBaler, // 电立方打包机
			electricCubeUnpackingMachine, // 电立方解包机
			superNuclearMachine, // 超核机
			tungstenSteelSmelter, // 钨钢冶炼厂
			nanoAlloyFactory, // 纳米合金制造厂
			researchCenter, // 研究中心
			dataMatrixResearchCenter, // 数据矩阵研究中心
			superComputingMatrixResearchCenter, // 超算矩阵研究中心
			mainCrafter;

	@Override
	public void load() {
		mainCrafter = new MultiCrafter("main-crafter") {
			{
				size = 2;
				hasItems = true;
				requirements(Category.crafting, ItemStack.with(
						Items.lead, 15,
						UDItems.iron, 30));
				switchStyle = RecipeSwitchStyle.detailed;
				resolvedRecipes = Seq.with(
						new Recipe() {
							{
								input = new IOEntry() {
									{
										items = ItemStack.with(
												Items.copper, 1,
												Items.lead, 1);
									}
								};
								output = new IOEntry() {
									{
										items = ItemStack.with(
												Items.surgeAlloy, 1,
												Items.thorium, 1);
									}
								};
								craftTime = 120f;
							}
						},
						new Recipe() {
							{
								input = new IOEntry() {
									{
										items = ItemStack.with(
												Items.copper, 1);
									}
								};
								output = new IOEntry() {
									{
										items = ItemStack.with(
												Items.copper, 1,
												Items.beryllium, 1);
									}
								};
								craftTime = 160f;
							}
						});
			}
		};
		steelFactory = new MultiRecipeCrafter("steel_factory") {
			{
				requirements(Category.crafting, ItemStack.with(
						Items.lead, 15,
						UDItems.iron, 30));
				size = 2;
				health = 800;
				isBuildTime = true;
				udBuildTime = 180f;
				craftTime = 50f;
				hasItems = true;
				hasPower = true;
				itemCapacity = 20;
				consumeItem(UDItems.iron, 1);
				consumePower(2f);
				outputItem = new ItemStack(UDItems.steel, 1);
			}
		};

		electricCubeBaler = new GenericCrafter("electric_cube_baler") {
			{
				requirements(Category.crafting, ItemStack.with(
						Items.lead, 20,
						Items.tungsten, 30,
						UDItems.steel, 30));
				size = 3;
				health = 2000;
				craftTime = 60f;
				buildCost = 210f;
				hasItems = true;
				hasPower = true;
				itemCapacity = 20;
				consumePower(800f);
				outputItem = new ItemStack(UDItems.electricCube, 1);
			}
		};

		electricCubeUnpackingMachine = new UDGenericCrafter("electric_cube_unpacking_machine") {
			{
				requirements(Category.crafting, ItemStack.with(
						Items.lead, 20,
						Items.tungsten, 30,
						UDItems.steel, 30));
				size = 3;
				health = 2000;
				isBuildTime = true;
				udBuildTime = 180f;
				hasItems = true;
				hasPower = true;
				itemCapacity = 20;
				consumeItem(UDItems.electricCube, 1);
				consumePower(60f);
				outputsPower = true;
				powerProduction = 800f;
			}
		};

		superNuclearMachine = new GenericCrafter("super_nuclear_machine") {
			{
				requirements(Category.crafting, ItemStack.with(
						Items.tungsten, 30,
						UDItems.steel, 20,
						UDItems.iron, 30));
				size = 3;
				health = 1500;
				craftTime = 85f;
				hasItems = true;
				hasPower = true;
				itemCapacity = 20;
				consumeItems(
						new ItemStack(Items.surgeAlloy, 1),
						new ItemStack(Items.thorium, 2));
				consumePower(45f);
				outputItem = new ItemStack(UDItems.nanoAlloy, 2);
			}
		};

		tungstenSteelSmelter = new GenericCrafter("tungsten_steel_smelter") {
			{
				requirements(Category.crafting, ItemStack.with(
						UDItems.steel, 20,
						Items.silicon, 10));
				size = 3;
				health = 1500;
				craftTime = 75f;
				hasItems = true;
				hasPower = true;
				itemCapacity = 20;
				consumeItems(
						new ItemStack(UDItems.steel, 1),
						new ItemStack(Items.tungsten, 1));
				consumePower(6f);
				outputItem = new ItemStack(UDItems.tungstenSteel, 2);
			}
		};

		researchCenter = new GenericCrafter("research_center") {
			{
				requirements(Category.crafting, ItemStack.with(
						Items.silicon, 300,
						UDItems.steel, 400));
				hasItems = true;
				hasPower = true;
				hasLiquids = true;
				size = 3;
				health = 1200;
			}
		};

		dataMatrixResearchCenter = new GenericCrafter("data_matrix_research_center") {
			{
				requirements(Category.crafting, ItemStack.with(
						Items.silicon, 300,
						UDItems.steel, 400));
				hasItems = true;
				hasPower = true;
				hasLiquids = true;
				consumePower(20f);
				size = 3;
				health = 1200;
			}
		};
	}
}
