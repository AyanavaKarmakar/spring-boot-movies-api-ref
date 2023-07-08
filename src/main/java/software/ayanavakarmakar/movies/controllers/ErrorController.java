package software.ayanavakarmakar.movies.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
    @RequestMapping("/error")
    @ResponseBody
    public String getErrorPath() {
        return "<center><h1>Route Not Found</h1></center>";
    }
}
