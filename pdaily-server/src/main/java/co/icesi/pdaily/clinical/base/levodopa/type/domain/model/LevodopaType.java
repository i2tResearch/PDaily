package co.icesi.pdaily.clinical.base.levodopa.type.domain.model;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import javax.persistence.*;

import co.haruk.core.domain.model.entity.PlainName;
import co.icesi.pdaily.common.model.PdailyEntity;

@Entity
@Table(name = "medicine_levodopa_types")
@NamedQuery(name = LevodopaType.findByName, query = "SELECT t FROM LevodopaType t WHERE  UPPER(t.label) = UPPER(:label)")
public class LevodopaType extends PdailyEntity<LevodopaTypeId> {
	private static final String PREFIX = "LevodopaType.";
	public static final String findByName = PREFIX + "findByName";

	@EmbeddedId
	private LevodopaTypeId id;
	@Embedded
	@AttributeOverride(name = "name", column = @Column(name = "label"))
	private PlainName label;

	protected LevodopaType() {
	}

	private LevodopaType(LevodopaTypeId id, PlainName label) {
		setId( id );
		setLabel( label );
	}

	public static LevodopaType of(LevodopaTypeId id, PlainName label) {
		return new LevodopaType( id, label );
	}

	public PlainName label() {
		return this.label;
	}

	public void setLabel(PlainName label) {
		this.label = requireNonNull( label, "El nombre del tipo de levodopa es necesario." );
	}

	@Override
	public LevodopaTypeId id() {
		return id;
	}

	@Override
	public void setId(LevodopaTypeId id) {
		this.id = id;
	}
}
