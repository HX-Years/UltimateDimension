/*package ultimatedimension.content;

import arc.graphics.Color;
import mindustry.content.Items;
import mindustry.content.Planets;
import mindustry.game.Team;
import mindustry.graphics.g3d.HexMesh;
import mindustry.graphics.g3d.HexSkyMesh;
import mindustry.graphics.g3d.MultiMesh;
import mindustry.graphics.g3d.SunMesh;
import mindustry.type.Planet;
import ultimatedimension.world.gen.UDPlanetGenerator;

public class UDPlanets implements ContentList{

	public static Planet NDsun,
	UD;

	public void load(){
		/*NDsun = new Planet("NDsun", null, 5f){{
			bloom = true;
			accessible = false;
			meshLoader = () -> new SunMesh(
				this, 5, 5, 0.35, 1.85, 1.2, 1.1, 1.1f,
				Color.valueOf("ff7a38"),
				Color.valueOf("ff9638"),
				Color.valueOf("ffc64c"),
				Color.valueOf("ffc64c"),
				Color.valueOf("ffe371"),
				Color.valueOf("f4ee8e")
			);
		}};*/

		/*UD = new Planet("UD", Planets.sun, 1f, 3){{
			generator = new UDPlanetGenerator();
			meshLoader = () -> new HexMesh(this, 5);
			cloudMeshLoader = () -> new MultiMesh(
				new HexSkyMesh(this, 2, 0.15f, 0.14f, 5, Color.valueOf("eba768").a(0.75f), 2, 0.42f, 1f, 0.43f),
				new HexSkyMesh(this, 3, 0.6f, 0.15f, 5, Color.valueOf("eea293").a(0.75f), 2, 0.42f, 1.2f, 0.45f)
			);
			launchCapacityMultiplier = 0.5f;
			sectorSeed = 2;
			allowWaves = true;
			allowWaveSimulation = true;
			allowSectorInvasion = true;
			allowLaunchSchematics = true;
			enemyCoreSpawnReplace = true;
			allowLaunchLoadout = true;
			//doesn't play well with configs
			prebuildBase = false;
			ruleSetter = r -> {
				r.waveTeam = Team.crux;
				r.placeRangeCheck = false;
				r.showSpawns = false;
			};
			iconColor = Color.valueOf("7d4dff");
			atmosphereColor = Color.valueOf("3c1b8f");
			atmosphereRadIn = 0.02f;
			atmosphereRadOut = 0.3f;
			startSector = 15;
			alwaysUnlocked = true;
			defaultCore = UDCoreBlocks.hanCore;
			hiddenItems.addAll(Items.serpuloItems).removeAll(UDItems.udItems);
			hiddenItems.addAll(Items.erekirItems).removeAll(UDItems.udItems);

			unlockedOnLand.add(UDCoreBlocks.hanCore);
		}};
	}
}*/