package Platformer;

import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Platformer_Factory implements EntityFactory {
	@Spawns("lava")
	public Entity newLava(SpawnData data) {
		return Entities.builder()
				.from(data)
				.type(Platformer_Types.LAVA)
				.bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
				.with(new CollidableComponent(true))
				.build();
	}
	
	@Spawns("")
	public Entity new__(SpawnData data) {
		return Entities.builder().build();
	}
	
	@Spawns("door")
	public Entity newDoor(SpawnData data) {
		return Entities.builder()
				.from(data)
				.type(Platformer_Types.DOOR)
				.bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
				.with(new CollidableComponent(true))
				.build();
	}
	
	@Spawns("platform")
	public Entity newPlatform(SpawnData data) {
		return Entities.builder()
				.from(data)
				.type(Platformer_Types.PLATFORM)
				.bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
				.with(new PhysicsComponent())
				.build(); 
	}
	
	@Spawns("coin")
	public Entity newCoin(SpawnData data) {
		return Entities.builder()
				.from(data)
				.type(Platformer_Types.COIN)
				.viewFromNode(new Circle(10, Color.GOLD))
				.bbox(new HitBox(BoundingShape.circle(10)))
				.with(new CollidableComponent(true))
				.build();
	}
	
	@Spawns("player")
	public Entity newPlayer(SpawnData data) {
		return Entities.builder().build();
	}
	
}
