package ultimatedimension.type;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.struct.*;
import arc.util.*;
import mindustry.type.Item;
import mindustry.ctype.*;
import mindustry.game.EventType.*;
import mindustry.graphics.*;
import mindustry.graphics.MultiPacker.*;
import mindustry.logic.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.meta.*;

import ultimatedimension.world.meta.*;

import static mindustry.Vars.*;

public class UDItem extends Item {

  public float udhaspower;

  public UDItem(String name, Color color) {
    super(name, color);
  }

  public UDItem(String name) {
    super(name);
  }

  @Override
  public void setStats() {
    super.setStats();
    stats.add(UDStat.udhaspower, udhaspower);
  }
}
