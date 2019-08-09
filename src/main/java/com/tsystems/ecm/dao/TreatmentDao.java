package com.tsystems.ecm.dao;

import com.tsystems.ecm.entity.TreatmentEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TreatmentDao extends AbstractDao<TreatmentEntity> {

    private static final Logger log = LogManager.getLogger(UserDao.class);

    private static final String SELECT_ALL_TREATMENT_ENTITY = "FROM TreatmentEntity";

    public List<TreatmentEntity> getAll() {
        return getSessionFactory().getCurrentSession().createQuery(SELECT_ALL_TREATMENT_ENTITY, TreatmentEntity.class).getResultList();
    }
}
