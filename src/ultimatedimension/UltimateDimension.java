package ultimatedimension;

import arc.util.Log;
import mindustry.mod.Mod;
import ultimatedimension.content.*;
import universecore.annotations.Annotations;

@Annotations.ImportUNC(requireVersion = "1.8.4")
public class UltimateDimension extends Mod{
	public static final String ModName = "ultimate-dimension";
	public static final ContentList[] modContents = new ContentList[]{
		new ultimatedimension.content.UDItems(),
		new ultimatedimension.content.UDLiquids(),
		//new NDPlanets(),
		//new NDSectorPresets(),
		//new NDCoreBlocks(),
		new ultimatedimension.content.UDTurrets(),
		new ultimatedimension.content.UDDefenseBlocks(),
		new ultimatedimension.content.UDCrafterBlocks(),
		new ultimatedimension.content.UDPowerBlocks(),
		new ultimatedimension.content.UDOreBlocks(),
		new ultimatedimension.content.UDTechTree()
	};
	public UltimateDimension(){
		Log.info(logInfo("Loaded UltimateDimension constructor."));
		/*Events.on(ClientLoadEvent.class, e -> {
			Time.runTask(10f, () -> {
				BaseDialog dialog = new BaseDialog("frog");
				dialog.cont.add("behold").row();
				dialog.cont.image(Core.atlas.find("new-dimension-frog")).pad(20f).row();
				dialog.cont.button("I see", dialog::hide).size(100f, 50f);
				dialog.show();
			});
		});*/
	}

	@Override
	public void loadContent(){
		Log.info(logInfo("Loading some ultimatedimension content."));
		for(ContentList udlist : UltimateDimension.modContents){
			udlist.load();
		}
	}

	public static String logInfo(String info){
		return "[" + ModName + "]" + info;
	}
}
