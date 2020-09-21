package co.icesi.pdaily.security.user.domain.model;

import static co.haruk.core.domain.model.guards.Guards.require;
import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author andres2508 on 15/11/19
 **/
@Embeddable
public class Password {

	@Column(name = "hash_iterations")
	private int hashIterations;
	private String salt;
	private String text;

	protected Password() {
	}

	private Password(int hashIterations, String salt, String password) {
		setHashIterations( hashIterations );
		setSalt( salt );
		setText( password );
	}

	public static Password of(int hashIterations, String salt, String password) {
		return new Password( hashIterations, salt, password );
	}

	public String text() {
		return text;
	}

	private void setText(String password) {
		this.text = requireNonNull( password, "La contraseña es requerida" );
	}

	public int hashIterations() {
		return hashIterations;
	}

	private void setHashIterations(int hashIterations) {
		require( hashIterations > 0, "Las iteraciones deben ser mayor a cero" );
		this.hashIterations = hashIterations;
	}

	public String salt() {
		return salt;
	}

	private void setSalt(String salt) {
		this.salt = requireNonNull( salt, "salt de seguridad es requerido" );
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}
		Password password = (Password) o;
		return hashIterations == password.hashIterations &&
				salt.equals( password.salt ) &&
				text.equals( password.text );
	}

	@Override
	public int hashCode() {
		return Objects.hash( hashIterations, salt, text );
	}
}
