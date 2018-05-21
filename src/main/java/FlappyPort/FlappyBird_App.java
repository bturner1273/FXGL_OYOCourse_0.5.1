package FlappyPort;

import javafx.util.Duration;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;

import javafx.scene.input.*;
import com.almasb.fxgl.settings.GameSettings;
import com.almasb.fxgl.texture.Texture;

public class FlappyBird_App extends GameApplication {
	Entity flappyBird, birdPointer;
	final int BACKGROUND_PIC_HEIGHT = 400, FLAP_FORCE = -250, MAX_GRAVITY = 800;
	
	@Override
	protected void initSettings(GameSettings settings) {
		settings.setTitle("FlappyBird");
		settings.setVersion("0.2.0");
		settings.setHeight(BACKGROUND_PIC_HEIGHT);
		settings.setManualResizeEnabled(true);
	}
	
	@Override
	protected void initGame() {
		getPhysicsWorld().setGravity(0, MAX_GRAVITY);
		getGameWorld().addEntityFactory(new FlappyBird_Factory());
		flappyBird = getGameWorld().spawn("flappybird", getWidth()/10, getHeight()/2);
		birdPointer = getGameWorld().spawn("bird_pointer", flappyBird.getCenter().getX()+20, flappyBird.getCenter().getY()+flappyBird.getComponent(PhysicsComponent.class).getVelocityY());
		getMasterTimer().runAtInterval(() -> {
			birdPointer.setPosition(flappyBird.getCenter().getX()+20, flappyBird.getCenter().getY()+flappyBird.getComponent(PhysicsComponent.class).getVelocityY()/10);
			if(birdPointer.getY() > flappyBird.getY()) {
				if(flappyBird.getComponent(PhysicsComponent.class).getBody().getAngle() > .125f)
				flappyBird.getComponent(PhysicsComponent.class).setAngularVelocity(Math.abs(flappyBird.getY()-birdPointer.getY())/10);
			}else{
				if(flappyBird.getComponent(PhysicsComponent.class).getBody().getAngle() < .35f)
				flappyBird.getComponent(PhysicsComponent.class).setAngularVelocity(-2*Math.abs(flappyBird.getY()-birdPointer.getY())/10);
			}
		}, Duration.millis(2));
	}
	
	@Override
	protected void initInput() {
		Input input = getInput();
		input.addAction(new UserAction("flap") {
			@Override
			protected void onActionBegin() {
				input.mockKeyRelease(KeyCode.SPACE);
				flappyBird.getComponent(PhysicsComponent.class).setVelocityY(FLAP_FORCE);
			}
		}, KeyCode.SPACE);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	protected void initUI() {
		getGameScene().setBackgroundRepeat("FlappyPort/background.png");
	}

}
