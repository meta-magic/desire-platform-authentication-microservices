package com.desire3d.auth.fw.query.service;

import com.desire3d.auth.exceptions.BusinessServiceException;
import com.desire3d.auth.exceptions.DataNotFoundException;
import com.desire3d.auth.exceptions.PersistenceException;

public interface LoginQueryService {

	public boolean userLogout() throws BusinessServiceException, DataNotFoundException, PersistenceException;

}
