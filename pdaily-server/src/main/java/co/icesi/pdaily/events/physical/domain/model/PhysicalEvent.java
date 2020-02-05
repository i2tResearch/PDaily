package co.icesi.pdaily.events.physical.domain.model;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.*;

import co.haruk.core.StreamUtils;
import co.icesi.pdaily.business.structure.patient.domain.model.PatientId;
import co.icesi.pdaily.common.model.UTCDateTime;
import co.icesi.pdaily.common.model.tenancy.HarukTenantEntity;
import co.icesi.pdaily.events.physical.domain.model.detail.BodyPartDetail;
import co.icesi.pdaily.events.physical.injury.type.domain.model.InjuryTypeId;

@Entity
@Table(name = "event_physical_events")
@NamedQuery(name = PhysicalEvent.findByPatientAsReadView, query = "SELECT new co.icesi.pdaily.events.physical.domain.model.view.PhysicalEventReadView(p.id.id, i.id.id, i.name.name, p.initialDate.date, p.finalDate.date, p.intensity.intensity) "
		+
		" FROM PhysicalEvent p INNER JOIN InjuryType i ON i.id = p.injuryType WHERE p.patientId = :patientId")
public class PhysicalEvent extends HarukTenantEntity<PhysicalEventId> {
	private static final String PREFIX = "PhysicalEvent.";
	public static final String findByPatientAsReadView = PREFIX + "findByPatientAsReadView";

	@EmbeddedId
	private PhysicalEventId id;
	@Embedded
	@AttributeOverride(name = "id", column = @Column(name = "patient_id"))
	private PatientId patientId;
	@Embedded
	private Intensity intensity;
	@Embedded
	@AttributeOverride(name = "id", column = @Column(name = "injury_type_id"))
	private InjuryTypeId injuryType;
	@Embedded
	@AttributeOverride(name = "date", column = @Column(name = "initial_date"))
	private UTCDateTime initialDate;
	@Embedded
	@AttributeOverride(name = "date", column = @Column(name = "final_date"))
	private UTCDateTime finalDate;
	@OneToMany(mappedBy = "physicalEvent", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<BodyPartDetail> bodyDetails;

	protected PhysicalEvent() {
	}

	private PhysicalEvent(PhysicalEventId id, PatientId patientId, Intensity intensity, InjuryTypeId injuryType,
			UTCDateTime initialDate, UTCDateTime finalDate) {
		setId( id );
		setPatientId( patientId );
		setIntensity( intensity );
		setInjuryType( injuryType );
		setInitialDate( initialDate );
		setFinalDate( finalDate );
		this.bodyDetails = new HashSet<BodyPartDetail>();
	}

	public static PhysicalEvent of(PhysicalEventId id, PatientId patientId, Intensity intensity, InjuryTypeId injuryType,
			UTCDateTime initialDate, UTCDateTime finalDate) {
		return new PhysicalEvent( id, patientId, intensity, injuryType, initialDate, finalDate );
	}

	private void setFinalDate(UTCDateTime finalDate) {
		this.finalDate = requireNonNull( finalDate, "La fecha en la que termina el evento es necesaria." );
	}

	private void setInitialDate(UTCDateTime initialDate) {
		this.initialDate = requireNonNull( initialDate, "La fecha en la que inicia el evento es necesaria." );
	}

	private void setInjuryType(InjuryTypeId injuryType) {
		this.injuryType = requireNonNull( injuryType, "El tipo de lesion que ocurrio en el evento es necesario." );
	}

	private void setPatientId(PatientId patientId) {
		this.patientId = requireNonNull( patientId, "Es necesario el paciente que genero el evento." );
	}

	private void setBodyDetails(Set<BodyPartDetail> bodyDetails) {
		// Removed details
		Iterator<BodyPartDetail> iterator = this.bodyDetails.iterator();
		while ( iterator.hasNext() ) {
			BodyPartDetail bodyDetail = iterator.next();
			if ( !bodyDetails.contains( bodyDetail ) ) {
				this.bodyDetails.remove( bodyDetail );
				bodyDetail.removeAssociationWithPhysicalEvent();
			}
		}
		// updates or insert new details
		for ( BodyPartDetail det : bodyDetails ) {
			var found = StreamUtils.find( this.bodyDetails, it -> it.equals( det ) );
			if ( found.isPresent() ) {
				// error if its present
				if ( !found.get().id().equals( det.id() ) ) {
					throw new IllegalStateException( "No se permiten dos partes del cuerpo afectadas iguales en el detalle." );
				}
			} else {
				// insert
				this.addBodyPartDetail( det );
			}
		}
	}

	public void addBodyPartDetail(BodyPartDetail det) {
		this.bodyDetails.add( det );
	}

	private void setIntensity(Intensity intensity) {
		this.intensity = intensity;
	}

	@Override
	public PhysicalEventId id() {
		return id;
	}

	@Override
	public void setId(PhysicalEventId id) {
		this.id = id;
	}

	public PatientId patientId() {
		return patientId;
	}

	public Intensity intensity() {
		return intensity;
	}

	public InjuryTypeId injuryType() {
		return injuryType;
	}

	public UTCDateTime initialDate() {
		return initialDate;
	}

	public UTCDateTime finalDate() {
		return finalDate;
	}

	public Set<BodyPartDetail> bodyDetails() {
		return bodyDetails;
	}

	public void updateFrom(PhysicalEvent event) {
		setIntensity( event.intensity );
		setInitialDate( event.initialDate );
		setFinalDate( event.finalDate );
		setBodyDetails( event.bodyDetails );
	}
}
