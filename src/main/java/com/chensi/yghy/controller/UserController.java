package com.chensi.yghy.controller;

import com.chensi.yghy.model.Yghy;
import com.chensi.yghy.model.vo.YghyVO;
import com.chensi.yghy.service.UserService;
import com.chensi.yghy.service.YghyService;
import com.chensi.yghy.util.AuthUtil;
import com.chensi.yghy.util.JSON;
import com.chensi.yghy.util.Sign;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: User Controller
 * @Author: redcomet
 * @Date: 2019-01-22-13:52
 */
@Controller
@RequestMapping(value = "/")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private YghyService yghyService;

    @Value("${appUrl}")
    private String appUrl;
    @Value("${wx.APPID}")
    public  String APPID;
    @Value("${wx.APPSECRET}")
    public  String APPSECRET;


    @RequestMapping(value = "/index")
    public String index(){
        return "index";
    }

    @RequestMapping(value = "/webauth")
    public void webauth(HttpServletRequest request, HttpServletResponse response){
        String state = request.getParameter("state");
        if(null == state)
            state = "";
        try {
            String url="https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ APPID+
                    "&redirect_uri="+ URLEncoder.encode(appUrl + "callback","UTF-8")+
                    "&response_type=code"+
                    "&scope=snsapi_userinfo"+
                    "&state=" + state + "#wechat_redirect"; //state是页面间相互传参用的.
            response.sendRedirect(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/callback")
    public void callback(HttpServletRequest request, HttpServletResponse response ){
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        String url="https://api.weixin.qq.com/sns/oauth2/access_token?appid="+APPID+
                "&secret="+APPSECRET+
                "&code="+code+
                "&grant_type=authorization_code";
        JSONObject jsonObject= null;
        try {
            jsonObject = AuthUtil.doGetJson(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String openid=jsonObject.getString("openid");
        String token=jsonObject.getString("access_token");
        String inforUrl="https://api.weixin.qq.com/sns/userinfo?"+
                "access_token="+token+
                "&openid="+openid+
                "&lang=zh_CN";
        JSONObject userInfo= null;
        try {
            userInfo = AuthUtil.doGetJson(inforUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //使用微信用户信息直接登录
        request.setAttribute("userInfo", userInfo);
        HttpSession session = request.getSession();
        //存openid？
        session.setAttribute("openid",(String)userInfo.get("openid"));
        session.setAttribute("nickname",(String)userInfo.get("nickname"));

        Yghy y = yghyService.isNew((String)userInfo.get("openid"));
        if(null == y) {
            YghyVO yVO = yghyService.save(new Yghy((String) userInfo.get("openid"), (String) userInfo.get("nickname")));
        }

        try {
            response.sendRedirect("index?state=" + state  );
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*
        try {
            request.getRequestDispatcher("/index").forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        /**
         *userInfo中的信息.
         final String strOpenid =(String)userInfo.get("openid");

         //如果有unionid,可以在直接获取..
         final String strUnionid =(String)userInfo.get("unionid");

         System.out.println("openid ++>>>>>"+userInfo.get("openid"));

         final String nickname = (String)userInfo.get("nickname");
         System.out.println("nickname++>>>"+userInfo.get("nickname"));

         final int sex = (int)userInfo.get("sex");
         System.out.println("sex+>>>"+userInfo.get("sex"));

         System.out.println(userInfo.get("city")); //城市
         System.out.println(userInfo.get("province"));  //省份
         System.out.println(userInfo.get("headimgurl")); //头像
         */
    }


    /**
     * @description:2.1 获取令牌
     *
     * @author: redcomet
     * @param: []
     * @return: java.lang.String        
     * @create: 2019/1/29 
     **/
    @RequestMapping(value = "/wxGetToken")
    @ResponseBody
    public String  wxGetToken(){
        String access_token;
        String json = "";

        try {
            access_token = AuthUtil.getAccessToken(APPID,APPSECRET);
            HashMap<String, Object> hashmap = new HashMap<String, Object>();
            hashmap.put("accessToken", access_token);
            json = JSON.Encode(hashmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    @RequestMapping(value = "/wxGetJsapiTicket")
    @ResponseBody
    public String  wxGetJsapiTicket(@RequestParam(value = "accessToken") String accessToken){
        String ticket;
        String json = "";

        try {
            ticket = AuthUtil.getJSApiTicket(APPID,APPSECRET,accessToken);
            HashMap<String, Object> hashmap = new HashMap<String, Object>();
            hashmap.put("ticket", ticket);
            json = JSON.Encode(hashmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    @RequestMapping(value = "/wxGetSignature")
    @ResponseBody
    public String  wxGetSignature(@RequestParam(value = "url") String url,
                                  @RequestParam(value = "nonceStr")String nonce_str,@RequestParam(value = "timestamp")String timestamp){
        String jsapi_ticket = null;
        String json = "";

        try {
            jsapi_ticket = AuthUtil.getJSApiTicket(APPID,APPSECRET);
            Map<String, String> hashmap = Sign.sign(jsapi_ticket, url,APPID, nonce_str, timestamp);
            json = JSON.Encode(hashmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

}
