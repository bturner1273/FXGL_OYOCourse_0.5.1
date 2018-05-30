package Platformer;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.settings.GameSettings;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;

import javafx.scene.input.*;
import javafx.util.Duration;
public class Platformer_App extends GameApplication {
	Entity player;
	final int PLAYER_SPEED = 400;

	@Override
	protected void initSettings(GameSettings settings) {
		settings.setTitle("Platformer");
		settings.setVersion("");
		settings.setWidth(10*70);
		settings.setHeight(7*70);
	}
	
	@Override
	protected void initInput() {
		AnimatedTexture running = new AnimatedTexture(
				new AnimationChannel("Platformer/run_sheet.png", 5, 1535/5, 445, Duration.millis(500), 0, 4));
		AnimatedTexture idle = new AnimatedTexture(
				new AnimationChannel("Platformer/stand_sheet.png", 2, 606/2, 431, Duration.millis(300), 0, 1));
		
		
		Input input = getInput();
		input.addAction(new UserAction("run_right") {
			@Override
			protected void onActionBegin() {
				running.setScaleX(.25);
				running.setScaleY(.25);
				player.getViewComponent().setAnimatedTexture(running, true, false);
			}
			@Override
			protected void onAction() {
				player.getComponent(PhysicsComponent.class).setVelocityX(PLAYER_SPEED);
			}
			@Override
			protected void onActionEnd() {
				idle.setScaleX(.25);
				idle.setScaleY(.25);
				player.getViewComponent().setAnimatedTexture(idle, true, false);
			}
		}, KeyCode.RIGHT);
		
		input.addAction(new UserAction("run_left") {
			@Override
			protected void onActionBegin() {
				running.setScaleX(-.25);
				running.setScaleY(.25);
				player.getViewComponent().setAnimatedTexture(running, true, false);
			}
			@Override
			protected void onAction() {
				player.getComponent(PhysicsComponent.class).setVelocityX(-PLAYER_SPEED);
			}
			@Override
			protected void onActionEnd() {
				idle.setScaleX(-.25);
				player.getViewComponent().setAnimatedTexture(idle, true, false);
			}
		}, KeyCode.LEFT);
		
		input.addAction(new UserAction("jump") {
			@Override
			protected void onActionBegin() {
				
			}
			@Override
			protected void onAction() {
				player.getComponent(PhysicsComponent.class).setVelocityY(-500);
			}
			@Override
			protected void onActionEnd() {
				
			}
		}, KeyCode.UP);
	}
	
	@Override
	protected void initGame() {
		getGameWorld().addEntityFactory(new Platformer_Factory());
		getGameWorld().setLevelFromMap("platformer_map_1.tmx");
		player = getGameWorld().spawn("player", 100, 100);
		getGameScene().getViewport().bindToEntity(player, getWidth()/2-150, getHeight()/2-200);
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
