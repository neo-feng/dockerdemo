package com.mzinno.sampleservice.city.service;


import com.mzinno.sampleservice.models.city.City;
import com.mzinno.sampleservice.citycommon.mapper.CityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service(value="cityService")
public class CityServiceImpl implements CityService {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CityMapper cityMapper;

    public List<City> getCities() {
        return cityMapper.getCities();
    }
    public List<City> getCitiesByCountry(String countryCode) {
        return cityMapper.getCitiesByCountry(countryCode);
    }

    public City getCityById(Integer id){
        return cityMapper.selectByPrimaryKey(id);
    }

    public int deleteById(Integer id){
        return cityMapper.deleteByPrimaryKey(id);
    }

    public int update(City city){
        return cityMapper.update(city);
    }

    public int insert(City city) {
        log.info("in city insert");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 0;
       //return cityMapper.insert(city);
    }
}
