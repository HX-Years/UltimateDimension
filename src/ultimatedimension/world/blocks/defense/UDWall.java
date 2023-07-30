package ultimatedimension.world.blocks.defense;

import mindustry.world.blocks.defense.Wall;
import ultimatedimension.world.components.ComponentBase;
import ultimatedimension.world.components.SelfHealing;

import java.util.ArrayList;

public class UDWall extends Wall{
	public ArrayList<ComponentBase<UDWallBuild>> components = new ArrayList<>();
	public static SelfHealing selfHealing = new SelfHealing();

	public UDWall(String name){
		super(name);
		update = true;
	}

	public class UDWallBuild extends WallBuild{
		@Override
		public void updateTile(){
			for(ComponentBase<UDWallBuild> component : components){
				component.onUpdate(this);
			}
		}
	}
}

