package co.icesi.pdaily.common.infrastructure.web;

import java.io.IOException;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;

import co.haruk.core.domain.model.guards.Guards;
import co.icesi.pdaily.business.structure.subsidiary.doctor.app.DoctorAppService;
import co.icesi.pdaily.common.infrastructure.session.HarukSession;
import co.icesi.pdaily.security.user.app.UserAppService;

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
	DoctorAppService doctorAppService;

	@Override
	public void doFilter(ContainerRequestContext requestContext) {
		Guards.require( identity != null && !identity.isAnonymous(), "La solicitud actual no ha sido autenticada" );
		final var user = userAppService.findByUserNameOrFail( identity.getPrincipal().getName() );
		HarukSession.setCurrentUser( user );
		if ( doctorAppService.existsWithId( user.id ) ) {
			final var doctor = doctorAppService.findById( user.id );
			HarukSession.setCurrentDoctor( doctor );
		}
	}

	@Override
	public void doFilter(ContainerRequestContext requestContext,
			ContainerResponseContext responseContext) throws IOException {
		if ( HarukSession.hasUser() ) {
			HarukSession.resetUser();
		}
		if ( HarukSession.hasDoctor() ) {
			HarukSession.resetDoctor();
		}
	}
}
