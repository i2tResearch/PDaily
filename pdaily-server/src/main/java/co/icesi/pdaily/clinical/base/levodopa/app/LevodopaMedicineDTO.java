package co.icesi.pdaily.clinical.base.levodopa.app;

import co.haruk.core.domain.model.entity.PlainName;
import co.icesi.pdaily.clinical.base.levodopa.domain.model.LevodopaMedicine;
import co.icesi.pdaily.clinical.base.levodopa.domain.model.LevodopaMedicineId;
import co.icesi.pdaily.clinical.base.levodopa.type.domain.model.LevodopaTypeId;
import co.icesi.pdaily.common.model.MedicineDose;

public class LevodopaMedicineDTO {
    public String id;
    public String name;
    public String typeId;
    public int dose;

    protected LevodopaMedicineDTO() {
    }

    private LevodopaMedicineDTO(String id, String name, String typeId, int dose) {
        this.id = id;
        this.name = name;
        this.typeId = typeId;
        this.dose = dose;
    }

    public static LevodopaMedicineDTO of(String id, String name, String typeId, int dose) {
        return new LevodopaMedicineDTO( id, name, typeId, dose );
    }

    public static LevodopaMedicineDTO of(LevodopaMedicine levodopa) {
        return new LevodopaMedicineDTO(
                levodopa.id().text(),
                levodopa.name().text(),
                levodopa.typeId().text(),
                levodopa.dose().value()
        );
    }

    public LevodopaMedicine toLevodopaMedicine() {
        return LevodopaMedicine.of(
                LevodopaMedicineId.of( id ),
                PlainName.of( name ),
                LevodopaTypeId.ofNotNull( id ),
                MedicineDose.of( dose )
        );
    }
}
