package com.eden.resource.client.controller;

import com.eden.resource.client.common.dto.Result;
import com.eden.resource.client.common.dto.NginxBlock;
import com.eden.resource.client.service.NginxService;
import com.eden.resource.client.service.NginxTransferHandler;
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
        NginxBlock nginxBlock = NginxTransferHandler.transferNgxConfig(config);
        return Result.success(nginxBlock);
    }

    @PostMapping("save")
    public Result save(@RequestBody NginxBlock conf) {
        NgxConfig config = NginxTransferHandler.reverseNgxConfig(conf);
        nginxService.save(config);
        return Result.success();
    }

    @PostMapping("bak")
    public Result save(@RequestBody String conf) {
        nginxService.save(conf);
        return Result.success();
    }

}
