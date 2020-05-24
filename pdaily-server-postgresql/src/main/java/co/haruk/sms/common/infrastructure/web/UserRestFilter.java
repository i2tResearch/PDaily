package co.haruk.sms.common.infrastructure.web;

import java.io.IOException;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;

import co.haruk.core.domain.model.guards.Guards;
import co.haruk.sms.business.structure.subsidiary.salesrep.app.SalesRepAppService;
import co.haruk.sms.common.infrastructure.session.HarukSession;
import co.haruk.sms.security.user.app.UserAppService;

import io.quarkus.security.identity.SecurityIdentity;

/**
 * @author andres2508 on 14/11/19
 **/
@Priority(2)
@Provider
@PreMatching
public class UserRestFilter extends SMSRestFilter {
	@Inject
	SecurityIdentity identity;
	@Inject
	UserAppService userAppService;
	@Inject
	SalesRepAppService salesRepAppService;

	@Override
	public void doFilter(ContainerRequestContext requestContext) {
		Guards.require( identity != null && !identity.isAnonymous(), "La solicitud actual no ha sido autenticada" );
		final var user = userAppService.findByUserNameOrFail( identity.getPrincipal().getName() );
		HarukSession.setCurrentUser( user );
		if ( salesRepAppService.existsWithId( user.id ) ) {
			final var salesRep = salesRepAppService.findById( user.id );
			HarukSession.setCurrentSalesRep( salesRep );
		}
	}

	@Override
	public void doFilter(ContainerRequestContext requestContext,
			ContainerResponseContext responseContext) throws IOException {
		if ( HarukSession.hasUser() ) {
			HarukSession.resetUser();
		}
		if ( HarukSession.hasSalesRep() ) {
			HarukSession.resetSalesRep();
		}
	}
}
