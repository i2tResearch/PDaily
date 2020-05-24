package co.haruk.sms.business.structure.security;

import static co.haruk.sms.security.authorization.domain.model.SimpleCRUDActivityBuilder.activitiesFor;

import java.util.Set;

import javax.enterprise.context.Dependent;

import co.haruk.sms.security.authorization.domain.model.AuthorizationGroup;
import co.haruk.sms.security.authorization.domain.model.AuthorizationGroupBuilder;
import co.haruk.sms.security.authorization.domain.model.IAuthorizationProducer;

/**
 * @author andres2508 on 1/5/20
 **/
@Dependent
public class BusinessStructureAuthorizationProducer implements IAuthorizationProducer {
	@Override
	public Set<AuthorizationGroup> authorizationGroups() {
		final var result = AuthorizationGroupBuilder.newGroup( "BUSINESS_STRUCTURE", "Estructura de negocios" )
				.addActivities( activitiesFor( "CUSTOMER", "Clientes" ) )
				.addActivities( activitiesFor( "CONTACT", "Contactos" ) )
				.addActivities( activitiesFor( "CONTACTROLE", "Roles de Contactos" ) )
				.addActivities( activitiesFor( "HOLDING", "Empresas Holding/Grupos Empresariales" ) )
				.addActivities( activitiesFor( "SUBSIDIARY", "Filiales" ) )
				.addActivities( activitiesFor( "SALES_OFFICE", "Oficinas de Ventas" ) )
				.addActivities( activitiesFor( "ZONE", "Zonas de Ventas" ) )
				.addActivities( activitiesFor( "SALES_REP", "Reps. de Ventas" ) )
				.addActivities( activitiesFor( "GEOGRAPHY", "Geografia (Pais,Estado,Ciudad)" ) )
				.addActivities( activitiesFor( "PRODUCT", "Productos" ) )
				.addActivities( activitiesFor( "PRODUCTLINE", "Lineas de Productos" ) )
				.addActivities( activitiesFor( "PRODUCTGROUP", "Grupos de Productos" ) )
				.addActivities( activitiesFor( "PRODUCTBRAND", "Marcas de Productos" ) )
				.build();
		return Set.of( result );
	}
}
