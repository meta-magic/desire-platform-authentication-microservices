package com.desire3d.auth.query.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desire3d.auth.dto.AuthenticateResponse;
import com.desire3d.auth.dto.LoginResponseDto;
import com.desire3d.auth.exceptions.BusinessServiceException;
import com.desire3d.auth.exceptions.DataNotFoundException;
import com.desire3d.auth.fw.command.repository.AppSessionCommandRepository;
import com.desire3d.auth.fw.command.repository.LoginFailureCommandRepository;
import com.desire3d.auth.fw.command.repository.LoginHistoryCommandRepository;
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
import com.desire3d.auth.service.LoginDomainService;
import com.desire3d.auth.service.TokenService;

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
	public boolean validateLoginId(String loginId) throws BusinessServiceException {
		if (loginId == null) {
			throw new BusinessServiceException("Invalid data, some of the fields are null", "invalidnull.data");
		}
		try {
			List<AuthSchema> auth = authSchemaQueryRepository.findByLoginIdAndIsActive(loginId, true);
			if (auth != null && auth.size() != 0) // Changes made
				return true;
		} catch (DataNotFoundException e) {
			System.err.println(e.getMessage());
			return false;
		}
		return false;
	}

	public LoginResponseDto authenticate(String loginId, String password) throws Exception {

		if (loginId == null || password == null) {
			throw new BusinessServiceException("Invalid data, some of the fields are null", "invalidnull.data");
		}

		try {
			String encodedPassword = loginDomainService.createPasswordHash(password);
			AuthenticateResponse authResp = this.processLoginRequest(loginId, encodedPassword);

			AppSession appSession = this.createAppSessionId(authResp);
			// CREATE LOGIN HISTORY
			loginHistory(authResp, appSession);
			String tokenid = tokenService.generateToken(authResp.getAuthSchema().getMteid(), authResp.getAuthSchema().getLoginUUID(),
					authResp.getAuthSchema().getUserUUID(), authResp.getAuthSchema().getPersonUUID(), appSession.getAppSessionId());

			LoginResponseDto loginResponse = new LoginResponseDto(appSession.getAppSessionId(), authResp.getUserSchema().isFirstTimeLogin(),
					authResp.getUserSchema().getAccountBlocked(), authResp.getUserSchema().isAccountExpired(), authResp.getUserSchema().isChangePassword(),
					tokenid, null);

			System.out.println("loginRespose********" + loginResponse);
			return loginResponse;
		} catch (BusinessServiceException e) {
			this.loginFailure(loginId, e.getMessageId());
			throw new BusinessServiceException(e.getMessage(), e.getMessageId());
		}
	}

	//AppSessionId
	private LoginHistory loginHistory(AuthenticateResponse authResp, AppSession appSession) {
		LoginHistory loginHistory = new LoginHistory(authResp.getAuthSchema().getMteid(), authResp.getAuthSchema().getUserUUID(), appSession.getAppSessionId(),
				1, 1, "127.0.0.1", "chrome", "", 0.0, 0.0);
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

	private LoginFailure loginFailure(String loginId, String errorId) {
		LoginFailure loginFailure = new LoginFailure(loginId, "", "", errorId, "127.0.0.1", "chrome", "", 0.0, 0.0);

		loginFailureRepo.save(loginFailure);

		return loginFailure;

	}

	private AppSession createAppSessionId(AuthenticateResponse authResp) {
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

	private AuthenticateResponse processLoginRequest(String loginId, String password) throws BusinessServiceException {

		if (loginId == null || password == null) {
			throw new BusinessServiceException("Invalid data");
		}
		try {
			// UserSchema user = null;
			AuthSchema auth = authSchemaQueryRepository.findAuthSchemaByLoginIdAndIsActive(loginId, true);
			if (auth != null && auth.getUserUUID() != null) {
				UserSchema user = userQRepo.findUserSchemaByUserUUIDAndIsActive(auth.getUserUUID(), true);
				if (user.getAccountBlocked().equals(1) || user.isAccountExpired()) {
					throw new BusinessServiceException("Account is blocked or Expired please contact admin", "valid.loginid.accountblockedorexpired");
				}

				PasswordSchema passwordSchema = passwordRepo.findPasswordSchemaByUserUUIDAndIsActive(auth.getUserUUID(), true);

				Iterable<PasswordSchema> passwordSchemas = passwordRepo.findByIsActive(true);
				for (PasswordSchema passwordSchema2 : passwordSchemas) {
					if (auth.getUserUUID().equals(passwordSchema2.getUserUUID())) {
						passwordSchema = passwordSchema2;
					}
				}

				if ((passwordSchema == null && passwordSchema.getPasswordHash() == null) || !passwordSchema.getPasswordHash().trim().equals(password.trim())) {
					throw new BusinessServiceException("Invalid login-id or password", "invalid.user.credentials");
				} else {
					AuthenticateResponse authResp = new AuthenticateResponse(auth, user, passwordSchema);

					// LoginResponseDto loginResponse = new
					// LoginResponseDto(appSessionId, firstTimeLogin,
					// accountBlocked, accountExpired, changePassword, profile)

					return authResp;
				}
			} else {
				throw new BusinessServiceException("Invalid login-id", "invalid.loginid");
			}

		} catch (DataNotFoundException e) {
			System.err.println(e.getMessage());
			throw new BusinessServiceException("Invalid login-id or password", "invalid.user.credentials");
		}

		// return null;
	}

}
