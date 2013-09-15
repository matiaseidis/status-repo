package org.cachos.dimon.state.logger.plan;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class RetrievalPlan {

	private long id;
	private List<RetrievalPlanParticipant> pushers = new ArrayList<RetrievalPlanParticipant>();
	private RetrievalPlanParticipant puller;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public List<RetrievalPlanParticipant> getPushers() {
		return pushers;
	}

	public void setPushers(List<RetrievalPlanParticipant> pushers) {
		this.pushers = pushers;
	}

	public RetrievalPlanParticipant getPuller() {
		return puller;
	}

	public void setPuller(RetrievalPlanParticipant puller) {
		this.puller = puller;
	}

	public RetrievalPlanParticipant getPusher(String clientId) {
		for(RetrievalPlanParticipant pusher : this.getPushers()){
			if(StringUtils.equals(pusher.getClientId(), clientId)) {
				return pusher;
			}
		}
		return null;
	}

}
