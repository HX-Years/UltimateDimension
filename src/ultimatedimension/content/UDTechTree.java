package ultimatedimension.content;

import mindustry.content.Items;
import mindustry.content.Liquids;

import static mindustry.content.TechTree.*;
import static ultimatedimension.content.UDCoreBlocks.hanCore;
import static ultimatedimension.content.UDCrafterBlocks.*;
import static ultimatedimension.content.UDDefenseBlocks.*;
import static ultimatedimension.content.UDItems.*;
import static ultimatedimension.content.UDLiquids.*;
import static ultimatedimension.content.UDPowerBlocks.*;

public class UDTechTree implements ContentList{

	@Override
	public void load(){

		UDPlanets.vastness.techTree = nodeRoot("vastness", hanCore, () -> {
           UDPlanets.mo.techTree = nodeRoot("mo", hanCore, () -> {
                node(tungstenSteelWallLarge, () -> {});
            });
			node(tungstenSteelWall, () -> {
				node(tungstenSteelWallLarge, () -> {
					node(nanoAlloyWall, () -> {
						node(nanoAlloyWallLarge, () -> {});
					});
				});
			});

			nodeProduce(Items.copper, () -> {
				nodeProduce(Items.lead, () -> {
					nodeProduce(iron, () -> {
						nodeProduce(aluminium, () -> {});
						nodeProduce(steel, () -> {
							nodeProduce(tungstenSteel, () -> {
								nodeProduce(radioactiveSubstances, () -> {});
								nodeProduce(electricCube, () -> {
									nodeProduce(refactoringModule, () -> {});
								});
								nodeProduce(iterativeModule, () -> {});
							});
						});
					});
					nodeProduce(Items.titanium, () -> {
						nodeProduce(Items.thorium, () -> {});
					});
					nodeProduce(Items.sand, () -> {
						nodeProduce(Items.silicon, () -> {});
						nodeProduce(Items.metaglass, () -> {});
					});
				});
				nodeProduce(Items.coal, () -> {
					nodeProduce(Items.graphite, () -> {});
				});
			});

			nodeProduce(Liquids.water, () -> {
				nodeProduce(lava, () -> {
					nodeProduce(nuclearWasteWater, () -> {
						nodeProduce(superFrozenLiquid, () -> {});
					});
				});
			});

			node(steelFactory, () -> {
				node(tungstenSteelSmelter, () -> {
					node(electricCubeBaler, () -> {
						node(superNuclearMachine, () -> {});
					});
				});
			});

			node(batteryStack, () -> {
				node(batteryStackLarge, () -> {
					node(nuclearFusionReactor, () -> {});
				});
			});

			/*node(huiCan, () -> {
				node(superconductingElectromagneticGun, () -> {});
				node(frost, () -> {});
			});*/

			//node(landingArea, () -> {});
		});
	}
}
