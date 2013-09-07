package org.cachos.dimon.state.logger.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.cachos.dimon.state.logger.plan.Puller;
import org.cachos.dimon.state.logger.plan.Pusher;
import org.cachos.dimon.state.logger.plan.RetrievalPlan;
import org.cachos.dimon.state.logger.plan.RetrievalPlanParticipant;

public class DemoRetrievalPlanFactory {
	
	static Logger logger = Logger.getLogger(DemoRetrievalPlanFactory.class.getName());

	static long pullerProgress = 0;
	static long pushersProgress = 0;
	private long pullerStep = 500;
	private long pusherStep = 50;

	public RetrievalPlan createDemoRetrievalPlan() {
		RetrievalPlan plan = new RetrievalPlan();
		plan.setId(1L);
		plan.setPushers(pushers());
		plan.setPuller(puller());
		return plan;
	}

	private Puller puller() {
		Puller p = new Puller("9.8.7.6", "0000");
		p.setId(1);
		p.setByteFrom(0);
		p.setByteTo(99999);
		
		pullerProgress = updateParticipantProgress(p, pullerProgress, pullerStep);
		return p;
	}
	
	private List<Pusher> pushers() {
		int pushers = 12;
		List<Pusher> result = new ArrayList<Pusher>();
		for (int i = 1; i <= pushers; i++) {
			result.add(pusher(i));
		}
		return result;
	}

	private Pusher pusher(int id) {
		Pusher p = new Pusher("" + id + ".1.1.1", "9876");
		p.setId(id);
		p.setByteFrom(0);
		p.setByteTo(5555);
		
		pushersProgress = updateParticipantProgress(p, pushersProgress, pusherStep);
		logger.debug("Pusher progress pre reset: ["+p.getProgress()+"]");
		// reset for demo
		if(p.getProgress() > 100) {
			p.setByteCurrent(p.getByteFrom());
			pushersProgress = 0;
			logger.debug("Pusher progress post reset: ["+p.getProgress()+"]");
		}
		return p;
	}

	private long updateParticipantProgress(RetrievalPlanParticipant p, long progress, long step) {
		long current = 0;
		if(0 == progress){
			current = step;
		} else {
			p.setByteCurrent(progress);
			logger.debug(p.getClass().getSimpleName() + " current byte: " + p.getByteCurrent());
			logger.debug("sumo para "+p.getClass().getSimpleName()+": ["+p.getByteCurrent()+"] + ["+p.getByteCurrent() / 13+"]");
			current = p.getByteCurrent() + step;
			logger.debug(p.getClass().getSimpleName()+" current byte incremented: " + current);
		}
		p.setByteCurrent(current);
		logger.debug(p.getClass().getSimpleName() +" progress: " + progress);
		
		return current;
	}

	

}
