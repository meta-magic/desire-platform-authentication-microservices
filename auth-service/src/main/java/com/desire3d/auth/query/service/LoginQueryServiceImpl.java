package com.desire3d.auth.query.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.desire3d.auth.beans.LoginInfoHelperBean;
import com.desire3d.auth.exceptions.BusinessServiceException;
import com.desire3d.auth.exceptions.DataNotFoundException;
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
	private LoginHistoryQueryRepository loginHistoryQRepo;

	@Autowired
	private LoginInfoHelperBean loginInfoHelperBean;

	@Override
	public boolean userLogout() throws BusinessServiceException, DataNotFoundException {

		String appSessionId = loginInfoHelperBean.getAppSessionId();
		String userId = loginInfoHelperBean.getUserId();
		if (appSessionId == null && userId == null) {
			throw new BusinessServiceException("user or appsessionid is null", "userid.null");
		} else {

			// UPDATE APPSSESSION ACTIVE STATUS
			AppSession appSession = appSessionQRepo.findAppSessionByAppSessionIdAndIsActive(appSessionId, true);
			appSession.setIsActive(false);
			AuditDetails auditDetails = new AuditDetails(loginInfoHelperBean.getUserId(), new Date(System.currentTimeMillis()));
			appSession.setAuditDetails(auditDetails);
			appSessionRepo.update(appSession);

			// UPDATE HISTRY TABLE
			LoginHistory loginHistory = loginHistoryQRepo.findLoginHistoryByAppSessionIDAndUserUUIDAndIsActive(appSessionId, userId, true);
			System.err.println("loginHistory : " + loginHistory);
			if (loginHistory != null) {
				loginHistory.setIsActive(false);
				loginHistory.setAuditDetails(auditDetails);
				loginHistoryRepo.save(loginHistory);
			}
			return true;
		}
	}
}

// TODO Auto-generated method stub// TODO Auto-generated method stub