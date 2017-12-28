package com.desire3d.auth.query.service;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.desire3d.auth.beans.LoginInfoHelperBean;
import com.desire3d.auth.exceptions.BaseDomainServiceException;
import com.desire3d.auth.fw.command.repository.AppSessionCommandRepository;
import com.desire3d.auth.fw.command.repository.LoginHistoryCommandRepository;
import com.desire3d.auth.fw.query.repository.AppSessionQueryRepository;
import com.desire3d.auth.fw.query.service.LoginQueryService;
import com.desire3d.auth.model.AuditDetails;
import com.desire3d.auth.model.transactions.AppSession;
import com.desire3d.auth.model.transactions.LoginHistory;
import com.desire3d.auth.utils.ExceptionID;

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
	public boolean userLogout(HttpServletRequest request) throws Throwable {

		String appSessionId = loginInfoHelperBean.getAppSessionId();
		String userId = loginInfoHelperBean.getUserId();
		if (appSessionId == null && userId == null) {
			throw new BaseDomainServiceException(ExceptionID.INVALID_USERSESSION);
		} else {
			// UPDATE APPSSESSION ACTIVE STATUS
			AppSession appSession = appSessionQRepo.findAppSessionByAppSessionIdAndIsActive(appSessionId, true);
			appSession.setIsActive(false);
			appSession.setAuditDetails(new AuditDetails(loginInfoHelperBean.getUserId(), new Date(System.currentTimeMillis())));
			appSessionRepo.update(appSession);

			// ADD REAL DATA FROM REQUEST
			LoginHistory loginHistory = new LoginHistory(loginInfoHelperBean.getMteid(), loginInfoHelperBean.getUserId(), loginInfoHelperBean.getAppSessionId(),
					2, 1, request.getHeader("host"), request.getHeader("User-Agent"), request.getHeader("User-Agent"), 0.0, 0.0);
			loginHistory.setAuditDetails(new AuditDetails(loginInfoHelperBean.getUserId(), new Date(System.currentTimeMillis()),
					loginInfoHelperBean.getUserId(), new Date(System.currentTimeMillis())));
			loginHistoryRepo.save(loginHistory);
			return true;
		}
	}
}