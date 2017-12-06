package com.desire3d.auth.query.repository;

import javax.jdo.PersistenceManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.desire3d.auth.fw.query.repository.AppStateQueryRepository;

@Repository
public class AppStateQueryRepositoryImpl implements AppStateQueryRepository {

	@Autowired
	private PersistenceManagerFactory pmf;

}
