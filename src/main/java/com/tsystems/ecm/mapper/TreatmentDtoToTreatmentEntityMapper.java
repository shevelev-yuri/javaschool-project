package com.tsystems.ecm.mapper;

import com.tsystems.ecm.dto.TreatmentDto;
import com.tsystems.ecm.entity.Treatment;
import org.springframework.stereotype.Component;

/**
 * Mapper class
 */
@Component
public class TreatmentDtoToTreatmentEntityMapper {

    /**
     * Maps DTO to entity.
     *
     * @param src the source DTO to map from
     * @return the entity to map to
     */
    public Treatment map(TreatmentDto src) {
        Treatment dst = new Treatment();

        dst.setTreatmentName(src.getTreatmentName());
        dst.setTreatmentType(src.getTreatmentType());

        return dst;
    }

}
