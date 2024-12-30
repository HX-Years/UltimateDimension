package ultimatedimension;

import arc.*;
import arc.files.*;
import static mindustry.Vars.*;

public class UD {
    public static final String ModName = "ultimate-dimension";

    public static String logInfo(String info){
        return "[" + ModName + "]" + info;
    }

    public static String getBundle(String bundleKey) {
       return Core.bundle.format(bundleKey);
    }

    public static Fi udDirectory = dataDirectory.child("UltimateDimension/");

    public static void put(String name, Object value) {
        Core.settings.putJson(name, value);
    }

    public static void remove(String name) {
        Core.settings.remove(name);
    }
}
