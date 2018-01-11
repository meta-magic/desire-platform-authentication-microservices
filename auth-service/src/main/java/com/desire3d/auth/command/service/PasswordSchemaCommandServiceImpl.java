package com.desire3d.auth.command.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.desire3d.auth.beans.LoginInfoHelperBean;
import com.desire3d.auth.exceptions.DataRetrievalFailureException;
import com.desire3d.auth.exceptions.PersistenceFailureException;
import com.desire3d.auth.fw.command.repository.PasswordSchemaCommandRepository;
import com.desire3d.auth.fw.command.service.PasswordSchemaCommandService;
import com.desire3d.auth.fw.query.repository.PasswordSchemaQueryRepository;
import com.desire3d.auth.model.AuditDetails;
import com.desire3d.auth.model.transactions.PasswordSchema;

@Service
@Scope(value = "request")

public class PasswordSchemaCommandServiceImpl implements PasswordSchemaCommandService {

	@Autowired
	private LoginInfoHelperBean loginInfoHelperBean;

	@Autowired
	private PasswordSchemaQueryRepository passwordSchemaQueryRepository;

	@Autowired
	private PasswordSchemaCommandRepository passwordSchemaCommandRepository;

	@Override
	public void update(final String newPasswordHash) throws PersistenceFailureException, DataRetrievalFailureException {
		PasswordSchema current_passwordSchema = passwordSchemaQueryRepository.findByUserUUID(loginInfoHelperBean.getUserId());
		current_passwordSchema.setPasswordHash(newPasswordHash);
		current_passwordSchema.updateAuditFields(new AuditDetails(loginInfoHelperBean.getUserId(), new Date(System.currentTimeMillis())));
		passwordSchemaCommandRepository.update(current_passwordSchema);
		System.out.println("Current Date" +current_passwordSchema.getAuditDetails().getUpdatedTime());
		
	}

}
