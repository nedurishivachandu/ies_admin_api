package in.ashokit.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "IES_PLANS")
public class PlanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer planId;
    private String planCategory;
    private String planName;
    private LocalDate planStartDate;
    private LocalDate planEndDate;
    private  String activeSw;

    @ManyToOne(cascade =  CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public void setPlanCategory(String planCategory) {
        this.planCategory = planCategory;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public void setPlanStartDate(LocalDate planStartDate) {
        this.planStartDate = planStartDate;
    }

    public void setPlanEndDate(LocalDate planEndDate) {
        this.planEndDate = planEndDate;
    }

    public void setActiveSw(String activeSw) {
        this.activeSw = activeSw;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Integer getPlanId() {
        return planId;
    }

    public String getPlanCategory() {
        return planCategory;
    }

    public String getPlanName() {
        return planName;
    }

    public LocalDate getPlanStartDate() {
        return planStartDate;
    }

    public LocalDate getPlanEndDate() {
        return planEndDate;
    }

    public String getActiveSw() {
        return activeSw;
    }

    public UserEntity getUser() {
        return user;
    }
}
