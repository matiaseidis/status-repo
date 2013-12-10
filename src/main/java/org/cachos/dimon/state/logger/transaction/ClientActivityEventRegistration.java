package org.cachos.dimon.state.logger.transaction;

import java.util.Date;

import org.apache.log4j.Logger;
import org.cachos.dimon.state.logger.event.ClientActivityEvent;
import org.cachos.dimon.state.logger.plan.RetrievalPlan;
import org.cachos.dimon.state.logger.plan.RetrievalPlanParticipant;
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
		if (plan == null) {
			logger.info("the plan is null");
			plan = new RetrievalPlan();
			repo.getPlansMap().put(this.getEvent().getPlanId(), plan);
			logger.debug("new plan created in repo: " + this.getEvent().getPlanId());
		}
		this.updateParticipant(plan, this.getEvent());
	}

	private void updateParticipant(RetrievalPlan plan, T event) {
		// identifico de que puller se trata por el byteFrom del cacho
		RetrievalPlanParticipant pull = null;
		for (RetrievalPlanParticipant p : plan.getPulls()) {
			if (p.getByteFrom() == event.getByteFrom()) {
				pull = p;
				break;
			}
		}
		if (pull == null) {
			// logger.debug("cacho reportado por primera vez para este plan");
			pull = new RetrievalPlanParticipant(event);
			plan.getPulls().add(pull);
		} else {
			// logger.debug("actualizamos el cacho para este plan");
			pull.setByteCurrent(event.getByteCurrent());
			pull.setBandWidth(event.getBandWidth());
		}
	}

}