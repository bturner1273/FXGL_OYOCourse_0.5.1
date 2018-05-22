package Frogger;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.extra.entity.components.OffscreenCleanComponent;

public class HalfWayTrackerComponent extends Component {
	private boolean past_halfway = false;
	
	@Override
	public void onUpdate(double tpf) {
		if(entity.getCenter().getX() < FXGL.getAppWidth()/2 + 10 && entity.getCenter().getX() > FXGL.getAppWidth()/2 - 10) {
			past_halfway = true;
		}
	}
	
	public boolean pastHalfway() {
		return past_halfway;
	}
	
	
}
