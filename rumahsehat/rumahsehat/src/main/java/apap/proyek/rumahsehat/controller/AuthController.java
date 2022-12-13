package apap.proyek.rumahsehat.controller;

import apap.proyek.rumahsehat.model.Admin;
import apap.proyek.rumahsehat.model.UserModel;
import apap.proyek.rumahsehat.security.xml.Attributes;
import apap.proyek.rumahsehat.security.xml.ServiceResponse;
import apap.proyek.rumahsehat.service.AdminService;
import apap.proyek.rumahsehat.service.DokterService;
import apap.proyek.rumahsehat.service.UserService;
import apap.proyek.rumahsehat.setting.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
public class AuthController {

    @Qualifier("userServiceImpl")
    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    private WebClient webClient = WebClient.builder().build();

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @GetMapping("/validate-ticket")
    public ModelAndView adminLoginSSO(@RequestParam(value = "ticket", required = false) String ticket,
                                      HttpServletRequest request) {
        ServiceResponse serviceResponse = this.webClient.get().uri(
                String.format(
                        Setting.SERVER_VALIDATE_TICKET,
                        ticket,
                        Setting.CLIENT_LOGIN
                )
        ).retrieve().bodyToMono(ServiceResponse.class).block();

        if (serviceResponse != null) {
            Attributes attributes = serviceResponse.getAuthenticationSuccess().getAttributes();
            String username = serviceResponse.getAuthenticationSuccess().getUser();

            UserModel user = userService.getUserByUsername(username);


            if (user==null) {
                user = new UserModel();
                user.setEmail(username+"@ui.ac.id");
                user.setNama(attributes.getNama());
                user.setPassword("rumahsehat");
                user.setUsername(username);
                user.setRole("Admin");

                UserModel savedUser = userService.addUser(user);
                adminService.addAdmin(new Admin(), savedUser);


            }

            Authentication authentication = new UsernamePasswordAuthenticationToken(username,"rumahsehat");
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authentication);

            HttpSession httpSession = request.getSession(true);
            httpSession.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);
        }

        return new ModelAndView("redirect:/");
    }

    @GetMapping(value = "/login-sso")
    public ModelAndView loginSSO() {
        return new ModelAndView("redirect:"+Setting.SERVER_LOGIN+Setting.CLIENT_LOGIN);
    }

    @GetMapping(value = "/logout-sso")
    public ModelAndView logoutSSO(Principal principal) {
        UserModel user = userService.getUserByUsername(principal.getName());
        if (!userService.isAdmin(principal.getName())) {
            return new ModelAndView("redirect:/logout");
        }
        return new ModelAndView("redirect:"+Setting.SERVER_LOGOUT+Setting.CLIENT_LOGOUT);

    }

    @GetMapping(value = "/pasien/forbidden")
    private String pasienForbidden() {
        return "user/pasien-forbidden";
    }
}