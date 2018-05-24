package PhysicsDemonstration;



import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import com.almasb.fxgl.settings.GameSettings;
import javafx.util.Duration;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PhysicsDemoMain extends GameApplication {

	@Override
	protected void initSettings(GameSettings settings) {
		settings.setTitle("Physics Demo");
		settings.setVersion("");
	}
	
	@Override
	protected void initGame() {
		getGameWorld().addEntityFactory(new PhysicsDemoFactory());
		initScreenBounds();
		getMasterTimer().runAtInterval(() -> {
			getGameWorld().spawn("random_shape", getWidth()/2, 0);
		}, Duration.seconds(1));
	}

	protected void initScreenBounds() {
		Entity ground = Entities.builder()
				.at(-100,getHeight())
				.viewFromNodeWithBBox(new Rectangle(getWidth()+200, 10, Color.TRANSPARENT))
				.with(new PhysicsComponent())
				.build();	
		
		Entity leftWall = Entities.builder()
				.at(-10,-100)
				.viewFromNodeWithBBox(new Rectangle(10, getHeight()+200, Color.TRANSPARENT))
				.with(new PhysicsComponent())
				.build();
		
		Entity rightWall = Entities.builder()
				.at(getWidth(), -100)
				.viewFromNodeWithBBox(new Rectangle(10, getHeight()+200, Color.TRANSPARENT))
				.with(new PhysicsComponent())
				.build();
		
		getGameWorld().addEntities(ground, leftWall, rightWall);
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
