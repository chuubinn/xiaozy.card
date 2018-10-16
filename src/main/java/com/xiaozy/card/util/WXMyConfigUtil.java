package com.xiaozy.card.util;

import com.github.wxpay.sdk.WXPayConfig;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class WXMyConfigUtil implements WXPayConfig {
     private byte[]  certData;

     public WXMyConfigUtil() throws Exception {

         String certPath = "D:/var/wechat/apiclient_cert.p12";//从微信商户平台下载的安全证书存放的目录

         File file = new File(certPath);
         InputStream certStream = new FileInputStream(file);
         this.certData = new byte[(int) file.length()];
         certStream.read(this.certData);

         certStream.close();
     }

    @Override
    public String getAppID() {
        return "wxdfe8e9a69851a406";
    }

    //parnerid
    @Override
    public String getMchID() {
        return "1488241012";
    }

    @Override
    public String getKey() {
        return "1488241012";
    }

    @Override
    public InputStream getCertStream() {
        ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return 10000;
    }




}
