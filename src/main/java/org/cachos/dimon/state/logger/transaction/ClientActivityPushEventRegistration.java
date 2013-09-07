package org.cachos.dimon.state.logger.transaction;

import org.cachos.dimon.state.logger.event.PushEvent;
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
		pusher.setProgress(event.getProgress());
	}

}
