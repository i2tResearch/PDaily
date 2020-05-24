package co.haruk.sms.events.animic.domain.model;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import javax.persistence.*;

import co.haruk.sms.business.structure.patient.domain.model.PatientId;
import co.haruk.sms.clinical.base.animic.domain.model.AnimicTypeId;
import co.haruk.sms.common.model.UTCDateTime;
import co.haruk.sms.common.model.tenancy.PdailyTenantEntity;

@Entity
@Table(name = "event_animic_events")
@NamedQuery(name = AnimicEvent.findByPatientAsReadView, query = "SELECT new co.haruk.sms.events.animic.domain.model.view.AnimicEventReadView( a.id.id, a.patientId.id, a.type.id, t.label.name, t.intensity.intensity, a.ocurrenceDate.date ) "
		+
		"FROM AnimicEvent a INNER JOIN AnimicType t ON t.id = a.type WHERE a.patientId = :id AND a.tenant = :company")
@NamedQuery(name = AnimicEvent.findByIdAsReadView, query = "SELECT new co.haruk.sms.events.animic.domain.model.view.AnimicEventReadView( a.id.id, a.patientId.id, a.type.id, t.label.name, t.intensity.intensity, a.ocurrenceDate.date ) "
		+
		"FROM AnimicEvent a INNER JOIN AnimicType t ON t.id = a.type WHERE a.id = :id AND a.tenant = :company")
public class AnimicEvent extends PdailyTenantEntity<AnimicEventId> {
	private static final String PREFIX = "AnimicEvent.";
	public static final String findByPatientAsReadView = PREFIX + "findByPatientAsReadView";
	public static final String findByIdAsReadView = PREFIX + "findByIdAsReadView";

	@EmbeddedId
	private AnimicEventId id;
	@Embedded
	@AttributeOverride(name = "id", column = @Column(name = "patient_id"))
	private PatientId patientId;
	@Embedded
	@AttributeOverride(name = "id", column = @Column(name = "type"))
	private AnimicTypeId type;
	@Embedded
	@AttributeOverride(name = "date", column = @Column(name = "ocurrence_date"))
	private UTCDateTime ocurrenceDate;

	protected AnimicEvent() {
	}

	private AnimicEvent(AnimicEventId id, PatientId patientId, AnimicTypeId typeId, UTCDateTime ocurrenceDate) {
		setId( id );
		setPatientId( patientId );
		setTypeId( typeId );
		setOcurrenceDate( ocurrenceDate );
	}

	public static AnimicEvent of(AnimicEventId id, PatientId patientId, AnimicTypeId typeId, UTCDateTime ocurrenceDate) {
		return new AnimicEvent( id, patientId, typeId, ocurrenceDate );
	}

	public PatientId patientId() {
		return patientId;
	}

	public AnimicTypeId type() {
		return type;
	}

	public UTCDateTime ocurrenceDate() {
		return ocurrenceDate;
	}

	private void setOcurrenceDate(UTCDateTime ocurrenceDate) {
		this.ocurrenceDate = requireNonNull( ocurrenceDate, "Es obligatoria la fecha en la que se genera el evento." );
	}

	private void setTypeId(AnimicTypeId typeId) {
		this.type = requireNonNull( typeId, "Es necesario el tipo de animo generado en el evento." );
	}

	private void setPatientId(PatientId patientId) {
		this.patientId = requireNonNull( patientId, "Es necesario el paciente que genero el evento." );
	}

	public void updateFrom(AnimicEvent event) {
		setTypeId( event.type() );
		setOcurrenceDate( event.ocurrenceDate() );
	}

	@Override
	public AnimicEventId id() {
		return id;
	}

	@Override
	public void setId(AnimicEventId id) {
		this.id = id;
	}
}
