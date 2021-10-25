package com.magnus.project.managee.work.service;

public interface TeamBusinessService {
    public void insertTeamBusiness(int teamId, int businessId, int teamRole);

    public void insertBusinessTeamAsManager(int teamId, int businessId);

    public void insertBusinessTeamAsSupport(int teamId, int businessId);

    public void deleteTeamBusiness(int teamId, int businessId);

    public void insertTeamEvaluateMission(int businessId, int teamId);

    public void declineSupportEvaluate(int businessId, int teamId, String reason);
}
