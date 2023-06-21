package newdimension.content;

import mindustry.content.Items;
import mindustry.content.Liquids;
import static mindustry.content.Blocks.*;
import static mindustry.content.TechTree.*;
import static newdimension.content.NDCrafterBlocks.*;
import static newdimension.content.NDDefenseBlocks.*;
import static newdimension.content.NDPowerBlocks.*;
import static newdimension.content.NDTurrets.*;
import static newdimension.content.NDCoreBlocks.*;
import static newdimension.content.NDSectorPresets.*;

public class NDTechTree implements ContentList{
	@Override
	public void load(){
		{
			//NDPlanets.ND.techTree = nodeRoot("ND", hanCore, () -> {
			    node(surgeWallLarge, () -> {
				    node(tungstenWall, () -> {
				    	node(tungstenWallLarge, () -> {
				    		node(nanoAlloyWall, () -> {
				    			node(nanoAlloyWallLarge, () -> {});
				    		});
				    	});
			    	});
		    	});

				nodeProduce(Items.copper, () -> {
					nodeProduce(Items.lead, () -> {
						nodeProduce(NDItems.iron, () -> {
							nodeProduce(NDItems.aluminium, () -> {});
							nodeProduce(NDItems.steel, () -> {
								nodeProduce(NDItems.tungstenSteel, () -> {
									nodeProduce(NDItems.radioactiveSubstances, () -> {
										nodeProduce(NDItems.hypernuclearCondensates, () -> {});
									});
									nodeProduce(NDItems.electricCube, () -> {
										nodeProduce(NDItems.refactoringModule, () -> {});
									});
									nodeProduce(NDItems.iterativeModule, () -> {});
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
					nodeProduce(NDLiquids.lava, () -> {
						nodeProduce(NDLiquids.nuclearWasteWater, () -> {
							nodeProduce(NDLiquids.superFrozenLiquid, () -> {});
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
			//});
		}
	}
}