package Frogger;

import java.util.HashMap;
import java.util.Map;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.RenderLayer;
import com.almasb.fxgl.entity.components.CollidableComponent;
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

import javafx.geometry.Point2D;
import javafx.scene.image.*;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Frogger_App extends GameApplication{
	final int SCREEN_HEIGHT = 470, START_X = 0, START_Y = SCREEN_HEIGHT-100, ANIM_DURATION = 500,
			MOVE_SPEED = 30, RIGHT_LOG_SPAWN_HEIGHT = 15, RIGHT_LOG_X_OFFSET = -150,
			LEFT_LOG_SPAWN_HEIGHT = 125, LEFT_LOG_X_OFFSET = 150;
	Entity frog, r_log, l_log, m_log;
	boolean start = true;
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
			protected void onCollisionBegin(Entity back_car, Entity front_car) {
				back_car.getComponent(ProjectileComponent.class).setSpeed(front_car.getComponent(ProjectileComponent.class).getSpeed());
			}
			@Override
			protected void onCollision(Entity back_car, Entity front_car) {
				back_car.getComponent(ProjectileComponent.class).setSpeed(front_car.getComponent(ProjectileComponent.class).getSpeed());
			}
			@Override
			protected void onCollisionEnd(Entity back_car, Entity front_car) {
				back_car.getComponent(ProjectileComponent.class).setSpeed(front_car.getComponent(ProjectileComponent.class).getSpeed());
			}
		});
		physicsWorld.addCollisionHandler(new CollisionHandler(Frogger_Types.FROG, Frogger_Types.FRONT_CAR) {
			@Override
			protected void onCollision(Entity frog, Entity car) {
				splatAnimation();
			}
		});
		physicsWorld.addCollisionHandler(new CollisionHandler(Frogger_Types.FROG, Frogger_Types.BACK_CAR) {
			@Override
			protected void onCollision(Entity frog, Entity car) {
				splatAnimation();
			}
		});
		physicsWorld.addCollisionHandler(new CollisionHandler(Frogger_Types.FROG, Frogger_Types.LOG) {
			@Override
			protected void onCollision(Entity frog, Entity log) {
				frog.setX(log.getCenter().getX());
			}
		});
		physicsWorld.addCollisionHandler(new CollisionHandler(Frogger_Types.FROG, Frogger_Types.WATER) {
			@Override
			protected void onCollision(Entity frog, Entity water) {
				if(!(frog.isColliding(r_log) || frog.isColliding(m_log) || frog.isColliding(l_log))) {
					sinkAnimation();
				}
			}
		});
		physicsWorld.addCollisionHandler(new CollisionHandler(Frogger_Types.FROG, Frogger_Types.POINT) {
			@Override
			protected void onCollision(Entity frog, Entity point) {
				getGameState().increment("score", 1);
				resetFrog();
			}
		});
	}
	
	protected void resetFrog() {
		frog.removeFromWorld();
		frog = getGameWorld().spawn("frog", START_X, START_Y);
		scaleFrog();
	}
	
	@Override
	protected void initGameVars(Map<String, Object> vars) {
		vars.put("score", 0);
	}
	
	@Override
	protected void initInput() {
		Input input = getInput();
		input.addAction(new UserAction("up") {
			@Override
			protected void onActionBegin() {
				input.mockKeyRelease(KeyCode.W);
				if(frog != null) {
					frog.setRotation(0);
					frog.translateY(-MOVE_SPEED);
					jumpAnimation();
				}
			}
		}, KeyCode.W);
		input.addAction(new UserAction("down") {
			@Override
			protected void onActionBegin() {
				input.mockKeyRelease(KeyCode.S);
				if(frog != null) {
					frog.setRotation(180);
					frog.translateY(MOVE_SPEED);
					jumpAnimation();
				}
			}
		}, KeyCode.S);
		input.addAction(new UserAction("left") {
			@Override
			protected void onActionBegin() {
				input.mockKeyRelease(KeyCode.A);
				if(frog != null) {
					frog.setRotation(-90);
					frog.translateX(-MOVE_SPEED);
					jumpAnimation();
				}
			}
		}, KeyCode.A);
		input.addAction(new UserAction("right") {
			@Override
			protected void onActionBegin() {
				input.mockKeyRelease(KeyCode.D);
				if(frog != null) {
					frog.setRotation(90);
					frog.translateX(MOVE_SPEED);
					jumpAnimation();
				}
			}
		}, KeyCode.D);		
		input.addAction(new UserAction("testd") {
			@Override
			protected void onActionBegin() {
				sinkAnimation();
			}
		}, KeyCode.SPACE);		
	}
	
	protected void jumpAnimation() {
		if(frog != null) {
			frog.setViewFromTexture("Frogger/frog_jump.png");
			frog.setScaleX(.18);
			frog.setScaleY(.18);
		}
		getMasterTimer().runOnceAfter(() -> {
			if(frog != null) {
				frog.setViewFromTexture("Frogger/frog_idle.png");
				scaleFrog();
			}
		}, Duration.millis(250));
	}
	
	protected void sinkAnimation() {
		AnimationChannel sink = new AnimationChannel("Frogger/sinking_frog.png", 5, 320/5, 64, Duration.millis(ANIM_DURATION), 0, 4);
		AnimatedTexture sinking = new AnimatedTexture(sink);
		scale(sinking, 5);
		frog.getViewComponent().setAnimatedTexture(sinking, false, true);
		frog.setPosition(new Point2D(frog.getX()+55, frog.getY()+55));
		frog = null;
		getMasterTimer().runOnceAfter(() -> {
			frog = getGameWorld().spawn("frog", START_X, START_Y);
			scaleFrog();
		}, Duration.millis(ANIM_DURATION));
	}

	protected void splatAnimation() {
		AnimatedTexture splat = getAssetLoader().loadTexture("Frogger/splatting_frog.png").toAnimatedTexture(3, Duration.millis(ANIM_DURATION));
		scale(splat,5.5);
		frog.setView(splat);
		frog.getViewComponent().setAnimatedTexture(splat, false, true);
		frog.setPosition(new Point2D(frog.getX()+55, frog.getY()+55));
		frog = null;
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
		getGameWorld().spawn("water");
		scale(.8, r_log, l_log, m_log);
		scale(1.0, getGameWorld().spawn("rotating_log",getWidth()-115, getHeight()-25));
		
		getGameWorld().spawn("score_hitbox");
		getMasterTimer().runAtInterval(() -> {
			Entity[] cars = {getGameWorld().spawn("right_car",-120, getHeight()/2+85),
			getGameWorld().spawn("left_car",getWidth() , getHeight()/2-30)};
			for(int i = 0; i <= 1; i++) {
				cars[i].setScaleX(.35);
				cars[i].setScaleY(.35);
			}
			//this statement is the same as r_log.getX() >= getWidth()/2
			//I just wanted to show you how to write a component because
			//for complicated games you will certainly need to write your own
			if(r_log.getComponent(HalfWayTrackerComponent.class).pastHalfway()) { 
				spawnLogs();
				scale(.8, r_log, l_log, m_log);	
			}
		}, Duration.seconds(.9));
	}
	
	protected void spawnLogs() {
		r_log = getGameWorld().spawn("right_log",RIGHT_LOG_X_OFFSET,RIGHT_LOG_SPAWN_HEIGHT);
		l_log = getGameWorld().spawn("left_log", getWidth()+LEFT_LOG_X_OFFSET, LEFT_LOG_SPAWN_HEIGHT);
		if(start ||(m_log.getX() > getWidth() || m_log.getRightX() < 0)) {
			start = false;
			if(FXGLMath.random(0, 1) % 2 == 0) {
				m_log = getGameWorld().spawn("right_log", RIGHT_LOG_X_OFFSET, (LEFT_LOG_SPAWN_HEIGHT+RIGHT_LOG_SPAWN_HEIGHT)/2);
			}else m_log = getGameWorld().spawn("left_log", getWidth()+LEFT_LOG_X_OFFSET, (LEFT_LOG_SPAWN_HEIGHT+RIGHT_LOG_SPAWN_HEIGHT)/2);
		}
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
		Text text = new Text();
		text.setFont(Font.font ("Arial Bold", 35));
		text.textProperty().bind(getGameState().intProperty("score").asString());
		text.setFill(Color.WHITE);
		text.setTranslateX(getWidth()-50);
		text.setTranslateY(getHeight()-5);
		getGameScene().addUINode(text);
		Text label = new Text();
		label.textProperty().set("Score:");
		label.setFont(Font.font ("Arial Bold", 20));
		label.setFill(Color.WHITE);
		label.setTranslateX(getWidth()-125);
		label.setTranslateY(getHeight()-15);
		getGameScene().addUINode(label);
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
