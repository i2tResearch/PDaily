package co.icesi.pdaily.clinical.base.animic.domain.model;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import javax.persistence.*;

import co.haruk.core.domain.model.entity.PlainName;
import co.icesi.pdaily.common.model.PdailyEntity;

@Entity
@Table(name = "cb_animic_types")
@NamedQuery(name = AnimicType.findByLabel, query = "SELECT a FROM AnimicType a WHERE UPPER(a.label) = UPPER(:label)")
public class AnimicType extends PdailyEntity<AnimicTypeId> {
	private static final String PREFIX = "AnimicType.";
	public static final String findByLabel = PREFIX + "findByLabel";

	@EmbeddedId
	private AnimicTypeId id;
	@Embedded
	@AttributeOverride(name = "name", column = @Column(name = "label"))
	private PlainName label;

	protected AnimicType() {
	}

	private AnimicType(AnimicTypeId id, PlainName label) {
		setId( id );
		setLabel( label );
	}

	public static AnimicType of(AnimicTypeId id, PlainName label) {
		return new AnimicType( id, label );
	}

	public PlainName label() {
		return label;
	}

	public void setLabel(PlainName label) {
		this.label = requireNonNull( label, "El label del estado de animo es necesario." );
	}

	@Override
	public AnimicTypeId id() {
		return id;
	}

	@Override
	public void setId(AnimicTypeId id) {
		this.id = id;
	}
}
