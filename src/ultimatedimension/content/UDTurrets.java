package ultimatedimension.content;

import mindustry.content.*;
import mindustry.entities.bullet.*;
import mindustry.entities.pattern.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.defense.turrets.*;

public class UDTurrets implements ContentList{
	
	public static Block huiCan,//毁残
	superconductingElectromagneticGun,//超导电磁炮
	frost,//霜冻
	emberFire;//烬火

	@Override
	public void load(){
		huiCan = new ItemTurret("hui_can"){{
			requirements(Category.turret, ItemStack.with(
				Items.lead, 500,
				UDItems.tungstenSteel, 2000,
				UDItems.aluminium, 1000,
				UDItems.steel, 1200
			));
			ammo(
				UDItems.tungstenSteel, new BasicBulletType(4f, 200){{
					width = 7f;
					height = 9f;
					lifetime = 10f;
					ammoMultiplier = 4;
				}}
			);
			size = 4;
			hasPower = true;
			consumePower(5f);
			shoot = new ShootAlternate(0.5f);
			shootY = 3f;
			reload = 20f;
			range = 220;
			shootCone = 10f;
			ammoUseEffect = Fx.none;
			health = 3000;
			inaccuracy = 2f;
			rotateSpeed = 10f;
			coolant = consumeCoolant(0.1f);
			limitRange();
		}};

		superconductingElectromagneticGun = new ItemTurret("superconducting_electromagnetic_gun"){{
			requirements(Category.turret, ItemStack.with(
				Items.lead, 1000,
				Items.silicon, 1600,
				UDItems.tungstenSteel, 2200,
				UDItems.aluminium, 850
			));
			ammo(
				UDItems.tungstenSteel, new BasicBulletType(10f, 10000f){{
					width = 8f;
					height = 10f;
					lifetime = 1800f;
					ammoMultiplier = 1f;
				}}
			);
			size = 6;
			hasPower = true;
			consumePower(12f);
			consumeLiquid(UDLiquids.superFrozenLiquid, 100f);
			shoot = new ShootAlternate(3.5f);
			shootY = 3f;
			reload = 20f;
			range = 500;
			shootCone = 60f;
			ammoUseEffect = Fx.casing1;
			health = 4200;
			inaccuracy = 2f;
			rotateSpeed = 10f;
			coolant = consumeCoolant(0.1f);
			limitRange();
		}};

		frost = new Turret("frost"){{
			requirements(Category.turret, ItemStack.with(
					UDItems.tungstenSteel, 500
			));
		}};

		emberFire = new LiquidTurret("ember_fire"){{
			requirements(Category.turret, ItemStack.with(
					UDItems.tungstenSteel, 2000
			));
		}};
	}
}