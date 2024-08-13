package in.ashokit.service;

import in.ashokit.binding.DashboardCard;
import in.ashokit.binding.LoginForm;
import in.ashokit.binding.UnlockAccForm;
import in.ashokit.binding.UserAccForm;

public interface UserService {
    //if we need to return only 2 conditions then take return types as boolean,
    //if we need to return more than 3 conditions then take return type as String
    public String login(LoginForm loginForm);

    public boolean recoverPsd(String email);

    public DashboardCard fetchDashboardInfo();

    public UserAccForm getUserByEmail(String email);


}
