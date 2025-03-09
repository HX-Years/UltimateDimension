package ultimatedimension.ui;

import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.scene.ui.Image;
import arc.scene.ui.layout.Stack;
import arc.scene.ui.layout.Table;
import mindustry.core.UI;
import mindustry.type.PayloadStack;
import mindustry.ui.Styles;

public class PayloadImage extends Stack {

  public PayloadImage(TextureRegion region) {
    add(new Table(o -> {
      o.left();
      o.add(new Image(region)).size(32f);
    }));
  }

  public PayloadImage(TextureRegion region, float amount) {
    add(new Table(o -> {
      o.left();
      o.add(new Image(region)).size(32f);
    }));

    if (amount != 0) {
      add(new Table(t -> {
        t.left().bottom();
        t.add(amount >= 1000 ? UI.formatAmount((long) amount) : Mathf.round(amount) + "").fontScale(0.9f)
            .style(Styles.outlineLabel);
        t.pack();
      }));
    }
  }

  public PayloadImage(PayloadStack stack) {
    this(stack.item.uiIcon, stack.amount);
  }
}
