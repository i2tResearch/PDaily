package co.haruk.sms.sales.activities.domain.model.view;

import java.io.Serializable;
import java.util.UUID;

public final class CampaignDetailReadView implements Serializable {
	public String id;
	public String campaignId;
	public String campaignName;
	public String detail;

	protected CampaignDetailReadView() {
	}

	public CampaignDetailReadView(
			UUID id,
			UUID campaignId,
			String campaignName,
			String detail) {
		this.id = id.toString();
		this.campaignId = campaignId.toString();
		this.campaignName = campaignName;
		this.detail = detail;
	}
}
