package Frogger;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.RenderLayer;
import com.almasb.fxgl.entity.view.EntityView;
import com.almasb.fxgl.extra.entity.components.OffscreenCleanComponent;
import com.almasb.fxgl.extra.entity.components.ProjectileComponent;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsWorld;
import com.almasb.fxgl.settings.GameSettings;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.texture.Texture;
import javafx.scene.image.*;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

public class Frogger_App extends GameApplication{
	final int SCREEN_HEIGHT = 470, START_X = 0, START_Y = SCREEN_HEIGHT-100, ANIM_DURATION = 500,
			MOVE_SPEED = 30, RIGHT_LOG_SPAWN_HEIGHT = 11, RIGHT_LOG_X_OFFSET = -150,
			LEFT_LOG_SPAWN_HEIGHT = 130, LEFT_LOG_X_OFFSET = 150;
	Entity frog, r_log, l_log;
	@Override
	protected void initSettings(GameSettings settings) {
		settings.setTitle("Frogger");
		settings.setVersion("0.2.0");
		settings.setHeight(SCREEN_HEIGHT);
	}
	
	@Override
	protected void initPhysics() {
		PhysicsWorld physicsWorld = getPhysicsWorld();
		physicsWorld.addCollisionHandler(new CollisionHandler(Frogger_Types.BACK_CAR, Frogger_Types.FRONT_CAR) {
			@Override
			protected void onCollision(Entity back_car, Entity front_car) {
				back_car.getComponent(ProjectileComponent.class).setSpeed(front_car.getComponent(ProjectileComponent.class).getSpeed());
			}
			@Override
			protected void onCollisionEnd(Entity back_car, Entity front_car) {
				back_car.getComponent(ProjectileComponent.class).setSpeed(front_car.getComponent(ProjectileComponent.class).getSpeed());
			}
		});
	}
	
	@Override
	protected void initInput() {
		Input input = getInput();
		input.addAction(new UserAction("up") {
			@Override
			protected void onActionBegin() {
				input.mockKeyRelease(KeyCode.W);
				frog.setRotation(0);
				frog.translateY(-MOVE_SPEED);
				jumpAnimation();
			}
		}, KeyCode.W);
		input.addAction(new UserAction("down") {
			@Override
			protected void onActionBegin() {
				input.mockKeyRelease(KeyCode.S);
				frog.setRotation(180);
				frog.translateY(MOVE_SPEED);
				jumpAnimation();
			}
		}, KeyCode.S);
		input.addAction(new UserAction("left") {
			@Override
			protected void onActionBegin() {
				input.mockKeyRelease(KeyCode.A);
				frog.setRotation(-90);
				frog.translateX(-MOVE_SPEED);
				jumpAnimation();
			}
		}, KeyCode.A);
		input.addAction(new UserAction("right") {
			@Override
			protected void onActionBegin() {
				input.mockKeyRelease(KeyCode.D);
				frog.setRotation(90);
				frog.translateX(MOVE_SPEED);
				jumpAnimation();
			}
		}, KeyCode.D);		
	}
	
	protected void jumpAnimation() {
		frog.setViewFromTexture("Frogger/frog_jump.png");
		frog.setScaleX(.18);
		frog.setScaleY(.18);
		getMasterTimer().runOnceAfter(() -> {
			frog.setViewFromTexture("Frogger/frog_idle.png");
			scaleFrog();
		}, Duration.millis(250));
	}
	
	protected void sinkAnimation() {
		AnimationChannel sink = new AnimationChannel("Frogger/sinking_frog.png", 5, 160/5, 32, Duration.millis(ANIM_DURATION), 0, 4);
		AnimatedTexture sinking = new AnimatedTexture(sink);
		scale(sinking, 1.5);
		frog.getViewComponent().setAnimatedTexture(sinking, false, true);
		getMasterTimer().runOnceAfter(() -> {
			frog = getGameWorld().spawn("frog", START_X, START_Y);
			scaleFrog();
		}, Duration.millis(ANIM_DURATION));
	}

	protected void splatAnimation() {
		AnimationChannel splat = new AnimationChannel("Frogger/splatting_frog.png", 3, 96/3, 32, Duration.millis(ANIM_DURATION), 0, 2);
		AnimatedTexture splatting = new AnimatedTexture(splat);
		scale(splatting, 1.5);
		frog.getViewComponent().setAnimatedTexture(splatting, false, true);
		getMasterTimer().runOnceAfter(() -> {
			frog = getGameWorld().spawn("frog", START_X, START_Y);
			scaleFrog();
		}, Duration.millis(ANIM_DURATION));
	}
	
	protected void scaleFrog() {
		frog.setScaleX(.125);
		frog.setScaleY(.125);
	}
	
	@Override
	protected void initGame() {
		getGameWorld().addEntityFactory(new Frogger_Factory());
		frog = getGameWorld().spawn("frog", START_X, START_Y);
		scaleFrog();
		spawnLogs();
		scale(.65,r_log,l_log);
		getGameWorld().spawn("score_hitbox");
		getMasterTimer().runAtInterval(() -> {
			Entity[] cars = {getGameWorld().spawn("right_car",getWidth(), getHeight()/2-20),
			getGameWorld().spawn("left_car", -75, getHeight()/2+75)};
			for(int i = 0; i <= 1; i++) {
				cars[i].setScaleX(.25);
				cars[i].setScaleY(.25);
			}
			//this statement is the same as r_log.getX() >= getWidth()/2
			//I just wanted to show you how to write a component because
			//for complicated games you will certainly need to write your own
			if(r_log.getComponent(HalfWayTrackerComponent.class).pastHalfway()) { 
				spawnLogs();
				scale(.65, r_log, l_log);	
			}
		}, Duration.seconds(.7));
	}
	
	protected void spawnLogs() {
		r_log = getGameWorld().spawn("right_log",RIGHT_LOG_X_OFFSET,RIGHT_LOG_SPAWN_HEIGHT);
		l_log = getGameWorld().spawn("left_log", getWidth()+LEFT_LOG_X_OFFSET, LEFT_LOG_SPAWN_HEIGHT);
	}
	
	protected void scale(double scaleFactor, Entity... toScale) {
		for(int i = 0; i < toScale.length; i++) {
			toScale[i].setScaleX(scaleFactor);
			toScale[i].setScaleY(scaleFactor);
		}
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
