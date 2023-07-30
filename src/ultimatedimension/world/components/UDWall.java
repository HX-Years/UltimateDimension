/*package newdimension.world.components;

import mindustry.world.meta.Stats;
import newdimension.world.meta.NDStat;
import universecore.annotations.Annotations;

public interface NDWall{
	@Annotations.BindField("selfHealing")
	default boolean selfHealing{
		return false;
	}
	
	@Annotations.MethodEntry(entryMethod = "setStats", context = {"stats -> stats"})
	default void setNDWallStats(Stats stats){
	if(hasEnergy()){
		stats.add(SglStat.energyCapacity, energyCapacity(), SglStatUnit.neutronFlux);
		stats.add(SglStat.energyResident, resident());
			if(basicPotentialEnergy() > 0) stats.add(SglStat.basicPotentialEnergy, basicPotentialEnergy(), SglStatUnit.neutronPotentialEnergy);
			if(maxEnergyPressure() > 0) stats.add(SglStat.maxEnergyPressure, maxEnergyPressure());
		}
	}
}
*/