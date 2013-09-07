package org.cachos.dimon.state.logger.service;

import java.util.ArrayList;
import java.util.List;

import org.cachos.dimon.state.logger.plan.Puller;
import org.cachos.dimon.state.logger.plan.Pusher;
import org.cachos.dimon.state.logger.plan.RetrievalPlan;

public class DemoRetrievalPlanFactory {
	
	static int progress = 0;

	public RetrievalPlan createDemoRetrievalPlan() {
		RetrievalPlan plan = new RetrievalPlan();
		plan.setId(1L);
		plan.setPushers(pushers());
		plan.setPuller(puller());
		return plan;
	}
	
	private Puller puller() {
		Puller p = new Puller("9.8.7.6","0000");
		p.setId(1);
		p.setProgress(progress);
		
		return p;
	}

	private List<Pusher> pushers() {
		int pushers = 12;
		List<Pusher> result = new ArrayList<Pusher>();
		for(int i = 1; i <= pushers; i++){
			Pusher p = new Pusher(""+i+".1.1.1", "9876");
			p.setProgress(progress);
			p.setId(i);
			result.add(p);
		}
		if(progress >= 100) {
			progress = 0;
		} else {
//			progress++;
			progress+=6;
		}
		return result;
	}

}
