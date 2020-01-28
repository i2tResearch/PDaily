package co.icesi.pdaily.events.physical.injury.type.domain.model;


import co.haruk.core.domain.model.entity.PlainName;
import co.icesi.pdaily.common.model.tenancy.HarukTenantEntity;

import javax.persistence.*;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

@Entity
@Table(name = "event_physical_injury_types")
@NamedQuery(name = InjuryType.findByName, query = "SELECT t FROM InjuryType t WHERE t.tenant = :company AND UPPER(t.name) = UPPER(:name)")
public class InjuryType extends HarukTenantEntity<InjuryTypeId> {
    private static final String PREFIX = "InjuryType.";
    public static final String findByName = PREFIX + "findByName";

    @EmbeddedId
    private InjuryTypeId id;
    @Embedded
    private PlainName name;

    protected InjuryType() {
    }

    private InjuryType(InjuryTypeId id, PlainName name) {
        setId( id );
        setName( name );
    }

    public static InjuryType of(InjuryTypeId id, PlainName name) {
        return new InjuryType( id, name );
    }

    @Override
    public InjuryTypeId id() {
        return id;
    }

    @Override
    public void setId(InjuryTypeId id) {
        this.id = id;
    }

    public PlainName name() {
        return this.name;
    }

    public void setName(PlainName name) {
        this.name = requireNonNull( name, "El nombre de la lesion es requerido" );
    }
}
