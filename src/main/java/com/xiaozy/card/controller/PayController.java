package com.xiaozy.card.controller;

import com.xiaozy.card.dataobject.UserInfo;
import com.xiaozy.card.repository.PayServiceRepository;
import com.xiaozy.card.service.impl.PayServiceImpl;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/wechat")
@Slf4j
public class PayController {

    @Autowired
    private PayServiceImpl payService;

    @Autowired
    private PayServiceRepository repository;

    /**
     * 授权之后的重定向页面
     * @param openid
     * @return
     */
    @GetMapping("/litter/index")
    public ModelAndView index(@RequestParam("openid") String openid){
        log.info("openid={}",openid);

        return new ModelAndView("index");

    }


    /**
     * 用户登录授权
     * @param userInfo
     * @param code
     * @param state
     * @return
     */
    @GetMapping("/litter/login")
    public Object login(UserInfo userInfo,
                        @RequestParam("code") String code,
                        @RequestParam("state") String state){
        String res = payService.login(code,state);
        log.info("res={}",res);
        //如果授权成功，则回调到首页
        if (!res.isEmpty()){
            //重定向的地址
            return "redirect:http://xiaozy.natapp1.cc/wechat/litter/index?openid=" + res;
        }else{
            //若过授权失败，回调到错误页面
            log.info("登陆授权不成功");
            return "index.ftl";
        }

    }
    @ResponseBody
    @GetMapping("/getConfig")
    public  Object getConfig(@RequestParam("timeStamp") String timestamp,
                             @RequestParam("nonceStr") String nonceStr,
                             @RequestParam("url") String url,
                             @RequestParam("openid") String openid){
        log.info("timestamp={}",timestamp);
        log.info("nonceStr+{}" + nonceStr);
        log.info("url+{}" + url);
        log.info("openid+{}" + openid);
        //返回config需要的参数
        return payService.getConfig(timestamp,nonceStr,url,openid);
    }

    /**
     * map转化成json
     * @return
     */
    @GetMapping("/testJson")
    @ResponseBody
    public Object testJson(){
        Map<Object,Object> config = new HashMap<>();
        config.put("timestamp", "12313");
        config.put("noncestr", "sjkfa");
        JSONObject jsonTest = JSONObject.fromObject(config);
        return jsonTest;
    }


    @GetMapping("/little/wePay")
    @ResponseBody
    public Object wePay(@RequestParam("body") String body,
                        @RequestParam("device_info") String device_info,
                        @RequestParam("nonceStr") String nonceStr,
                        @RequestParam("out_trade_no") String out_trade_no,
                        @RequestParam("total_fee") String total_fee,
                        @RequestParam("spbill_create_ip") String spbill_create_ip,
                        @RequestParam("timeStamp") String timeStamp)throws Exception{
        log.info("body={}",body);
        return payService.wxPay(body,device_info,nonceStr,out_trade_no,total_fee,spbill_create_ip,timeStamp);
    }

    @PostMapping("/litter/payRes")
    @ResponseBody
    public String payRes(HttpServletRequest request){
        return payService.payRes(request);
    }

    @GetMapping("/index")
    public ModelAndView index(){
        return new ModelAndView("index");
    }


}


