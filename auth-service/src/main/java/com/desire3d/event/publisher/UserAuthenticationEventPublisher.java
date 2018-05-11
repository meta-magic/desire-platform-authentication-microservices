package com.desire3d.event.publisher;

import com.desire3d.auth.domainservice.HelperDomainService;

//@Component
public class UserAuthenticationEventPublisher extends HelperDomainService {/*

	private final Logger logger = LoggerFactory.getLogger(UserAuthenticationEventPublisher.class);

	@Autowired
	private UserAuthenticationChannel userAuthenticationChannel;
	
	public boolean publish(final UserLoggedinEvent event) {
		Message<UserLoggedinEvent> message = MessageBuilder.withPayload(event).build();
		logger.info("Publishing message '{}' with payload type '{}' ", message, UserLoggedinEvent.class);
//		boolean status = userAuthenticationChannel.userLoginOutputChannel().send(message);
		logger.info("Published message '{}' with status '{}' ", message, status);
		return status;
	}

	public boolean publish(final UserLoggedoutEvent event) {
		Message<UserLoggedoutEvent> message = MessageBuilder.withPayload(event).build();
		logger.info("Publishing message '{}' with payload type '{}' ", message, UserLoggedoutEvent.class);
		boolean status = userAuthenticationChannel.userLogoutOutputChannel().send(message);
		logger.info("Published message '{}' with status '{}' ", message, status);
		return status;
	}
*/}
