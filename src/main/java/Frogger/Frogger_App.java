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
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.texture.Texture;
import javafx.scene.image.*;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

public class Frogger_App extends GameApplication{
	final int SCREEN_HEIGHT = 470, START_X = 100, START_Y = SCREEN_HEIGHT-50, ANIM_DURATION = 500;
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
		

//		TESTING ANIMATIONS
//		
//		input.addAction(new UserAction("test") {
//			@Override
//			protected void onActionBegin() {
//				input.mockKeyRelease(KeyCode.SPACE);
//				splatAnimation();
//			}
//		}, KeyCode.SPACE);
		
		
	}
	
	protected void jumpAnimation() {
		frog.setViewFromTexture("Frogger/frog_jump.png");
		getMasterTimer().runOnceAfter(() -> {
			frog.setViewFromTexture("Frogger/frog_idle.png");
		}, Duration.millis(200));
	}
	
	protected void sinkAnimation() {
		AnimationChannel sink = new AnimationChannel("Frogger/sinking_frog.png", 5, 160/5, 32, Duration.millis(ANIM_DURATION), 0, 4);
		AnimatedTexture sinking = new AnimatedTexture(sink);
		scale(sinking, 1.5);
		frog.getViewComponent().setAnimatedTexture(sinking, false, true);
		getMasterTimer().runOnceAfter(() -> {
			frog = getGameWorld().spawn("frog", START_X, START_Y);
		}, Duration.millis(ANIM_DURATION));
	}

	protected void splatAnimation() {
		AnimationChannel splat = new AnimationChannel("Frogger/splatting_frog.png", 3, 96/3, 32, Duration.millis(ANIM_DURATION), 0, 2);
		AnimatedTexture splatting = new AnimatedTexture(splat);
		scale(splatting, 1.5);
		frog.getViewComponent().setAnimatedTexture(splatting, false, true);
		getMasterTimer().runOnceAfter(() -> {
			frog = getGameWorld().spawn("frog", START_X, START_Y);
		}, Duration.millis(ANIM_DURATION));
	}
	
	@Override
	protected void initGame() {
		getGameWorld().addEntityFactory(new Frogger_Factory());
		frog = getGameWorld().spawn("frog", START_X, START_Y);
	}
	
	protected void scale(AnimatedTexture toScale, double scaleFactor) {
		toScale.setScaleX(scaleFactor);
		toScale.setScaleY(scaleFactor);
	}
	
	@Override
	protected void initUI() {
		getGameScene().setBackgroundRepeat("Frogger/background.jpg");
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
