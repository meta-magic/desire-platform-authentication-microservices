package com.desire3d.auth.domainservice;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.desire3d.auth.fw.domainservice.TokenService;

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
	public String generateToken(String mteid, String loginId, String userId, String personId, String appSessionId, Integer subscriptionType) {
		JSONObject json = new JSONObject();
		try {
			json.put(MTE_ID_KEY, mteid);
			json.put(LOGIN_ID_KEY, loginId);
			json.put(USER_ID_KEY, userId);
			json.put(PERSON_ID_KEY, personId);
			json.put(APP_SESSION_ID_KEY, appSessionId);
			json.put(SUBSCRIPTION_TYPE_KEY, subscriptionType);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return generateTokenFromJson(json, tokenValidity);
	}

	@Override
	public JSONObject getTokenData(String tokenId)
			throws JSONException, ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException {
		String tokenData = Jwts.parser().setSigningKey(tokenKey).parseClaimsJws(tokenId).getBody().getSubject();
		return new JSONObject(tokenData);
	}

	private String generateTokenFromJson(JSONObject tokenJson, Long tokenExpiry) {
		return Jwts.builder().setSubject(tokenJson.toString()).setExpiration(new Date(System.currentTimeMillis() + tokenExpiry))
				.signWith(SignatureAlgorithm.HS512, tokenKey).compact();
	}

	/** 
	 * method to create JWT token using json object as a payload and expiry in miliseconds 
	 * 
	 * @param tokenJson a payload for JWT token
	 * @param tokenExpiry token expiry in milliseconds 
	 * 
	 * @return JWT token
	 * */
	@Override
	public String generateToken(JSONObject tokenJson, Long tokenExpiry) {
		return generateTokenFromJson(tokenJson, tokenExpiry);
	}
}