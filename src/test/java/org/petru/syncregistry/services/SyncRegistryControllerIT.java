package org.petru.syncregistry.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.IOUtils;
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
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;
import org.springframework.web.client.RestTemplate;

@RunWith(Parameterized.class)
@SpringApplicationConfiguration(classes = SyncRegitryServer.class)
@WebIntegrationTest(randomPort = true)
public class SyncRegistryControllerIT {

	@Value("${local.server.port}")
	int port;

	private static String pathToTestFiles = "org/petru/syncregistry/services/";

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
	public static Collection<String> parameters() throws IOException {
		List<String> fileNames = new ArrayList<String>();
		ClassLoader classloader = SyncRegistryControllerIT.class
				.getClassLoader();
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(
				classloader);
		Resource[] resources = resolver
				.getResources(pathToTestFiles + "*.json");
		for (Resource resource : resources) {
			fileNames.add(resource.getFilename());
		}
		return fileNames;
	}

	private void initTestData() throws IOException {
		String json = IOUtils.toString(this.getClass().getClassLoader()
				.getResourceAsStream(pathToTestFiles + testFile), "UTF-8");
		testData = JsonMarshalling.unmarshalling(json, TestData.class);
	}

	private void validateResponsePayload(
			ResponseEntity<ResponseMessage> response) {
		ResponseMessage responseMessage = response.getBody();
		assertNotNull(responseMessage);
		assertEquals(testData.getResponsePayload().getMessage(),
				responseMessage.getMessage());
	}

	private void validateResponseStatus(ResponseEntity<ResponseMessage> response) {
		assertEquals(response.getStatusCode().value(), testData
				.getResponseStatus().getCode());
	}

	private ResponseEntity<ResponseMessage> postRequest() {
		ResponseEntity<ResponseMessage> response = template.postForEntity(base
				+ "syncregistry/", testData.getRequestPayload(),
				ResponseMessage.class);
		return response;
	}

	@Test
	public void testPost() {
		ResponseEntity<ResponseMessage> response = postRequest();

		validateResponseStatus(response);

		validateResponsePayload(response);
	}
}
