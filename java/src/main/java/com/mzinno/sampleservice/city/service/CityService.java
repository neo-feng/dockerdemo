package com.mzinno.sampleservice.city.service;




import com.mzinno.sampleservice.models.city.City;

import java.util.List;

public interface CityService {
     List<City> getCities();

     List<City> getCitiesByCountry(String countryCode);

     City getCityById(Integer id);

     int deleteById(Integer id);

     int update(City city);

     int insert(City city);

}
