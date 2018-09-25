package com.xiaozy.card.controller;




import com.xiaozy.card.VO.ResultVO;
import com.xiaozy.card.common.Params;
import com.xiaozy.card.constant.CookieConstant;
import com.xiaozy.card.dataobject.UserInfo;
import com.xiaozy.card.enums.ResultEnum;
import com.xiaozy.card.exception.UserException;
import com.xiaozy.card.service.UserService;
import com.xiaozy.card.util.CookieUtil;
import com.xiaozy.card.util.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;


@RestController
@Slf4j
@RequestMapping("/user/*")
public class UserController {

    @Autowired
    private UserService userService;


/*    @Autowired
    private StringRedisTemplate redisTemplate;*/

    private UserInfo userInfo = new UserInfo();

    /**
     * 登陆的接口
     * @param userName
     * @param password
     * @param response
     * @return
     */
    @PostMapping("/login")
    public ResultVO login(@RequestParam("userName") String userName,
                          @RequestParam("password") String password,
                          HttpServletResponse response){
        //1.用户名先去和数据库里的匹配
        userInfo = userService.findByUserName(userName);
        if(userInfo ==null || !password .equals( userInfo.getPassword())){
              return  ResultVOUtil.error(ResultEnum.USERNAME_OR_PASSWORD_ERROR.getCode(),ResultEnum.USERNAME_OR_PASSWORD_ERROR.getMsg());
        }
/*
            //设置token至redis
            String token  = UUID.randomUUID().toString();
            Integer expire = RedisConstant.EXPIRE;
            redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX,token),userName,expire, TimeUnit.SECONDS);
*/

            //设置token至cookie
            String token  = UUID.randomUUID().toString();
            CookieUtil.set(response, CookieConstant.TOKEN,token,7200);
            Map<String,Object> map = new HashMap<>();
            map.put("token",token);
            map.put("userName",userName);
            return ResultVOUtil.success(map);
    }

    /**
     * 添加用户的接口
     * @param userName
     * @param userPhone
     * @param userEmail
     * @param sexy
     * @param city
     * @param userStatus
     * @return
     * @throws Exception
     */
    @PostMapping("/add")
    public ResultVO add(@RequestParam String userName,String userPhone,String userEmail,
                        Integer sexy,String city,Integer userStatus) throws Exception{

         //查询数据库
         userInfo = userService.findByUserName(userName);
         //判断用户是否已存在
         if (userInfo != null){
                 return ResultVOUtil.error(ResultEnum.ADD_FAIL.getCode(),ResultEnum.ADD_FAIL.getMsg());
            }
         UserInfo userInfo1 = new UserInfo();
            userInfo1.setUserName(userName);
            userInfo1.setPassword("123456");
            userInfo1.setUserPhone(userPhone);
            userInfo1.setUserEmail(userEmail);
            userInfo1.setSexy(sexy);
            userInfo1.setCity(city);
            userInfo1.setUserStatus(userStatus);
            userInfo1.setUserType(0);
            userService.save(userInfo1);
       return ResultVOUtil.success(userInfo1);

    }



    //@GetMapping("/check")
    @GetMapping("/check")
    public ResultVO check(@RequestParam("pageIndex") Integer pageIndex,
                          @RequestParam("pageSize") Integer pageSize,
                          Params params){
        Page<UserInfo> userInfoPage = userService.findAll(pageIndex,pageSize,params);
        return ResultVOUtil.success(userInfoPage);
    }


    /**
     * 编辑用户的接口
     * @param userName
     * @param userPhone
     * @param userEmail
     * @param sexy
     * @param city
     * @param userStatus
     * @return
     */
    @PostMapping("/edit")
    public ResultVO edit(@RequestParam String userName,String userPhone,String userEmail,Integer sexy,String city,Integer userStatus){
        //通过用户名在数据库中查询
        userInfo = userService.findByUserName(userName);
        //编辑用户资料
        userInfo.setUserName(userName);
        userInfo.setUserPhone(userPhone);
        userInfo.setUserEmail(userEmail);
        userInfo.setSexy(sexy);
        userInfo.setCity(city);
        userInfo.setUserStatus(userStatus);
        //保存所作的修改
        userService.save(userInfo);
        return ResultVOUtil.success(userInfo);
    }

    /**
     * 启用或禁用用户的接口
     * @param userName
     * @return
     */
    @PostMapping("/active")
    public ResultVO active(@RequestParam String userName){
        //去数据库查看用户状态
        userInfo = userService.findByUserName(userName);
        int result =userInfo.getUserStatus();
        if (result == 0){
            userInfo.setUserStatus(1);
        }else {
            userInfo.setUserStatus(0);
        }
        userService.save(userInfo);
        return  ResultVOUtil.success(userInfo);

    }

    /**
     * 退出接口
     * @param request
     * @param response
     * @param map
     * @return
     */
    @GetMapping("/exit")
    public ResultVO logout(HttpServletRequest request,
                           HttpServletResponse response,
                           Map<String,Object> map){
        //1.从cookie里查询
        Cookie cookie = CookieUtil.get(request,CookieConstant.TOKEN);
        if (cookie != null){
            /*//2.清楚redis
            redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));*/
            //3.清楚cookie,将cookie过期时间设置为0就清除了
            CookieUtil.set(response,CookieConstant.TOKEN,null,0);
        }
        map.put(ResultEnum.LOGOUT_SUCCESS.getMsg(),ResultEnum.LOGOUT_SUCCESS.getCode());
        return ResultVOUtil.success(map);
    }
    //跨域处理
    @Configuration
    public class WebConfig extends WebMvcConfigurationSupport {

        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
                    .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
        }
    }
    /**
     * 测试接口
     * @return
     */
    @GetMapping("/test")
    public String test() {
        String str = "这是一个测试接口";
        return str;
    }
}



