package co.icesi.pdaily.sales.activities.domain.model.view;

import java.io.Serializable;
import java.util.UUID;

public final class CampaignDetailReadView implements Serializable {
	public String campaignId;
	public String campaignName;
	public String detail;

	protected CampaignDetailReadView() {
	}

	public CampaignDetailReadView(
			UUID campaignId,
			String campaignName,
			String detail) {
		this.campaignId = campaignId.toString();
		this.campaignName = campaignName;
		this.detail = detail;
	}
}
