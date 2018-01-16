package com.desire3d.auth.fw.domainservice;

import atg.taglib.json.util.JSONException;
import atg.taglib.json.util.JSONObject;

public interface TokenService {

	public String generateToken(String mteid, String loginId, String userId, String personId, String appSessionId);

	public JSONObject getTokenData(String tokenId) throws JSONException;

	public String generateToken(JSONObject tokenJson, Long tokenExpiry);

}
