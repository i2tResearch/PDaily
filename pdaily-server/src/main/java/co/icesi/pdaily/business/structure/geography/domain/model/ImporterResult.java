package co.icesi.pdaily.business.structure.geography.domain.model;

/**
 * @author cristhiank on 5/12/19
 **/
public final class ImporterResult {
	private final String importer;
	private final boolean success;
	private final String message;

	private ImporterResult(String importer, boolean success, String message) {
		this.importer = importer;
		this.success = success;
		this.message = message;
	}

	public static ImporterResult success(String importer) {
		return new ImporterResult( importer, true, "OK" );
	}

	public static ImporterResult failure(String importer, String message) {
		return new ImporterResult( importer, false, message );
	}

	public String getImporter() {
		return importer;
	}

	public String getMessage() {
		return message;
	}

	public boolean isSuccess() {
		return success;
	}
}
