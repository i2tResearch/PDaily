package co.haruk.sms.core.database.upgrader;

/**
 * @author cristhiank on 30/10/19
 **/
public final class UpgraderConfig {
	final String locations;

	private UpgraderConfig(String locations) {
		this.locations = locations;
	}

	public static UpgraderConfig of(String locations) {
		return new UpgraderConfig( locations );
	}
}
