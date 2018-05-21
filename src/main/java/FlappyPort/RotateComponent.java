package FlappyPort;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;

public class RotateComponent extends Component {
	@Override
	public void onUpdate(double tpf) {
		PhysicsComponent phy = entity.getComponent(PhysicsComponent.class);
		if(phy.getVelocityY() < 0) {
				phy.setAngularVelocity(phy.getVelocityY()/200);
		}else if(phy.getVelocityY() > 0) {
				phy.setAngularVelocity(phy.getVelocityY()/200);
		}else if(phy.getVelocityY() == 0) {
			entity.getComponent(PhysicsComponent.class).setAngularVelocity(0);
		}
	}
}
