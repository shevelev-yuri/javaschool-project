package com.tsystems.ecm.mapper;

import com.tsystems.ecm.dto.TreatmentDto;
import com.tsystems.ecm.entity.TreatmentEntity;
import org.springframework.stereotype.Component;

@Component
public class TreatmentEntityToTreatmentDtoMapper {

    public TreatmentDto map(TreatmentEntity src) {
        TreatmentDto dst = new TreatmentDto();

        dst.setTreatmentId(src.getTreatmentId());
        dst.setTreatmentName(src.getTreatmentName());
        dst.setTreatmentType(src.getTreatmentType());

        return dst;
    }
}
