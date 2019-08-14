package com.tsystems.ecm.dao;

import com.tsystems.ecm.entity.TreatmentEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

@Repository
public class TreatmentDao extends AbstractDao<TreatmentEntity> {

    private static final Logger log = LogManager.getLogger(TreatmentDao.class);

    private static final String SELECT_ALL_TREATMENTS = "FROM TreatmentEntity";

    private static final String SELECT_TREATMENT_BY_ID = "FROM TreatmentEntity WHERE id = ?1";

    public List<TreatmentEntity> getAll() {
        return getSessionFactory().getCurrentSession()
                .createQuery(SELECT_ALL_TREATMENTS, TreatmentEntity.class)
                .getResultList();
    }

    public TreatmentEntity get(long id) {
        Query query = getSessionFactory().getCurrentSession()
                .createQuery(SELECT_TREATMENT_BY_ID, TreatmentEntity.class)
                .setParameter(1, id);

        TreatmentEntity entity;
        try {
            entity = (TreatmentEntity) query.getSingleResult();
        } catch (NoResultException nre) {
            //TODO handle
            log.debug(nre.getMessage());

            return null;
        } catch (Exception e) {
            //TODO handle
            log.warn(e.getMessage());

            return null;
        }
        log.debug("Treatment with ID \"{}\" found!", id);

        return entity;
    }
}
