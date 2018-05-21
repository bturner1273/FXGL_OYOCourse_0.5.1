package FlappyPort;

import javafx.util.Duration;
import javafx.scene.text.*;
import javafx.scene.paint.Color;
import javafx.scene.input.*;

import java.util.List;
import java.util.Map;

import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.RenderLayer;
import com.almasb.fxgl.entity.view.EntityView;
import com.almasb.fxgl.extra.entity.components.ProjectileComponent;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.PhysicsWorld;
import com.almasb.fxgl.settings.GameSettings;
import com.almasb.fxgl.texture.Texture;

public class FlappyBird_App extends GameApplication {
	boolean done = false;
	Entity flappyBird, birdPointer;
	final int BACKGROUND_PIC_HEIGHT = 400, FLAP_FORCE = -250, MAX_GRAVITY = 800, PIPE_SPACING = 350,
			BOTTOM_PIPE_LOWER_LIMIT = 300, BOTTOM_PIPE_UPPER_LIMIT = 120;
	
	@Override
	protected void initSettings(GameSettings settings) {
		settings.setTitle("FlappyBird");
		settings.setVersion("0.2.0"); 
		settings.setHeight(BACKGROUND_PIC_HEIGHT);
		settings.setApplicationMode(ApplicationMode.DEVELOPER);
	}
	
	@Override
	protected void initPhysics() {
		PhysicsWorld physicsWorld = getPhysicsWorld();
		physicsWorld.addCollisionHandler(new CollisionHandler(FlappyBird_Types.FLAPPYBIRD, FlappyBird_Types.GAMEOVER) {
			@Override
			protected void onCollisionBegin(Entity flappybird, Entity gameOver) {
				initGameOver();
			}
		});
		physicsWorld.addCollisionHandler(new CollisionHandler(FlappyBird_Types.FLAPPYBIRD, FlappyBird_Types.HITBOX) {
			@Override
			protected void onCollisionBegin(Entity flappybird, Entity score_box) {
				if(!done) getGameState().increment("score", 1);
			}
		});
	}
	
	@Override
	protected void initGameVars(Map<String, Object> vars) {
		vars.put("score", 0);
	}
	
	protected void initGameOver() {
		done = true;
		Texture gO = getAssetLoader().loadTexture("FlappyPort/game_over.png");
		EntityView gameOver = new EntityView(gO);
		gameOver.setLayoutX(getWidth()/2-80);
		gameOver.setLayoutY(getHeight()/3);
		getGameScene().addGameView(gameOver);
		flappyBird.removeComponent(PhysicsComponent.class);
		List<Entity> pipes = getGameWorld().getEntitiesByType(FlappyBird_Types.GAMEOVER);
		for(int i = 0; i < pipes.size(); i++) {
			pipes.get(i).setRenderLayer(RenderLayer.BACKGROUND);
			pipes.get(i).removeComponent(ProjectileComponent.class);
		}
		getMasterTimer().clear();
	}
	
	@Override
	protected void initGame() {
		getPhysicsWorld().setGravity(0, MAX_GRAVITY);
		getGameWorld().addEntityFactory(new FlappyBird_Factory());
		flappyBird = getGameWorld().spawn("flappybird", getWidth()/10, getHeight()/2);
		birdPointer = getGameWorld().spawn("bird_pointer", flappyBird.getCenter().getX()+20, flappyBird.getCenter().getY()+flappyBird.getComponent(PhysicsComponent.class).getVelocityY());
		getGameWorld().spawn("ground", 0 , getHeight()-50);
		getGameWorld().spawn("ground", getWidth()-200, getHeight()-50);
		getMasterTimer().runAtInterval(() -> {
			int x = FXGLMath.random(BOTTOM_PIPE_UPPER_LIMIT, BOTTOM_PIPE_LOWER_LIMIT);
				getGameWorld().spawn("top_pipe", getWidth(), x - PIPE_SPACING);
				getGameWorld().spawn("score_hitbox", getWidth(), x - PIPE_SPACING);
				getGameWorld().spawn("bottom_pipe", getWidth(), x);
				getGameWorld().spawn("ground", getWidth(), getHeight()-50);
			}, Duration.seconds(1));

		getMasterTimer().runAtInterval(() -> {
			pointer_rotate();
		}, Duration.millis(2));
	}
	
	protected void pointer_rotate() {
		birdPointer.setPosition(flappyBird.getCenter().getX()+20, 
				flappyBird.getCenter().getY()+flappyBird.getComponent(PhysicsComponent.class).getVelocityY()/10);
		
		if(birdPointer.getY() > flappyBird.getY()) {
			if(flappyBird.getComponent(PhysicsComponent.class).getBody().getAngle() > .125f)
			flappyBird.getComponent(PhysicsComponent.class)
			.setAngularVelocity(Math.abs(flappyBird.getY()-birdPointer.getY())/10);
		}else{
			if(flappyBird.getComponent(PhysicsComponent.class).getBody().getAngle() < .35f)
			flappyBird.getComponent(PhysicsComponent.class)
			.setAngularVelocity(-2*Math.abs(flappyBird.getY()-birdPointer.getY())/10);
		}
	}
	
	@Override
	protected void initInput() {
		Input input = getInput();
		input.addAction(new UserAction("flap") {
			@Override
			protected void onActionBegin() {
				input.mockKeyRelease(KeyCode.SPACE);
				if(flappyBird.hasComponent(PhysicsComponent.class))
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
		Text text = new Text();
		text.setFont(Font.font ("Arial Bold", 40));
		text.textProperty().bind(getGameState().intProperty("score").asString());
		text.setFill(Color.WHITE);
		text.setTranslateX(getWidth()/40);
		text.setTranslateY(getHeight()-5);
		getGameScene().addUINode(text);
	}

}
