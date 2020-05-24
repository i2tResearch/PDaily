package co.haruk.sms.security.authorization.app;

/**
 * @author cristhiank on 3/5/20
 **/
public class AuthorizationResult {
	public boolean granted;

	protected AuthorizationResult() {
	}

	private AuthorizationResult(boolean granted) {
		this.granted = granted;
	}

	public static AuthorizationResult of(boolean granted) {
		return new AuthorizationResult( granted );
	}
}
