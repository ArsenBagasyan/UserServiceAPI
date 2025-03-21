package helpers;

import services.UserService;

public class UserHelper {
    public static void deleteUser(String userId) {
        UserService.deleteUser(userId);
    }
}