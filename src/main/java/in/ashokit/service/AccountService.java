package in.ashokit.service;

import in.ashokit.binding.UnlockAccForm;
import in.ashokit.binding.UserAccForm;

import java.util.List;

public interface AccountService {

    public boolean createUserAccount(UserAccForm accForm);

    public List<UserAccForm> fetchUserAccounts();

    public UserAccForm getUserAccById(Long accId);

    public String changeAccStatus(Integer accId, String status);

    public String unlockUserAccount(UnlockAccForm unlockAccForm);

}
