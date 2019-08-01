package com.tsystems.ecm.service;

import com.tsystems.ecm.dto.UserSession;

public interface AuthorizationSessionService {

    UserSession createOrUpdateSession(String login);

    boolean isExpired(String sid);

    String getLoginBySessionId(String sid);

    void removeSession(String sid);

    String getAuthenticatedUser();

    void setAuthenticatedUser(String login);

}