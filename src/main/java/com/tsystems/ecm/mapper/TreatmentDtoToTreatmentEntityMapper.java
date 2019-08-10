package com.tsystems.ecm.mapper;

import com.tsystems.ecm.dto.TreatmentDto;
import com.tsystems.ecm.entity.TreatmentEntity;
import org.springframework.stereotype.Component;

@Component
public class TreatmentDtoToTreatmentEntityMapper {

    public TreatmentEntity map(TreatmentDto src) {
        TreatmentEntity dst = new TreatmentEntity();
        dst.setTreatmentName(src.getTreatmentName());
        dst.setTreatmentType(src.getTreatmentType());

        return dst;
    }

}
