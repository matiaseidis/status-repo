package org.cachos.dimon.state.logger.repo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.cachos.dimon.state.logger.event.ClientEvent;
import org.cachos.dimon.state.logger.plan.RetrievalPlan;

public class StateRepository implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static Logger logger = Logger.getLogger(StateRepository.class.getName());

	private Map<String, RetrievalPlan> plans = null;
	private Map<String, List<ClientEvent>> events = null;

	public StateRepository() {
		plans = new HashMap<String, RetrievalPlan>();
		events = new HashMap<String, List<ClientEvent>>();
	}

	public Map<String, List<ClientEvent>> getEvents() {
		return events;
	}

	public Map<String, RetrievalPlan> getPlans() {
		return plans;
	}
}
