package in.ashokit.service;

import in.ashokit.binding.DashboardCard;
import in.ashokit.binding.LoginForm;
import in.ashokit.binding.UnlockAccForm;
import in.ashokit.binding.UserAccForm;
import in.ashokit.entity.EligEntity;
import in.ashokit.entity.UserEntity;
import in.ashokit.repository.EligRepo;
import in.ashokit.repository.PlanRepo;
import in.ashokit.repository.UserRepo;
import in.ashokit.utils.EmailUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.DoubleStream;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private EmailUtils emailUtils;
    @Autowired
    private PlanRepo planRepo;
    @Autowired
    private EligRepo eligRepo;
    @Override
    public String login(LoginForm loginForm) {
        UserEntity entity = userRepo.findByEmailAndPwd(loginForm.getEmail(), loginForm.getPwd());
        if(entity == null){
            return "Invalid Credentials";
        }
        if("Y".equals(entity.getActiveSw()) && "UNLOCKED".equals(entity.getAccStatus())){
            return "Success";
        }else {
            return "Account Locked/In-Active";
        }
    }

    @Override
    public boolean recoverPsd(String email) {
        UserEntity userEntity = userRepo.findByEmail(email);
        if(null == userEntity){
            return false;
        }else{
            String subject ="";
            String body ="";
           return emailUtils.sendEmail(subject,body,email);
        }

    }

    @Override
    public DashboardCard fetchDashboardInfo() {
        long planCount = planRepo.count();

        List<EligEntity> eligList = eligRepo.findAll();
        long approvedCnt = eligList.stream().filter(ed -> ed.getPlanStatus().equals("AP")).count();

        long deniedCnt = eligList.stream().filter(ed -> ed.getPlanStatus().equals("DN")).count();

        double total = eligList.stream().mapToDouble(ed -> ed.getBenefitAmt()).sum();

        DashboardCard card=new DashboardCard();
        card.setPlansCnt(planCount);
        card.setApprovedCnt(approvedCnt);
        card.setDeniedCnt(deniedCnt);
        card.setBeniftAmtGiven(total);

        return card;
    }

    @Override
    public UserAccForm getUserByEmail(String email) {
        UserEntity userEntity = userRepo.findByEmail(email);
        UserAccForm user=new UserAccForm();
        BeanUtils.copyProperties(userEntity,user);
        return user;
    }

}
