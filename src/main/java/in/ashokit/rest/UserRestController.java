package in.ashokit.rest;

import in.ashokit.binding.DashboardCard;
import in.ashokit.binding.LoginForm;
import in.ashokit.binding.UserAccForm;
import in.ashokit.service.AccountService;
import in.ashokit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserRestController {
    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accService;

    @PostMapping("/login")
    public String login(@RequestBody LoginForm loginForm){
        String status = userService.login(loginForm);
        if(status.equals("Success")){
            return "redirect:/dashboard?email="+loginForm.getEmail();
        }else{
            return status;
        }
    }

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardCard> buildDashboard(@RequestParam("email") String email){
        UserAccForm user = userService.getUserByEmail(email);
        DashboardCard dashboardCard = userService.fetchDashboardInfo();
        dashboardCard.setUser(user);
        return new ResponseEntity<>(dashboardCard, HttpStatus.OK);
    }

    
}
