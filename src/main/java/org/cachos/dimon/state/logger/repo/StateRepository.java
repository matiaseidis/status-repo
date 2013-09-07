package org.cachos.dimon.state.logger.repo;

import java.io.Serializable;
import java.util.ArrayList;
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

	private Map<String, RetrievalPlan> plansMap = null;
	private Map<String, List<ClientEvent>> eventsMap = null;

	public StateRepository() {
		plansMap = new HashMap<String, RetrievalPlan>();
		eventsMap = new HashMap<String, List<ClientEvent>>();
	}
	
	public <T extends ClientEvent> List<ClientEvent> getEvents(T event) {
		return getEvents(event.getClass());
	}

	private <T extends ClientEvent> List<ClientEvent> getEventsByClassSimpleName(Class event) {
		return getEventsMap().get(event.getSimpleName());
	}
	
	public <T extends ClientEvent> List<ClientEvent> getEvents(Class eventClass) {
		List<ClientEvent> result = getEventsByClassSimpleName(eventClass);
		if(result == null) {
			result = new ArrayList<ClientEvent>();
			this.getEventsMap().put(eventClass.getSimpleName(), result);
		}
		return result;
	}

	public Map<String, List<ClientEvent>> getEventsMap() {
		return eventsMap;
	}

	public Map<String, RetrievalPlan> getPlansMap() {
		return plansMap;
	}
	
	
}
