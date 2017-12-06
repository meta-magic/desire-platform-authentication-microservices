package com.desire3d.auth.command.repository;

import javax.jdo.PersistenceManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.desire3d.auth.fw.command.repository.AppStateCommandRepository;

@Repository
public class AppStateCommandRepositoryImpl implements AppStateCommandRepository {

	@Autowired
	private PersistenceManagerFactory pmf;

}