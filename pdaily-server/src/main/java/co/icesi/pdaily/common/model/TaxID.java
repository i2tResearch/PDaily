package co.icesi.pdaily.common.model;

import static co.haruk.core.domain.model.guards.Guards.require;
import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.io.Serializable;
import java.util.Objects;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author cristhiank on 14/11/19
 **/
@Embeddable
public class TaxID implements Serializable {
	private static final String regex = "^\\d{9,15}(-\\d{1})?$";
	private static final Pattern pattern = Pattern.compile( regex );
	@Column(name = "tax_id")
	private String text;

	protected TaxID() {
	}

	private TaxID(String taxId) {
		setTaxId( taxId );
	}

	public static TaxID of(String taxId) {
		return new TaxID( taxId );
	}

	public static TaxID ofNullable(String taxId) {
		if ( taxId == null ) {
			return null;
		}
		return new TaxID( taxId );
	}

	public String text() {
		return text;
	}

	private void setTaxId(String taxId) {
		final var finalTaxId = requireNonNull( taxId, "El identificador fiscal es requerido" ).trim();
		require(
				pattern.matcher( finalTaxId ).matches(),
				String.format( "El identificador fiscal (%s) es invalido", finalTaxId )
		);
		this.text = finalTaxId;
	}

	@Override
	public String toString() {
		return text;
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}
		TaxID taxID = (TaxID) o;
		return text.equalsIgnoreCase( taxID.text );
	}

	@Override
	public int hashCode() {
		return Objects.hash( text );
	}
}
