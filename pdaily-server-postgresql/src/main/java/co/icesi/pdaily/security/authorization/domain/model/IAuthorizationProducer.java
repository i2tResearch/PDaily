package co.icesi.pdaily.security.authorization.domain.model;

import java.util.Set;

/**
 * @author andres2508 on 1/5/20
 **/
public interface IAuthorizationProducer {
	Set<AuthorizationGroup> authorizationGroups();
}
