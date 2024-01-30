package ultimatedimension.content;

import mindustry.world.blocks.environment.OreBlock;

public class UDOreBlocks implements ContentList{

	public static OreBlock oreIron,
	oreGold,
	oreAluminium;

	@Override
	public void load(){
		oreIron = new OreBlock("ore_iron", UDItems.iron){{
			oreDefault = true;
			oreThreshold = 0.838f;
			oreScale = 24.39528f;
		}};

		oreAluminium = new OreBlock("ore_aluminium", UDItems.aluminium){{
			oreDefault = true;
			oreThreshold = 0.843f;
			oreScale = 24.56281f;
		}};
	}
}