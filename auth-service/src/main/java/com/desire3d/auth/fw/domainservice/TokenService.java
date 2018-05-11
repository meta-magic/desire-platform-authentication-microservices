package com.desire3d.auth.fw.domainservice;

import atg.taglib.json.util.JSONException;
import atg.taglib.json.util.JSONObject;

public interface TokenService {
	
	/** TOKEN KEY CONSTANTS */
	public final static String MTE_ID_KEY = "mteid";

	public final static String LOGIN_ID_KEY = "loginId";
	
	public final static String USER_ID_KEY = "userId";
	
	public final static String PERSON_ID_KEY = "personId";
	
	public final static String APP_SESSION_ID_KEY = "appSessionId";
	
	public final static String SUBSCRIPTION_TYPE_KEY = "subscriptionType";

	public String generateToken(String mteid, String loginId, String userId, String personId, String appSessionId, Integer subscriptionType);

	public JSONObject getTokenData(String tokenId) throws JSONException;

	public String generateToken(JSONObject tokenJson, Long tokenExpiry);
	
}