package co.icesi.pdaily.common.infrastructure.web;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;

import co.icesi.pdaily.common.infrastructure.session.PdailySession;
import co.icesi.pdaily.common.model.tenancy.TenantId;

/**
 * @author cristhiank on 14/11/19
 **/
@Provider
@PreMatching
public class TenantRestFilter implements ContainerRequestFilter, ContainerResponseFilter {
	private static final String TENANT_HEADER = "pdaily-tenant";

	@Override
	public void filter(ContainerRequestContext requestContext) {
		final String tenantId = requestContext.getHeaders().getFirst( TENANT_HEADER );
		if ( tenantId != null ) {
			PdailySession.setCurrentTenant( TenantId.of( tenantId ) );
		}
	}

	@Override
	public void filter(
			ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
		if ( PdailySession.hasTenant() ) {
			PdailySession.resetTenant();
		}
	}
}
