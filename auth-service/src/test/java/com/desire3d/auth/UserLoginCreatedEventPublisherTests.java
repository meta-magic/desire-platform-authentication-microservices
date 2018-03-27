package com.desire3d.auth;

import java.util.UUID;

import com.desire3d.auth.beans.LoginInfoHelperBean;
import com.desire3d.event.UserLoginCreatedEvent;
import com.desire3d.event.publisher.UserLoginCreatedEventPublisher;

/**
 * @author Mahesh Pardeshi
 *
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest
public class UserLoginCreatedEventPublisherTests {

//	@Autowired
	private UserLoginCreatedEventPublisher publisher;

//	@Test
	public void test() {
		publisher.publish(new UserLoginCreatedEvent(UUID.randomUUID().toString()), new LoginInfoHelperBean());
	}

}
