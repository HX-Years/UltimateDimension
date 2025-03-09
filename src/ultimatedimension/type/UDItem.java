package ultimatedimension.type;

import arc.graphics.Color;
import mindustry.type.Item;
import ultimatedimension.world.meta.UDStat;

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
