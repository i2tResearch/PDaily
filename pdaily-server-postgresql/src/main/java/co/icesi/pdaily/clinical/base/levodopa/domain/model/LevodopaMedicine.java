package co.icesi.pdaily.clinical.base.levodopa.domain.model;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import javax.persistence.*;

import co.haruk.core.domain.model.entity.PlainName;
import co.icesi.pdaily.clinical.base.levodopa.type.domain.model.LevodopaTypeId;
import co.icesi.pdaily.common.model.MedicineConcentration;
import co.icesi.pdaily.common.model.tenancy.PdailyTenantEntity;

@Entity
@Table(name = "medicine_levodopa")
@NamedQuery(name = LevodopaMedicine.findByName, query = "SELECT l FROM LevodopaMedicine l WHERE l.tenant = :company AND UPPER(l.name) = UPPER(:name)")
@NamedQuery(name = LevodopaMedicine.findAllAsReadView, query = "SELECT new co.icesi.pdaily.clinical.base.levodopa.domain.model.view.LevodopaMedicineReadView( lm.id.id, lm.name.name, lt.id.id, lt.label.name, lm.dose.value ) "
		+
		"FROM LevodopaMedicine lm " +
		"INNER JOIN LevodopaType lt ON lm.typeId = lt.id " +
		"WHERE lm.tenant = :company")
@NamedQuery(name = LevodopaMedicine.findByIdAsReadView, query = "SELECT new co.icesi.pdaily.clinical.base.levodopa.domain.model.view.LevodopaMedicineReadView( lm.id.id, lm.name.name, lt.id.id, lt.label.name, lm.dose.value ) "
		+
		"FROM LevodopaMedicine lm " +
		"INNER JOIN LevodopaType lt ON lm.typeId = lt.id " +
		"WHERE lm.tenant = :company " +
		"AND lm.id = :id")
public class LevodopaMedicine extends PdailyTenantEntity<LevodopaMedicineId> {
	private static final String PREFIX = "LevodopaMedicine.";
	public static final String findByName = PREFIX + "findByName";
	public static final String findAllAsReadView = PREFIX + "findAllAsReadView";
	public static final String findByIdAsReadView = PREFIX + "findByIdAsReadView";

	@EmbeddedId
	private LevodopaMedicineId id;
	@Embedded
	private PlainName name;
	@Embedded
	@AttributeOverride(name = "id", column = @Column(name = "type_id"))
	private LevodopaTypeId typeId;
	@Embedded
	@AttributeOverride(name = "value", column = @Column(name = "dose_value"))
	private MedicineConcentration dose;

	protected LevodopaMedicine() {
	}

	private LevodopaMedicine(LevodopaMedicineId id, PlainName name, LevodopaTypeId typeId, MedicineConcentration dose) {
		setId( id );
		setName( name );
		setType( typeId );
		setDose( dose );
	}

	public static LevodopaMedicine of(LevodopaMedicineId id, PlainName name,
			LevodopaTypeId typeId, MedicineConcentration dose) {
		return new LevodopaMedicine( id, name, typeId, dose );
	}

	public PlainName name() {
		return name;
	}

	public LevodopaTypeId typeId() {
		return typeId;
	}

	public MedicineConcentration dose() {
		return dose;
	}

	private void setName(PlainName name) {
		this.name = requireNonNull( name, "El nombre del medicamento tipo levodopa es necesario." );
	}

	private void setType(LevodopaTypeId typeId) {
		this.typeId = requireNonNull( typeId, "El tipo del medicamento es necesario." );
	}

	private void setDose(MedicineConcentration dose) {
		this.dose = requireNonNull( dose, "La concentracion en mg que corresponde al medicamento es necesaria." );
	}

	@Override
	public LevodopaMedicineId id() {
		return id;
	}

	@Override
	public void setId(LevodopaMedicineId id) {
		this.id = id;
	}

	public void updateFrom(LevodopaMedicine updated) {
		setDose( updated.dose );
		setName( updated.name );
		setType( updated.typeId );
	}
}
