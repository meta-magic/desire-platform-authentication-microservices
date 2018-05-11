package com.desire3d.auth.query.service;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.desire3d.auth.beans.LoginInfoHelperBean;
import com.desire3d.auth.exceptions.DataRetrievalFailureException;
import com.desire3d.auth.fw.command.repository.AppSessionCommandRepository;
import com.desire3d.auth.fw.command.repository.LoginHistoryCommandRepository;
import com.desire3d.auth.fw.query.repository.AppSessionQueryRepository;
import com.desire3d.auth.fw.query.service.LoginQueryService;
import com.desire3d.auth.model.AuditDetails;
import com.desire3d.auth.model.transactions.AppSession;
import com.desire3d.auth.model.transactions.LoginHistory;
import com.desire3d.auth.utils.Constants;
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
	
//	@Autowired
//	private UserAuthenticationEventPublisher publisher;
	
	@Override
	public boolean userLogout(Double latitude, Double longitude, HttpServletRequest request) throws Throwable {

		String appSessionId = loginInfoHelperBean.getAppSessionId();
		String userId = loginInfoHelperBean.getUserId();
		if (appSessionId == null && userId == null) {
			throw new DataRetrievalFailureException(ExceptionID.INVALID_USERSESSION);
		} else {
			// UPDATE APPSSESSION ACTIVE STATUS
			AppSession appSession = appSessionQRepo.findAppSessionByAppSessionIdAndIsActive(appSessionId, true);
			appSession.setIsActive(false);
			appSession.setAuditDetails(new AuditDetails(loginInfoHelperBean.getUserId(), new Date(System.currentTimeMillis())));
			appSessionRepo.update(appSession);
			
			/** REQUEST TO STOP USER INSTANCE */
			// publisher.publish(new UserLoggedoutEvent(loginInfoHelperBean.getPersonId()));
			
			// ADD REAL DATA FROM REQUEST
			int loginformfactor = 0;
			if (request.getHeader("User-Agent").indexOf("Mobile") != -1) {
				loginformfactor = Constants.MOBILE_AGENT;
			} else {
				loginformfactor = Constants.DESKTOP_AGENT;
			}
			LoginHistory loginHistory = new LoginHistory(loginInfoHelperBean.getMteid(), loginInfoHelperBean.getUserId(), loginInfoHelperBean.getAppSessionId(),
					2, loginformfactor, request.getHeader("host"), request.getHeader("User-Agent"), request.getHeader("User-Agent"), latitude, longitude);
			loginHistory.setAuditDetails(new AuditDetails(loginInfoHelperBean.getUserId(), new Date(System.currentTimeMillis()),
					loginInfoHelperBean.getUserId(), new Date(System.currentTimeMillis())));
			loginHistoryRepo.save(loginHistory);
			return true;
		}
	}
}