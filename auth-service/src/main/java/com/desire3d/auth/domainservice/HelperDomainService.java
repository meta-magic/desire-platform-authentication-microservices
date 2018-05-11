package com.desire3d.auth.domainservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.desire3d.auth.beans.LoginInfoHelperBean;
import com.desire3d.auth.fw.domainservice.TokenService;

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
				loginInfoHelperBean.getPersonId(), loginInfoHelperBean.getAppSessionId(), loginInfoHelperBean.getSubscriptionType());
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
			loginInfoHelperBean.setProperty(tokenData.getString(TokenService.APP_SESSION_ID_KEY), tokenData.getString(TokenService.LOGIN_ID_KEY),
					tokenData.getString(TokenService.USER_ID_KEY), tokenData.getString(TokenService.PERSON_ID_KEY),
					tokenData.getString(TokenService.APP_SESSION_ID_KEY), tokenData.getInt(TokenService.SUBSCRIPTION_TYPE_KEY));
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