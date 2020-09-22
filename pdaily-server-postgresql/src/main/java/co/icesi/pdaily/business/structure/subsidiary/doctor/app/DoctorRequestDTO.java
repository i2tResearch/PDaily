package co.icesi.pdaily.business.structure.subsidiary.doctor.app;

/**
 * @author andres2508 on 25/11/19
 **/
public final class DoctorRequestDTO {
	public String reference;
	public String subsidiaryId;

	protected DoctorRequestDTO() {
	}

	private DoctorRequestDTO(String reference, String subsidiaryId) {
		this.reference = reference;
		this.subsidiaryId = subsidiaryId;
	}

	public static DoctorRequestDTO of(String reference, String subsidiaryId) {
		return new DoctorRequestDTO( reference, subsidiaryId );
	}
}
