package in.ashokit.service;

import in.ashokit.binding.DashboardCard;
import in.ashokit.binding.LoginForm;
import in.ashokit.binding.UnlockAccForm;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Override
    public String login(LoginForm loginForm) {
        return null;
    }

    @Override
    public boolean recoverPsd(String email) {
        return false;
    }

    @Override
    public DashboardCard fetchDashboardInfo() {
        return null;
    }

    @Override
    public String unlockUserAccount(UnlockAccForm unlockAccForm) {
        return null;
    }
}
