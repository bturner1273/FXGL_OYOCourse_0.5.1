package ShapeShootout;

import java.util.Map;

import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsWorld;
import com.almasb.fxgl.settings.GameSettings;

import javafx.scene.input.MouseButton;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javafx.scene.text.Font;
import javafx.scene.text.Text;



public class Shape_Shootout_App extends GameApplication {
	
	final int MAX_ENEMIES = 25, TIME = 60;
	Text score, time;
	Input input;
	boolean over = false;
	
	@Override
	protected void initSettings(GameSettings settings) {
		settings.setIntroEnabled(true);
		settings.setCloseConfirmation(true);
		settings.setTitle("Shape Shooter");
		settings.setVersion("1.0.0");
		settings.setApplicationMode(ApplicationMode.DEVELOPER);
	}
	
	@Override
	protected void initInput() {
		input = getInput();
		input.addAction(new UserAction("shoot"){
			@Override
			protected void onActionBegin() {
				if(!over) {
					getGameWorld().spawn("bullet", getInput().getMousePositionWorld());
				}	
			}
		}, MouseButton.PRIMARY);
	}
	
	@Override
	protected void initGame() {
		getGameWorld().addEntityFactory(new Shape_Shootout_Factory());
		getMasterTimer().runAtInterval(() ->{
			if(getGameState().getInt("enemyCount") < MAX_ENEMIES) {
				getGameWorld().spawn("enemy", FXGLMath.random(20, getGameScene().getWidth()), 
						FXGLMath.random(0,getGameScene().getHeight()/2));
				
				getGameState().increment("enemyCount", 1);
			}
		}, Duration.millis(550));
		getMasterTimer().runAtInterval(() -> {
			if(getGameState().getInt("time") > 0) {
				getGameState().increment("time", -1);
				over = getGameState().getInt("time") == 0;
			}else {
				over = getGameState().getInt("time") == 0;
				getMasterTimer().clear();
				endGame();
			}
		}, Duration.seconds(1));
	}
	
	protected void endGame() {
		getGameScene().removeUINode(time);
		getMasterTimer().runOnceAfter(() -> {
			score.setTranslateX(getWidth()/2);
			score.setTranslateY(getHeight()/2);
		}, Duration.millis(350));
		getMasterTimer().runOnceAfter(() -> {
			score.setFill(Color.RED);
			if(getGameState().getInt("score") >= 100) {
				FXGL.getNotificationService().pushNotification("Master Clicker");
			}
		}, Duration.millis(350));
	}
	
	@Override
	protected void initPhysics() {
		PhysicsWorld physicsWorld = getPhysicsWorld();
		physicsWorld.addCollisionHandler(new CollisionHandler(Shape_Shootout_Types.BULLET,
				Shape_Shootout_Types.ENEMY) {
			@Override
			protected void onCollision(Entity bullet, Entity enemy) {
				enemy.removeFromWorld();
				bullet.removeFromWorld();
				getGameState().increment("enemyCount", -1);
				getGameState().increment("score", 1);
			}
		});
	}
	
	@Override
	protected void initGameVars(Map<String, Object> vars){
		vars.put("enemyCount", 0);
		vars.put("score", 0);
		vars.put("time", TIME);
	}
	
	@Override
	protected void initUI() {
		getGameScene().setBackgroundColor(Color.LIGHTSKYBLUE);
		score = new Text();
		score.setFont(Font.font ("Arial Bold", 70));
		score.textProperty().bind(getGameState().intProperty("score").asString());
		score.setFill(Color.WHITE);
		score.setTranslateX(10);
		score.setTranslateY(getHeight()-5);
		
		time = new Text();
		time.setFont(Font.font ("Arial Bold", 70));
		time.textProperty().bind(getGameState().intProperty("time").asString());
		time.setFill(Color.WHITE);
		time.setTranslateX(getWidth() - 115);
		time.setTranslateY(60);
	
		getGameScene().addUINodes(score, time);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
