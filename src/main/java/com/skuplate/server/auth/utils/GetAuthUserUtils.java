package com.skuplate.server.auth.utils;

import com.skuplate.server.exception.BusinessLogicException;
import com.skuplate.server.exception.ExceptionCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class GetAuthUserUtils {
    public static Authentication getAuthUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getName() == null || authentication.getName().equals("anonymousUser")){
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);
        }
        authentication.getPrincipal();
        return authentication;
    }
}
