package clients;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;

import static configuration.ConfigurationHelper.*;

@Getter
public class RequestSpecificationClient {
    RequestSpecification requestSpecification;

    public RequestSpecificationClient(ContentType contentType){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .setBaseUri(getBaseUrl())
                .setBasePath(getBasePath())
                .setContentType(contentType);
        requestSpecification = requestSpecBuilder.build();
    }
}