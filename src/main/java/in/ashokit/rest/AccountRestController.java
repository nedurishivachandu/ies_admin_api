package in.ashokit.rest;

import in.ashokit.binding.UserAccForm;
import in.ashokit.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountRestController {

    private Logger logger = LoggerFactory.getLogger(AccountRestController.class);
    @Autowired
    private AccountService accService;

    @PostMapping("/user")
    public ResponseEntity<String> createAccount(@RequestBody UserAccForm userAccForm){
        logger.debug("Account Creation Process Started...");

        boolean status = accService.createUserAccount(userAccForm);
        logger.debug("Account Creation Process Completed...");
        if(status){
            logger.info("Account Created Successfully...");
            return new ResponseEntity<>("Account Created", HttpStatus.CREATED);//201
        }else {
            logger.info("Account Creation Failed...");
            return new ResponseEntity<>("Account Creation Failed", HttpStatus.INTERNAL_SERVER_ERROR);//500
        }
    }
    @GetMapping("/users")
    public ResponseEntity<List<UserAccForm>> getUsers(){
        logger.debug("Fetching User Account Process Started...");
        List<UserAccForm> userAccForms = accService.fetchUserAccounts();
        logger.debug("Fetching User Account Process completed...");
        logger.info("User Account Fetched Successfully...");
        return new ResponseEntity<>(userAccForms, HttpStatus.OK);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<UserAccForm> getUser(@PathVariable("userId") Long userId){
        UserAccForm userAccById = accService.getUserAccById(userId);
        logger.info("User Account Fetched Successfully...");
        return new ResponseEntity<>(userAccById,HttpStatus.OK);
    }
    @PutMapping("/user/{userId}/{status}")
    public ResponseEntity<List<UserAccForm>>  updateUserAcc(@PathVariable("userId") Integer userId,@PathVariable("status") String status){
        logger.debug("User Account Status Update Process Started...");
        accService.changeAccStatus(userId,status);
        logger.debug("User Account Status Update Process Completed...");
        logger.info("User Account Status Updated Successfully...");
        List<UserAccForm> userAccForms = accService.fetchUserAccounts();
        return new ResponseEntity<>(userAccForms, HttpStatus.OK);
    }

}
