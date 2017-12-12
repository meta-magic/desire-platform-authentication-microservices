package com.desire3d.auth.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import atg.taglib.json.util.JSONException;
import atg.taglib.json.util.JSONObject;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class TokenServiceImpl implements TokenService {
	
	@Value("${token.key}")
	private String tokenKey = null;

	@Value("${token.validity}")
	private Long tokenValidity = null;

	@Override
	public String generateToken(String mteid, String loginId, String userId, String personId, String appSessionId) {

		JSONObject json = new JSONObject();
		try {
			json.put("mteid", mteid);
			json.put("loginId", loginId);
			json.put("userId", userId);
			json.put("personId", personId);
			json.put("appSessionId", appSessionId);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		String tokenId = Jwts.builder().setSubject(json.toString()).setExpiration(new Date(System.currentTimeMillis() + tokenValidity))
				.signWith(SignatureAlgorithm.HS512, tokenKey).compact();

		return tokenId;
	}

	@Override
	public JSONObject getTokenData(String tokenId)
			throws JSONException, ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException {
		String tokenData = Jwts.parser().setSigningKey(tokenKey).parseClaimsJws(tokenId).getBody().getSubject();
		return new JSONObject(tokenData);
	}
}
