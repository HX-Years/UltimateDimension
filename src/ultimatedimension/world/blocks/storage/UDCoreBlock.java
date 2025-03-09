package ultimatedimension.world.blocks.storage;

import arc.Core;
import mindustry.core.UI;
import mindustry.ctype.UnlockableContent;
import mindustry.game.Team;
import mindustry.graphics.Pal;
import mindustry.ui.Bar;
import mindustry.world.Tile;
import mindustry.world.blocks.storage.CoreBlock;

import static mindustry.Vars.content;
import static mindustry.Vars.state;

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
