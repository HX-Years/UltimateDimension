package ultimatedimension.world.blocks.crafter;

import mindustry.ui.Bar;
import mindustry.world.*;
import mindustry.world.blocks.power.PowerGenerator.GeneratorBuild;
import mindustry.world.blocks.production.GenericCrafter;
import ultimatedimension.world.blocks.UDBlock;

public class UDGenericCrafter extends GenericCrafter {

    public UDGenericCrafter(String name) {
        super(name);
        update = true;
        hasPower = true;
        hasLiquids = false;
        consumesPower = true;
        outputsPower = true;
    }
}
