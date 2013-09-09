package org.cachos.dimon.state.logger.transaction;

import java.util.Date;

import org.cachos.dimon.state.logger.event.ClientActivityEvent;
import org.cachos.dimon.state.logger.event.type.CachoDirection;
import org.cachos.dimon.state.logger.plan.Puller;
import org.cachos.dimon.state.logger.plan.Pusher;
import org.cachos.dimon.state.logger.plan.RetrievalPlan;
import org.cachos.dimon.state.logger.repo.StateRepository;

public class ClientActivityEventRegistration extends ClientEventRegistration<ClientActivityEvent> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ClientActivityEventRegistration(ClientActivityEvent event) {
		super(event);
	}
	
	@Override
	public void executeOn(StateRepository repo, Date date) {
		super.executeOn(repo, date);
		RetrievalPlan plan = repo.getPlansMap().get(this.getEvent().getPlanId());
		if(plan == null) {
			plan = new RetrievalPlan();
			repo.getPlansMap().put(this.getEvent().getPlanId(), plan);
		}
		this.updateParticipant(plan, this.getEvent());
	}

	private void updateParticipant(RetrievalPlan plan, ClientActivityEvent event){
		if(CachoDirection.PULL.equals(this.getEvent().getCachoDirection())) {
			updatePuller(plan, event);
		} else {
			updatePusher(plan, event);
		}
	}

	private void updatePusher(RetrievalPlan plan, ClientActivityEvent event) {
		Pusher pusher = plan.getPusher(event.getClientId());
		if(pusher == null) {
			pusher = new Pusher(event.getIp(), event.getPort(), event.getClientId(), event.getByteFrom(), event.getByteTo(), event.getByteCurrent());
			plan.getPushers().add(pusher);
		}
		pusher.setByteCurrent(event.getByteCurrent());
	}

	private void updatePuller(RetrievalPlan plan, ClientActivityEvent event) {
		Puller puller = plan.getPuller();
		if(puller == null) {
			puller = new Puller(event.getIp(), event.getPort(), event.getClientId(), event.getByteFrom(), event.getByteTo(), event.getByteCurrent());
			plan.setPuller(puller);
		}
		puller.setByteCurrent(event.getByteCurrent());
	}
}
