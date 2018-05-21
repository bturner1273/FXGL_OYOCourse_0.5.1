package FlappyPort;


import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyDef;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.texture.Texture;

import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.scene.paint.Color;

public class FlappyBird_Factory implements EntityFactory{

	@Spawns("flappybird")
	public Entity newFlappyBird(SpawnData data) {
		PhysicsComponent bird_physics = new PhysicsComponent();
		bird_physics.setBodyType(BodyType.DYNAMIC);
		BodyDef bdef = new BodyDef();
		bdef.setAngularDamping(10f);
		bdef.setType(BodyType.DYNAMIC);
		bird_physics.setBodyDef(bdef);
		AnimationChannel flying = new AnimationChannel("FlappyPort/flappybird_fly_sheet.png", 3, 34, 24, Duration.millis(300), 0, 2);
		AnimatedTexture bird_animation = new AnimatedTexture(flying);
		return Entities.builder()
				.from(data)
				.type(FlappyBird_Types.FLAPPYBIRD)
				.viewFromAnimatedTexture(bird_animation)
				.with(new CollidableComponent(true))
				.with(bird_physics)
				.build();
	}
	
	@Spawns("bird_pointer")
	public Entity newBirdPointer(SpawnData data) {
		return Entities.builder()
				.from(data)
				.build();
	}
	
	@Spawns("bottom_pipe")
	public Entity newBottomPipe(SpawnData data) {
		return Entities.builder()
				.from(data)
				.with(new CollidableComponent(true))
				.build();	
	}
	
	@Spawns("top_pipe")
	public Entity newTopPipe(SpawnData data) {
		return Entities.builder()
				.from(data)
				.with(new CollidableComponent(true))
				.build();
	}
	
	@Spawns("ground")
	public Entity newGround(SpawnData data) {
		return Entities.builder()
				.from(data)
				.with(new CollidableComponent(true))
				.build();
	}
	
	@Spawns("score_hitbox")
	public Entity newScoreHitBox(SpawnData data) {
		return Entities.builder()
				.from(data)
				.with(new CollidableComponent(true))
				.build();
	}
	
	
}
