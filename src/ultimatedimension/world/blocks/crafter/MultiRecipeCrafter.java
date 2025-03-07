package ultimatedimension.world.blocks.crafter;

import arc.util.Eachable;
import mindustry.entities.units.BuildPlan;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.scene.ui.layout.*;
import arc.scene.ui.*;
import arc.struct.*;
import arc.util.*;
import arc.util.io.*;
import arc.util.pooling.*;
import mindustry.entities.units.*;
import mindustry.ui.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.content.*;
import mindustry.world.blocks.*;
import mindustry.world.meta.*;

import java.util.*;

import static mindustry.Vars.*;

public class MultiRecipeCrafter extends UDGenericCrafter {

  public MultiRecipeCrafter(String name) {
    super(name);
    configurable = true;
    clearOnDoubleTap = true;
    update = true;
  }

  @Override
  public void drawPlanConfig(BuildPlan plan, Eachable<BuildPlan> list) {
    drawPlanConfigCenter(plan, plan.config, "unloader-center");
  }

  /*
   * @Override
   * public void buildConfiguration(Table table) {
   * Table cont = new Table();
   * cont.table(t -> {
   * Button recipe = new Button(Styles.togglet);
   * recipe.table(b -> {
   * b.add(new ItemImage(new ItemStack(Items.copper, 3))).padRight(4f);
   * });
   * });
   * }
   */

  public class MultiRecipeCrafterBuild extends UDGenericCrafterBuild {

    @Override
    public void updateTile() {
      for (Building other : this.proximity) {
        if (other.block != this.block) {
        }
        float thisH = this.health;
        float otherH = other.health;
        this.health -= 1;
        other.health -= 1;
      }
    }

    @Override
    public void buildConfiguration(Table table) {
      Table cont = new Table();
      cont.table(b -> {
        Button recipe = new Button(Styles.togglet);
        recipe.table(t -> {
          t.add(new ItemImage(new ItemStack(Items.copper, 3))).padRight(4f);
        });
      });
    }
  }
}
