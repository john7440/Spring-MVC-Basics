package com.example.utils;

import com.example.entities.AppUser;
import jakarta.servlet.http.HttpSession;

public class SessionUtils {

    /**
     * Checks whether the currently authenticated user doesnot
     * have the "ADMIN" role
     *
     * @param session the current HTTP session
     * @return True if the user is absent or not an admin,
     *         false otherwise
     */
   public static boolean isNotAdmin(HttpSession session) {
        AppUser user = (AppUser) session.getAttribute("currentUser");
        return user == null || !user.getRole().equals("ADMIN");
    }
}
