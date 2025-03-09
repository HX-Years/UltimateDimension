package ultimatedimension.content;

import mindustry.content.UnitTypes;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import ultimatedimension.world.blocks.storage.UDCoreBlock;

public class UDCoreBlocks implements ContentList {

	public static Block hanCore, // 函
			moCore, // 模
			daoCore;// 道

	@Override
	public void load() {
		hanCore = new UDCoreBlock("han_core") {
			{
				requirements(Category.effect, ItemStack.with(
						UDItems.steel, 1000,
						UDItems.aluminium, 200));
				alwaysUnlocked = true;
				isFirstTier = true;
				unitType = UnitTypes.gamma;
				health = 2000;
				itemCapacity = 20000;
				size = 3;
				unitCapModifier = 12;
			}
		};
	}
}
