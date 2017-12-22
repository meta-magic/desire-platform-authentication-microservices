package com.desire3d.auth.query.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.desire3d.auth.beans.LoginInfoHelperBean;
import com.desire3d.auth.exceptions.BusinessServiceException;
import com.desire3d.auth.exceptions.DataNotFoundException;
import com.desire3d.auth.exceptions.PersistenceException;
import com.desire3d.auth.fw.command.repository.AppSessionCommandRepository;
import com.desire3d.auth.fw.command.repository.LoginHistoryCommandRepository;
import com.desire3d.auth.fw.query.repository.AppSessionQueryRepository;
import com.desire3d.auth.fw.query.repository.LoginHistoryQueryRepository;
import com.desire3d.auth.fw.query.service.LoginQueryService;
import com.desire3d.auth.model.AuditDetails;
import com.desire3d.auth.model.transactions.AppSession;
import com.desire3d.auth.model.transactions.LoginHistory;

@Service
@Scope(value = "request")
public class LoginQueryServiceImpl implements LoginQueryService {

	@Autowired
	private AppSessionCommandRepository appSessionRepo;

	@Autowired
	private AppSessionQueryRepository appSessionQRepo;

	@Autowired
	private LoginHistoryCommandRepository loginHistoryRepo;

	@Autowired
	private LoginInfoHelperBean loginInfoHelperBean;

	@Override
	public boolean userLogout() throws BusinessServiceException, DataNotFoundException, PersistenceException {

		String appSessionId = loginInfoHelperBean.getAppSessionId();
		String userId = loginInfoHelperBean.getUserId();
		if (appSessionId == null && userId == null) {
			throw new BusinessServiceException("user or appsessionid is null", "userid.null");
		} else {
			// UPDATE APPSSESSION ACTIVE STATUS
			AppSession appSession = appSessionQRepo.findAppSessionByAppSessionIdAndIsActive(appSessionId, true);
			appSession.setIsActive(false);
			appSession.setAuditDetails(
					new AuditDetails(loginInfoHelperBean.getUserId(), new Date(System.currentTimeMillis())));
			appSessionRepo.update(appSession);

			// ADD ENTRY IN LOGIN HISTORY FOR LOGOUT
			LoginHistory loginHistory = new LoginHistory(loginInfoHelperBean.getMteid(),
					loginInfoHelperBean.getUserId(), loginInfoHelperBean.getAppSessionId(), 2, 1, "127.0.0.1", "chrome",
					"", 0.0, 0.0);
			loginHistory.setAuditDetails(
					new AuditDetails(loginInfoHelperBean.getUserId(), new Date(System.currentTimeMillis()),
							loginInfoHelperBean.getUserId(), new Date(System.currentTimeMillis())));
			loginHistoryRepo.save(loginHistory);
			return true;
		}
	}
}