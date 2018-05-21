package TicTacToe;

import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.RenderLayer;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.view.EntityView;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.texture.Texture;

import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.event.Event;

public class TicTacToe_Factory implements EntityFactory {
	@Spawns("tttBox")
	public Entity newtttBox(SpawnData data) {
		Rectangle rect = new Rectangle();
		rect.setWidth(200);
		rect.setHeight(200);
		rect.setStroke(Color.BLACK);
		rect.setFill(Color.LIGHTBLUE);
		rect.setStrokeWidth(10);
		rect.setOnMouseEntered(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				rect.setFill(Color.CORNFLOWERBLUE);
			}
		});
		rect.setOnMouseExited(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				rect.setFill(Color.LIGHTBLUE);

			}
		});
		return Entities.builder().viewFromNode(rect).bbox(new HitBox(BoundingShape.box(30, 30))).from(data)
				.renderLayer(RenderLayer.TOP).with(new CollidableComponent(true)).build();
	}
	@Spawns("Main Diagnol")
	public Entity newMainDiag(SpawnData data) {
		Line line = new Line(0,0,600,600);
		line.setStroke(Color.RED);
		line.setStrokeWidth(10);
		return Entities.builder()
				.viewFromNode(line)
				.renderLayer(RenderLayer.TOP)
				.build();
	}
	@Spawns("Row 1")
	public Entity newRowOne(SpawnData data) {
		Line line = new Line(0,100,600,100);
		line.setStroke(Color.RED);
		line.setStrokeWidth(10);
		return Entities.builder()
				.viewFromNode(line)
				.renderLayer(RenderLayer.TOP)
				.build();
	}
	@Spawns("Row 2")
	public Entity newRowTwo(SpawnData data) {
		Line line = new Line(0,300,600,300);
		line.setStroke(Color.RED);
		line.setStrokeWidth(10);
		return Entities.builder()
				.viewFromNode(line)
				.renderLayer(RenderLayer.TOP)
				.build();
	}
	@Spawns("Row 3")
	public Entity newRowThree(SpawnData data) {
		Line line = new Line(0,500,600,500);
		line.setStroke(Color.RED);
		line.setStrokeWidth(10);
		return Entities.builder()
				.viewFromNode(line)
				.renderLayer(RenderLayer.TOP)
				.build();
	}
	@Spawns("Col 1")
	public Entity newColOne(SpawnData data) {
		Line line = new Line(100,0,100,600);
		line.setStroke(Color.RED);
		line.setStrokeWidth(10);
		return Entities.builder()
				.viewFromNode(line)
				.renderLayer(RenderLayer.TOP)
				.build();
	}
	@Spawns("Col 2")
	public Entity newColTwo(SpawnData data) {
		Line line = new Line(300,0,300,600);
		line.setStroke(Color.RED);
		line.setStrokeWidth(10);
		return Entities.builder()
				.viewFromNode(line)
				.renderLayer(RenderLayer.TOP)
				.build();
	}
	@Spawns("Col 3")
	public Entity newColThree(SpawnData data) {
		Line line = new Line(500,0,500,600);
		line.setStroke(Color.RED);
		line.setStrokeWidth(10);
		return Entities.builder()
				.viewFromNode(line)
				.renderLayer(RenderLayer.TOP)
				.build();
	}
	@Spawns("Lesser Diagnol")
	public Entity newLesserDiag(SpawnData data) {
		Line line = new Line(0,0,600,600);
		line.setStroke(Color.RED);
		line.setStrokeWidth(10);
		line.setScaleX(-1);
		return Entities.builder()
				.viewFromNode(line)
				.renderLayer(RenderLayer.TOP)
				.build();
	}
	
	@Spawns("X")
	public Entity newX(SpawnData data) {
		return Entities.builder()
				.from(data)
				.renderLayer(RenderLayer.TOP)
				.viewFromTexture("TicTacToe/X.png")
				.build();
	}
	@Spawns("O")
	public Entity newO(SpawnData data) {
		return Entities.builder()
				.from(data)
				.renderLayer(RenderLayer.TOP)
				.viewFromTexture("TicTacToe/O.png")
				.build();
	}
}
