package com.tsystems.ecm.dao;

import com.tsystems.ecm.entity.Treatment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

/**
 * Dao class for treatment entities.
 *
 * @author Yurii Shevelev
 * @version 1.0.0
 */
@Repository
public class TreatmentDao extends AbstractDao<Treatment> {

    /**
     * Log4j logger.
     */
    private static final Logger log = LogManager.getLogger(TreatmentDao.class);

    /**
     * HQL query to get all treatments.
     */
    private static final String SELECT_ALL_TREATMENTS = "FROM Treatment";

    /**
     * HQL query to get treatment by id.
     */
    private static final String SELECT_TREATMENT_BY_ID = "FROM Treatment WHERE id = ?1";

    /**
     * Gets the list containing all treatments.
     *
     * @return the {@code List} containing all treatments
     */
    public List<Treatment> getAll() {
        return getSessionFactory().getCurrentSession()
                .createQuery(SELECT_ALL_TREATMENTS, Treatment.class)
                .getResultList();
    }

    /**
     * Gets the treatment by its id.
     *
     * @param id the treatment's id
     * @return the treatment entity
     */
    public Treatment get(long id) {
        Query query = getSessionFactory().getCurrentSession()
                .createQuery(SELECT_TREATMENT_BY_ID, Treatment.class)
                .setParameter(1, id);

        Treatment entity;
        try {
            entity = (Treatment) query.getSingleResult();
        } catch (NoResultException nre) {
            log.debug(nre.getMessage());

            return null;
        } catch (Exception e) {
            log.warn(e.getMessage());

            return null;
        }
        log.debug("Treatment with ID \"{}\" found!", id);

        return entity;
    }
}
