package TicTacToe;

import com.almasb.fxgl.app.GameApplication;
import java.util.ArrayList;
import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.view.EntityView;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.settings.GameSettings;
import javafx.util.Duration;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Line;

public class TicTacToe_App extends GameApplication{
	int count = 0;
	int spot1 = 0, spot2 = 0, spot3 = 0, spot4 = 0, spot5 = 0, spot6 = 0, spot7 = 0, spot8 = 0, spot9 = 0;
	@Override
	protected void initSettings(GameSettings settings) {
		// TODO Auto-generated method stub
		settings.setIntroEnabled(true);
		settings.setMenuEnabled(false);
		settings.setProfilingEnabled(false);
		settings.setCloseConfirmation(false);
		settings.setManualResizeEnabled(true);
		settings.setTitle("FXGL TicTacToe");
		settings.setVersion("");
		settings.setWidth(600);
		settings.setHeight(600);
	}

	@Override
	protected void initGame() {
		getGameWorld().addEntityFactory(new TicTacToe_Factory());
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				getGameWorld().spawn("tttBox", i * 200, j * 200);
			}
		}
	}

	@Override
	protected void initInput() {
		getInput().addAction(new UserAction("Place Character") {
			@Override
			protected void onActionBegin() {
				if (getInput().getMouseXWorld() >= 0 && getInput().getMouseXWorld() < 200) {
					if (getInput().getMouseYWorld() >= 0 && getInput().getMouseYWorld() < 200) {
						if (spot1 == 0) {
							count++;
							if (count % 2 == 0) {
								spot1 = 1; // this is for a spot array later for the check win 											function there will be
											// 0's if the spot is not filled in with an x or o 												and a 1 for o and a 2 for x
								getGameWorld().spawn("O", 25, 25);
							} else {
								spot1 = 2;
								getGameWorld().spawn("X", 25, 25);
							}
						}
					}
				}
				if (getInput().getMouseXWorld() >= 200 && getInput().getMouseXWorld() < 400) {
					if (getInput().getMouseYWorld() >= 0 && getInput().getMouseYWorld() < 200) {
						if (spot2 == 0) {
							count++;
							if (count % 2 == 0) {
								spot2 = 1;
								getGameWorld().spawn("O", 225, 25);
							} else {
								spot2 = 2;
								getGameWorld().spawn("X", 225, 25);
							}
						}
					}
				}
				if (getInput().getMouseXWorld() >= 400 && getInput().getMouseXWorld() < 600) {
					if (getInput().getMouseYWorld() >= 0 && getInput().getMouseYWorld() < 200) {
						if (spot3 == 0) {
							count++;
							if (count % 2 == 0) {
								spot3 = 1;
								getGameWorld().spawn("O", 425, 25);
							} else {
								spot3 = 2;
								getGameWorld().spawn("X", 425, 25);
							}
						}
					}
				}
				if (getInput().getMouseXWorld() >= 0 && getInput().getMouseXWorld() < 200) {
					if (getInput().getMouseYWorld() >= 200 && getInput().getMouseYWorld() < 400) {
						if (spot4 == 0) {
							count++;
							if (count % 2 == 0) {
								spot4 = 1;
								getGameWorld().spawn("O", 25, 225);
							} else {
								spot4 = 2;
								getGameWorld().spawn("X", 25, 225);
							}
						}
					}
				}
				if (getInput().getMouseXWorld() >= 200 && getInput().getMouseXWorld() < 400) {
					if (getInput().getMouseYWorld() >= 200 && getInput().getMouseYWorld() < 400) {
						if (spot5 == 0) {
							count++;
							if (count % 2 == 0) {
								spot5 = 1;
								getGameWorld().spawn("O", 225, 225);
							} else {
								spot5 = 2;
								getGameWorld().spawn("X", 225, 225);
							}
						}
					}
				}
				if (getInput().getMouseXWorld() >= 400 && getInput().getMouseXWorld() < 600) {
					if (getInput().getMouseYWorld() >= 200 && getInput().getMouseYWorld() < 400) {
						if (spot6 == 0) {
							count++;
							if (count % 2 == 0) {
								spot6 = 1;
								getGameWorld().spawn("O", 425, 225);
							} else {
								spot6 = 2;
								getGameWorld().spawn("X", 425, 225);
							}
						}
					}
				}
				if (getInput().getMouseXWorld() >= 0 && getInput().getMouseXWorld() < 200) {
					if (getInput().getMouseYWorld() >= 400 && getInput().getMouseYWorld() < 600) {
						if (spot7 == 0) {
							count++;
							if (count % 2 == 0) {
								spot7 = 1;
								getGameWorld().spawn("O", 25, 425);
							} else {
								spot7 = 2;
								getGameWorld().spawn("X", 25, 425);
							}
						}
					}
				}
				if (getInput().getMouseXWorld() >= 200 && getInput().getMouseXWorld() < 400) {
					if (getInput().getMouseYWorld() >= 400 && getInput().getMouseYWorld() < 600) {
						if (spot8 == 0) {
							count++;
							if (count % 2 == 0) {
								spot8 = 1;
								getGameWorld().spawn("O", 225, 425);
							} else {
								spot8 = 2;
								getGameWorld().spawn("X", 225, 425);
							}
						}
					}
				}
				if (getInput().getMouseXWorld() >= 400 && getInput().getMouseXWorld() < 600) {
					if (getInput().getMouseYWorld() >= 400 && getInput().getMouseYWorld() < 600) {
						if (spot9 == 0) {
							count++;
							if (count % 2 == 0) {
								spot9 = 1;
								getGameWorld().spawn("O", 425, 425);
							} else {
								spot9 = 2;
								getGameWorld().spawn("X", 425, 425);
							}
						}
					}
				}
				checkWin(); //to be written based of of the spots array
			}
		}, MouseButton.PRIMARY);

	}
	public void checkWin() {
		boolean wino = false, winx = false;
		if(spot1 == 1 && spot2 == 1 && spot3 == 1) {
			getGameWorld().spawn("Row 1");
			wino = true;
		}
		if(spot4 == 1 && spot5 == 1 && spot6 == 1) {
			getGameWorld().spawn("Row 2");
			wino = true;
		}
		if(spot7 == 1 && spot8 == 1 && spot9 == 1) {
			getGameWorld().spawn("Row 3");
			wino = true;
		}
		if(spot1 == 1 && spot4 == 1 && spot7 == 1) {
			getGameWorld().spawn("Col 1");
			wino = true;
		}
		if(spot2 == 1 && spot5 == 1 && spot8 == 1) {
			getGameWorld().spawn("Col 2");
			wino = true;
		}
		if(spot3 == 1 && spot6 == 1 && spot9 == 1) {
			getGameWorld().spawn("Col 3");
			wino = true;
		}
		if(spot1 == 1 && spot5 == 1 && spot9 == 1) {
			getGameWorld().spawn("Main Diagnol");
			wino = true;
		}
		if(spot3 == 1 && spot5 == 1 && spot7 == 1) {
			getGameWorld().spawn("Lesser Diagnol");
			wino = true;
		}
		if(spot1 == 2 && spot2 == 2 && spot3 == 2) {
			getGameWorld().spawn("Row 1");
			winx = true;
		}
		if(spot4 == 2 && spot5 == 2 && spot6 == 2) {
			getGameWorld().spawn("Row 2");
			winx = true;
		}
		if(spot7 == 2 && spot8 == 2 && spot9 == 2) {
			getGameWorld().spawn("Row 3"); 
			winx = true;
		}
		if(spot1 == 2 && spot4 == 2 && spot7 == 2) {
			getGameWorld().spawn("Col 1");
			winx = true;
		}
	    if(spot2 == 2 && spot5 == 2 && spot8 == 2) {
	    		getGameWorld().spawn("Col 2");
	    		winx = true;
	    }
		if(spot3 == 2 && spot6 == 2 && spot9 == 2) {
			getGameWorld().spawn("Col 3");
			winx = true;
		}
		if(spot1 == 2 && spot5 == 2 && spot9 == 2) {
			getGameWorld().spawn("Main Diagnol");
			winx = true;
		}
		if(spot3 == 2 && spot5 == 2 && spot7 == 2) {
			getGameWorld().spawn("Lesser Diagnol");
			winx = true;
		}
		int zeroCount = 0;
		int[] spots = {spot1,spot2,spot3,spot4,spot5,spot6,spot7,spot8,spot9};
		for(int i = 0; i < spots.length; i++) {
			if(spots[i] == 0) {
				zeroCount++;
			}
		}
		if(zeroCount == 0 && !winx && !wino) {
			FXGL.getNotificationService().pushNotification("You tied!");
			restart();
		}
		if(wino) {
			FXGL.getNotificationService().pushNotification("Player O wins!");
			restart();
		}
		if(winx) {
			FXGL.getNotificationService().pushNotification("Player X wins!");
			restart();
		}
	}
	public void restart() {
		getMasterTimer().runOnceAfter(() -> {
			spot1 = 0;
			spot2 = 0;
			spot3 = 0;
			spot4 = 0;
			spot5 = 0;
			spot6 = 0;
			spot7 = 0;
			spot8 = 0;
			spot9 = 0;
			getGameWorld().removeEntities();
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					getGameWorld().spawn("tttBox", i * 200, j * 200);
				}
			}
			count = 0;
		}, Duration.seconds(3));
	}
	public static void main(String[] args) {
		launch(args);
	}
}
