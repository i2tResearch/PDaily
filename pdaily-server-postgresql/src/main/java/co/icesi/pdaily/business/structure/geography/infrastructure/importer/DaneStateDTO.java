package co.icesi.pdaily.business.structure.geography.infrastructure.importer;

/**
 * @author andres2508 on 2/12/19
 **/
public final class DaneStateDTO {
	public String c_digo_dane_del_departamento;
	public String departamento;

	protected DaneStateDTO() {
	}

	private DaneStateDTO(String code, String name) {
		this.c_digo_dane_del_departamento = code;
		this.departamento = name;
	}

	public static DaneStateDTO of(String code, String name) {
		return new DaneStateDTO( code, name );
	}

	public String departamentoSanitized() {
		return departamento != null ? departamento.trim().toUpperCase() : null;
	}

	public String departamentoCode() {
		return c_digo_dane_del_departamento != null ? c_digo_dane_del_departamento.trim().toUpperCase() : null;
	}
}
