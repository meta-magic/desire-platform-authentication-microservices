package com.desire3d.event.listener;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.desire3d.auth.exceptions.DataRetrievalFailureException;
import com.desire3d.auth.exceptions.PersistenceFailureException;
import com.desire3d.auth.fw.command.repository.AppSessionCommandRepository;
import com.desire3d.auth.fw.command.repository.LoginHistoryCommandRepository;
import com.desire3d.auth.fw.domainservice.TokenService;
import com.desire3d.auth.fw.query.repository.AppSessionQueryRepository;
import com.desire3d.auth.model.AuditDetails;
import com.desire3d.auth.model.transactions.AppSession;
import com.desire3d.auth.model.transactions.LoginHistory;
import com.desire3d.auth.utils.Constants;
import com.desire3d.channel.SessionHandlerChannel;
import com.desire3d.event.UserSessionEvent;

import atg.taglib.json.util.JSONException;
import atg.taglib.json.util.JSONObject;

/**
 * @author sagar
 * 
 *         IN THIS LISTENER CONSUME USER SESSION COMPLETED EVENT AND UPDATE THE
 *         DATABASE
 */
@Component
public class UserSessionEventListener {

	@Autowired
	private AppSessionCommandRepository appSessionRepo;

	@Autowired
	private AppSessionQueryRepository appSessionQRepo;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private LoginHistoryCommandRepository loginHistoryRepo;

	@StreamListener(SessionHandlerChannel.SESSION_HANDLER_INPUT_CHANNEL)
	public void onReceive(@Payload UserSessionEvent sessionEvent, @Header(name = "tokenId") String token)
			throws JSONException, DataRetrievalFailureException, PersistenceFailureException {

		JSONObject tokenJSON = tokenService.getTokenData(sessionEvent.getTokenId());
		if (tokenJSON.has(TokenService.APP_SESSION_ID_KEY) && tokenJSON.has(TokenService.USER_ID_KEY)) {

			// SESSION UPDATED
			AppSession appSession = appSessionQRepo.findAppSessionByAppSessionIdAndIsActive(
					tokenJSON.getString(TokenService.APP_SESSION_ID_KEY), true);
			if(appSession!=null) {
				appSession.setIsActive(false);
				appSession.setAuditDetails(new AuditDetails(tokenJSON.getString(TokenService.USER_ID_KEY),
						new Date(System.currentTimeMillis())));
				appSessionRepo.update(appSession);
				
				
				// ADD NEW ENTERY IN LOGIN HISTRY TABLE
				LoginHistory loginHistory = new LoginHistory(tokenJSON.getString(TokenService.MTE_ID_KEY),
						tokenJSON.getString(TokenService.USER_ID_KEY), tokenJSON.getString(TokenService.APP_SESSION_ID_KEY),
						2, Constants.OTHER_AGENT, "NONE", "NONE", "NONE", 1.0, 1.0);
				loginHistory.setAuditDetails(new AuditDetails(tokenJSON.getString(TokenService.USER_ID_KEY),
						new Date(System.currentTimeMillis()), tokenJSON.getString(TokenService.USER_ID_KEY),
						new Date(System.currentTimeMillis())));
				loginHistoryRepo.save(loginHistory);
				System.out.println("USER LOGGED OUT BY EVENT.");
			}else {
				System.out.println("USER ALREADY LOGGED OUT.");
			}

		}

	}

}
