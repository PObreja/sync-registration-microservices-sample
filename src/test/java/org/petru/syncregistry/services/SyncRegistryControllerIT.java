package org.petru.syncregistry.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.petru.syncregistry.model.TestData;
import org.petru.syncregistry.util.JsonMarshalling;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;
import org.springframework.web.client.RestTemplate;

@RunWith(Parameterized.class)
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

	private String testFile;

	@ClassRule
	public static final SpringClassRule SPRING_CLASS_RULE = new SpringClassRule();

	@Rule
	public final SpringMethodRule springMethodRule = new SpringMethodRule();

	@Before
	public void setUp() throws Exception {
		this.base = new URL("http://localhost:" + port + "/");
		template = new TestRestTemplate();
		initTestData();
	}

	public SyncRegistryControllerIT(String testFile) {
		this.testFile = testFile;
	}

	@Parameterized.Parameters
	public static Collection parameters() {
		return Arrays.asList(new Object[][] { { "test01.json" },
				{ "test02.json" } });
	}

	private void initTestData() throws IOException {
		// Read in our JSON representation
		String json = IOUtils.toString(
				this.getClass()
						.getClassLoader()
						.getResourceAsStream(
								"org/petru/syncregistry/services/" + testFile),
				"UTF-8");
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
				+ "syncregistry/", testData.getRequestPayload(),
				ResponseMessage.class);
		HttpStatus httpStatus = response.getStatusCode();
		assertEquals(HttpStatus.OK, httpStatus);
		ResponseMessage responseMessage = response.getBody();
		assertNotNull(responseMessage);
		assertEquals(testData.getResponsePayload().getMessage(),
				responseMessage.getMessage());
	}
}
