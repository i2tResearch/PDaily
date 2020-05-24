package co.haruk.sms.business.structure.customer.holding.domain.model;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.sms.business.structure.subsidiary.domain.model.SubsidiaryId;
import co.haruk.sms.common.model.tenancy.PdailyTenantEntity;

/**
 * @author andres2508 on 1/12/19
 **/
@Entity
@Table(name = "bs_customer_holding_companies")
@NamedQuery(name = HoldingCompany.findByName, query = "SELECT c FROM HoldingCompany c WHERE c.tenant = :company  AND c.subsidiaryId = :subsidiary AND UPPER(c.name.name) = UPPER(:name)")
@NamedQuery(name = HoldingCompany.findBySubsidiary, query = "SELECT c FROM HoldingCompany c WHERE c.tenant = :company  AND c.subsidiaryId = :subsidiary")
@NamedQuery(name = HoldingCompany.countBySubsidiary, query = "SELECT COUNT(c.id) FROM HoldingCompany c WHERE c.tenant = :company  AND c.subsidiaryId = :subsidiary")
public class HoldingCompany extends PdailyTenantEntity<HoldingCompanyId> {
	private static final String PREFIX = "HoldingCompany.";
	public static final String findByName = PREFIX + "findByName";
	public static final String findBySubsidiary = PREFIX + "findBySubsidiary";
	public static final String countBySubsidiary = PREFIX + "countBySubsidiary";

	@EmbeddedId
	private HoldingCompanyId id;
	@Embedded
	private PlainName name;
	@Embedded
	@AttributeOverride(name = "id", column = @Column(name = "subsidiary_id"))
	private SubsidiaryId subsidiaryId;

	protected HoldingCompany() {
	}

	private HoldingCompany(HoldingCompanyId id, SubsidiaryId subsidiaryId, PlainName name) {
		setId( id );
		setSubsidiaryId( subsidiaryId );
		setName( name );
	}

	public static HoldingCompany of(HoldingCompanyId id, SubsidiaryId subsidiaryId, PlainName name) {
		return new HoldingCompany( id, subsidiaryId, name );
	}

	@Override
	public HoldingCompanyId id() {
		return this.id;
	}

	@Override
	public void setId(HoldingCompanyId id) {
		this.id = id;
	}

	public PlainName name() {
		return name;
	}

	private void setName(PlainName name) {
		this.name = requireNonNull( name, "El nombre del holding es requerido" );
	}

	public void updateFrom(HoldingCompany changes) {
		setName( changes.name );
	}

	public void setSubsidiaryId(SubsidiaryId subsidiaryId) {
		this.subsidiaryId = requireNonNull( subsidiaryId, "La filial del holding es requerida" );
	}

	public SubsidiaryId subsidiaryId() {
		return subsidiaryId;
	}
}
