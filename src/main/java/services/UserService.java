package services;

import clients.RequestSpecificationClient;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.PayloadBuilderUtil;

import static clients.ApiClient.*;
import static endpoints.UserEndpoints.SEARCH_USERS;
import static endpoints.UserEndpoints.GET_USERS_BY_ID;
import static io.restassured.http.ContentType.JSON;

public class UserService {
    public synchronized static Response getUsers() {
        RequestSpecification requestSpecification = new RequestSpecificationClient(JSON).getRequestSpecification();
        return get(requestSpecification, SEARCH_USERS);
    }

    public synchronized static Response getUserById(String userId) {
        RequestSpecification requestSpecification = new RequestSpecificationClient(JSON).getRequestSpecification();
        String endpoint = String.format(GET_USERS_BY_ID, userId);
        return get(requestSpecification, endpoint);
    }

    public synchronized static Response createUser(String name, String email) {
        RequestSpecification requestSpecification = new RequestSpecificationClient(JSON).getRequestSpecification();

        PayloadBuilderUtil<String> payloadBuilderUtil = new PayloadBuilderUtil<String>()
                .add("name", name)
                .add("email", email);

        requestSpecification.body(payloadBuilderUtil.getPayload());

        return post(requestSpecification, SEARCH_USERS);
    }

    public synchronized static Response deleteUser(String userId) {
        RequestSpecification requestSpecification = new RequestSpecificationClient(JSON).getRequestSpecification();
        String endpoint = String.format(GET_USERS_BY_ID, userId);
        return delete(requestSpecification, endpoint);
    }

    public synchronized static Response updateUser(String userId, String name, String email) {
        RequestSpecification requestSpecification = new RequestSpecificationClient(JSON).getRequestSpecification();
        String endpoint = String.format(GET_USERS_BY_ID, userId);

        PayloadBuilderUtil<String> payloadBuilderUtil = new PayloadBuilderUtil<String>()
                .add("name", name)
                .add("email", email);

        requestSpecification.body(payloadBuilderUtil.getPayload());

        return patch(requestSpecification, endpoint);
    }
}