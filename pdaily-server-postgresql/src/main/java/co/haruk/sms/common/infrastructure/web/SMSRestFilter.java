package co.haruk.sms.common.infrastructure.web;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

import co.haruk.sms.startup.SMSStartup;

/**
 * @author andres2508 on 11/3/20
 **/
public abstract class SMSRestFilter implements ContainerRequestFilter, ContainerResponseFilter {

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		if ( SMSStartup.STARTED ) {
			doFilter( requestContext );
		}
	}

	@Override
	public void filter(
			ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
		if ( SMSStartup.STARTED ) {
			doFilter( requestContext, responseContext );
		}
	}

	public abstract void doFilter(ContainerRequestContext requestContext) throws IOException;

	public abstract void doFilter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException;
}
