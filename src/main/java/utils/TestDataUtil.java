package utils;

import com.github.javafaker.Faker;

public class TestDataUtil {
    private static final Faker faker = new Faker();

    public static String generateRandomName() {
        return faker.name().name();
    }

    public static String generateRandomEmail() {
        return faker.internet().emailAddress();
    }
}