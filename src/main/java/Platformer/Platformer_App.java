package Platformer;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.settings.GameSettings;

public class Platformer_App extends GameApplication {

	@Override
	protected void initSettings(GameSettings settings) {
		settings.setTitle("Platformer");
		settings.setVersion("");
		settings.setWidth(20*70);
		settings.setHeight(15*70);
	}
	
	@Override
	protected void initGame() {
		getGameWorld().addEntityFactory(new Platformer_Factory());
		getGameWorld().setLevelFromMap("platformer_map_1.tmx");
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
