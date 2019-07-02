package com.eden.resource.client.service;

import com.github.odiszapc.nginxparser.NgxConfig;

/**
 * @author chenqw
 * @version 1.0
 * @since 2019/6/30
 */
public interface NginxService {

    String getNginxConfPath();

    NgxConfig read();

    void save(NgxConfig conf);

    void save(String conf);
}
