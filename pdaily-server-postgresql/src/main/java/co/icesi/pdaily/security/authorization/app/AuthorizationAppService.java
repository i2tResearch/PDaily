package co.icesi.pdaily.security.authorization.app;

import static co.haruk.core.domain.model.guards.Guards.require;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import co.haruk.core.StreamUtils;
import co.icesi.pdaily.common.infrastructure.session.HarukSession;
import co.icesi.pdaily.common.model.tenancy.TenantId;
import co.icesi.pdaily.security.authorization.domain.model.AuthorizationGroup;
import co.icesi.pdaily.security.authorization.domain.model.ProtectedActivityId;
import co.icesi.pdaily.security.authorization.domain.model.permission.UserPermission;
import co.icesi.pdaily.security.authorization.domain.model.permission.UserPermissionId;
import co.icesi.pdaily.security.authorization.infraestructure.persistence.AuthorizationGroupRepository;
import co.icesi.pdaily.security.authorization.infraestructure.persistence.UserPermissionRepository;
import co.icesi.pdaily.security.user.app.UserAppService;
import co.icesi.pdaily.security.user.domain.model.UserId;
import co.icesi.pdaily.subscription.account.app.AccountAppService;
import co.icesi.pdaily.subscription.account.app.AccountDTO;

/**
 * @author andres2508 on 1/5/20
 **/
@ApplicationScoped
public class AuthorizationAppService {
	@Inject
	AuthorizationGroupRepository groupRepository;
	@Inject
	UserPermissionRepository userPermissionRepository;
	@Inject
	UserAppService userAppService;
	@Inject
	AccountAppService accountAppService;

	public List<AuthorizationGroupDTO> findAllGroups() {
		final var all = groupRepository.findAll();
		return StreamUtils.map( all, AuthorizationGroupDTO::of );
	}

	public List<AuthorizationGroupDTO> findAllGroupsIncActivities() {
		final var all = groupRepository.findAll( AuthorizationGroup.graphActivities );
		return StreamUtils.map( all, AuthorizationGroupDTO::ofInclActivities );
	}

	@Transactional
	public void grantUserPermission(UserPermissionDTO dto) {
		boolean hasPermission = userHasPermissionFor( dto.userId, dto.tenantId, dto.activityId );
		require( !hasPermission, "El usuario ya está autorizado para la actividad" );
		final var permission = dto.toUserPermission();
		userPermissionRepository.create( permission );
	}

	@Transactional
	public void revokeUserPermission(UserPermissionDTO dto) {
		boolean hasPermission = userHasPermissionFor( dto.userId, dto.tenantId, dto.activityId );
		require( hasPermission, "El usuario no está autorizado para la actividad" );
		final UserPermissionId permissionId = dto.toUserPermissionId();
		userPermissionRepository.delete( permissionId );
	}

	public boolean userHasPermissionFor(String userId, String tenantId, String activityId) {
		return userPermissionRepository.find(
				UserPermissionId.of(
						UserId.ofNotNull( userId ),
						TenantId.of( tenantId ),
						ProtectedActivityId.of( activityId )
				)
		).isPresent();
	}

	public AuthorizationResult currentUserIsAuthorizedTo(String activityId) {
		final var granted = userHasPermissionFor(
				HarukSession.currentUser().id, HarukSession.currentTenant().text(), activityId
		);
		return AuthorizationResult.of( granted );
	}

	public List<AccountDTO> availableAccountsForUser(String userId) {
		final var found = userAppService.findByIdOrFail( userId );
		if ( found.accountId == null ) {
			return Collections.emptyList();
		}
		final var account = accountAppService.findById( found.accountId );
		return List.of( account );
	}

	public List<String> getActivitiesGrantedToUser(String userId, String tenantId) {
		List<UserPermission> perms = userPermissionRepository
				.findForUserInTenant( UserId.ofNotNull( userId ), TenantId.of( tenantId ) );
		return perms.stream().map( it -> it.id().activityId().text() ).collect( Collectors.toList() );
	}
}
