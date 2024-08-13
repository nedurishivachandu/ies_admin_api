package in.ashokit.service;

import in.ashokit.binding.UnlockAccForm;
import in.ashokit.binding.UserAccForm;
import in.ashokit.constants.AppConstants;
import in.ashokit.entity.UserEntity;
import in.ashokit.repository.UserRepo;
import in.ashokit.utils.EmailUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private EmailUtils emailUtils;
    @Override
    public boolean createUserAccount(UserAccForm accForm) {
        UserEntity entity=new UserEntity();
        BeanUtils.copyProperties(accForm, entity);
        // set random pwd
        entity.setPwd(generatePwd());
        //set acc status
        entity.setAccStatus("LOCKED");
        entity.setActiveSw("Y");
        userRepo.save(entity);
        // send email
        String subject="User Registration";
        String body = readEmailBody("REG_EMAIL_BODY.txt", entity);
        return emailUtils.sendEmail(subject, body, entity.getEmail());
    }

    private String readEmailBody(String fileName, UserEntity user) {
    StringBuilder sb=new StringBuilder();
    try (Stream<String> lines = Files.lines(Paths.get(fileName))) {
        lines.forEach( line -> {
            line = line.replace(AppConstants.FNAME, user.getFullName());
            line = line.replace(AppConstants.PWD, user.getPwd());
            line = line.replace(AppConstants.EMAIL, user.getEmail());
            sb.append(lines);
    });
    }catch (Exception e){
        e.printStackTrace();
    }
    return sb.toString();
    }

    @Override
    public List<UserAccForm> fetchUserAccounts() {
        List<UserEntity> userEntities = userRepo.findAll();
        List<UserAccForm> users= new ArrayList<UserAccForm>();
        for(UserEntity userEntity: userEntities){
            UserAccForm user = new UserAccForm();
            BeanUtils.copyProperties(userEntities,user);
            users.add(user);
        }
        return users;
    }

    @Override
    public UserAccForm getUserAccById(Long accId) {
        Optional<UserEntity> optional = userRepo.findById(accId);
        if(optional.isPresent()){
            UserEntity userEntity = optional.get();
            UserAccForm user=new UserAccForm();
            BeanUtils.copyProperties(userEntity,user);
            return user;
        }
        return null;
    }

    @Override
    public String changeAccStatus(Integer userId, String status) {
        Integer count = userRepo.updateAccStatus(userId, status);
        if(count>0){
            return "Status changed";
        }
        return "Failed To Change";
    }

    @Override
    public String unlockUserAccount(UnlockAccForm unlockAccForm) {
        UserEntity entity = userRepo.findByEmail(unlockAccForm.getEmail());
        entity.setPwd(unlockAccForm.getNewPwd());
        entity.setAccStatus("UNLOCKED");
        userRepo.save(entity);
        return "Account Unlocked";
    }

    private String generatePwd(){
            String upperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            String lowerAlphabet = "abcdefghijklmnopqrstuvwxyz";
            String numbers = "0123456789";

            // combine all strings
            String alphaNumeric = upperAlphabet + lowerAlphabet + numbers;

            // create random string builder
            StringBuilder sb = new StringBuilder();

            // create an object of Random class
            Random random = new Random();

            // specify length of random string
            int length = 6;

            for (int i = 0; i < length; i++) {

                // generate random index number
                int index = random.nextInt(alphaNumeric.length());

                // get character specified by index
                // from the string
                char randomChar = alphaNumeric.charAt(index);

                // append the character to string builder
                sb.append(randomChar);
            }

            return sb.toString();
        }

}
