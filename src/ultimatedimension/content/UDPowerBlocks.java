package ultimatedimension.content;

import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import mindustry.world.blocks.power.Battery;
import mindustry.world.blocks.power.ImpactReactor;
import mindustry.world.blocks.power.PowerNode;

public class UDPowerBlocks implements ContentList{

	public static Block batteryStack,//电池堆
	batteryStackLarge,//大型电池堆
	nuclearFusionReactor,//核聚变反应堆
	powerHealNode,//电力恢复节点
	powerGridNode,//电网节点
	healNode;//恢复节点

	@Override
	public void load(){
		batteryStack = new Battery("battery_stack"){{
			requirements(Category.power, ItemStack.with(
				UDItems.steel, 10,
				UDItems.tungstenSteel, 15,
				Items.silicon, 15
			));
			size = 3;
			consumePowerBuffered(200000f);
			baseExplosiveness = 18f;
		}};

		batteryStackLarge = new Battery("battery_stack_large"){{
			requirements(Category.power, ItemStack.with(
				UDItems.tungstenSteel, 30,
				UDItems.iterativeModule, 1,
				Items.silicon, 40
			));
			size = 4;
			consumePowerBuffered(2400000f);
			baseExplosiveness = 50f;
		}};

		nuclearFusionReactor = new ImpactReactor("nuclear_fusion_reghctor"){{
			requirements(Category.power, ItemStack.with(
				UDItems.steel, 200,
				UDItems.tungstenSteel, 300,
				Items.metaglass, 60
			));
			size = 4;
			powerProduction = 3650f;
            itemDuration = 100f;
            consumePower(100f);
            consumeItem(UDItems.radioactiveSubstances);
            consumeLiquid(Liquids.water, 0.75f);
		}};
		//待实现电量恢复
		/*powerHealNode = new PowerNode("power-heal-node"){{
			requirements(Category.power, ItemStack.with(
				Items.silicon, 10,
				NDItems.steel, 20
			));
			laserRange = 6f;
			maxNodes = 10;
		}};*/

		powerGridNode = new PowerNode("power_grid_node"){{
			requirements(Category.power, ItemStack.with(
				Items.titanium, 5,
				Items.lead, 10,
				Items.silicon, 3
			));
			size = 2;
			maxNodes = 15;
			laserRange = 15f;
		}};
	}
}
