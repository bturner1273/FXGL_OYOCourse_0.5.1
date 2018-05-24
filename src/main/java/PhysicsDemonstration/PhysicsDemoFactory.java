package PhysicsDemonstration;


import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.extra.entity.components.KeepOnScreenComponent;
import com.almasb.fxgl.extra.entity.components.ExpireCleanComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyDef;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;

import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Duration;

public class PhysicsDemoFactory implements EntityFactory {
	@Spawns("random_shape")
	public Entity newRandomShape(SpawnData data) {
		Shape[] shapes = {new Rectangle(FXGLMath.random(30, 130),FXGLMath.random(30, 130),Color.rgb(FXGLMath.random(0, 255), FXGLMath.random(0, 255), FXGLMath.random(0, 255))),
				new Circle(FXGLMath.random(30, 80), Color.rgb(FXGLMath.random(0, 255), FXGLMath.random(0, 255), FXGLMath.random(0, 255)))};
		PhysicsComponent shape_physics = new PhysicsComponent();
		shape_physics.setBodyType(BodyType.DYNAMIC);
		FixtureDef fd = new FixtureDef();
		fd.setDensity(FXGLMath.randomFloat());
		fd.setRestitution(FXGLMath.randomFloat());
		fd.setFriction(FXGLMath.randomFloat());
		BodyDef bd = new BodyDef();
		bd.setAngularDamping(1f);
		bd.setType(BodyType.DYNAMIC);
		shape_physics.setBodyDef(bd);
		Shape randShape = shapes[FXGLMath.random(0, shapes.length-1)];
		
		shape_physics.setOnPhysicsInitialized(() -> {
			shape_physics.setAngularVelocity(FXGLMath.random(-10,10));
		});
		return Entities.builder()
				.from(data)
				.viewFromNodeWithBBox(randShape)
				.with(shape_physics)
				.with(new KeepOnScreenComponent(true,true))
				.with(new ExpireCleanComponent(Duration.seconds(20)))
				.build();
	}
	

}
