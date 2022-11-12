package apap.proyek.rumahsehat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @GetMapping(value = "")
    private String manageUsers() {
        return "user/users-management";
    }

    @GetMapping(value = "/doctors")
    private String listDoctors() {

        return "user/viewall-doctors";
    }

}
