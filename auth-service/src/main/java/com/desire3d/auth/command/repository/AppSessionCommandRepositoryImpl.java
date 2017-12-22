package com.desire3d.auth.command.repository;

import java.util.Date;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.desire3d.auth.exceptions.PersistenceException;
import com.desire3d.auth.fw.command.repository.AppSessionCommandRepository;
import com.desire3d.auth.model.AuditDetails;
import com.desire3d.auth.model.transactions.AppSession;

@Repository
public class AppSessionCommandRepositoryImpl implements AppSessionCommandRepository {

	@Autowired
	private PersistenceManagerFactory pmf;

	@Override
	public AppSession delete(String appSessionId) {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		AppSession appSession = null;
		try {
			tx.begin();
			appSession = pm.getObjectById(AppSession.class, appSessionId);
			if (appSession.getIsActive()) {
				appSession.setIsActive(false);
			}
			appSession = pm.makePersistent(appSession);
			tx.commit();
		} catch (Exception e) {
			if (tx.isActive()) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			pm.close();
		}
		return appSession;
	}

	@Override
	public AppSession update(AppSession appSession) throws PersistenceException {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			AppSession appSession_old = pm.getObjectById(AppSession.class, appSession.getAppSessionId());
			appSession_old.setIsActive(false);

			AuditDetails auditDetails = new AuditDetails();
			if (appSession.getAuditDetails() != null) {
				auditDetails.setUpdatedBy(appSession.getAuditDetails().getUpdatedBy());
			}
			auditDetails.setUpdatedTime(new Date(System.currentTimeMillis()));
			appSession.setAuditDetails(auditDetails);

			System.err.println("Before update : " + appSession_old);
			appSession = pm.makePersistent(appSession_old);
			System.err.println("After update : " + appSession_old);

			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (tx.isActive()) {
				tx.rollback();
			}
			throw new PersistenceException(e.getMessage());

		} finally {
			// pm.close();
		}
		return appSession;
	}

	@Override
	public AppSession save(AppSession appSession) throws PersistenceException {
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			appSession = pm.makePersistent(appSession);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (tx.isActive()) {
				tx.rollback();
			}
			throw new PersistenceException(e.getMessage());
		} finally {
			pm.close();
		}
		return appSession;
	}
}
