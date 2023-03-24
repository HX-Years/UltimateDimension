package newdimension.content;

import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.storage.*;

public class NDCoreBlocks implements ContentList{
	public static Block hanCore;

	@Override
	public void load() {
		hanCore = new CoreBlock("han_core"){{
			requirements(Category.effect, ItemStack.with(
				NDItems.steel, 1000,
				NDItems.aluminium, 200
			));
			alwaysUnlocked = true;
			isFirstTier = true;
			unitType = UnitTypes.gamma;
			health = 2000;
			itemCapacity = 10000;
			size = 3;
			unitCapModifier = 12;
		}};
	}
}
