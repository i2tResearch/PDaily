package co.haruk.sms.common.infrastructure.web;

import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

import co.haruk.core.infrastructure.rest.SynekusExceptionMapper;

@Provider
public class SMSJAXRSFeature implements Feature {

	@Override
	public boolean configure(FeatureContext context) {
		context.register( SynekusExceptionMapper.class );
		return true;
	}
}
