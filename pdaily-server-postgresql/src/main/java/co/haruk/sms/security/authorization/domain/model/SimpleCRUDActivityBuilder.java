package co.haruk.sms.security.authorization.domain.model;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.Map;

/**
 * @author andres2508 on 1/5/20
 **/
public class SimpleCRUDActivityBuilder {

	private SimpleCRUDActivityBuilder() {
	}

	@SuppressWarnings("ResultOfMethodCallIgnored")
	public static Map<String, String> activitiesFor(String prefix, String entityName) {
		requireNonNull( prefix );
		requireNonNull( entityName );
		return Map.of(
				prefix + "_EDIT", "Crear/Editar/Eliminar " + entityName,
				prefix + "_VIEW", "Ver " + entityName
		);
	}
}
