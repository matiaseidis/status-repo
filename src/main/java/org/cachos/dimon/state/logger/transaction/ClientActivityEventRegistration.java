package org.cachos.dimon.state.logger.transaction;

import java.util.Date;

import org.cachos.dimon.state.logger.event.ClientActivityEvent;
import org.cachos.dimon.state.logger.plan.RetrievalPlan;
import org.cachos.dimon.state.logger.repo.StateRepository;

public abstract class ClientActivityEventRegistration<T extends ClientActivityEvent> extends ClientEventRegistration<T> {

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
		this.updateParticipant(plan, this.getEvent());
	}

	protected abstract void updateParticipant(RetrievalPlan plan, T event);


}
