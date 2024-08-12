package in.ashokit.repository;

import in.ashokit.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepo extends JpaRepository<UserEntity, Long> {

    @Query("update UserEntity set accStatus=:status where userID=:userId")
    public Integer updateAccStatus(Integer userId, String status);

    public UserEntity findByEmail(String email);

    public UserEntity findByEmailAndPwd(String email, String pwd);
}
