package Frogger;

import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.extra.entity.components.KeepOnScreenComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;

import javafx.util.Duration;

public class Frogger_Factory implements EntityFactory {
	final double FROG_HIT_RADIUS = 22.5;

	@Spawns("frog")
	public Entity newFrog(SpawnData data) {
		return Entities.builder()
				.from(data)
//				.viewFromAnimatedTexture(new AnimatedTexture(sink))
				.viewFromTexture("Frogger/frog_idle.png")
				.bbox(new HitBox(BoundingShape.circle(FROG_HIT_RADIUS)))
				.with(new CollidableComponent(true))
				.with(new KeepOnScreenComponent(true,true))
				.build();
	}
	
	@Spawns("right_log")
	public Entity newRightLog(SpawnData data) {
		return Entities.builder().build();
	}
	
	@Spawns("left_log")
	public Entity newLeftLog(SpawnData data) {
		return Entities.builder().build();
	}
	
	@Spawns("right_car")
	public Entity newRightCar(SpawnData data) {
		return Entities.builder().build();
	}
	
	@Spawns("left_car")
	public Entity newLeftCar(SpawnData data) {
		return Entities.builder().build();
	}
	
	@Spawns("water")
	public Entity newWater(SpawnData data) {
		return Entities.builder().build();
	}
	
	@Spawns("score_hitbox")
	public Entity newScoreHitBox(SpawnData data) {
		return Entities.builder().build();
	}
	
}
