package com.desire3d.auth.query.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desire3d.auth.domainservice.LoginDomainService;
import com.desire3d.auth.dto.AuthenticateResponse;
import com.desire3d.auth.dto.LoginResponseDto;
import com.desire3d.auth.exceptions.BaseDomainServiceException;
import com.desire3d.auth.exceptions.BaseException;
import com.desire3d.auth.exceptions.PersistenceException;
import com.desire3d.auth.fw.command.repository.AppSessionCommandRepository;
import com.desire3d.auth.fw.command.repository.LoginFailureCommandRepository;
import com.desire3d.auth.fw.command.repository.LoginHistoryCommandRepository;
import com.desire3d.auth.fw.domainservice.TokenService;
import com.desire3d.auth.fw.query.repository.AuthSchemaQueryRepository;
import com.desire3d.auth.fw.query.repository.PasswordSchemaQueryRepository;
import com.desire3d.auth.fw.query.repository.UserSchemaQueryRepository;
import com.desire3d.auth.fw.query.service.AuthQueryService;
import com.desire3d.auth.model.AuditDetails;
import com.desire3d.auth.model.transactions.AppSession;
import com.desire3d.auth.model.transactions.AuthSchema;
import com.desire3d.auth.model.transactions.LoginFailure;
import com.desire3d.auth.model.transactions.LoginHistory;
import com.desire3d.auth.model.transactions.PasswordSchema;
import com.desire3d.auth.model.transactions.UserSchema;
import com.desire3d.auth.utils.ExceptionID;

@Service
public class AuthQueryServiceImpl implements AuthQueryService {

	@Autowired
	private AuthSchemaQueryRepository authSchemaQueryRepository;

	@Autowired
	private LoginHistoryCommandRepository loginHistoryRepo;

	@Autowired
	private LoginFailureCommandRepository loginFailureRepo;

	@Autowired
	private AppSessionCommandRepository appSessionRepo;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private PasswordSchemaQueryRepository passwordRepo;

	@Autowired
	private UserSchemaQueryRepository userQRepo;

	@Autowired
	private LoginDomainService loginDomainService;

	@Override
	public boolean validateLoginId(String loginId) throws Throwable {
		if (loginId == null) {
			throw new BaseDomainServiceException(ExceptionID.INVALID_LOGINID);
		}
		List<AuthSchema> auth = authSchemaQueryRepository.findByLoginIdAndIsActive(loginId, true);
		if (auth.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public LoginResponseDto authenticate(String loginId, String password, HttpServletRequest request) throws Throwable {
		if (loginId == null || password == null) {
			throw new BaseDomainServiceException(ExceptionID.INVALID_USER_CREDENTIALS);
		}

		try {
			String encodedPassword = loginDomainService.createPasswordHash(password);
			AuthenticateResponse authResp = this.processLoginRequest(loginId, encodedPassword);

			AppSession appSession = this.createAppSessionId(authResp);
			// CREATE LOGIN HISTORY
			loginHistory(authResp, appSession, request);
			String tokenid = tokenService.generateToken(authResp.getAuthSchema().getMteid(), authResp.getAuthSchema().getLoginUUID(),
					authResp.getAuthSchema().getUserUUID(), authResp.getAuthSchema().getPersonUUID(), appSession.getAppSessionId());

			LoginResponseDto loginResponse = new LoginResponseDto(appSession.getAppSessionId(), authResp.getUserSchema().isFirstTimeLogin(),
					authResp.getUserSchema().getAccountBlocked(), authResp.getUserSchema().isAccountExpired(), authResp.getUserSchema().isChangePassword(),
					tokenid, null);

			System.out.println("loginRespose********" + loginResponse);
			return loginResponse;
		} catch (Throwable e) {
			this.loginFailure(loginId, ((BaseException) e).getMessageId(), request);
			throw new BaseDomainServiceException(ExceptionID.INVALID_USER_CREDENTIALS);
		}

	}

	// AppSessionId
	private LoginHistory loginHistory(AuthenticateResponse authResp, AppSession appSession, HttpServletRequest request) throws PersistenceException {
		LoginHistory loginHistory = new LoginHistory(authResp.getAuthSchema().getMteid(), authResp.getAuthSchema().getUserUUID(), appSession.getAppSessionId(),
				1, 1, request.getHeader("host"), request.getHeader("User-Agent"), request.getHeader("User-Agent"), 0.0, 0.0);
		// SET HELPER CLASS DATA
		loginHistory.setIsActive(true);
		if (authResp.getUserSchema().getAuditDetails() != null) {
			AuditDetails auditDetails = new AuditDetails(authResp.getUserSchema().getAuditDetails().getCreatedBy(), new Date(System.currentTimeMillis()),
					authResp.getUserSchema().getAuditDetails().getCreatedBy(), new Date(System.currentTimeMillis()));
			loginHistory.setAuditDetails(auditDetails);
		}
		loginHistoryRepo.save(loginHistory);

		return loginHistory;
	}

	private LoginFailure loginFailure(String loginId, String errorId, HttpServletRequest request) throws PersistenceException {
		LoginFailure loginFailure = new LoginFailure(loginId, "", "", errorId, request.getHeader("host"), request.getHeader("User-Agent"),
				request.getHeader("User-Agent"), 0.0, 0.0);
		AuditDetails auditDetails = new AuditDetails("", new Date(System.currentTimeMillis()), "", new Date(System.currentTimeMillis()));
		loginFailure.setAuditDetails(auditDetails);

		loginFailureRepo.save(loginFailure);

		return loginFailure;

	}

	private AppSession createAppSessionId(AuthenticateResponse authResp) throws PersistenceException {
		AppSession appSession = new AppSession();
		appSession.setAppData("AppData");
		appSession.setIsActive(true);
		if (authResp.getUserSchema().getAuditDetails() != null) {
			AuditDetails auditDetails = new AuditDetails(authResp.getUserSchema().getAuditDetails().getCreatedBy(), new Date(System.currentTimeMillis()),
					authResp.getUserSchema().getAuditDetails().getCreatedBy(), new Date(System.currentTimeMillis()));
			appSession.setAuditDetails(auditDetails);
		}
		appSessionRepo.save(appSession);
		return appSession;
	}

	@SuppressWarnings("null")
	private AuthenticateResponse processLoginRequest(String loginId, String password) throws Throwable {

		if (loginId == null || password == null) {
			throw new BaseDomainServiceException(ExceptionID.INVALID_USER_CREDENTIALS);
		}

		// UserSchema user = null;
		AuthSchema auth = authSchemaQueryRepository.findAuthSchemaByLoginIdAndIsActive(loginId, true);
		if (auth != null && auth.getUserUUID() != null) {
			UserSchema user = userQRepo.findUserSchemaByUserUUIDAndIsActive(auth.getUserUUID(), true);
			if (user.getAccountBlocked().equals(1) || user.isAccountExpired()) {
				throw new BaseDomainServiceException(ExceptionID.INVALID_LOGINID_ACCOUNTBLOCKEDOREXPIRED);
			}

			PasswordSchema passwordSchema = passwordRepo.findPasswordSchemaByUserUUIDAndIsActive(auth.getUserUUID(), true);

			/*Iterable<PasswordSchema> passwordSchemas = passwordRepo.findByIsActive(true);
			for (PasswordSchema passwordSchema2 : passwordSchemas) {
				if (auth.getUserUUID().equals(passwordSchema2.getUserUUID())) {
					passwordSchema = passwordSchema2;
				}
			}*/

			if ((passwordSchema == null && passwordSchema.getPasswordHash() == null) || !passwordSchema.getPasswordHash().trim().equals(password.trim())) {
				throw new BaseDomainServiceException(ExceptionID.INVALID_USER_CREDENTIALS);
			} else {
				AuthenticateResponse authResp = new AuthenticateResponse(auth, user, passwordSchema);

				// LoginResponseDto loginResponse = new
				// LoginResponseDto(appSessionId, firstTimeLogin,
				// accountBlocked, accountExpired, changePassword, profile)

				return authResp;
			}
		} else {
			throw new BaseDomainServiceException(ExceptionID.INVALID_LOGINID);
		}

		// return null;
	}

}
