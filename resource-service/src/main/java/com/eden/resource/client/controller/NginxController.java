package com.eden.resource.client.controller;

import com.alibaba.fastjson.JSONObject;
import com.eden.resource.client.common.dto.Result;
import com.eden.resource.client.service.NginxService;
import com.github.odiszapc.nginxparser.NgxConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/nginx")
public class NginxController {

    @Autowired
    private NginxService nginxService;

    @GetMapping("read")
    public Result read() {
        NgxConfig config = nginxService.read();
        return Result.success(JSONObject.toJSONString(config));
    }

    @PostMapping("save")
    public Result save(@RequestBody NgxConfig conf) {
        nginxService.save(conf);
        return Result.success();
    }

    @PostMapping("bak")
    public Result save(@RequestParam String conf) {
        nginxService.save(conf);
        return Result.success();
    }

}
