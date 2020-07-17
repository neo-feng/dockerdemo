package com.mzinno.sampleservice.city.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mzinno.sampleservice.models.city.City;
import com.mzinno.sampleservice.city.ServiceCityApplication;
import com.mzinno.sampleservice.city.controller.CityController;
import com.mzinno.sampleservice.citycommon.mapper.CityMapper;
import org.json.JSONArray;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import java.io.IOException;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceCityApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
public class CityControllerTest {

	private static final ObjectMapper mapper = new ObjectMapper();
	@InjectMocks
	private CityController cityController;
	@Autowired
	private CityMapper cityMapper;
	@Autowired
	private TestRestTemplate testRestTemplate;

	@BeforeClass
	public static void setup() throws IOException {

	}
	@Before
	public  void before(){
		deleteAll();

		//prepare shared test data
	}

	@After
	public  void delete(){
		deleteAll();
	}

    @Test
	public void testGetCitiesByCountryMock() throws Exception {
		String countryCode = "CHN";
		//int originCount = cityMapper.getCitiesByCountry(countryCode).size();

		City city1 = constructCity(9997);
		city1.setCountrycode(countryCode);
		city1.setName("1");
		City city2 = constructCity(9998);
		city2.setCountrycode(countryCode);
		city2.setName("2");
		City city3 = constructCity(9999);
		city3.setCountrycode(countryCode);
		city3.setName("3");
		cityMapper.insert(city1);
		cityMapper.insert(city2);
		cityMapper.insert(city3);

		ResponseEntity<String> response = testRestTemplate.getForEntity("/get-by-country/" + countryCode,String.class);
		Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
		String data = response.getBody();
		Assert.assertNotNull(data);
		assertNotEquals("", data.trim());
		JSONArray array = new JSONArray(data);
		assertNotNull(array);
		int currentCount = array.length();
		assertNotEquals(0, currentCount);
	}
	@Test
	public void testInsertCityMock() throws Exception {
		int id = 9999;
		City city = constructCity(id);
		ResponseEntity<String> response = templatePost("/add", city, HttpMethod.POST);
		Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);

		City cityy = cityMapper.selectByPrimaryKey(id);
		assertNotNull(cityy);
		assertEquals(new Integer(id), new Integer(cityy.getId()));
	}

	@Test
	public void testUpdateControllerMock() throws Exception {
		int id = 9999;
		cityMapper.insert(constructCity(id));
		City city = cityMapper.selectByPrimaryKey(id);
		String newName = "new";
		city.setName(newName);
		ResponseEntity<String> response = templatePost("/update", city, HttpMethod.PUT);
		Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);

		City cityy = cityMapper.selectByPrimaryKey(id);
		assertNotNull(cityy);
		assertEquals(newName, cityy.getName());
	}

	@Test
	public void testDeleteControllerMock() throws Exception {
		int id  = 9999;
		City city = constructCity(id);
		cityMapper.insert(city);
		City city1 = cityMapper.selectByPrimaryKey(id);
		assertNotNull(city1);
		assertEquals(new Integer(id), new Integer(city1.getId()));
		ResponseEntity<String> response = templatePost("/delete/" + id, null, HttpMethod.DELETE);
		Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);

		City cityy = cityMapper.selectByPrimaryKey(id);
		assertNull(cityy);
	}

	private ResponseEntity<String> templatePost(String url, Object data, HttpMethod method) throws JsonProcessingException {
		HttpHeaders headers = new HttpHeaders();
		MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
		headers.setContentType(type);
		headers.add("Accept", MediaType.APPLICATION_JSON.toString());
		String postData = new ObjectMapper().writeValueAsString(data);
		HttpEntity<String> entity = new HttpEntity(postData, headers);
		ResponseEntity<String> response = testRestTemplate.exchange(url, method, entity, String.class);
		return response;
	}

	private void deleteAll(){
		cityMapper.deleteByPrimaryKey(9997);
		cityMapper.deleteByPrimaryKey(9998);
		cityMapper.deleteByPrimaryKey(9999);
	}

	public static City constructCity(Integer id){
		City city = new City();
		city.setId(id);
		city.setName("1");
		city.setCountrycode("CHN");
		city.setPopulation(1);
		city.setDistrict("1");
		return city;
	}
}
