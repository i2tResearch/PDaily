package co.icesi.pdaily.sales.activities.marketing.domain.model;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import javax.persistence.*;

import co.haruk.core.domain.model.entity.PlainName;
import co.icesi.pdaily.common.model.tenancy.HarukTenantEntity;

@Entity
@Table(name = "sales_activities_marketing_campaigns")
@NamedQuery(name = MarketingCampaign.findByName, query = "SELECT m FROM MarketingCampaign m WHERE m.tenant = :company AND UPPER(m.name) = UPPER(:name)")
public class MarketingCampaign extends HarukTenantEntity<MarketingCampaignId> {

	private static final String PREFIX = "MarketingCampaign.";
	public static final String findByName = PREFIX + "findByName";

	@EmbeddedId
	private MarketingCampaignId id;
	@Embedded
	private PlainName name;

	protected MarketingCampaign() {
	}

	private MarketingCampaign(MarketingCampaignId id, PlainName name) {
		setId( id );
		setName( name );
	}

	public static MarketingCampaign of(MarketingCampaignId id, PlainName name) {
		return new MarketingCampaign( id, name );
	}

	@Override
	public MarketingCampaignId id() {
		return id;
	}

	@Override
	public void setId(MarketingCampaignId id) {
		this.id = id;
	}

	public PlainName name() {
		return this.name;
	}

	public void setName(PlainName name) {
		this.name = requireNonNull( name, "El nombre es requerido" );
	}

}
