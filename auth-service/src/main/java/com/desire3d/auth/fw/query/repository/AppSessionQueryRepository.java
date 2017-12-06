package com.desire3d.auth.fw.query.repository;

import com.desire3d.auth.exceptions.DataNotFoundException;
import com.desire3d.auth.model.transactions.AppSession;

public interface AppSessionQueryRepository {

	public AppSession findAppSessionByAppSessionIdAndIsActive(String appSessionId, Boolean isActive) throws DataNotFoundException;

}
