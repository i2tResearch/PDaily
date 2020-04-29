package co.icesi.pdaily.sales.activities.domain.model.details;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.Objects;

import javax.persistence.*;

import co.icesi.pdaily.common.model.Reference;
import co.icesi.pdaily.common.model.tenancy.PdailyTenantEntity;
import co.icesi.pdaily.sales.activities.domain.model.Activity;
import co.icesi.pdaily.sales.activities.domain.model.ActivityId;
import co.icesi.pdaily.sales.activities.marketing.domain.model.MarketingCampaignId;

@Entity
@Table(name = "sales_activities_campaign_details")
@NamedQuery(name = CampaignDetail.findCampaignDetailsAsReadView, query = "SELECT new co.icesi.pdaily.sales.activities.domain.model.view.CampaignDetailReadView(c.id.id,c.name.name,a.detail.text)"
		+ " FROM CampaignDetail a INNER JOIN MarketingCampaign c ON a.campaignId = c.id " +
		" WHERE a.activity.id = :activity AND a.tenant = :company")
public class CampaignDetail extends PdailyTenantEntity<CampaignDetailId> {
	private static final String PREFIX = "CampaignDetail.";
	public static final String findCampaignDetailsAsReadView = PREFIX + "findCampaignDetailsAsReadView";

	@EmbeddedId
	private CampaignDetailId id;
	@ManyToOne
	@JoinColumn(name = "activity_id")
	private Activity activity;
	@Embedded
	@AttributeOverride(name = "id", column = @Column(name = "campaign_id"))
	private MarketingCampaignId campaignId;
	@Embedded
	@AttributeOverride(name = "text", column = @Column(name = "details"))
	private Reference detail;

	protected CampaignDetail() {
	}

	private CampaignDetail(Activity activity, CampaignDetailId id, MarketingCampaignId campaignId, Reference detail) {
		setId( id );
		setCampaignId( campaignId );
		this.activity = activity;
		setDetail( detail );
	}

	public static CampaignDetail of(Activity activity, CampaignDetailId id, MarketingCampaignId campaignId, Reference detail) {
		return new CampaignDetail( activity, id, campaignId, detail );
	}

	public static CampaignDetail of(Activity activity, CampaignDetailId id, MarketingCampaignId campaignId) {
		return new CampaignDetail( activity, id, campaignId, null );
	}

	@Override
	public CampaignDetailId id() {
		return id;
	}

	@Override
	public void setId(CampaignDetailId id) {
		this.id = id;
	}

	public MarketingCampaignId campaignId() {
		return campaignId;
	}

	private void setCampaignId(MarketingCampaignId campaignId) {
		this.campaignId = requireNonNull( campaignId, "La campaña de mercadeo realizada en la actividad es requerida." );
	}

	public Reference detail() {
		return this.detail;
	}

	public void setDetail(Reference detail) {
		this.detail = detail;
	}

	public ActivityId activity() {
		return activity.id();
	}

	private void setActivity(Activity activity) {
		this.activity = activity;
	}

	public void removeAssociationWithActivity() {
		setActivity( null );
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o )
			return true;
		if ( o == null || getClass() != o.getClass() )
			return false;
		CampaignDetail that = (CampaignDetail) o;
		return Objects.equals( campaignId, that.campaignId );
	}
}
