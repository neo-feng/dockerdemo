package com.mzinno.sampleservice.city.controller;



import com.mzinno.sampleservice.models.city.City;
import com.mzinno.sampleservice.city.service.CityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "CityController",description = "控制器")
@RestController
//@RequestMapping(value = "/service-city")
public class CityController {
    @Autowired
    private CityService cityService;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @ResponseBody
    @RequestMapping(value = "/get-cities",method = RequestMethod.GET)
    @ApiOperation(value="getCities",notes = "获取全部城市")
    public List<City> getCities(){
        List<City> cities = cityService.getCities();
        return cities;
    }

    @ResponseBody
    @RequestMapping(value = "/getString",method = RequestMethod.POST)
    @ApiOperation(value="getString",notes = "获取一个String")
    public String getString(){
        try {
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            Enumeration<String> headerNames = request.getHeaderNames();
            String t = request.getHeader("AuthToken");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "This is from city-service";
    }

    @ResponseBody
    @RequestMapping(value = "/get-by-country/{countrycode}",method = RequestMethod.GET)
    @ApiOperation(value="getCitiesByCountry",notes = "根据国家获取城市")
    public List<City> getCitiesByCountry(@PathVariable("countrycode") String countryCode){
        List<City> cityList = cityService.getCitiesByCountry(countryCode);
        return cityList;
    }

    @ResponseBody
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ApiOperation(value="getCityById",notes = "根据id获取城市")
    public City getCityById(@PathVariable("id") Integer id){
        return  cityService.getCityById(id);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value="insert",notes = "插入城市")
    public int insert(@RequestBody City city){
        return  cityService.insert(city);
    }

    @ResponseBody
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value="deleteCityById",notes = "根据id删除城市")
    public int deleteCityById(@PathVariable("id") Integer id){
        return  cityService.deleteById(id);
    }

    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ApiOperation(value="update",notes = "更新城市")
    public int update(@RequestBody City city){
        return  cityService.update(city);
    }

    @ApiOperation(value = "uploadFile",notes = "上传文件")
    @RequestMapping(value = "/uploadFile",method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadFile(@RequestPart("file") MultipartFile file) {
        try{
            InputStream inputStream = file.getInputStream();
            String result = new BufferedReader(new InputStreamReader(inputStream))
                    .lines().collect(Collectors.joining(System.lineSeparator()));
            System.out.println(result);
            return result;
        } catch(Exception ex) {
            return ex.getMessage();
        }
    }
}
