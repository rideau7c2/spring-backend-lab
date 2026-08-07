package com.gitlab.rideau7c2.backendlab;

import com.gitlab.rideau7c2.backendlab.component.LoginCounter;
import com.gitlab.rideau7c2.backendlab.model.LoginEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL) konfiguracja zamiast @Autowired nad konstruktorem
class SpringBackendLabApplicationTests {
	private final LoginCounter loginCounter;

	@Autowired
	SpringBackendLabApplicationTests(LoginCounter counter) {
		this.loginCounter = counter;
	}

	@Test
	void contextLoads() {
        assertNotNull(loginCounter);
	}

	@Test
	void registerLoginShouldIncrementCounter() {
		loginCounter.registerLogin(new LoginEvent(1L));
		loginCounter.registerLogin(new LoginEvent(2L));
		loginCounter.registerLogin(new LoginEvent(1L));

		assertEquals(2, loginCounter.getLoginCount(1L));
		assertEquals(1, loginCounter.getLoginCount(2L));
		assertEquals(0, loginCounter.getLoginCount(3L));
	}
}
