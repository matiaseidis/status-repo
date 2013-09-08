package org.cachos.dimon.state.logger.transaction;

import org.cachos.dimon.state.logger.event.PushEvent;
import org.cachos.dimon.state.logger.plan.Puller;
import org.cachos.dimon.state.logger.plan.Pusher;
import org.cachos.dimon.state.logger.plan.RetrievalPlan;

public class ClientActivityPushEventRegistration extends
		ClientActivityEventRegistration<PushEvent> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ClientActivityPushEventRegistration(PushEvent event) {
		super(event);
	}

	@Override
	protected void updateParticipant(RetrievalPlan plan,
			PushEvent event) {
		Pusher pusher = plan.getPusher(event.getId());
		if(pusher == null) {
			pusher = new Pusher(event.getIp(), event.getPort(), event.getId(), event.getByteFrom(), event.getByteTo(), event.getByteCurrent());
			plan.getPushers().add(pusher);
		}
		pusher.setByteCurrent(event.getByteCurrent());
	}

}
