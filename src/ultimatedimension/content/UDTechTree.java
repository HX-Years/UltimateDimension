package ultimatedimension.content;

import mindustry.content.Items;
import mindustry.content.Liquids;

import static mindustry.content.Blocks.*;
import static mindustry.content.TechTree.*;
import static ultimatedimension.content.UDCoreBlocks.*;
import static ultimatedimension.content.UDSectorPresets.*;
import static ultimatedimension.content.UDCrafterBlocks.*;
import static ultimatedimension.content.UDDefenseBlocks.*;
import static ultimatedimension.content.UDPowerBlocks.*;
import static ultimatedimension.content.UDTurrets.*;

public class UDTechTree implements ContentList{
	@Override
	public void load(){
		{
			UDPlanets.vastness.techTree = nodeRoot("vastness", hanCore, () -> {
			    //node(surgeWallLarge, () -> {
				    node(tungstenWall, () -> {
				    	node(tungstenWallLarge, () -> {
				    		node(nanoAlloyWall, () -> {
				    			node(nanoAlloyWallLarge, () -> {});
				    		});
				    	});
			    	});
		    	//});

				nodeProduce(Items.copper, () -> {
					nodeProduce(Items.lead, () -> {
						nodeProduce(UDItems.iron, () -> {
							nodeProduce(UDItems.aluminium, () -> {});
							nodeProduce(UDItems.steel, () -> {
								nodeProduce(UDItems.tungstenSteel, () -> {
									nodeProduce(UDItems.radioactiveSubstances, () -> {
										nodeProduce(UDItems.hypernuclearCondensates, () -> {});
									});
									nodeProduce(UDItems.electricCube, () -> {
										nodeProduce(UDItems.refactoringModule, () -> {});
									});
									nodeProduce(UDItems.iterativeModule, () -> {});
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
					nodeProduce(UDLiquids.lava, () -> {
						nodeProduce(UDLiquids.nuclearWasteWater, () -> {
							nodeProduce(UDLiquids.superFrozenLiquid, () -> {});
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

				node(huiCan, () -> {
					node(superconductingElectromagneticGun, () -> {});
					node(frost, () -> {});
				});
				
				node(landingArea, () -> {});
			});
		}
	}
}