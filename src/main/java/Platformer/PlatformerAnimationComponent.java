package Platformer;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.component.Required;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.texture.Texture;

import javafx.util.Duration;

@Required(PhysicsComponent.class)
public class PlatformerAnimationComponent extends Component {
	double scalePos, scaleNeg;
	Texture jump_up;
	Texture jump_down;
	Texture idle;
	Texture run;
	double min_run_speed;
	boolean right = true;
	
	@Override
	public void onUpdate(double tpf) {		
		//set left or right
		if(entity.getComponent(PhysicsComponent.class).getVelocityX() < -min_run_speed) {
			setScaleAll(scaleNeg);
			right = false;
		}else if(entity.getComponent(PhysicsComponent.class).getVelocityX() > min_run_speed) {
			setScaleAll(scalePos);
			right = true;
		}
		
		//jump
		if(entity.getComponent(PhysicsComponent.class).getVelocityY() < 0) {
			entity.setView(jump_up);
		}
		else if(entity.getComponent(PhysicsComponent.class).getVelocityY() > 0) {
			entity.setView(jump_down);
		}else {
			
			//THIS IS NOT IN USE CURRENTLY BECAUSE OF AN FXGL BUG
			//run
			if(entity.getComponent(PhysicsComponent.class).getVelocityX() < -min_run_speed ||
					entity.getComponent(PhysicsComponent.class).getVelocityX() > min_run_speed &&
					(entity.getComponent(PhysicsComponent.class).getVelocityY() < 10 && 
					entity.getComponent(PhysicsComponent.class).getVelocityY() > -10)) {
					
//					entity.setView(run);
			}
			//THE REST OF THE CODE IS USED
			
			//idle
			else if(entity.getComponent(PhysicsComponent.class).getVelocityY() < 10 &&
					entity.getComponent(PhysicsComponent.class).getVelocityY() > -10 &&
					entity.getComponent(PhysicsComponent.class).getVelocityX() > -min_run_speed &&
					entity.getComponent(PhysicsComponent.class).getVelocityX() < min_run_speed) {
			
					entity.setView(idle);
		}
		}
	}
	
	public void setMinRunSpeed(double speed) {
		min_run_speed = speed;
	}
	
	public double getMinRunSpeed() {
		return min_run_speed;
	}
	
	public void setJumpUpImage(String image_path) {
		jump_up = FXGL.getAssetLoader().loadTexture(image_path);
	}
	
	public void setJumpUpImage(Texture img) {
		jump_up = img;
	}
	
	public void setJumpDownImage(String image_path) {
		jump_down = FXGL.getAssetLoader().loadTexture(image_path);
	}
	
	public void setJumpDownImage(Texture img) {
		jump_down = img;
	}
	
	public void setRunImage(AnimatedTexture anim) {
		run = anim;
	}
	
	public void setRunImage(Texture img) {
		run = img;
	}
	
	public void setRunImage(String image_path) {
		run = FXGL.getAssetLoader().loadTexture(image_path);
	}
	
	public void setIdleImage(AnimatedTexture anim) {
		idle = anim;
	}

	public void setIdleImage(Texture img) {
		idle = img;
	}
	
	public void setIdleImage(String image_path) {
		idle = FXGL.getAssetLoader().loadTexture(image_path);
	}
	
	public void setScaleAll(double scaler) {
		if(scaler < 0) {
			scaleNeg = scaler;
			scalePos = -scaler;
		}else {
			scaleNeg = -scaler;
			scalePos = scaler;
		}
		jump_up.setScaleX(scaler);
		jump_down.setScaleX(scaler);
		idle.setScaleX(scaler);
		run.setScaleX(scaler);
		
		if(scaler > 0) {
			jump_up.setScaleY(scaler);
			jump_down.setScaleY(scaler);
			idle.setScaleY(scaler);
			run.setScaleY(scaler);
		}
	}
	
	public double getScale() {
		return scalePos;
	}
	
}
