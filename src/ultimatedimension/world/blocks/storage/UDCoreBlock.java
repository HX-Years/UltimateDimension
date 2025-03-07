package ultimatedimension.world.blocks.storage;

import static mindustry.Vars.state;

import arc.*;
import arc.func.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.util.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.core.*;
import mindustry.ctype.*;
import mindustry.entities.*;
import mindustry.game.EventType.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.logic.*;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.*;
import mindustry.world.meta.*;
import mindustry.world.modules.*;

import mindustry.core.UI;
import mindustry.game.Team;
import mindustry.ui.Bar;
import mindustry.world.Tile;
import mindustry.world.blocks.storage.CoreBlock;
import ultimatedimension.world.blocks.*;
import ultimatedimension.world.blocks.storage.*;
import static ultimatedimension.UD.*;

import static mindustry.Vars.*;

public class UDCoreBlock extends CoreBlock {

    public UDCoreBlock(String name) {
        super(name);
        update = true;
    }

    @Override
    public boolean canBreak(Tile tile) {
        return state.teams.cores(tile.team()).size > 1;
    }

    @Override
    public void setBars() {
        super.setBars();

        addBar("capacity", (CoreBuild e) -> new Bar(
                () -> Core.bundle.format("bar.capacity", UI.formatAmount(e.storageCapacity)),
                () -> Pal.items,
                () -> e.items.total()
                        / ((float) e.storageCapacity *
                                content.items().count(UnlockableContent::unlockedNow))));

        addBar("dianli", (CoreBuild e) -> new Bar(
                () -> Core.bundle.format("bar.dianli", UI.formatAmount(e.storageCapacity)),
                () -> Pal.items,
                () -> e.items.total()
                        / ((float) e.storageCapacity *
                                content.items().count(UnlockableContent::unlockedNow))));
    }

    @Override
    public boolean canPlaceOn(Tile tile, Team team, int rotation) {
        if (tile == null)
            return false;
        if (state.isEditor())
            return true;
        if (!state.isEditor())
            return true;

        // return false;
        CoreBuild core = team.core();

        tile.getLinkedTilesAs(this, tempTiles);
        if (!tempTiles.contains(o -> !o.floor().allowCorePlacement || o.block() instanceof CoreBlock)) {
            return true;
        }

        if (core == null
                || (!state.rules.infiniteResources && !core.items.has(requirements, state.rules.buildCostMultiplier)))
            return false;

        return tile.block() instanceof CoreBlock && size > tile.block().size
                && (!requiresCoreZone || tempTiles.allMatch(o -> o.floor().allowCorePlacement));
    }
}
