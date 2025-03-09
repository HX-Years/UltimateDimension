package ultimatedimension.ui;

import arc.graphics.Color;
import arc.scene.ui.Image;
import arc.scene.ui.layout.Stack;
import arc.scene.ui.layout.Table;
import arc.util.Scaling;
import mindustry.core.UI;
import mindustry.gen.Icon;
import mindustry.ui.Styles;

/** An ItemDisplay, but for heat. */
public class HeatImage extends Table {
  public final float amount;

  public HeatImage(float amount) {
    this.amount = amount;

    add(new Stack() {
      {
        add(new Table(o -> {
          o.left();
          o.add(new Image(Icon.waves)).size(32f).scaling(Scaling.fit).color(new Color(1f, 0.22f, 0.22f, 0.8f));
        }));

        if (amount != 0) {
          add(new Table(t -> {
            t.left().bottom();
            t.add(amount >= 1000f ? UI.formatAmount((int) amount) : (int) amount + "").style(Styles.outlineLabel);
            t.pack();
          }));
        }
      }
    });
  }
}
