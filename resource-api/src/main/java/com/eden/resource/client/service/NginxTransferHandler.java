package com.eden.resource.client.service;

import com.eden.resource.client.common.dto.NginxBlock;
import com.eden.resource.client.common.dto.NginxParam;
import com.github.odiszapc.nginxparser.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NginxTransferHandler {

    public static NginxBlock transferNgxConfig(NgxBlock ngxBlock) {
        NginxBlock nginxBlock = new NginxBlock();
        if (ngxBlock.getTokens().size() > 0) {
            Iterator<NgxToken> iterator = ngxBlock.getTokens().iterator();
            nginxBlock.setName(iterator.next().getToken());
            while (iterator.hasNext()) {
                nginxBlock.setValue(iterator.next().getToken());
            }
        }

        List<NginxParam> nginxParams = new ArrayList<>();
        ArrayList<NginxBlock> nginxBlocks = new ArrayList<>();
        Iterator<NgxEntry> entryIterator = ngxBlock.getEntries().iterator();
        while (entryIterator.hasNext()) {
            NgxEntry ngxEntry = entryIterator.next();
            if (ngxEntry instanceof NgxParam) {
                NginxParam nginxParam = new NginxParam();
                NgxParam ngxParam = (NgxParam) ngxEntry;
                Iterator<NgxToken> tokenIterator = ngxParam.getTokens().iterator();
                nginxParam.setName(tokenIterator.next().getToken());
                nginxParam.setValue(getValue(tokenIterator));
                nginxParams.add(nginxParam);
            }

            if (ngxEntry instanceof NgxBlock) {
                NginxBlock subBlock = transferNgxConfig((NgxBlock) ngxEntry);
                nginxBlocks.add(subBlock);
            }
        }
        nginxBlock.setNginxBlocks(nginxBlocks);
        nginxBlock.setNginxParams(nginxParams);
        return nginxBlock;
    }

    public static NgxConfig reverseNgxConfig(NginxBlock nginxBlock) {
        NgxBlock ngxBlock = NginxTransferHandler.transferNgxConfig(nginxBlock);
        NgxConfig ngxConfig = new NgxConfig();
        Iterator<NgxEntry> iterator = ngxBlock.getEntries().iterator();
        while (iterator.hasNext()) {
            ngxConfig.addEntry(iterator.next());
        }
        return ngxConfig;
    }

    public static NgxBlock transferNgxConfig(NginxBlock nginxBlock) {
        NgxBlock ngxBlock = new NgxBlock();
        ngxBlock.addValue(nginxBlock.getName());
        if (nginxBlock.getValue() != null) {
            ngxBlock.addValue(nginxBlock.getValue());
        }

        List<NginxParam> nginxParams = nginxBlock.getNginxParams();
        if (nginxParams != null) {
            for (NginxParam nginxParam : nginxParams) {
                NgxParam ngxParam = new NgxParam();
                ngxParam.addValue(nginxParam.getName());
                String[] tokens = nginxParam.getValue().split(" ");
                for (String token : tokens) {
                    ngxParam.addValue(token);
                }
                ngxBlock.addEntry(ngxParam);
            }
        }

        List<NginxBlock> nginxBlocks = nginxBlock.getNginxBlocks();
        if (nginxBlocks != null) {
            for (NginxBlock block : nginxBlocks) {
                NgxBlock subNgxBlock = transferNgxConfig(block);
                ngxBlock.addEntry(subNgxBlock);
            }
        }

        return ngxBlock;
    }

    private static String getValue(Iterator<NgxToken> iterator) {
        StringBuilder value = new StringBuilder();
        while (iterator.hasNext()) {
            value.append(iterator.next().getToken()).append(" ");
        }
        return value.toString().trim();
    }
}
