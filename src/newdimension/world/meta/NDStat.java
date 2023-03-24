/*package mindustry.world.meta;

import arc.*;
import arc.struct.*;

public class NDStat{
	public static final Stat

	private static Stat create(String name, int index, StatCat cat){
		Seq<Stat> all = Stat.all;
		Stat res = new Stat(name, cat);

		all.insert(index, res);

		for(int i = 0; i < all.size; i++){
			FieldHandler.setValueDefault(all.get(i), "id", i);
		}

		return res;
	}
}*/
