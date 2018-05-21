package ShapeShootoutApp;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.RenderLayer;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.components.RotationComponent;
import com.almasb.fxgl.extra.entity.components.ProjectileComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;

import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public class Shape_Shootout_Factory implements EntityFactory {
	
	public Color getRandomColor() {
		int[] randColors = {FXGLMath.random(0, 255), FXGLMath.random(0, 255), FXGLMath.random(0, 255)};
		return Color.rgb(randColors[0], randColors[1], randColors[2]);
	}
	
	@Spawns("bullet")
	public Entity newBullet(SpawnData data) {
		int width = 7, height = 35;
		Rectangle pic = new Rectangle(width,height, getRandomColor());
		return Entities.builder()
				.type(Shape_Shootout_Types.BULLET)
				.from(data)
				.with(new ProjectileComponent(new Point2D(0,-1),500))
				.with(new CollidableComponent(true))
				.bbox(new HitBox(BoundingShape.box(width, height)))
				.viewFromNode(pic)
				.renderLayer(RenderLayer.TOP)
				.rotate(0)
				.build();
	}
	
	@Spawns("enemy")
	public Entity newEnemy(SpawnData data) {
		int randLength = FXGLMath.random(20, 50);
		Shape shape;
		HitBox hit;
		if(FXGLMath.random(1, 2) % 2 == 0) {
			shape = new Rectangle(randLength, randLength, getRandomColor());
			hit = new HitBox(BoundingShape.box(randLength, randLength));
		}else {
			shape = new Circle(randLength, getRandomColor());
			hit = new HitBox(BoundingShape.circle(randLength));
		}
		return Entities.builder()
				.from(data)
				.type(Shape_Shootout_Types.ENEMY)
				.with(new CollidableComponent(true))
				.bbox(hit)
				.viewFromNode(shape)
				.renderLayer(RenderLayer.TOP)
				.build();
	}
	

}
