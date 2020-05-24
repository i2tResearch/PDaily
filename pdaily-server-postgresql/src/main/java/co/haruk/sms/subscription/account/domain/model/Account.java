package co.haruk.sms.subscription.account.domain.model;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.Objects;

import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.sms.common.model.PdailyEntity;
import co.haruk.sms.common.model.TaxID;

/**
 * @author andres2508 on 30/10/19
 **/
@Entity
@Table(name = "subs_accounts")
@NamedQuery(name = Account.findByTaxID, query = "SELECT c FROM Account c WHERE c.taxID = :taxID")
@NamedQuery(name = Account.findByName, query = "SELECT c FROM Account c WHERE UPPER(c.name.name) = UPPER(:name)")
public class Account extends PdailyEntity<AccountId> {

	private static final String PREFIX = "Account.";
	public static final String findByTaxID = PREFIX + "findByTaxID";
	public static final String findByName = PREFIX + "findByName";

	@EmbeddedId
	private AccountId id;
	@Embedded
	private PlainName name;
	@Embedded
	private TaxID taxID;

	protected Account() {
	}

	private Account(AccountId id, PlainName name, TaxID taxID) {
		setId( id );
		setTaxID( taxID );
		setName( name );
	}

	public static Account of(AccountId id, PlainName name, TaxID taxID) {
		return new Account( id, name, taxID );
	}

	@Override
	public AccountId id() {
		return id;
	}

	public PlainName name() {
		return name;
	}

	@Override
	public void setId(AccountId id) {
		this.id = id;
	}

	public void setTaxID(TaxID taxID) {
		this.taxID = requireNonNull( taxID, "El identificador fiscal es requerido" );
	}

	public TaxID taxID() {
		return taxID;
	}

	public void setName(PlainName name) {
		this.name = requireNonNull( name, "El nombre de la empresa es requerido" );
	}

	@Override
	public boolean equals(Object o) {
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}
		return super.equalsInternal( (Account) o, other -> Objects.equals( taxID, other.taxID ) );
	}

	@Override
	public int hashCode() {
		return super.hashCode( () -> Objects.hash( taxID ) );
	}
}
