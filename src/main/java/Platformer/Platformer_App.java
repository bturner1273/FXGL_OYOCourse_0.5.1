package Platformer;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.settings.GameSettings;

public class Platformer_App extends GameApplication {

	@Override
	protected void initSettings(GameSettings settings) {
		settings.setTitle("Platformer");
		settings.setVersion("");
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
