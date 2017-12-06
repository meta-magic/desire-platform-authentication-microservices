package com.desire3d.auth.fw.query.service;

import com.desire3d.auth.exceptions.BusinessServiceException;
import com.desire3d.auth.exceptions.DataNotFoundException;

public interface LoginQueryService {

	public boolean userLogout() throws BusinessServiceException, DataNotFoundException;

}
