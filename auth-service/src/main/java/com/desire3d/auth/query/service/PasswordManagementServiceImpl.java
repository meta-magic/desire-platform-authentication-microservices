package com.desire3d.auth.query.service;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.desire3d.auth.beans.LoginInfoHelperBean;
import com.desire3d.auth.dto.ForgotPasswordDTO;
import com.desire3d.auth.dto.PasswordDTO;
import com.desire3d.auth.dto.UsernameAuthentication;
import com.desire3d.auth.exceptions.DataRetrievalFailureException;
import com.desire3d.auth.exceptions.DomainServiceFailureException;
import com.desire3d.auth.exceptions.PasswordRecoveryFailureException;
import com.desire3d.auth.exceptions.PersistenceFailureException;
import com.desire3d.auth.fw.command.repository.PasswordHistoryCommandRepository;
import com.desire3d.auth.fw.command.repository.RecoveryTokenCommandRepository;
import com.desire3d.auth.fw.command.service.PasswordSchemaCommandService;
import com.desire3d.auth.fw.domainservice.TokenService;
import com.desire3d.auth.fw.query.repository.AuthSchemaQueryRepository;
import com.desire3d.auth.fw.query.repository.PasswordHistoryQueryRepository;
import com.desire3d.auth.fw.query.repository.PasswordSchemaQueryRepository;
import com.desire3d.auth.fw.query.repository.RecoveryTokenQueryRepository;
import com.desire3d.auth.fw.query.service.AuthQueryService;
import com.desire3d.auth.fw.query.service.PasswordManagementService;
import com.desire3d.auth.model.AuditDetails;
import com.desire3d.auth.model.transactions.AuthSchema;
import com.desire3d.auth.model.transactions.PasswordHistory;
import com.desire3d.auth.model.transactions.PasswordSchema;
import com.desire3d.auth.model.transactions.RecoveryToken;
import com.desire3d.auth.utils.Constants;
import com.desire3d.auth.utils.ExceptionID;
import com.desire3d.auth.utils.HashingAlgorithms;
import com.desire3d.event.TokenGeneratedEvent;
import com.desire3d.event.publisher.TokenGeneratedEventPublisher;

import atg.taglib.json.util.JSONException;
import io.jsonwebtoken.ExpiredJwtException;

@Service
@Scope(value = "request")
public class PasswordManagementServiceImpl implements PasswordManagementService {

	private Logger LOGGER = LoggerFactory.getLogger(PasswordManagementServiceImpl.class);

	@Autowired
	private LoginInfoHelperBean loginInfoHelperBean;

	@Autowired
	private PasswordHistoryQueryRepository passwordHistoryQueryRepository;

	@Autowired
	private PasswordHistoryCommandRepository passwordHistoryCommandRepository;

	@Autowired
	private AuthQueryService authQueryService;

	@Autowired
	private TokenGeneratedEventPublisher tokenGeneratedEventPublisher;

	@Autowired
	private RecoveryTokenQueryRepository recoveryTokenQueryRepository;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private AuthSchemaQueryRepository authSchemaQueryRepository;

	@Autowired
	private PasswordSchemaQueryRepository passwordSchemaQueryRepository;

	@Autowired
	private PasswordSchemaCommandService passwordSchemaCommandService;

	@Autowired
	private RecoveryTokenCommandRepository recoveryTokenCommandRepository;

	@Override
	public void resetPassword(final PasswordDTO passwordDTO) throws Throwable {
		if (validateCurrentPassword(passwordDTO.getCurrentPassword())) {
			validateAndChangePassword(passwordDTO.getNewPassword(), loginInfoHelperBean.getUserId(),
					loginInfoHelperBean.getMteid());
		}
	}

	@Override
	public void sendRecoveryToken(UsernameAuthentication usernameAuthentication) throws Throwable {
		if (authQueryService.validateLoginId(usernameAuthentication.getLoginId())) {
			RecoveryToken recoveryToken = generateRecoveryToken(usernameAuthentication.getLoginId());
			tokenGeneratedEventPublisher.publish(new TokenGeneratedEvent(recoveryToken.getToken(),
					recoveryToken.getTokenExpiry(), recoveryToken.getPersonId()));
		} else {
			throw new DomainServiceFailureException(ExceptionID.INVALID_LOGINID);
		}
	}

	@Override
	public void forgotPassword(final ForgotPasswordDTO forgotPasswordDTO) throws Throwable {
		if (validateToken(forgotPasswordDTO)) {
			AuthSchema authschema = authSchemaQueryRepository.findAuthSchemaByLoginId(forgotPasswordDTO.getLoginId());
			validateAndChangePassword(forgotPasswordDTO.getNewPassword(), authschema.getUserUUID(),
					authschema.getMteid());
		}
	}

	/**
	 * method to validate current password is present or not in database
	 * 
	 * @throws Throwable
	 */
	private boolean validateCurrentPassword(String currentPassword) throws Throwable {
		final String currentPasswordHash = HashingAlgorithms.getInstance().createHash(currentPassword,
				HashingAlgorithms.MD5);
		final PasswordSchema passwordSchema = passwordSchemaQueryRepository
				.findByUserUUID(loginInfoHelperBean.getUserId());
		if (passwordSchema.getPasswordHash().trim().equals(currentPasswordHash.trim())) {
			return true;
		} else {
			throw new DomainServiceFailureException(ExceptionID.INVALID_CURRENTPASSWORD);
		}
	}

	/**
	 * validate password with history and update if valid
	 * 
	 * @throws DomainServiceFailureException
	 * @throws PersistenceFailureException
	 * @throws DataRetrievalFailureException
	 * @throws Exception
	 */
	private void validateAndChangePassword(final String newPassword, final String userId, final String mteid)
			throws DataRetrievalFailureException, PersistenceFailureException, DomainServiceFailureException,
			Exception {
		final String newPasswordHash = HashingAlgorithms.getInstance().createHash(newPassword, HashingAlgorithms.MD5);
		if (validateHistory(newPasswordHash, userId)) {
			savePasswordHistory(newPasswordHash, userId, mteid);
			passwordSchemaCommandService.update(newPasswordHash, userId);
		} else {
			throw new DomainServiceFailureException(ExceptionID.USED_PASSWORD);
		}
	}

	/**
	 * method used to validate password history
	 * 
	 * @throws DataRetrievalFailureException
	 */
	private boolean validateHistory(final String newPasswordHash, final String userId)
			throws DataRetrievalFailureException {
		boolean isValidPassword = true;
		final Collection<PasswordHistory> passwordHistories = passwordHistoryQueryRepository.findByUserUUID(userId);
		for (PasswordHistory passwordHistory : passwordHistories) {
			if (passwordHistory.getPasswordHash().trim().equals(newPasswordHash.trim())) {
				isValidPassword = false;
			}
		}
		return isValidPassword;
	}

	private void savePasswordHistory(final String newPasswordHash, final String userId, final String mteid)
			throws PersistenceFailureException {
		PasswordHistory passwordHistory1 = new PasswordHistory(mteid, userId, newPasswordHash);
		passwordHistory1.setIsActive(true);
		AuditDetails auditDetails = new AuditDetails(userId, new Date(System.currentTimeMillis()), userId,
				new Date(System.currentTimeMillis()));
		passwordHistory1.setAuditDetails(auditDetails);
		passwordHistoryCommandRepository.save(passwordHistory1);
	}

	private RecoveryToken generateRecoveryToken(String loginId)
			throws PersistenceFailureException, DataRetrievalFailureException, JSONException {
		AuthSchema authschema = authSchemaQueryRepository.findAuthSchemaByLoginId(loginId);
		// String tokenId = UUID.randomUUID().toString();
		// JSONObject tokenJson = new JSONObject();
		// tokenJson.put("tokenId", tokenId);
		// String token = tokenService.generateToken(tokenJson,
		// Constants.PASSWORD_RECOVERY_TOKEN_EXPIRY);
		// String generatedToken=tokenId.substring(tokenId.length()-8,
		// tokenId.length());

		RecoveryToken recoveryTokenObj = this.checkDuplicateToken(authschema);
		AuditDetails auditDetails = new AuditDetails();
		auditDetails.setCreatedTime(new Date());
		recoveryTokenObj.setAuditDetails(auditDetails);
		return recoveryTokenCommandRepository.save(recoveryTokenObj);
	}

	// THIS METHOD GENERATE UNIQUE TOKEN AND VALIDATE ALREADY PRESENT OR NOT
	private RecoveryToken checkDuplicateToken(AuthSchema authschema) throws DataRetrievalFailureException {
		String tokenId = UUID.randomUUID().toString();
		String generatedToken = tokenId.substring(tokenId.length() - 8, tokenId.length());
		RecoveryToken recoveryToken = recoveryTokenQueryRepository.findByToken(generatedToken);
		while (recoveryToken != null) {
			this.checkDuplicateToken(authschema);
		}
		RecoveryToken recoveryTokenObj = new RecoveryToken(UUID.randomUUID().toString(), generatedToken,
				Constants.PASSWORD_RECOVERY_TOKEN_EXPIRY, authschema.getPersonUUID());
		return recoveryTokenObj;
	}

	private boolean validateToken(final ForgotPasswordDTO forgotPasswordDTO)
			throws PasswordRecoveryFailureException, DataRetrievalFailureException, DomainServiceFailureException {
		if (forgotPasswordDTO.getToken() == null) {
			throw new DomainServiceFailureException(ExceptionID.TOKEN_EMPTY);
		}
		boolean valid = false;
		try {
			// JSONObject jsonObject =
			// tokenService.getTokenData(forgotPasswordDTO.getToken());
			RecoveryToken recoveryToken = recoveryTokenQueryRepository.findByToken(forgotPasswordDTO.getToken());
			if (recoveryToken != null) {
				try {
					this.validateTokenExpiry(recoveryToken);
				} catch (Exception e) {
					throw new PasswordRecoveryFailureException(ExceptionID.RECOVERYTOKEN_EXPIRED, e);
				}
				valid = true;
			} else {
				valid = false;
				LOGGER.error(new Date() + " [ " + "Recovery Token Invalid. LoginId: '{}'",
						forgotPasswordDTO.getLoginId() + "]");
				throw new PasswordRecoveryFailureException(ExceptionID.RECOVERYTOKEN_INVALID);
			}
			// if (token != null &&
			// token.getTokenId().equals(jsonObject.getString(Constants.TOKEN_ID_KEY))) {
			// valid = true;
			// }
		} catch (ExpiredJwtException e) {
			LOGGER.error(new Date() + " [ " + "Recovery Token Expired. LoginId: '{}'",
					forgotPasswordDTO.getLoginId() + "]");
			throw new PasswordRecoveryFailureException(ExceptionID.RECOVERYTOKEN_EXPIRED, e);
		} catch (IllegalArgumentException e) {
			LOGGER.error(new Date() + " [ " + "Recovery Token Required. LoginId: '{}'",
					forgotPasswordDTO.getLoginId() + "]");
			throw new PasswordRecoveryFailureException(ExceptionID.RECOVERYTOKEN_REQUIRED, e);
		} catch (Exception e) {
			LOGGER.error(new Date() + " [ " + "Recovery Token Invalid. LoginId: '{}'",
					forgotPasswordDTO.getLoginId() + "]");
			throw new PasswordRecoveryFailureException(ExceptionID.RECOVERYTOKEN_INVALID, e);
		}
		return valid;
	}

	// THIS METHOD IS VALIDATING TOKEN
	private void validateTokenExpiry(final RecoveryToken recoveryToken) throws PasswordRecoveryFailureException {
		long tokenCreatedTime = recoveryToken.getAuditDetails().getCreatedTime().getTime();
		long nowTime = new Date().getTime();
		long diff = nowTime - tokenCreatedTime;
		long diffMinutes = diff / (60 * 1000) % 60;
		if (diffMinutes > 10) {
			throw new PasswordRecoveryFailureException(ExceptionID.RECOVERYTOKEN_EXPIRED);
		}
	}

}