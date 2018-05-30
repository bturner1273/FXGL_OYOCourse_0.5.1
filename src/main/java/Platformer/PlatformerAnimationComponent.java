package Platformer;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.texture.Texture;

import javafx.util.Duration;

public class PlatformerAnimationComponent extends Component {
	double scalePos, scaleNeg;
	Texture jump_up;
	Texture jump_down;
	AnimatedTexture idle;
	boolean right = true;
	
	@Override
	public void onUpdate(double tpf) {
		if(entity.getComponent(PhysicsComponent.class).getVelocityX() < -10) {
			setScale(scaleNeg);
			right = false;
		}else if(entity.getComponent(PhysicsComponent.class).getVelocityX() > 10) {
			setScale(scalePos);
			right = true;
		}
		if(entity.getComponent(PhysicsComponent.class).getVelocityY() < 0) {
			entity.setView(jump_up);
		}
		else if(entity.getComponent(PhysicsComponent.class).getVelocityY() > 0) {
			entity.setView(jump_down);
		}
		else if(entity.getComponent(PhysicsComponent.class).getVelocityY() < 10 &&
			entity.getComponent(PhysicsComponent.class).getVelocityY() > -10) {
			entity.setView(idle);
		}
	}
	
	public void setJumpUpImage(String image_path) {
		jump_up = FXGL.getAssetLoader().loadTexture(image_path);
	}
	
	public void setJumpDownImage(String image_path) {
		jump_down = FXGL.getAssetLoader().loadTexture(image_path);
	}
	
	public void setIdleImage(AnimatedTexture anim) {
		idle = anim;
	}
	
	public void setScale(double scaler) {
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
		
		if(scaler > 0) {
			jump_up.setScaleY(scaler);
			jump_down.setScaleY(scaler);
			idle.setScaleY(scaler);
		}
	}
	
	public double getScale() {
		return scalePos;
	}
	
}
