package newdimension.content;

import arc.graphics.*;
import mindustry.content.*;
import mindustry.game.*;
import mindustry.graphics.g3d.*;
import mindustry.maps.planet.SerpuloPlanetGenerator;
import mindustry.type.*;
import mindustry.world.meta.Attribute;
import mindustry.world.meta.Env;
import newdimension.world.gen.NDPlanetGenerator;

public class NDPlanets implements ContentList{

	public static Planet NDsun,
	ND;

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

		ND = new Planet("ND", Planets.sun, 1f, 3){{
			generator = new NDPlanetGenerator();
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
			defaultCore = NDCoreBlocks.hanCore;
			hiddenItems.addAll(Items.serpuloItems).removeAll(NDItems.ndItems);
			hiddenItems.addAll(Items.erekirItems).removeAll(NDItems.ndItems);

			unlockedOnLand.add(NDCoreBlocks.hanCore);
		}};
	}
}