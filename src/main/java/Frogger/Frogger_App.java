package Frogger;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.RenderLayer;
import com.almasb.fxgl.entity.view.EntityView;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.settings.GameSettings;
import com.almasb.fxgl.texture.Texture;
import javafx.scene.image.*;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

public class Frogger_App extends GameApplication{
	final int SCREEN_HEIGHT = 470;
	Entity frog;
	@Override
	protected void initSettings(GameSettings settings) {
		settings.setTitle("Frogger");
		settings.setVersion("0.2.0");
		settings.setHeight(SCREEN_HEIGHT);
	}
	
	@Override
	protected void initInput() {
		Input input = getInput();
		input.addAction(new UserAction("up") {
			@Override
			protected void onActionBegin() {
				input.mockKeyRelease(KeyCode.W);
				frog.setRotation(0);
				frog.translateY(-30);
				jumpAnimation();
			}
		}, KeyCode.W);
		input.addAction(new UserAction("down") {
			@Override
			protected void onActionBegin() {
				input.mockKeyRelease(KeyCode.S);
				frog.setRotation(180);
				frog.translateY(30);
				jumpAnimation();
			}
		}, KeyCode.S);
		input.addAction(new UserAction("left") {
			@Override
			protected void onActionBegin() {
				input.mockKeyRelease(KeyCode.A);
				frog.setRotation(-90);
				frog.translateX(-30);
				jumpAnimation();
			}
		}, KeyCode.A);
		input.addAction(new UserAction("right") {
			@Override
			protected void onActionBegin() {
				input.mockKeyRelease(KeyCode.D);
				frog.setRotation(90);
				frog.translateX(30);
				jumpAnimation();
			}
		}, KeyCode.D);
	}
	
	protected void jumpAnimation() {
		frog.setViewFromTexture("Frogger/frog_jump.png");
		getMasterTimer().runOnceAfter(() -> {
			frog.setViewFromTexture("Frogger/frog_idle.png");
		}, Duration.millis(200));
	}
	
	@Override
	protected void initGame() {
		getGameWorld().addEntityFactory(new Frogger_Factory());
		frog = getGameWorld().spawn("frog", getWidth()/2, getHeight()/2);
	}
	
	protected void scale(Entity toScale, double scaleFactor) {
		toScale.getView().setScaleX(scaleFactor);
		toScale.getView().setScaleY(scaleFactor);
	}
	
	@Override
	protected void initUI() {
		getGameScene().setBackgroundRepeat("Frogger/background.jpg");
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
