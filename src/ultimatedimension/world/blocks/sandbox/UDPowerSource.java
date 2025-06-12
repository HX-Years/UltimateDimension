package ultimatedimension.world.blocks.sandbox;

import arc.math.Mathf;
import arc.math.geom.Point2;
import arc.scene.ui.ImageButton;
import arc.scene.ui.TextField;
import arc.scene.ui.layout.Table;
import arc.util.Strings;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.gen.Icon;
import mindustry.ui.dialogs.BaseDialog;
import mindustry.world.blocks.power.PowerNode;
import mindustry.world.meta.Env;

public class UDPowerSource extends PowerNode {

    public static final float MAX_POWER = Float.MAX_VALUE;
    
    public UDPowerSource(String name) {
        super(name);
        configurable = true;
        maxNodes = 100;
        outputsPower = true;
        consumesPower = false;
        envEnabled = Env.any;
        // 配置浮点数处理器
        config(Float.class, (UDPowerSourceBuild build, Float value) -> {
            build.setPowerOutput(Mathf.clamp(value, 0f, MAX_POWER));
        });
    }



    public class UDPowerSourceBuild extends PowerNodeBuild {
        public float powerProduction = 0f;

        @Override
        public void updateTile() {
            super.updateTile();
            if(enabled) {
                power.graph.update();
            }
        }
        
        @Override
        public void tapped() {
            configure(null);
        }

        @Override
        public float getPowerProduction(){
            return enabled ? powerProduction : 0f;
        }

        @Override
        public void buildConfiguration(Table table) {
            super.buildConfiguration(table); // 调用父类节点连接界面

            // 添加编辑图标按钮
            table.row();
            ImageButton editButton = new ImageButton(Icon.pencil);
            editButton.clicked(() -> showConfigDialog());
            table.add(editButton).size(40f).padTop(10);
        }
        
        // 显示配置对话框
        public void showConfigDialog() {
            BaseDialog dialog = new BaseDialog("高级配置");
            dialog.cont.clear();
            dialog.addCloseButton();

            // 输入框和确认按钮
            Table content = new Table();

            // 电力配置部分
            content.add("[cyan]电力输出配置").padBottom(10).row();
            TextField powerField = createFloatField(powerProduction, MAX_POWER);
            content.add("电力值:").pad(5).left().row();
            content.add(powerField).width(250f).padBottom(10).row();


            // 确认按钮
            content.button("应用", () -> {
                applyConfig(powerField.getText());
                dialog.hide();
            }).size(120f, 40f);

            dialog.cont.add(content);
            dialog.show();
        }
        // 浮点输入框
        private TextField createFloatField(float current, float max) {
            TextField field = new TextField(Strings.autoFixed(current, 1));
            field.setFilter((t, c) -> Character.isDigit(c) || c == '.');
            return field;
        }

//        // 应用配置逻辑
        private void applyConfig(String powerInput) {
            try {
                // 处理电力值
                float powerValue = Float.parseFloat(powerInput);
                powerProduction = Mathf.clamp(powerValue, 0f, MAX_POWER);
                configure(powerValue); // 触发电力配置保存
            } catch (NumberFormatException e) {
                // 恢复显示值
                powerProduction = Mathf.clamp(powerProduction, 0f, MAX_POWER);
            }
}
        
        // 设置电力输出（带范围检查）
        public void setPowerOutput(float value) {
            powerProduction = Mathf.clamp(value, 0f, MAX_POWER);
        }

        // 数据持久化
        @Override
        public void write(Writes write) {
            super.write(write);
            write.f(powerProduction);
            //write.i(connectionRange);
        }

        @Override
        public void read(Reads read) {
            super.read(read);
            powerProduction = read.f();
//            connectionRange = read.i();
//            laserRange = connectionRange;
        }
        
        // 当前配置值（用于保存）
        @Override
        public Point2[] config() {
            return super.config();
        }


        // 核心修改：调整配置方法
        @Override
        public void configure(Object value) {
            if(value instanceof Float) {
                setPowerOutput((Float)value);
            } else {
                super.configure(value);
            }
        }

    }
}
