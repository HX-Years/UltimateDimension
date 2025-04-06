package ultimatedimension.world.meta;

import mindustry.world.meta.Stat;
import mindustry.world.meta.StatCat;

public class UDStat extends Stat {

  public static final Stat unitPower = new Stat("unitPower"),
          udhaspower = new Stat("udhaspower");

  public UDStat(String name, StatCat category) {
    super(name, category);
  }

  public UDStat(String name) {
    super(name);
  }

}
