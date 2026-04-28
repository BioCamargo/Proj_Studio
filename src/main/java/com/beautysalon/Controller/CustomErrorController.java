package com.beautysalon.Controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        // Get error status
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            model.addAttribute("errorCode", statusCode);

            if(statusCode == 404) {
                model.addAttribute("errorMessage", "Page not found");
            }
            else if(statusCode == 403) {
                model.addAttribute("errorMessage", "Access denied");
            }
            else {
                model.addAttribute("errorMessage", "Something went wrong");
            }
        }
        return "error/customError";
    }
}
