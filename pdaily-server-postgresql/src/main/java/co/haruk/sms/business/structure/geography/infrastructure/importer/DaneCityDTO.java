package co.haruk.sms.business.structure.geography.infrastructure.importer;

/**
 * @author andres2508 on 2/12/19
 **/
public final class DaneCityDTO {
	public String municipio;
	public String c_digo_dane_del_municipio;

	protected DaneCityDTO() {
	}

	private DaneCityDTO(String code, String name) {
		this.municipio = name;
		this.c_digo_dane_del_municipio = code;
	}

	public static DaneCityDTO of(String code, String name) {
		return new DaneCityDTO( code, name );
	}

	public String municipioSanitized() {
		return municipio != null ? municipio.trim().toUpperCase() : null;
	}

	public String municipioCode() {
		return c_digo_dane_del_municipio != null ? c_digo_dane_del_municipio.trim().toUpperCase() : null;
	}
}
