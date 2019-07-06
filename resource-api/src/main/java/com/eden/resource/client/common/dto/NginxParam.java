package com.eden.resource.client.common.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class NginxParam implements Serializable {

    private String name;

    private String value;

}