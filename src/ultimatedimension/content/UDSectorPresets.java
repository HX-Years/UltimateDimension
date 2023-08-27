package ultimatedimension.content;

import mindustry.type.SectorPreset;

import static ultimatedimension.content.UDPlanets.*;

public class UDSectorPresets implements ContentList{
	public static SectorPreset landingArea;

	@Override
	public void load(){
		landingArea = new SectorPreset("landingArea", vastness, 15){{
			alwaysUnlocked = true;
			difficulty = 1;
			addStartingItems = true;
		}};
	}
}