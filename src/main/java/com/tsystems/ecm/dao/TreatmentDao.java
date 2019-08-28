package com.tsystems.ecm.dao;

import com.tsystems.ecm.entity.Treatment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

@Repository
public class TreatmentDao extends AbstractDao<Treatment> {

    private static final Logger log = LogManager.getLogger(TreatmentDao.class);

    private static final String SELECT_ALL_TREATMENTS = "FROM Treatment";

    private static final String SELECT_TREATMENT_BY_ID = "FROM Treatment WHERE id = ?1";

    public List<Treatment> getAll() {
        return getSessionFactory().getCurrentSession()
                .createQuery(SELECT_ALL_TREATMENTS, Treatment.class)
                .getResultList();
    }

    public Treatment get(long id) {
        Query query = getSessionFactory().getCurrentSession()
                .createQuery(SELECT_TREATMENT_BY_ID, Treatment.class)
                .setParameter(1, id);

        Treatment entity;
        try {
            entity = (Treatment) query.getSingleResult();
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
