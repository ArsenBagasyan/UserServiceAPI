import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import services.UserService;
import utils.TestDataUtil;

import java.util.ArrayList;
import java.util.List;

import static enums.ResponseStatus.NOT_FOUND;
import static org.hamcrest.Matchers.*;
import static enums.ResponseStatus.SUCCESS;

public class UserPositiveTest extends TestBase {
    //TODO move user creation and tracking to TestBase
    //TODO

    @Test
    public void createUserTest() {
        String userId = null;
        String name = TestDataUtil.generateRandomName();
        String email = TestDataUtil.generateRandomEmail();

        try {
            Response response = UserService.createUser(name, email);
            userId = response.then().extract().jsonPath().getString("id");

            response
                    .then()
                    .statusCode(SUCCESS.getStatusCode())
                    .body("id", notNullValue())
                    .body("name", equalTo(name))
                    .body("email", equalTo(email));

            Response getUserResponse = UserService.getUserById(userId);

            getUserResponse
                    .then()
                    .statusCode(SUCCESS.getStatusCode())
                    .body("id", equalTo(userId))
                    .body("name", equalTo(name))
                    .body("email", equalTo(email));

        } finally {
            if (userId != null) {
                addUserId(userId);
            }
        }
    }

    @Test
    public void searchUsersTest() {
        int usersToCreate = 5;
        List<String> createdUserIds = new ArrayList<>();

        try {
            for (int i = 0; i < usersToCreate; i++) {
                String name = TestDataUtil.generateRandomName();
                String email = TestDataUtil.generateRandomEmail();

                Response response = UserService.createUser(name, email);
                String userId = response.then().extract().jsonPath().getString("id");

                createdUserIds.add(userId);
            }

            Response searchResponse = UserService.getUsers();
            searchResponse
                    .then()
                    .statusCode(SUCCESS.getStatusCode())
                    .body("size()", greaterThanOrEqualTo(usersToCreate),
                            "id", hasItems(createdUserIds.toArray(new String[0])))
                    .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/searchUserSchema.json"));


        } finally {
            createdUserIds.forEach(this::addUserId);
        }
    }

    @Test
    public void getUserByIdTest() {
        String expectedName = TestDataUtil.generateRandomName();
        String expectedEmail = TestDataUtil.generateRandomEmail();

        String userId = null;
        try {
            Response response = UserService.createUser(expectedName, expectedEmail);
            userId = response.then().extract().jsonPath().getString("id");

            Response getUserResponse = UserService.getUserById(userId);

            getUserResponse
                    .then()
                    .statusCode(SUCCESS.getStatusCode())
                    .body("id", equalTo(userId))
                    .body("name", equalTo(expectedName))
                    .body("email", equalTo(expectedEmail));

        } finally {
            if (userId != null) {
                addUserId(userId);
            }
        }
    }

    @Test
    public void updateUserTest() {
        String initialName = TestDataUtil.generateRandomName();
        String initialEmail = TestDataUtil.generateRandomEmail();

        String userId = null;
        try {
            Response createResponse = UserService.createUser(initialName, initialEmail);
            userId = createResponse.then().extract().jsonPath().getString("id");

            String updatedName = TestDataUtil.generateRandomName();
            String updatedEmail = TestDataUtil.generateRandomEmail();

            Response updateResponse = UserService.updateUser(userId, updatedName, updatedEmail);

            updateResponse
                    .then()
                    .statusCode(SUCCESS.getStatusCode());

            Response getUserResponse = UserService.getUserById(userId);

            getUserResponse
                    .then()
                    .statusCode(SUCCESS.getStatusCode())
                    .body("id", equalTo(userId))
                    .body("name", equalTo(updatedName))
                    .body("email", equalTo(updatedEmail));

        } finally {
            if (userId != null) {
                addUserId(userId);
            }
        }
    }

    @Test
    public void deleteUserTest() {
        String userId = null;
        String name = TestDataUtil.generateRandomName();
        String email = TestDataUtil.generateRandomEmail();

        try {
            Response createResponse = UserService.createUser(name, email);
            userId = createResponse.then().extract().jsonPath().getString("id");
            Response deleteResponse = UserService.deleteUser(userId);
            deleteResponse
                    .then()
                    .statusCode(SUCCESS.getStatusCode())
                    .body("success", equalTo(true));

            Response getUserResponse = UserService.getUserById(userId);
            getUserResponse
                    .then()
                    .statusCode(NOT_FOUND.getStatusCode());

        } finally {
            if (userId != null) {
                addUserId(userId);
            }
        }
    }
}