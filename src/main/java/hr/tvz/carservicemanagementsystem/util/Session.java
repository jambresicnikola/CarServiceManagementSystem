package hr.tvz.carservicemanagementsystem.util;

import hr.tvz.carservicemanagementsystem.model.Role;
import hr.tvz.carservicemanagementsystem.model.User;

public class Session {
    private Session() {}

    private static User loggedUser;

    public static User getLoggedUser() {
        return loggedUser;
    }

    public static void setLoggedUser(User loggedUser) {
        Session.loggedUser = loggedUser;
    }

    public static void clear() {
        loggedUser = null;
    }

    public static boolean isAdmin() {
        return loggedUser != null && loggedUser.getRole() == Role.ADMIN;
    }
}
