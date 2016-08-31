package org.petru.syncregitry.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SyncRegitryServer.class)
@WebIntegrationTest(randomPort = true)
public class SyncRegistryControllerIT {

	private static final Log logger = LogFactory
			.getLog(SyncRegistryControllerIT.class);

	@Value("${local.server.port}")
	int port;

	private URL base;
	private RestTemplate template;

	@Before
	public void setUp() throws Exception {
		this.base = new URL("http://localhost:" + port + "/");
		template = new TestRestTemplate();
	}

	@Test
	public void testPost() {
		logger.debug("SyncRegistryControllerIT - POST");
		SyncRegistryDefinition definition = new SyncRegistryDefinition();
		String firstSystemId = "1stSystem";
		String firstSystemItem = "1stSystemItem";
		String secondSystemId = "2ndSystem";
		String secondSystemItem = "2ndSystemItem";
		definition.setFirstSystemId(firstSystemId);
		definition.setFirstSystemItem(firstSystemItem);
		definition.setSecondSystemId(secondSystemId);
		definition.setSecondSystemItem(secondSystemItem);
		
		ResponseEntity<ResponseMessage> response = template.postForEntity(base
				+ "syncregistry/", definition, ResponseMessage.class);
		HttpStatus httpStatus = response.getStatusCode();
		assertEquals(HttpStatus.OK, httpStatus);
		ResponseMessage responseMessage = response.getBody();
		assertNotNull(responseMessage);
		assertEquals("Registered sync definition for "+firstSystemId+":"+firstSystemItem+" <-> "+secondSystemId+":"+secondSystemItem, responseMessage.getMessage());
	}
}
