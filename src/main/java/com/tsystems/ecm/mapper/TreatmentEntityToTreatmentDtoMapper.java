package com.tsystems.ecm.mapper;

import com.tsystems.ecm.dto.TreatmentDto;
import com.tsystems.ecm.entity.Treatment;
import org.springframework.stereotype.Component;

/**
 * Mapper class
 */
@Component
public class TreatmentEntityToTreatmentDtoMapper {

    /**
     * Maps entity to DTO.
     *
     * @param src the source entity to map from
     * @return the DTO to map to
     */
    public TreatmentDto map(Treatment src) {
        TreatmentDto dst = new TreatmentDto();

        dst.setId(src.getId());
        dst.setTreatmentName(src.getTreatmentName());
        dst.setTreatmentType(src.getTreatmentType());

        return dst;
    }
}
