package re_lease.controller_layer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class TestController {

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ModelAndView test(HttpSession session, HttpServletRequest request) {
        final ModelAndView testView = new ModelAndView("test");
        testView.addObject("path", request.getContextPath());
        return testView;
    }

}
