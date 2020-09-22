package co.icesi.pdaily.events.physical.domain.model.detail;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.Objects;

import javax.persistence.*;

import co.icesi.pdaily.common.model.PdailyEntity;
import co.icesi.pdaily.events.physical.body.domain.model.BodyPartId;
import co.icesi.pdaily.events.physical.domain.model.PhysicalEvent;

@Entity
@Table(name = "event_physical_body_details")
@NamedQuery(name = BodyPartDetail.findByEventAsReadView, query = "SELECT new co.icesi.pdaily.events.physical.domain.model.view.BodyPartDetailReadView(b.id.id, p.id.id, p.name.name) "
		+
		" FROM BodyPartDetail b INNER JOIN BodyPart p ON p.id = b.bodyPartId WHERE b.physicalEvent.id = :eventId")
public class BodyPartDetail extends PdailyEntity<BodyPartDetailId> {
	private static final String PREFIX = "BodyPartDetail.";
	public static final String findByEventAsReadView = PREFIX + "findByEventAsReadView";

	@EmbeddedId
	private BodyPartDetailId id;
	@ManyToOne
	@JoinColumn(name = "physical_event_id")
	private PhysicalEvent physicalEvent;
	@Embedded
	@AttributeOverride(name = "id", column = @Column(name = "body_part_id"))
	private BodyPartId bodyPartId;

	protected BodyPartDetail() {
	}

	private BodyPartDetail(PhysicalEvent physicalEvent, BodyPartDetailId id, BodyPartId bodyPartId) {
		this.physicalEvent = physicalEvent;
		setId( id );
		setBodyPartId( bodyPartId );
	}

	public static BodyPartDetail of(PhysicalEvent physicalEvent, BodyPartDetailId id, BodyPartId bodyPartId) {
		return new BodyPartDetail( physicalEvent, id, bodyPartId );
	}

	private void setBodyPartId(BodyPartId bodyPartId) {
		this.bodyPartId = requireNonNull( bodyPartId, "La parte del cuerpo afectada es necesaria." );
	}

	public void removeAssociationWithPhysicalEvent() {
		this.physicalEvent = null;
	}

	@Override
	public BodyPartDetailId id() {
		return id;
	}

	@Override
	public void setId(BodyPartDetailId id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o )
			return true;
		if ( o == null || getClass() != o.getClass() )
			return false;
		BodyPartDetail that = (BodyPartDetail) o;
		return Objects.equals( bodyPartId, that.bodyPartId );
	}
}
