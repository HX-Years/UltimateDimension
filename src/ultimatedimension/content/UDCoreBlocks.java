package ultimatedimension.content;

import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.storage.*;

public class UDCoreBlocks implements ContentList{

	public static Block hanCore,//函
	moCore,//模
	daoCore;//道

	@Override
	public void load() {
		hanCore = new CoreBlock("han_core"){{
			requirements(Category.effect, ItemStack.with(
				UDItems.steel, 1000,
				UDItems.aluminium, 200
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
