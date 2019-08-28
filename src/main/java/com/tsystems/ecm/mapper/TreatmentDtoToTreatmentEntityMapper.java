package com.tsystems.ecm.mapper;

import com.tsystems.ecm.dto.TreatmentDto;
import com.tsystems.ecm.entity.Treatment;
import org.springframework.stereotype.Component;

@Component
public class TreatmentDtoToTreatmentEntityMapper {

    public Treatment map(TreatmentDto src) {
        Treatment dst = new Treatment();

        dst.setTreatmentName(src.getTreatmentName());
        dst.setTreatmentType(src.getTreatmentType());

        return dst;
    }

}
