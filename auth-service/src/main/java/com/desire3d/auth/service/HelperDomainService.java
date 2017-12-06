package com.desire3d.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.desire3d.auth.beans.LoginInfoHelperBean;

import atg.taglib.json.util.JSONException;
import atg.taglib.json.util.JSONObject;
import io.jsonwebtoken.ExpiredJwtException;

/**
 * Class used to provide all helper methods for other domain services 
 * 
 * @author Mahesh Pardeshi
 *
 */
@Component
public class HelperDomainService {

	@Autowired
	private TokenService tokenService;

	/** METHOD TO PREPARE JWT TOKEN 
	 * 
	 * @return token id containing user auth information */
	protected String prepareToken(final LoginInfoHelperBean loginInfoHelperBean) {
		return tokenService.generateToken(loginInfoHelperBean.getMteid(), loginInfoHelperBean.getLoginId(), loginInfoHelperBean.getUserId(),
				loginInfoHelperBean.getPersonId(), loginInfoHelperBean.getAppSessionId());
	}

	/** METHOD TO DESERIALIZE & AUTHENTICATE JWT TOKEN 
	 * 
	 * @return LoginInfoHelperBean contains user auth information
	 * @throws JSONException 
	 * */
	protected LoginInfoHelperBean deserializeToken(String tokenId) throws Exception {
		try {
			JSONObject tokenData = tokenService.getTokenData(tokenId);
			LoginInfoHelperBean loginInfoHelperBean = new LoginInfoHelperBean();
			loginInfoHelperBean.setProperty(tokenData.getString("mteid"), tokenData.getString("loginId"), tokenData.getString("userId"),
					tokenData.getString("personId"), tokenData.getString("appSessionId"));
			return loginInfoHelperBean;
		} catch (ExpiredJwtException e) {
			e.printStackTrace();
			throw new Exception("Authentication token expired");
		} catch (IllegalArgumentException e) {
			throw new Exception("Authentication token required to process message");
		} catch (Exception e) {
			throw new Exception("Invalid authentication token to process message");
		}
	}
}