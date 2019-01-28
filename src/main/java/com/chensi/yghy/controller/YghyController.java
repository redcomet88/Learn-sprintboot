package com.chensi.yghy.controller;

import com.chensi.yghy.model.Yghy;
import com.chensi.yghy.model.vo.YghyVO;
import com.chensi.yghy.service.QRCodeService;
import com.chensi.yghy.service.YghyService;
import com.chensi.yghy.util.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;

/**
 * @Description: 一罐好运控制器
 * @Author: redcomet
 * @Date: 2019-01-25-10:48
 */
@Controller
@RequestMapping(value = "/")
public class YghyController {
    @Autowired
    private YghyService yghyService;
    @Autowired
    private QRCodeService qrcodeServce;

    @RequestMapping(value = "/webauthXXXX")
    @ResponseBody
    public String webauth(@RequestParam(value = "userID") String userID, @RequestParam(value = "name") String name,
                        HttpServletRequest request){
        Yghy y = yghyService.isNew(userID);
        if(null == y) {
            return "未参加活动";
        }
        else{
            HttpSession session = request.getSession();
            //存openid？
            session.setAttribute("openid",y.getUserID());
            session.setAttribute("nickname",y.getNickName());
            return "OK";
        }
    }

    @RequestMapping(value = "/getUserInfo")
    @ResponseBody
    public String isNew(HttpServletRequest request){
        HttpSession session = request.getSession();
        //存openid？
        String userID = (String) session.getAttribute("openid");
        YghyVO y = yghyService.isNew(userID);
        if(null == y) {
            return "未参加活动";
        }
        else{
            HashMap<String, Object> hashmap = new HashMap<String, Object>();
            hashmap.put("id", y.getId());
            hashmap.put("nickname", y.getNickName());
            hashmap.put("formname", y.getFormName());
            hashmap.put("collects", y.getList());
            hashmap.put("isCollected", y.getIsCollected());
            hashmap.put("isDraw", y.getIsDraw());
            hashmap.put("prizeType", y.getPrizeType());
            hashmap.put("address", y.getAddress());
            hashmap.put("phone", y.getPhone());
            hashmap.put("email", y.getEmail());
            hashmap.put("contacts", y.getContacts());

            String json = JSON.Encode(hashmap);
            return json;
        }
    }

    /*
    @RequestMapping(value = "/create")
    @ResponseBody
    public String create(@RequestParam(value = "userID") String userID,@RequestParam(value = "name") String name,
                         HttpServletRequest request){
        YghyVO yVO = yghyService.save(new Yghy(userID,name));
        HttpSession session = request.getSession();
        session.setAttribute("openid",userID);

        HashMap<String, Object> hashmap = new HashMap<String, Object>();
        hashmap.put("data", yVO);
        String json = JSON.Encode(hashmap);
        return json;
    }
    */
    
    /**
     * @description:一罐好运，填写祝福语提交操作
     *
     * @author: redcomet
     * @param: []
     * @return: java.lang.String        
     * @create: 2019/1/25 
     **/
    @RequestMapping(value = "/thumb-up")
    @ResponseBody
    public String share(@RequestParam(value = "index")int index,@RequestParam(value = "message") String message,
                        @RequestParam(value = "id") String userID,HttpSession session){
        HashMap<String, Object> hashmap = new HashMap<String, Object>();
        if("".equals(message)) {
            hashmap.put("result", 0);
            hashmap.put("msg", "请填写祝福语");
        }
        String openid = (String) session.getAttribute("openid");
        String nickname = (String) session.getAttribute("nickname");
        String result = yghyService.wish(openid,index,message,userID,nickname);
        hashmap.put("result", 1);
        hashmap.put("msg", "成功");

        String json = JSON.Encode(hashmap);
        return json;
    }


    @RequestMapping(value = "/getQRCode")
    @ResponseBody
    public String getQRCode(HttpServletRequest request) throws IOException {
        HttpSession session = request.getSession();
        String userID = (String) session.getAttribute("openid");
        String url = "http://106.13.52.59/yghy/webauth?state=" + userID;
        //    HashMap<String, Object> hashmap = new HashMap<String, Object>();
        //    hashmap.put("url", y);
        //    String json = JSON.Encode(hashmap);
        return qrcodeServce.createQRCode(url,200,200);

    }
}
