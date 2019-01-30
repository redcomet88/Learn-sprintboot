package com.chensi.yghy.controller;

import com.chensi.yghy.model.Collect;
import com.chensi.yghy.model.Yghy;
import com.chensi.yghy.model.vo.YghyVO;
import com.chensi.yghy.service.QRCodeService;
import com.chensi.yghy.service.YghyService;
import com.chensi.yghy.util.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

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

    @Value("${appUrl}")
    private String appUrl;

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

    /**
     * @description:修改表单名
     *
     * @author: redcomet
     * @param: [formname]
     * @return: java.lang.String        
     * @create: 2019/1/28 
     **/
    @RequestMapping(value = "/formname")
    @ResponseBody
    public String formname(@RequestParam(value = "formname") String formname,HttpServletRequest request){

        HttpSession session = request.getSession();
        String userID = (String) session.getAttribute("openid");
        yghyService.save(userID,formname);

        HashMap<String, Object> hashmap = new HashMap<String, Object>();
        hashmap.put("result", 1);
        hashmap.put("msg", "成功");
        String json = JSON.Encode(hashmap);
        return json;
    }


    /**
     * @description:帮赞
     *
     * @author: redcomet
     * @param: []
     * @return: java.lang.String        
     * @create: 2019/1/25 
     **/
    @RequestMapping(value = "/thumb-up")
    @ResponseBody
    public String thumbup(@RequestParam(value = "index")int index,@RequestParam(value = "message") String message,
                        @RequestParam(value = "id") String userID,HttpSession session){
        HashMap<String, Object> hashmap = new HashMap<String, Object>();
        if("".equals(message)) {
            hashmap.put("result", 0);
            hashmap.put("msg", "请填写祝福语");
            String json = JSON.Encode(hashmap);
            return json;
        }
        String openid = (String) session.getAttribute("openid");
        String nickname = (String) session.getAttribute("nickname");
        if(openid.equals(userID)){
            hashmap.put("result", 0);
            hashmap.put("msg", "自己不能给自己点赞哦");
            String json = JSON.Encode(hashmap);
            return json;
        }
        String result = yghyService.wish(openid,index,message,userID,nickname);
        if("THUMB_AGAIN".equals(result)){
            hashmap.put("result", 0);
            hashmap.put("msg", "不能重复点赞哦");
            String json = JSON.Encode(hashmap);
            return json;
        }
        else {
            hashmap.put("result", 1);
            //YghyVO yVO = yghyService.isNew(userID);
            hashmap.put("msg", "点赞成功");
            String json = JSON.Encode(hashmap);
            return json;
        }
    }


    @RequestMapping(value = "/getQRCode")
    @ResponseBody
    public String getQRCode(HttpServletRequest request) throws IOException {
        HttpSession session = request.getSession();
        String userID = (String) session.getAttribute("openid");
        String url = appUrl + "webauth?state=" + userID;
        //    HashMap<String, Object> hashmap = new HashMap<String, Object>();
        //    hashmap.put("url", y);
        //    String json = JSON.Encode(hashmap);
        return qrcodeServce.createQRCode(url,200,200);

    }

    @RequestMapping(value = "/drawPrize")
    @ResponseBody
    public String drawPrize(HttpServletRequest request) throws IOException {
        HttpSession session = request.getSession();
        String userID = (String) session.getAttribute("openid");

        String result = yghyService.preDraw(userID);
        HashMap<String, Object> hashmap = new HashMap<String, Object>();

        if("NOT_FINISHED".equals(result)){
            hashmap.put("result", 0);
            hashmap.put("msg", "请集赞完成后再抽奖哦");
            String json = JSON.Encode(hashmap);
            return json;
        }else if("ALREADY_DRAW".equals(result)){
            hashmap.put("result", 0);
            hashmap.put("msg", "抽过奖了哟");
            String json = JSON.Encode(hashmap);
            return json;
        }

        synchronized (String.valueOf(userID).intern()) {
            int prizeType = yghyService.drawPrize(userID);
            hashmap.put("prizeType",prizeType);
            hashmap.put("result", 1);
            hashmap.put("msg", "抽奖成功");
        }

        String json = JSON.Encode(hashmap);
        return json;
    }


    /**
     * @description:更新联系方法
     *
     * @author: redcomet
     * @param: [address, phone, email, contacts, request]
     * @return: java.lang.String        
     * @create: 2019/1/29 
     **/
    @RequestMapping(value = "/updateContact")
    @ResponseBody
    public String updateContact(@RequestParam(value = "address") String address,@RequestParam(value = "phone") String phone,
                           @RequestParam(value = "email") String email,@RequestParam(value = "contacts") String contacts,
                           HttpServletRequest request){

        HttpSession session = request.getSession();
        String userID = (String) session.getAttribute("openid");
        yghyService.save(userID,address,phone,email,contacts);

        HashMap<String, Object> hashmap = new HashMap<String, Object>();
        hashmap.put("result", 1);
        hashmap.put("msg", "成功");
        String json = JSON.Encode(hashmap);
        return json;
    }

    /**
     * @description:查看其他人帮点赞的信息
     *
     * @author: redcomet
     * @param: [id, request]
     * @return: java.lang.String        
     * @create: 2019/1/29 
     **/
    @RequestMapping(value = "/getOtherInfo")
    @ResponseBody
    public String getOtherInfo(@RequestParam(value = "id") String id, HttpServletRequest request){

        List<Collect> list = yghyService.getCollects(id);

        HashMap<String, Object> hashmap = new HashMap<String, Object>();
        hashmap.put("result", 1);
        YghyVO yVO = yghyService.isNew(id);
        hashmap.put("msg", yVO.getFormName());
        hashmap.put("collects", list);
        String json = JSON.Encode(hashmap);
        return json;
    }
}
