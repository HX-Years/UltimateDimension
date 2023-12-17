package ultimatedimension.world.blocks.power;

import mindustry.world.blocks.power.PowerNode;
import ultimatedimension.world.components.ComponentBase;

import java.util.ArrayList;

public class UDPowerNode extends PowerNode {
	public ArrayList<ComponentBase<UDPowerNode.UDPowerNodeBuild>> components = new ArrayList<>();

	public UDPowerNode(String name) {
		super(name);
		update = true;
	}

	public class UDPowerNodeBuild extends PowerNodeBuild {
		@Override
		public void updateTile() {
			for (ComponentBase<UDPowerNode.UDPowerNodeBuild> component : components) {
				component.onUpdate(this);
			}
		}
	}
}