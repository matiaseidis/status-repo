package org.cachos.dimon.state.logger.plan;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class RetrievalPlan {

	private long id;

	private List<RetrievalPlanParticipant> pulls = new ArrayList<RetrievalPlanParticipant>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public RetrievalPlanParticipant getPusher(String clientId) {
		for(RetrievalPlanParticipant pusher : this.getPulls()){
			if(StringUtils.equals(pusher.getClientId(), clientId)) {
				return pusher;
			}
		}
		return null;
	}
	
	public List<RetrievalPlanParticipant> getPulls() {
		return pulls;
	}

	public void setPulls(List<RetrievalPlanParticipant> pulls) {
		this.pulls = pulls;
	}

}
