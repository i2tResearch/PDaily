package co.haruk.sms.common.infrastructure.web;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;

import co.haruk.sms.common.infrastructure.session.HarukSession;
import co.haruk.sms.common.model.tenancy.TenantId;

/**
 * @author cristhiank on 14/11/19
 **/
@Priority(1)
@Provider
@PreMatching
public class TenantRestFilter extends SMSRestFilter {
	private static final String TENANT_HEADER = "haruk-tenant";

	@Override
	public void doFilter(ContainerRequestContext requestContext) {
		final String tenantId = requestContext.getHeaders().getFirst( TENANT_HEADER );
		if ( tenantId != null ) {
			HarukSession.setCurrentTenant( TenantId.of( tenantId ) );
		}
	}

	@Override
	public void doFilter(
			ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
		if ( HarukSession.hasTenant() ) {
			HarukSession.resetTenant();
		}
	}
}
