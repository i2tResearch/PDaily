package co.haruk.sms.clinical.base.animic.domain.model;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import javax.persistence.*;

import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.sms.common.model.PdailyEntity;
import co.haruk.sms.events.physical.domain.model.Intensity;

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
	@Embedded
	private Intensity intensity;

	protected AnimicType() {
	}

	private AnimicType(AnimicTypeId id, PlainName label, Intensity intensity) {
		setId( id );
		setLabel( label );
		setIntensity( intensity );
	}

	private void setIntensity(Intensity intensity) {
		this.intensity = requireNonNull( intensity, "El tipo de estado animico tiene que estar asociado con una intensidad." );
	}

	public static AnimicType of(AnimicTypeId id, PlainName label, Intensity intensity) {
		return new AnimicType( id, label, intensity );
	}

	public Intensity intensity() {
		return intensity;
	}

	public PlainName label() {
		return label;
	}

	private void setLabel(PlainName label) {
		this.label = requireNonNull( label, "El label del estado de animo es necesario." );
	}

	public void updateFrom(AnimicType animicType) {
		setLabel( animicType.label );
		setIntensity( animicType.intensity );
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
