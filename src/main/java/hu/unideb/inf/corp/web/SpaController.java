package hu.unideb.inf.corp.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// Single Page Application
@Controller
public class SpaController {
    @GetMapping({"/assets", "/assets/**", "/employees", "/employees/**"})
    public String forwardToIndex() {
        return "forward:/index.html";
    }
}
