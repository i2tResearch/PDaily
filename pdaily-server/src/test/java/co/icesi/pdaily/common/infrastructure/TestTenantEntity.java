package co.icesi.pdaily.common.infrastructure;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import co.icesi.pdaily.common.model.tenancy.HarukTenantEntity;

/**
 * @author cristhiank on 14/11/19
 **/
@Entity
@Table(name = "test_tenant_entity")
@NamedQuery(name = "TestTenantEntity.findByTenant", query = "SELECT t FROM TestTenantEntity t WHERE t.tenant = :company")
public class TestTenantEntity extends HarukTenantEntity<UUID> {
	@Id
	@GeneratedValue
	private UUID id;
	private String name;

	protected TestTenantEntity() {
	}

	private TestTenantEntity(String name) {
		this.name = name;
	}

	public static TestTenantEntity of(String name) {
		return new TestTenantEntity( name );
	}

	@Override
	public UUID id() {
		return id;
	}

	@Override
	public void setId(UUID id) {
		this.id = id;
	}
}
