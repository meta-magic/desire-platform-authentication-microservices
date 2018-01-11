package com.desire3d.auth.query.service;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.desire3d.auth.beans.LoginInfoHelperBean;
import com.desire3d.auth.dto.PasswordDTO;
import com.desire3d.auth.exceptions.BaseDomainServiceException;
import com.desire3d.auth.exceptions.DataRetrievalFailureException;
import com.desire3d.auth.exceptions.PersistenceFailureException;
import com.desire3d.auth.fw.command.repository.PasswordHistoryCommandRepository;
import com.desire3d.auth.fw.command.service.PasswordSchemaCommandService;
import com.desire3d.auth.fw.query.repository.PasswordHistoryQueryRepository;
import com.desire3d.auth.fw.query.repository.PasswordSchemaQueryRepository;
import com.desire3d.auth.fw.query.service.PasswordManagementService;
import com.desire3d.auth.model.AuditDetails;
import com.desire3d.auth.model.transactions.PasswordHistory;
import com.desire3d.auth.model.transactions.PasswordSchema;
import com.desire3d.auth.utils.ExceptionID;
import com.desire3d.auth.utils.HashingAlgorithms;

@Service
@Scope(value = "request")

public class PasswordManagementServiceImpl implements PasswordManagementService {

	@Autowired
	private LoginInfoHelperBean loginInfoHelperBean;

	@Autowired
	private PasswordHistoryQueryRepository passwordHistoryQueryRepository;

	@Autowired
	private PasswordHistoryCommandRepository passwordHistoryCommandRepository;

	@Autowired
	private PasswordSchemaCommandService passwordSchemaCommandService;

	@Autowired
	private PasswordSchemaQueryRepository passwordSchemaQueryRepository;

	@Override
	public void resetPassword(final PasswordDTO passwordDTO) throws Throwable {
		if (validateCurrentPassword(passwordDTO.getCurrentPassword())) {
			final String newPasswordHash = HashingAlgorithms.getInstance().createHash(passwordDTO.getNewPassword(), HashingAlgorithms.MD5);
			if (validateHistory(newPasswordHash)) {
				savePasswordHistory(newPasswordHash);
				passwordSchemaCommandService.update(newPasswordHash);
			} else {
				throw new BaseDomainServiceException(ExceptionID.USED_PASSWORD);
			}
		}
	}

	/** method to validate current password is present or not in database 
	 * @throws Throwable 
	*/
	private boolean validateCurrentPassword(String currentPassword) throws Throwable {
		final String currentPasswordHash = HashingAlgorithms.getInstance().createHash(currentPassword, HashingAlgorithms.MD5);
		final PasswordSchema passwordSchema = passwordSchemaQueryRepository.findByUserUUID(loginInfoHelperBean.getUserId());
		if (passwordSchema.getPasswordHash().trim().equals(currentPasswordHash.trim())) {
			return true;
		} else {
			throw new BaseDomainServiceException(ExceptionID.INVALID_CURRENTPASSWORD);
		}
	}

	/** method used to validate password history 
	 * @throws DataRetrievalFailureException */
	private boolean validateHistory(final String newPasswordHash) throws DataRetrievalFailureException {
		boolean isValidPassword = true;
		final Collection<PasswordHistory> passwordHistories = passwordHistoryQueryRepository.findByUserUUID(loginInfoHelperBean.getUserId());
		for (PasswordHistory passwordHistory : passwordHistories) {
			if (passwordHistory.getPasswordHash().trim().equals(newPasswordHash.trim())) {
				isValidPassword = false;
			}
		}
		return isValidPassword;
	}

	private void savePasswordHistory(final String newPasswordHash) throws PersistenceFailureException {
		PasswordHistory passwordHistory1 = new PasswordHistory(loginInfoHelperBean.getMteid(), loginInfoHelperBean.getUserId(), newPasswordHash);
		passwordHistory1.setIsActive(true);
		AuditDetails auditDetails = new AuditDetails(loginInfoHelperBean.getUserId(), new Date(System.currentTimeMillis()), loginInfoHelperBean.getUserId(),
				new Date(System.currentTimeMillis()));
		passwordHistory1.setAuditDetails(auditDetails);
		passwordHistoryCommandRepository.save(passwordHistory1);
	}
}
