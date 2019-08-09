package com.tsystems.ecm.service;

import com.tsystems.ecm.dto.UserSessionDto;

public interface AuthorizationSessionService {

    UserSessionDto createOrUpdateSession(String login);

    boolean isExpired(String sid);

    String getLoginBySessionId(String sid);

    void removeSession(String sid);

}