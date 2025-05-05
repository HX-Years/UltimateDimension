package ultimatedimension;

import arc.Core;
import arc.Events;
import arc.files.Fi;
import arc.func.Boolp;
import arc.func.Cons;
import arc.func.Floatc;
import arc.func.Intc;
import arc.util.Log;
import arc.util.Time;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.game.EventType.ClientLoadEvent;
import mindustry.gen.Icon;
import mindustry.graphics.Pal;
import mindustry.mod.Mod;
import mindustry.ui.Bar;
import mindustry.ui.dialogs.BaseDialog;
import mindustry.ui.dialogs.SettingsMenuDialog;
import ultimatedimension.content.*;
import ultimatedimension.ui.dialog.RecipeTreeDialog;

import java.io.BufferedInputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static mindustry.Vars.mainExecutor;
import static mindustry.Vars.ui;
import static ultimatedimension.UD.*;
import static ultimatedimension.fileIO.Password.inputPassWord;

public class UltimateDimension extends Mod {
    //TODO
    private boolean complete = false;
    private String name = "@cancel";
    //
    public static void download(String furl, Fi dest, Intc length, Floatc progressor, Boolp canceled, Runnable done, Cons<Throwable> error) {
        mainExecutor.submit(() -> {
            try {
                HttpURLConnection con = (HttpURLConnection) new URL(furl).openConnection();
                BufferedInputStream in = new BufferedInputStream(con.getInputStream());
                OutputStream out = dest.write(false, 4096);

                byte[] data = new byte[4096];
                long size = con.getContentLength();
                long counter = 0;
                length.get((int) size);
                int x;
                while ((x = in.read(data, 0, data.length)) >= 0 && !canceled.get()) {
                    counter += x;
                    progressor.get((float) counter / (float) size);
                    out.write(data, 0, x);
                }
                out.close();
                in.close();
                if (!canceled.get()) done.run();
            } catch (Throwable e) {
                error.get(e);
            }
        });
    }

    private void w() {
        complete = true;
        name = "UpdateComplete";
    }

    public UltimateDimension() {
        Log.info(logInfo("加载对话框，测试用。"));
        // 注册科技树界面
        Events.on(EventType.ClientLoadEvent.class, e -> {
            new RecipeTreeDialog();
            Log.info(logInfo("配方树创建完成。"));
        });
        Events.on(ClientLoadEvent.class, e -> {
            Time.runTask(10f, () -> {
                BaseDialog dialog = new BaseDialog("frog");
                dialog.cont.add("欢迎来到终维度").row();
                dialog.cont.image(Core.atlas.find("new-dimension-frog")).pad(20f).row();
                dialog.cont.button("I know", dialog::hide).size(150f, 50f);
                dialog.cont.button("下载", () -> {
                    boolean[] cancel = {false};
                    float[] progress = {0};
                    int[] length = {0};

                    Fi file = udDirectory.child("mt.apk");
                    String updateUrl = "https://pan.mt2.cn/mt/MT2.16.5.apk";
//TODO
                    BaseDialog dialogdown = new BaseDialog("@be.updating");
                    download(updateUrl, file, i -> length[0] = i, v -> progress[0] = v, () -> cancel[0], () -> {
                        //这里写下载完成后运行的代码
                        w();
                    }, ed -> {
                        dialogdown.hide();
                        ui.showException(ed);
                    });

                    dialogdown.cont.add(new Bar(() -> length[0] == 0 ? Core.bundle.get("be.updating") : complete ? name : String.format("%.2f", (progress[0] * length[0]) / 1024/ 1024) + "/" + length[0]/1024/1024 + " MB", () -> Pal.accent, () -> progress[0])).width(400f).height(70f);
                    dialogdown.buttons.button(name, Icon.cancel, () -> {
                        cancel[0] = true;
                        complete = false;
                        name = "@cancel";
                        dialogdown.hide();
                    }).size(210f, 64f);
                    dialogdown.setFillParent(false);
                    dialogdown.show();
                }).size(50f, 50f).row();

                dialog.cont.button("退出", dialog::hide).size(50f, 50f);
                dialog.closeOnBack();
                dialog.show();
            });
        });
        Log.info(logInfo("正常加载。"));
    }

    @Override
    public void init() {
        Log.info(logInfo("模组init初始化……"));
        inputPassWord();
        Log.info(logInfo("写入完成"));
        if(Vars.ui != null && Vars.ui.settings != null) {
            Vars.ui.settings.addCategory(getBundle("setting.ultimate-dimension"), "icon", settingsTable -> {
                settingsTable.pref(new SettingsMenuDialog.SettingsTable.Setting(getBundle("setting.ultimate-dimension")) {
                    @Override
                    public void add(SettingsMenuDialog.SettingsTable table) {
                        table.row();
                    }
                });
            });
        }
        Log.info(logInfo("模组init初始化完成。"));
    }



    public static final ContentList[] modContents = new ContentList[]{
            new UDItems(),
            new UDLiquids(),
            new UDPlanets(),
            new UDSectorPresets(),
            new UDCoreBlocks(),
            //new UDTurrets(),
            new UDDefenseBlocks(),
            new UDCrafterBlocks(),
            new UDPowerBlocks(),
            new UDOreBlocks(),
            new UDTechTree(),
    };

    @Override
    public void loadContent(){
        Log.info(logInfo("正在加载模组内容……"));
        for(ContentList udlist : UltimateDimension.modContents){
            udlist.load();
        }
        Log.info(logInfo("模组内容加载完成。"));
    }
}
