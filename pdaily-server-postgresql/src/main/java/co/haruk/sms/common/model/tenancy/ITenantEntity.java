package co.haruk.sms.common.model.tenancy;

/**
 * @author andres2508 on 14/11/19
 **/
public interface ITenantEntity {
	TenantId tenantId();

	void setTenantId(TenantId tenantId);
}
