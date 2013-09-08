package org.cachos.dimon.state.logger.transaction;

import org.cachos.dimon.state.logger.event.PullEvent;
import org.cachos.dimon.state.logger.plan.Puller;
import org.cachos.dimon.state.logger.plan.RetrievalPlan;

public class ClientActivityPullEventRegistration extends
		ClientActivityEventRegistration<PullEvent> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ClientActivityPullEventRegistration(PullEvent event) {
		super(event);
	}

	@Override
	protected void updateParticipant(RetrievalPlan plan,
			PullEvent event) {
		Puller puller = plan.getPuller();
		if(puller == null) {
			puller = new Puller(event.getIp(), event.getPort(), event.getClientId(), event.getByteFrom(), event.getByteTo(), event.getByteCurrent());
			plan.setPuller(puller);
		}
		puller.setByteCurrent(event.getByteCurrent());
	}

}
