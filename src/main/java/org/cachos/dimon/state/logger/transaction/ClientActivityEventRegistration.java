package org.cachos.dimon.state.logger.transaction;

import java.util.Date;

import org.apache.log4j.Logger;
import org.cachos.dimon.state.logger.event.ClientActivityEvent;
import org.cachos.dimon.state.logger.event.type.CachoDirection;
import org.cachos.dimon.state.logger.plan.RetrievalPlan;
import org.cachos.dimon.state.logger.plan.RetrievalPlanParticipant;
import org.cachos.dimon.state.logger.repo.RepositoryManager;
import org.cachos.dimon.state.logger.repo.StateRepository;

public class ClientActivityEventRegistration<T extends ClientActivityEvent> extends ClientEventRegistration<T> {

	static Logger logger = Logger.getLogger(ClientActivityEventRegistration.class.getName());

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ClientActivityEventRegistration(T event) {
		super(event);
	}
	
	@Override
	public void executeOn(StateRepository repo, Date date) {
		super.executeOn(repo, date);
		RetrievalPlan plan = repo.getPlansMap().get(this.getEvent().getPlanId());
		if(plan == null) {
			plan = new RetrievalPlan();
			repo.getPlansMap().put(this.getEvent().getPlanId(), plan);
			logger.debug("new plan created in repo: "+this.getEvent().getPlanId());
		}
		this.updateParticipant(plan, this.getEvent());
	}

	private void updateParticipant(RetrievalPlan plan, T event){
		if(CachoDirection.PULL.equals(this.getEvent().getCachoDirection())) {
			updatePuller(plan, event);
		} else {
			updatePusher(plan, event);
		}
	}

	private void updatePusher(RetrievalPlan plan, T event) {
		RetrievalPlanParticipant pusher = plan.getPusher(event.getClientId());
		if(pusher == null) {
			pusher = new RetrievalPlanParticipant(event.getIp(), event.getPort(), event.getClientId(), event.getByteFrom(), event.getByteTo(), event.getByteCurrent(), event.getBandWidth());
			plan.getPushers().add(pusher);
		}
		pusher.setByteCurrent(event.getByteCurrent());
	}

	private void updatePuller(RetrievalPlan plan, T event) {
		RetrievalPlanParticipant puller = plan.getPuller();
		if(puller == null) {
			puller = new RetrievalPlanParticipant(event.getIp(), event.getPort(), event.getClientId(), event.getByteFrom(), event.getByteTo(), event.getByteCurrent(), event.getBandWidth());
			plan.setPuller(puller);
		}
		puller.setByteCurrent(event.getByteCurrent());
	}
}
