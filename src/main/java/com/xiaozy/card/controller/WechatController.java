package com.xiaozy.card.controller;

import com.google.gson.Gson;
import com.xiaozy.card.common.Wechat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
@RequestMapping("/wechat")
public class WechatController {

    private String url1= "";

    @GetMapping("/auth")
    //   @ResponseBody
    public String auth(@RequestParam("code") String code, RedirectAttributes redirectAttributes) {
        log.info("code={}", code);
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wxdfe8e9a69851a406&secret=16007b600d84658cbbe894488efb9940&code=" + code + "&grant_type=authorization_code";
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);
        log.info("response={}", response);
        //将json转成java对象
        Gson gson = new Gson();
        Wechat result = gson.fromJson(response, Wechat.class);
        log.info("result = {}", result);
        log.info("access_token={}", result.getAccess_token());
        //获取userinfo
        url1 = "https://api.weixin.qq.com/sns/userinfo?access_token="
                + result.getAccess_token() + "&openid=" + result.getOpenid() + "&lang=zh_CN";

        if (result.getAccess_token() != null) {

            RestTemplate restTemplate2 = new RestTemplate();
            String response1 = restTemplate2.getForObject(url1, String.class);
            log.info("response1={}", response1);
//            return response1;
            return "redirect:http://xiaozy.natapp1.cc/test/test2";
        } else {
            return "error";
        }

    }

    @GetMapping("/test2")
    public String test2(){
        return "/index";
    }


}
