package ultimatedimension.world.components;

import mindustry.gen.Building;

public class SharingPowerHealth extends ComponentBase<ultimatedimension.world.blocks.power.UDPowerNode.UDPowerNodeBuild>{
	@Override
	public void onUpdate(ultimatedimension.world.blocks.power.UDPowerNode.UDPowerNodeBuild ud){
		for(Building other : ud.proximity){
			if(other.block != ud.block){
				continue;
			}
			float thisH = ud.health;
			float maxH = ud.maxHealth;
			float otherH = other.health;
			float gH = 0;
			if(thisH > 0 && otherH < thisH && otherH > 0){
				other.health += 1;
				ud.health += 1;
				gH += 1;
				if(gH >= maxH * 3){
					continue;
				}
			}
		}
	}
}
