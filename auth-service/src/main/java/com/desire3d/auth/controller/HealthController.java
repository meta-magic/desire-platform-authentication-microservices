package com.desire3d.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.desire3d.auth.dto.VersionInfo;

@RestController
@RequestMapping(value = "/check")
public class HealthController {

	@Autowired
	private VersionInfo version;

	@GetMapping(value = "/live")
	public ResponseEntity<String> checklive() {
		return new ResponseEntity<String>("App is Live " + version.getVersion(), HttpStatus.OK);
	}

	@GetMapping(value = "/ready")
	public ResponseEntity<String> checkready() {
		return new ResponseEntity<String>("App is Ready", HttpStatus.OK);
	}

}
