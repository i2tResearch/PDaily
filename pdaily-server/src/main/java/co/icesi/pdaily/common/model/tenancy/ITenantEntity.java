package co.icesi.pdaily.common.model.tenancy;

/**
 * @author cristhiank on 14/11/19
 **/
public interface ITenantEntity {
	TenantId tenantId();

	void setTenantId(TenantId tenantId);
}
