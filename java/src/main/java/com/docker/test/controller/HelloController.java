package com.docker.test.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@Api(tags = "HelloController",description = "控制器")
@RestController
@RequestMapping(value = "/api")
public class HelloController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @ResponseBody
    @RequestMapping(value = "/dockertest",method = RequestMethod.GET)
    @ApiOperation(value="dockertest",notes = "docker测试")
    public String getString(){
        return "Hello Lucas, we have SpringBoot in a Docker container!!";
    }
}
