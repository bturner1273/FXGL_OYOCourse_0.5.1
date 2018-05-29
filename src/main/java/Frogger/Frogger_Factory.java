package Frogger;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.RenderLayer;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.extra.entity.components.ExpireCleanComponent;
import com.almasb.fxgl.extra.entity.components.ProjectileComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.texture.Texture;

import javafx.geometry.Point2D;
import javafx.util.Duration;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class Frogger_Factory implements EntityFactory {
int right = 0, left = 0;
ExpireCleanComponent trash_bin = new ExpireCleanComponent(Duration.seconds(10));

	@Spawns("frog")
	public Entity newFrog(SpawnData data) {
		Texture splat = FXGL.getAssetLoader().loadTexture("Frogger/splatting_frog.png").toAnimatedTexture(3, Duration.millis(500));
		
		return Entities.builder()
				.type(Frogger_Types.FROG)
				.from(data)
                .viewFromTexture("Frogger/frog_idle.png")
				.with(new CollidableComponent(true))
				.bbox(new HitBox(new Point2D((180*.125)/2,(192*.125)/2), BoundingShape.box(180*.125,192*.125)))
				.renderLayer(RenderLayer.TOP)
				.build();
	}
	
	@Spawns("rotating_log")
	public Entity newRotatingLog(SpawnData data) {
		AnimationChannel rotate = new AnimationChannel("Frogger/rotating_log.png", 4, 128/4, 32, Duration.millis(500), 0, 3);
		AnimatedTexture rot_log = new AnimatedTexture(rotate);
		return Entities.builder()
				.from(data)
				.viewFromAnimatedTexture(rot_log)
				.build();
		}
	
	@Spawns("right_log")
	public Entity newRightLog(SpawnData data) {
		right++;
		int rand = FXGLMath.random(0, 1);
		HitBox[] hits = {new HitBox(new Point2D((84*.8)/2-120, (60*.8)/2-110), BoundingShape.box(84*.8, 60*.8)),new HitBox(new Point2D((174*.8)/2-145, (60*.8)/2-110), BoundingShape.box(174*.8, 60*.8))};

		String[] right_logs = {"Frogger/half_log.png", "Frogger/full_log.png"};
		return Entities.builder()
				.type(Frogger_Types.LOG)
				.from(data)
				.viewFromTexture(right_logs[rand])
				.with(new CollidableComponent(true))
				.with(new ProjectileComponent(new Point2D(1,0), FXGLMath.random(100, 200)))
				.with(new HalfWayTrackerComponent())
				.bbox(hits[rand])
				.with(trash_bin)
				.build();
	}
	
	@Spawns("left_log")
	public Entity newLeftLog(SpawnData data) {
		int rand = FXGLMath.random(0, 1);
		String[] left_logs = {"Frogger/half_log.png", "Frogger/full_log.png"};
		HitBox[] hits = {new HitBox(new Point2D((84*.8)/2-120, (60*.8)/2-110), BoundingShape.box(84*.8, 60*.8)),new HitBox(new Point2D((174*.8)/2-145, (60*.8)/2-110), BoundingShape.box(174*.8, 60*.8))};
		return Entities.builder()
				.type(Frogger_Types.LOG)
				.from(data)
				.viewFromTexture(left_logs[rand])
				.with(new CollidableComponent(true))
				.with(new ProjectileComponent(new Point2D(-1,0), FXGLMath.random(100, 200)))
				.with(new HalfWayTrackerComponent())
				.bbox(hits[rand])
				.with(trash_bin)	
				.build();
	}
	
	@Spawns("right_car")
	public Entity newRightCar(SpawnData data) {
		right++;
		String[] right_cars = {"Frogger/blue_car.png", "Frogger/green_car.png",
				"Frogger/orange_car.png", "Frogger/purple_car.png", "Frogger/red_car.png"};
		return Entities.builder()
				.type(right % 2 != 0 ? Frogger_Types.FRONT_CAR : Frogger_Types.BACK_CAR)
				.from(data)
				.bbox(new HitBox(new Point2D(-(168*.35)/2, -(110*.35)/2-10), BoundingShape.box(168*.45, 120*.45)))
				.viewFromTexture(right_cars[FXGLMath.random(0, 4)])
				.with(new ProjectileComponent(new Point2D(1,0), FXGLMath.random(300, 550)))
				.with(new CollidableComponent(true))
				.with(trash_bin)
				.renderLayer(RenderLayer.TOP)
				.build();
	}
	
	@Spawns("left_car")
	public Entity newLeftCar(SpawnData data) {
		left++;
		String[] left_cars = {"Frogger/blue_car.png", "Frogger/green_car.png",
				"Frogger/orange_car.png", "Frogger/purple_car.png", "Frogger/red_car.png"};
		return Entities.builder()
				.type(left % 2 != 0 ? Frogger_Types.FRONT_CAR : Frogger_Types.BACK_CAR)
				.from(data)
				.bbox(new HitBox(new Point2D(-(168*.35)/2,-(110*.35)/2-10), BoundingShape.box(168*.45, 120*.45)))
				.viewFromTexture(left_cars[FXGLMath.random(0, 4)])
				.with(new ProjectileComponent(new Point2D(-1,0), FXGLMath.random(300, 550)))
				.with(new CollidableComponent(true))
				.with(trash_bin)
				.renderLayer(RenderLayer.TOP)
				.build();
	}
	
	@Spawns("water")
	public Entity newWater(SpawnData data) {
		return Entities.builder()
				.at(0,23)
				.type(Frogger_Types.WATER)
				.viewFromNode(new Rectangle(FXGL.getAppWidth(), FXGL.getAppHeight()/3, Color.TRANSPARENT))
				.bbox(new HitBox(BoundingShape.box(FXGL.getAppWidth(), FXGL.getAppHeight()/6)))
				.with(new CollidableComponent(true))
				.build();
	}
	
	@Spawns("score_hitbox")
	public Entity newScoreHitBox(SpawnData data) {
		return Entities.builder()
				.at(0,-140)
				.type(Frogger_Types.POINT)				
				.with(new CollidableComponent(true))
				.viewFromNodeWithBBox(new Rectangle(FXGL.getAppWidth(), FXGL.getAppHeight()/8, Color.BLACK))
				.build();
	}
	
}
