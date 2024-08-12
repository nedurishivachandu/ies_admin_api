package in.ashokit.service;

import in.ashokit.binding.PlanForm;
import in.ashokit.entity.PlanEntity;

import java.util.List;

public interface PlanService {

    public boolean createPlan(PlanForm planForm);

    public List<PlanForm> fetchPlans();

    public PlanForm getPlanById(Integer planId);

    public String changePlanStatus(Integer planId, String status);
}
