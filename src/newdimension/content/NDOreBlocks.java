package newdimension.content;

import mindustry.world.blocks.environment.OreBlock;

public class NDOreBlocks implements ContentList{

	public static OreBlock oreIron,
	oreGold,
	oreAluminium;

	@Override
	public void load(){
		oreIron = new OreBlock("ore_iron", NDItems.iron){{
			oreDefault = true;
			oreThreshold = 0.838f;
			oreScale = 24.39528f;
		}};

		oreAluminium = new OreBlock("ore_aluminium", NDItems.aluminium){{
			oreDefault = true;
			oreThreshold = 0.843f;
			oreScale = 24.56281f;
		}};
	}
}