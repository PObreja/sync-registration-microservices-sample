package org.petru.syncregistry.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.petru.syncregistry.model.TestData;
import org.petru.syncregistry.services.ResponseMessage;
import org.petru.syncregistry.services.SyncRegistryDefinition;
import org.petru.syncregistry.services.SyncRegitryServer;
import org.petru.syncregistry.util.JsonMarshalling;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;
import org.apache.commons.io.IOUtils;

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
	
	private TestData testData;

	@Before
	public void setUp() throws Exception {
		this.base = new URL("http://localhost:" + port + "/");
		template = new TestRestTemplate();
		initTestData();
	}

	private void initTestData() throws IOException {
		// Read in our JSON representation
		String json = IOUtils.toString(
				this.getClass()
						.getClassLoader()
						.getResourceAsStream(
								"org/petru/syncregistry/services/"
										+ "test01.json"), "UTF-8");
		testData = JsonMarshalling.unmarshalling(json, TestData.class);
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
				+ "syncregistry/", testData.getRequestPayload(), ResponseMessage.class);
		HttpStatus httpStatus = response.getStatusCode();
		assertEquals(HttpStatus.OK, httpStatus);
		ResponseMessage responseMessage = response.getBody();
		assertNotNull(responseMessage);
		assertEquals(testData.getResponsePayload().getMessage(), responseMessage.getMessage());
	}
}
