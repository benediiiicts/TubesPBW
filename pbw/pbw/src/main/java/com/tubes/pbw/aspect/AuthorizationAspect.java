package com.tubes.pbw.aspect;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tubes.pbw.RequiredRole;

import jakarta.servlet.http.HttpSession;

@Aspect
@Component
public class AuthorizationAspect {
    @Autowired
    private HttpSession session;

    @Before("@annotation(requiredRole)")
    public void checkAuthorization(JoinPoint joinPoint, RequiredRole requiredRole) throws Throwable {
        if (session == null || session.getAttribute("username") == null) {
            System.out.println("Authorization failed: Session is null or user not logged in.");
            throw new SecurityException("User is not logged in!");
        }

        String[] roles = requiredRole.value();
        String currentUserRole = getCurrentUserRole();
        Set<String> allowedRoles = new HashSet<>(Arrays.asList(roles));

        if (!allowedRoles.contains("*") && !allowedRoles.contains(currentUserRole)) {
            System.out.println(
                    "Authorization failed: Current role: " + currentUserRole + ", Allowed roles: " + allowedRoles);
            throw new SecurityException(
                    "User with role [" + currentUserRole + "] is not authorized to access this resource.");
        }
    }

    private String getCurrentUserRole() {
        Object role = session.getAttribute("role");
        if (role == null || !(role instanceof String)) {
            System.out.println("Invalid role detected: " + role);
            throw new SecurityException("Role is invalid");
        }
        return (String) role;
    }
}
