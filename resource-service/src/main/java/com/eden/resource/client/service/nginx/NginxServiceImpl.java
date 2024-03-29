package com.eden.resource.client.service.nginx;

import com.eden.resource.client.common.constants.Constants;
import com.eden.resource.client.common.exception.NginxException;
import com.eden.resource.client.service.NginxService;
import com.github.odiszapc.nginxparser.NgxConfig;
import com.github.odiszapc.nginxparser.NgxDumper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author chenqw
 * @version 1.0
 * @since 2019/6/21
 */
@Service
@Slf4j
public class NginxServiceImpl implements NginxService {

    @Value("${nginx.conf}")
    private String nginxConfPath;

    @Value("${nginx.ip}")
    private String ip;

    /**
     * 读取配置文件
     *
     * @return
     */
    @Override
    public NgxConfig read() {
        try {
            String url = nginxConfPath + Constants.NGINX_CONF_NAME;
            log.info("url: {}", url);
            NgxConfig ngxConfig = NgxConfig.read(url);
            ngxConfig.addValue("conf");
            ngxConfig.addValue(this.ip);
            return ngxConfig;
        } catch (IOException e) {
            throw new NginxException();
        }
    }

    /**
     * 写配置到文件中
     *
     * @param conf
     */
    @Override
    public void save(NgxConfig conf) {
        try (FileOutputStream out = new FileOutputStream(nginxConfPath + Constants.NGINX_CONF_NAME)) {
            out.write(toString(conf).getBytes("UTF-8"));
            out.flush();
        } catch (Exception e) {
            throw new NginxException("Nginx配置文件写入失败");
        }
    }

    /**
     * 写配置到文件中
     * @param conf
     */
    @Override
    public void save(String conf) {
        try (FileOutputStream out = new FileOutputStream(nginxConfPath + Constants.NGINX_CONF_NAME)) {
            out.write(conf.getBytes("UTF-8"));
            out.flush();
        } catch (Exception e) {
            throw new NginxException("Nginx配置文件写入失败");
        }
    }

    private String toString(NgxConfig conf) {
        if (null == conf) {
            throw new NginxException("不能写入空配置");
        }
        NgxDumper dumper = new NgxDumper(conf);
        return dumper.dump();
    }

}
