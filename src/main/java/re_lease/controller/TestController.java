package re_lease.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Map;

@Controller
public class TestController {

    @GetMapping("/")
    public String welcome(Map<String, Object> model) {
        model.put("time", new Date());
        model.put("message", "Hello World");
        return "welcome";
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ModelAndView test(HttpSession session, HttpServletRequest request) {
        final ModelAndView testView = new ModelAndView("test");
        testView.addObject("path", request.getContextPath());
        return testView;
    }

}
