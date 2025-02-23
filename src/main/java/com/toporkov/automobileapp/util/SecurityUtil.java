package com.toporkov.automobileapp.util;

import com.toporkov.automobileapp.model.Manager;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtil {

    public static Manager getCurrentManager() {
        return (Manager) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
