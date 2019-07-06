package com.eden.resource.client.common.dto;

import lombok.Data;

import java.util.List;

@Data
public class NginxBlock {

    private String name;

    private String value;

    private List<NginxBlock> nginxBlocks;

    private List<NginxParam> nginxParams;
}