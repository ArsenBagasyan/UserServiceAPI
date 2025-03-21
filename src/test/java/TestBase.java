import helpers.UserHelper;
import org.testng.annotations.AfterTest;

import java.util.ArrayList;
import java.util.List;

public abstract class TestBase {
    protected ThreadLocal<ArrayList<String>> userIdThreadLocal = ThreadLocal.withInitial(ArrayList::new);

    protected void addUserId(String userId) {
        userIdThreadLocal.get().add(userId);
    }

    @AfterTest
    public void cleanup() {
        List<String> userIds = userIdThreadLocal.get();
        if (!userIds.isEmpty()) {
            for (String userId : userIds) {
                UserHelper.deleteUser(userId);
            }
            userIds.clear();
        }
        userIdThreadLocal.remove();
    }
}
