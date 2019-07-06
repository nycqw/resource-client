package com.eden.resource.client.common.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class NginxBlock implements Serializable {

    private String name;

    private String value;

    private List<NginxBlock> nginxBlocks;

    private List<NginxParam> nginxParams;
}