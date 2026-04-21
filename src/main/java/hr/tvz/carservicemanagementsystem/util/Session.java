package hr.tvz.carservicemanagementsystem.util;

import hr.tvz.carservicemanagementsystem.model.Role;
import hr.tvz.carservicemanagementsystem.model.User;

/**
 * Holds the currently authenticated user for the duration of the application session.
 * Acts as a simple in-memory session store for the desktop application.
 */
public class Session {
    private Session() {}

    private static User loggedUser;

    /**
     * Returns the currently logged-in user.
     *
     * @return the logged-in {@link User}, or {@code null} if no user is authenticated
     */
    public static User getLoggedUser() {
        return loggedUser;
    }

    /**
     * Sets the currently logged-in user.
     *
     * @param loggedUser the authenticated {@link User}
     */
    public static void setLoggedUser(User loggedUser) {
        Session.loggedUser = loggedUser;
    }

    /**
     * Clears the current session, effectively logging out the user.
     */
    public static void clear() {
        loggedUser = null;
    }

    /**
     * Returns whether the currently logged-in user has the admin role.
     *
     * @return {@code true} if the logged-in user is an admin, {@code false} otherwise
     */
    public static boolean isAdmin() {
        return loggedUser != null && loggedUser.getRole() == Role.ADMIN;
    }
}
