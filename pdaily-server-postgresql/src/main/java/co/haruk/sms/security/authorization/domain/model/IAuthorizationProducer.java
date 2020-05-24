package co.haruk.sms.security.authorization.domain.model;

import java.util.Set;

/**
 * @author cristhiank on 1/5/20
 **/
public interface IAuthorizationProducer {
	Set<AuthorizationGroup> authorizationGroups();
}
