package com.desire3d.auth.service;

import atg.taglib.json.util.JSONException;
import atg.taglib.json.util.JSONObject;

public interface TokenService {

	public String generateToken(String mteid, String loginId, String userId, String personId, String appSessionId);

	public JSONObject getTokenData(String tokenId) throws JSONException;

}
