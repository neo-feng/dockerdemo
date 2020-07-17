package com.mzinno.sampleservice.city.test;


import com.mzinno.sampleservice.city.ServiceCityApplication;
import com.mzinno.sampleservice.citycommon.mapper.CityMapper;
import com.mzinno.sampleservice.models.city.City;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceCityApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
public class CityDaoTest {

    @Autowired
    private CityMapper cityMapper;

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
    public void testgetCityById() {
        insertCity(9999);
        City city = cityMapper.selectByPrimaryKey(9999);
        assertNotNull(city);
    }

    @Test
    public void testInsert() {
        int result = insertCity(9999);
        assertEquals(1,result);
    }


    @Test
    public void testUpdate() {
        insertCity(9999);
        City city = cityMapper.selectByPrimaryKey(9999);
        city.setName("2");
        int result = cityMapper.update(city);
        assertEquals(1,result);
    }

    private int insertCity(Integer id){
        City city = constructCity(id);
        int result = cityMapper.insert(city);
        return result;
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

    private void deleteAll(){
        cityMapper.deleteByPrimaryKey(9999);
    }
}