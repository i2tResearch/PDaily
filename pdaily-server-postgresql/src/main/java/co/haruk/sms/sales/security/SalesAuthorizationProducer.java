package co.haruk.sms.sales.security;

import static co.haruk.sms.security.authorization.domain.model.SimpleCRUDActivityBuilder.activitiesFor;

import java.util.Set;

import javax.enterprise.context.Dependent;

import co.haruk.sms.security.authorization.domain.model.AuthorizationGroup;
import co.haruk.sms.security.authorization.domain.model.AuthorizationGroupBuilder;
import co.haruk.sms.security.authorization.domain.model.IAuthorizationProducer;

/**
 * @author cristhiank on 1/5/20
 **/
@Dependent
public class SalesAuthorizationProducer implements IAuthorizationProducer {
	@Override
	public Set<AuthorizationGroup> authorizationGroups() {
		final var result = AuthorizationGroupBuilder.newGroup( "SALES", "Ventas" )
				.addActivities( activitiesFor( "ORDER_SOURCE", "Fuentes/Origenes de Pedidos" ) )
				.addActivities( activitiesFor( "ACTIVITY_TASK", "Tareas de Actividades" ) )
				.addActivities( activitiesFor( "ACTIVITY_PURPOSE", "Proposito/Motivo de Actividad" ) )
				.addActivities( activitiesFor( "MARKETING_CAMPAIGN", "Campañas de Mercadeo" ) )
				.addActivities( activitiesFor( "SALES_ORDER", "Pedidos" ) )
				.addActivities( activitiesFor( "ACTIVITY", "Actividades" ) )
				.build();
		return Set.of( result );
	}
}
