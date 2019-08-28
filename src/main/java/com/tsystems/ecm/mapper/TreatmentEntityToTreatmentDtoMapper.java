package com.tsystems.ecm.mapper;

import com.tsystems.ecm.dto.TreatmentDto;
import com.tsystems.ecm.entity.Treatment;
import org.springframework.stereotype.Component;

@Component
public class TreatmentEntityToTreatmentDtoMapper {

    public TreatmentDto map(Treatment src) {
        TreatmentDto dst = new TreatmentDto();

        dst.setId(src.getId());
        dst.setTreatmentName(src.getTreatmentName());
        dst.setTreatmentType(src.getTreatmentType());

        return dst;
    }
}
