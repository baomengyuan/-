package com.swjtu.Controller;
import com.swjtu.Service.EmailService;
import com.swjtu.Service.InitService;
import org.apache.catalina.connector.Request;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @author baomengyuan
 * @create 2021-12-14 16:56
 */

@EnableAutoConfiguration//开启自动注解
@Controller
public class HelloController {
    private InitService initService=new InitService();
    @RequestMapping(value = "/NetWork",params = {"address","message","title"})
    public String hello(String address, String message, String title,Model model){
        initService.startMethod(message,address,title);
        model.addAttribute("firstParam",message);
        return "result";
    }

    @RequestMapping(value = "/")
    public String index(){
        return "index";
    }
}
