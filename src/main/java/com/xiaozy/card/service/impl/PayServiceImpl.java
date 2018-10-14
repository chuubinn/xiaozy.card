package com.xiaozy.card.service.impl;

import com.xiaozy.card.dataobject.UserInfo;
import com.xiaozy.card.repository.PayServiceRepository;
import com.xiaozy.card.util.SHA1;
import com.xiaozy.card.util.SendHttpRequest;
import com.xiaozy.card.util.XmlOrMapToggle;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.xiaozy.card.util.HttpClientUtils.postXML;
import static com.xiaozy.card.util.signUtils.createSign;

@Service
@Slf4j
public class PayServiceImpl {

    @Autowired
    private PayServiceRepository repository;

    //登陆极客小助手授权
    public String login(String code, String state){
       //微信官方文档登陆链接,获取access_token和openid
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wxdfe8e9a69851a406&secret=16007b600d84658cbbe894488efb9940&code="
                     + code + "&grant_type=authorization_code";

        JSONObject json = SendHttpRequest.sendGet(url);
        log.info("json={}",json);
        String login_openid = json.getString("openid");
        log.info("login_opendi={}",login_openid);
        String login_access_token = json.getString("access_token");
        log.info("login_access_token={}",login_access_token);
        String lang ="zh_CN";

        //获取用户信息url
        String userInfo_url = "https://api.weixin.qq.com/sns/userinfo?access_token="
                              + login_access_token + "&openid=" + login_openid + "&lang=" + lang;
        UserInfo user1 = repository.findByOpenid(login_openid);
        log.info("user1={}",user1);
        if (user1 ==null) {
            log.info("当前用户不存在");
            //获取用户信息
            JSONObject userInfo = SendHttpRequest.sendGet(userInfo_url);
            //new一个新的用户对象
            UserInfo addUserOne = new UserInfo();
            try {
                //转换微信的响应格式
                String openid = new String(userInfo.getString("openid").getBytes("ISO-8859-1"),"utf-8");
                String nickname= new String(userInfo.getString("nickname").getBytes("ISO-8859-1"),"UTF-8");
                String sex= new String(userInfo.getString("sex").getBytes("ISO-8859-1"),"UTF-8");
                String province= new String(userInfo.getString("province").getBytes("ISO-8859-1"),"UTF-8");
                String city= new String(userInfo.getString("city").getBytes("ISO-8859-1"),"UTF-8");
                String country= new String(userInfo.getString("country").getBytes("ISO-8859-1"),"UTF-8");
                String headimgurl= new String(userInfo.getString("headimgurl").getBytes("ISO-8859-1"),"UTF-8");
                String privilege = new String(userInfo.getString("privilege").getBytes("ISO-8859-1"),"UTF-8");
                try {
                    String unionid = new String(userInfo.getString("unionid").getBytes("ISO-8859-1"),"utf-8");
                    addUserOne.setNickname(unionid);
                }catch (Exception e){
                   // e.printStackTrace();
                }
                //设置新用户信息
                addUserOne.setOpenid(openid);
                addUserOne.setNickname(nickname);
                addUserOne.setCity(city);
                addUserOne.setSex(sex);
                //将新用户信息保存到数据库中
                repository.save(addUserOne);
                log.info("addUserOne={}",addUserOne);
                return login_openid;
            } catch (Exception e) {
                log.info("新用户添加失败");
                return login_openid;
            }
        }
        //如果当前用户已存在数据库中，打印用户信息
        else {
            log.info("用户信息+ userInfo={}",user1.toString());
            return login_openid;
        }
    }

    //获取jsapi的config参数
    public Object  getConfig(String timestamp,String noncestr,String url,String openid){
        Integer timeStart = 0;
        Integer timeNow = 0;
        //判断数据库中已有用户
        UserInfo user1 = repository.findByOpenid(openid);
        //获取获得access_token时间戳
        String access_token_time = user1.getAccessTokenTime();
        if (access_token_time == null || access_token_time == ""||access_token_time.isEmpty()){
            timeStart = Integer.valueOf(timestamp)-7500;
        }else {
            //把创建的时间戳改为数字
            timeStart = Integer.valueOf(access_token_time);
        }
        if (timestamp == null || timestamp == ""||timestamp.isEmpty()){
            timeStart = 0;
        }else {
            //把当前时间转化为数字
            timeNow = Integer.valueOf(timestamp);
        }
        //判断access_token时间（7200s有效期）
        //大于7000则access_token失效，需要重新获取access_token
        if (timeNow -timeStart >7000){
            log.info("初始化ticket");
            //初始化签名变量
            String signature = "";
            //获取js-api中的token   (需要在公众号中设置白名单才能刷新token)
            String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxdfe8e9a69851a406&secret=16007b600d84658cbbe894488efb9940";
            //获取access_token有效期2小时
            JSONObject json = SendHttpRequest.sendGet(tokenUrl);
            log.info(json.toString());
            //获取到json中的access_token
            String access_token = json.getString("access_token");

            //获取ticket_url
            String ticket_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + access_token + "&type=jsapi";
            String jsapi_ticket = SendHttpRequest.sendGet(ticket_url).getString("ticket");
            log.info("jsapi_ticket"+jsapi_ticket);
            //jsapi签名(其他三个参数由前端传递过来,保持一致)
            String sign = "jsapi_ticket" +jsapi_ticket +
                          "&noncestr" + noncestr+
                          "&timestamp" + timestamp+
                          "&url" + url+
                          "";
            try{
                 //获取签名
                signature = new SHA1().getDigestOfString(sign.getBytes("utf-8"));
                log.info("signature={}",signature);


            }catch (Exception e){
                e.printStackTrace();
            }
            //把数据保存到数据库中
            user1.setAccessToken(access_token);
            user1.setAccessTokenTime(timestamp);
            user1.setJsapiTicket(jsapi_ticket);
            user1.setTimeOut("7200");
            repository.save(user1);
            //将数据放入map，然后转化为json发到前端
            Map<Object,Object> config = new HashMap<>();
            config.put("appId","wxdfe8e9a69851a406");
            config.put("timestamp",timestamp);
            config.put("nonceStr",noncestr);
            config.put("signature",signature);
            config.put("ticket",jsapi_ticket);
            JSONObject jsonRes = JSONObject.fromObject(config);
            return jsonRes;

        }else {
            log.info("直接加密");
            log.info("jsapiTicket={}",user1.getJsapiTicket());
            String signature = "";
            //签名和前面一样
            String sign = "jsapi_ticket=" + user1.getJsapiTicket() +
                          "&noncestr=" + noncestr +
                          "&timestamp=" + timestamp +
                          "&url=" + url +
                          "";
            try{
                signature = new SHA1().getDigestOfString(sign.getBytes("utf-8"));
                log.info("signature={}",signature);
            }catch (Exception e){
                e.printStackTrace();
            }
            //将数据放入到map并转化为json返还给前端
            Map<Object,Object> config = new HashMap<>();
            config.put("appId", "wxdfe8e9a69851a406");
            config.put("timestamp", timestamp);
            config.put("nonceStr", noncestr);
            config.put("signature", signature);
            config.put("ticket", user1.getJsapiTicket());
            JSONObject jsonRes = JSONObject.fromObject(config);
            return jsonRes;
        }
    }

    //微信统一下单
    public Object wxPay(String body,String device_info,String nonceStr,String out_trade_no,
                        String total_fee,String spbill_create_ip,String timeStamp) throws Exception{
        //打印参数查看是否获取到
        log.info("打印参数 >>>>>>>>>>>>>>>>>");
        log.info("body >>>>" + body);
        log.info("device_info >>>>" + device_info);
        log.info("nonceStr >>>>" + nonceStr);
        log.info("out_trade_no >>>>" + out_trade_no);
        log.info("spbill_create_ip >>>>" + spbill_create_ip);
        log.info("total_fee >>>>" + total_fee);
        log.info("timeStamp >>>>" + timeStamp);
        //timeStamp二次签名需要(统一下单不需要)注意s大小写,前后端不一致
        //设置map数据
        SortedMap<Object, Object> parameters = new TreeMap<>();


        parameters.put("appid", "wxdfe8e9a69851a406");

        parameters.put("body", body);

        parameters.put("device_info", device_info);

        parameters.put("mch_id", "1488241012");

        parameters.put("notify_url", "http://dsx2016.s1.natapp.cc/shalou/litter/payRes");

        parameters.put("nonce_str", nonceStr);

        parameters.put("openid", "opBQtwcFrxY89xFPrAKwzXLhmop4");

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

        mapSign.put("openid", "opBQtwcFrxY89xFPrAKwzXLhmop4");

        mapSign.put("out_trade_no", out_trade_no);

        mapSign.put("spbill_create_ip", spbill_create_ip);

        mapSign.put("total_fee", total_fee);

        mapSign.put("trade_type", "JSAPI");

        mapSign.put("sign_type", "MD5");

        String xml = XmlOrMapToggle.map2XmlString(mapSign);
        log.info("打印参数xml >>>>>>>>>>>>>>>>>" + xml);

        //微信统一下单
        String url =  "https://api.mch.weixin.qq.com/pay/unifiedorder";
        //将数据转化成xml
        try{
            String responseContent = postXML(url, xml);

            System.out.println("商户返回:" + responseContent);
            Map map2 = XmlOrMapToggle.readStringXmlOut(responseContent);

            JSONObject jsonRes = JSONObject.fromObject(map2);

            System.out.println("map2：" + map2);

            String prepay_id = jsonRes.getString("prepay_id");
            System.out.println("获取二次签名需要的参数：" + prepay_id);


            SortedMap<Object, Object> signAgian = new TreeMap<Object, Object>();

            signAgian.put("appId", "wxdfe8e9a69851a406");

            signAgian.put("nonceStr", nonceStr);

            signAgian.put("package", "prepay_id=" + prepay_id);

            signAgian.put("signType", "MD5");

            signAgian.put("timeStamp", timeStamp);


            String paySign = createSign(characterEncoding, signAgian);

            System.out.println(" mySignTwo的签名是：" + paySign);

            Map<String, String> resMap = new HashMap<>();

            resMap.put("package", "prepay_id=" + prepay_id);
            resMap.put("paySign", paySign);

            return resMap;

        }catch (Exception e){
            e.printStackTrace();
        }
        return "出错了";
    }

    /**
     * 微信支付回调需要用到工具方法
     */
    public static StringBuilder setXmlKV(StringBuilder sb, String Key, String value) {
        sb.append("<");
        sb.append(Key);
        sb.append(">");

        sb.append(value);

        sb.append("</");
        sb.append(Key);
        sb.append(">");

        return sb;
    }
    /**
     * 解析XML 获得名称为para的参数值
     *
     * @param xml
     * @param para
     * @return
     */
    public static String getXmlPara(String xml, String para) {
        int start = xml.indexOf("<" + para + ">");
        int end = xml.indexOf("</" + para + ">");

        if (start < 0 && end < 0) {
            return null;
        }
        return xml.substring(start + ("<" + para + ">").length(), end).replace("<![CDATA[", "").replace("]]>", "");
    }

    //支付回调，异步通知（告诉微信支付成功了）
    public String payRes(HttpServletRequest request){
        //初始化变量
        String inputLine;
        String notifyXml = " ";
        String resXml = "";
        try {
            while ((inputLine = request.getReader().readLine()) != null) {
                notifyXml += inputLine;
            }
            request.getReader().close();
        } catch (Exception e) {
            //logger.debug("xml获取失败：" + e);
            e.printStackTrace();
        }
        System.out.println("接收到的xml：" + notifyXml);

        //微信发来的数据(用于验证签名等)
        String appid = getXmlPara(notifyXml, "appid");
        String bank_type = getXmlPara(notifyXml, "bank_type");
        String cash_fee = getXmlPara(notifyXml, "cash_fee");
        String fee_type = getXmlPara(notifyXml, "fee_type");
        String is_subscribe = getXmlPara(notifyXml, "is_subscribe");
        String mch_id = getXmlPara(notifyXml, "mch_id");
        String nonce_str = getXmlPara(notifyXml, "nonce_str");
        String openid = getXmlPara(notifyXml, "openid");
        String out_trade_no = getXmlPara(notifyXml, "out_trade_no");
        String result_code = getXmlPara(notifyXml, "result_code");
        String return_code = getXmlPara(notifyXml, "return_code");
        String sign = getXmlPara(notifyXml, "sign");
        String time_end = getXmlPara(notifyXml, "time_end");
        String total_fee = getXmlPara(notifyXml, "total_fee");
        String trade_type = getXmlPara(notifyXml, "trade_type");
        String transaction_id = getXmlPara(notifyXml, "transaction_id");

        //todo和数据库中的信息对比

        //如果成功了，返回一下信息
        System.out.println("支付成功....");
        resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
        return resXml;
    }
}
