package co.icesi.pdaily.clinical.base.routines.domain.model;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import javax.persistence.*;

import co.icesi.pdaily.common.model.Reference;
import co.icesi.pdaily.common.model.tenancy.PdailyTenantEntity;

@Entity
@Table(name = "cb_routine_types")
@NamedQuery(name = RoutineType.findByLabel, query = "SELECT a FROM RoutineType a WHERE UPPER(a.label) = UPPER(:label)")
public class RoutineType extends PdailyTenantEntity<RoutineTypeId> {
	private static final String PREFIX = "RoutineType.";
	public static final String findByLabel = PREFIX + "findByLabel";

	@EmbeddedId
	private RoutineTypeId id;
	@Embedded
	@AttributeOverride(name = "text", column = @Column(name = "label"))
	private Reference label;

	protected RoutineType() {
	}

	private RoutineType(RoutineTypeId id, Reference label) {
		setId( id );
		setLabel( label );
	}

	public static RoutineType of(RoutineTypeId id, Reference label) {
		return new RoutineType( id, label );
	}

	public Reference label() {
		return label;
	}

	public void setLabel(Reference label) {
		this.label = requireNonNull( label, "El nombre de la actividad de la rutina es necesario." );
	}

	@Override
	public RoutineTypeId id() {
		return id;
	}

	@Override
	public void setId(RoutineTypeId id) {
		this.id = id;
	}
}
