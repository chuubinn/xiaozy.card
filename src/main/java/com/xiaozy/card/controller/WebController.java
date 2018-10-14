package com.xiaozy.card.controller;


import com.xiaozy.card.util.XmlOrMapToggle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

import static com.xiaozy.card.util.signUtils.createSign;

@Controller
@Slf4j
public class WebController {
    @GetMapping("/MP_verify_bIYfTjRREsRFUbaE.txt")
    @ResponseBody
    public Object txt(){
        return "bIYfTjRREsRFUbaE";
    }

    @GetMapping("/testMD5")
    @ResponseBody
    public Object testMD5(){

        SortedMap<Object, Object> parameters = new TreeMap<>();
        String body = "test";
        String device_info = "web";
        String nonceStr = String.valueOf(new Date().getTime()) + "1";
        String openid = String.valueOf(new Date().getTime()) + "100";
        String out_trade_no = String.valueOf(new Date().getTime()) + "1000";
        String spbill_create_ip = "192.168.75.1";
        String total_fee = "1";


        parameters.put("appid", "wxdfe8e9a69851a406");

        parameters.put("body", body);

        parameters.put("device_info", device_info);

        parameters.put("mch_id", "1488241012");

        parameters.put("notify_url", "http://dsx2016.s1.natapp.cc/shalou/litter/payRes");

        parameters.put("nonce_str", nonceStr);

        parameters.put("openid", openid);

        parameters.put("out_trade_no", out_trade_no);

        parameters.put("spbill_create_ip", spbill_create_ip);

        parameters.put("total_fee", total_fee);

        parameters.put("trade_type", "JSAPI");

        parameters.put("sign_type", "MD5");


        String characterEncoding = "UTF-8";

        String mySign = createSign(characterEncoding, parameters);

        System.out.println("我 的签名是：" + mySign);


        Map<String, String> mapSign = new HashMap<>();
        mapSign.put("sign", mySign);

        mapSign.put("appid", "wxdfe8e9a69851a406");

        mapSign.put("body", body);

        mapSign.put("device_info", device_info);

        mapSign.put("mch_id", "1488241012");

        mapSign.put("notify_url", "http://dsx2016.s1.natapp.cc/shalou/litter/payRes");

        mapSign.put("nonce_str", nonceStr);

        mapSign.put("openid", openid);

        mapSign.put("out_trade_no", out_trade_no);

        mapSign.put("spbill_create_ip", spbill_create_ip);

        mapSign.put("total_fee", total_fee);

        mapSign.put("trade_type", "JSAPI");

        mapSign.put("sign_type", "MD5");

        String xml = XmlOrMapToggle.map2XmlString(mapSign);
        log.info("打印参数xml >>>>>>>>>>>>>>>>>" + xml);
        return xml;
    }

}
