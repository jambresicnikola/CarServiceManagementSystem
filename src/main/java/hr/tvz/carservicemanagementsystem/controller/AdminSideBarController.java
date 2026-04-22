package hr.tvz.carservicemanagementsystem.controller;

import hr.tvz.carservicemanagementsystem.util.SceneManager;

public class AdminSideBarController {
    public void openDashboardScreen() {
        SceneManager.openHomeScreen();
    }

    public void openManageUsersScreen() {
        SceneManager.openManageUsersScreen();
    }

    public void openManageClientsScreen() {
    }

    public void openManageVehiclesScreen() {
    }

    public void openManagePartsScreen() {
    }

    public void openManageServiceOrdersScreen() {
    }

    public void signOut() {
        SceneManager.openWelcomeScreen();
    }
}
