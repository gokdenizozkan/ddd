package com.gokdenizozkan.ddd.mainservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gokdenizozkan.ddd.mainservice.client.recommendation.RecommendationClient;
import com.gokdenizozkan.ddd.mainservice.feature.address.Address;
import com.gokdenizozkan.ddd.mainservice.feature.address.AddressRepository;
import com.gokdenizozkan.ddd.mainservice.feature.address.dto.AddressEntityMapper;
import com.gokdenizozkan.ddd.mainservice.feature.address.dto.request.AddressSaveRequest;
import com.gokdenizozkan.ddd.mainservice.feature.review.Rating;
import com.gokdenizozkan.ddd.mainservice.feature.review.Review;
import com.gokdenizozkan.ddd.mainservice.feature.review.ReviewRepository;
import com.gokdenizozkan.ddd.mainservice.feature.review.dto.request.ReviewSaveRequest;
import com.gokdenizozkan.ddd.mainservice.feature.store.Store;
import com.gokdenizozkan.ddd.mainservice.feature.store.StoreRepository;
import com.gokdenizozkan.ddd.mainservice.feature.store.StoreType;
import com.gokdenizozkan.ddd.mainservice.feature.store.dto.request.StoreSaveRequest;
import com.gokdenizozkan.ddd.mainservice.feature.user.buyer.Buyer;
import com.gokdenizozkan.ddd.mainservice.feature.user.buyer.BuyerRepository;
import com.gokdenizozkan.ddd.mainservice.feature.user.buyer.dto.request.BuyerSaveRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static com.gokdenizozkan.ddd.mainservice.Entity.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GeneralServiceApplicationTests {

    @LocalServerPort
    private Integer port;

    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.2");
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    static void startContainer() {
        postgres.start();
    }

    @AfterAll
    static void stopContainer() {
        postgres.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        // set active profile to test
        registry.add("spring.profiles.active", () -> "test");
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.datasource.driver-class-name", postgres::getDriverClassName);
        registry.add("spring.jpa.database-platform", () -> "org.hibernate.dialect.PostgreSQLDialect");
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "update");
        registry.add("spring.jpa.show-sql", () -> "true");
        registry.add("ddd.client.recommendation-service.complete-url", () -> "http://localhost:8080");
        registry.add("spring.sql.init.mode=", () -> "always");
    }

    @Autowired
    AddressRepository addressRepository;
    @Autowired
    AddressEntityMapper addressEntityMapper;
    @Autowired
    BuyerRepository buyerRepository;
    @Autowired
    StoreRepository storeRepository;
    @Autowired
    ReviewRepository reviewRepository;

    @BeforeEach
    void beforeEach() {
        RestAssured.baseURI = "http://localhost:" + port;
    }

    @MockBean
    private RecommendationClient recommendationClient;

    @Test
    void contextLoads() {
    }

    @Test
    void testAddress_whenValidAddressSaveRequest_thenAddressSaved() {
        var request = addressSaveRequest();
        given()
                .contentType(ContentType.JSON)
                .when()
                .body(toJson(request))
                .post("api/v1/addresses/")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("success", equalTo(true));
    }

    @Test
    void testStore_whenValidStoreSaveRequest_thenStoreSaved() {
        storeRepository.deleteAll();

        // save address
        var addressSaveRequest = addressSaveRequest();
        Address address = addressRepository.save(addressEntityMapper.fromSaveRequest.apply(addressSaveRequest));

        // save store
        var request = storeSaveRequest(address.getId());
        given()
                .contentType(ContentType.JSON)
                .when()
                .body(toJson(request))
                .post("api/v1/stores/")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("success", equalTo(true));

        // get stores
        when()
                .get("api/v1/stores/")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("data", hasSize(1))
                .body("data[0].name", equalTo("Test Store"))
                .body("data[0].email", equalTo("store@at.com"))
                .body("data[0].phone", equalTo("1234567890"));
    }

    @Test
    void testBuyer_whenValidBuyerSaveRequest_thenBuyerSaved() {
        buyerRepository.deleteAll();

        // save buyer
        var request = BuyerSaveRequest.builder()
                .name("Test")
                .surname("Buyer")
                .email("a@a.com")
                .phone("1234567890")
                .build();

        given()
                .contentType(ContentType.JSON)
                .when()
                .body(toJson(request))
                .post("api/v1/buyers/")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("success", equalTo(true));

        // get buyers
        when()
                .get("api/v1/buyers/")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("data", hasSize(1))
                .body("data[0].name", equalTo("Test"))
                .body("data[0].surname", equalTo("Buyer"));
    }

    @Test
    void testReview_whenValidReviewSaveRequest_thenReviewSaved() {
        reviewRepository.deleteAll();

        var buyer = buyerRepository.save(buyer());
        var address = addressRepository.save(address());
        var store = storeRepository.save(store(address));

        var request = reviewSaveRequest(buyer, store);
        given()
                .contentType(ContentType.JSON)
                .when()
                .body(toJson(request))
                .post("api/v1/reviews/")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("success", equalTo(true));

        // get reviews
        when()
                .get("api/v1/reviews/")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("data", hasSize(1))
                .body("data[0].buyerId", equalTo(buyer.getId().intValue()))
                .body("data[0].storeId", equalTo(store.getId().intValue()));
    }

    @Test
    void testAddress_whenValidAddressUpdateRequest_thenAddressUpdated() {
        var address = addressRepository.save(address());
        var request = addressSaveRequest();
        given()
                .contentType(ContentType.JSON)
                .when()
                .body(toJson(request))
                .put("api/v1/addresses/" + address.getId())
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("success", equalTo(true));
    }

    @Test
    void testStore_whenValidStoreUpdateRequest_thenStoreUpdated() {
        var address = addressRepository.save(address());
        var store = storeRepository.save(store(address));
        var request = storeSaveRequest(address.getId());
        given()
                .contentType(ContentType.JSON)
                .when()
                .body(toJson(request))
                .put("api/v1/stores/" + store.getId())
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("success", equalTo(true));
    }

    @Test
    void testBuyer_whenValidBuyerUpdateRequest_thenBuyerUpdated() {
        var buyer = buyerRepository.save(buyer());
        var request = buyerSaveRequest();
        given()
                .contentType(ContentType.JSON)
                .when()
                .body(toJson(request))
                .put("api/v1/buyers/" + buyer.getId())
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("success", equalTo(true));
    }

    @Test
    void testReview_whenValidReviewUpdateRequest_thenReviewUpdated() {
        var buyer = buyerRepository.save(buyer());
        var address = addressRepository.save(address());
        var store = storeRepository.save(store(address));
        var review = reviewRepository.save(review(buyer, store));
        var request = reviewSaveRequest(buyer, store);
        given()
                .contentType(ContentType.JSON)
                .when()
                .body(toJson(request))
                .put("api/v1/reviews/" + review.getId())
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("success", equalTo(true));
    }

    @Test
    void testUpdatePersists_whenReviewUpdated_thenStoreRatingAverageChanges() {
        var buyer = buyerRepository.save(buyer());
        var address = addressRepository.save(address());
        var store = storeRepository.save(store(address));
        var request = reviewSaveRequest(buyer, store);
        given()
                .contentType(ContentType.JSON)
                .when()
                .body(toJson(request))
                .post("api/v1/reviews/")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("success", equalTo(true));

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("api/v1/stores/" + store.getId() + "/details")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("data.storeRatingAverage", greaterThan(0.0F));
    }

    private String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}

class Entity {
    public static List<Long> idsAddress = new ArrayList<>();
    public static List<Long> idsStore = new ArrayList<>();
    public static List<Long> idsBuyer = new ArrayList<>();
    public static List<Long> idsReview = new ArrayList<>();
    public static Random random = new Random();

    public static Address address() {
        var id = random.nextLong();
        while (idsAddress.contains(id)) {
            id = random.nextLong();
        }
        idsAddress.add(id);
        return address(id);
    }

    public static Address address(Long id) {
        Address address = new Address();

        address.setId(id);
        address.setLatitude(BigDecimal.valueOf(40.0));
        address.setLongitude(BigDecimal.valueOf(40.0));
        address.setCountryCode("TUR");
        address.setCity("Istanbul");
        address.setRegion("Kadikoy");
        address.setAddressLine("Sahrayicedit");
        address.setContactPhone("1234567890");

        address.setEnabled(true);
        address.setDeleted(false);
        return address;
    }

    public static Store store() {
        var id = random.nextLong();
        while (idsStore.contains(id)) {
            id = random.nextLong();
        }
        idsStore.add(id);
        return store(id);
    }

    public static Store store(Long id) {
        Store store = new Store();

        store.setId(id);
        store.setName("Test Store");
        store.setAddress(address());
        store.setEmail("store@at.com");
        store.setPhone("1234567890");
        store.setStoreType(StoreType.FOOD_STORE);
        store.setReviewCount(0L);
        store.setStoreRatingAverage(0.0F);
        store.setReviews(Collections.emptyList());
        store.setSellers(Collections.emptyList());

        store.setEnabled(true);
        store.setDeleted(false);
        return store;
    }

    public static Store store(Address address) {
        var store = store();
        store.setAddress(address);
        return store;
    }

    public static Buyer buyer() {
        var id = random.nextLong();
        while (idsBuyer.contains(id)) {
            id = random.nextLong();
        }
        idsBuyer.add(id);
        return buyer(id);
    }

    public static Buyer buyer(Long id) {
        Buyer buyer = new Buyer();

        buyer.setId(id);
        buyer.setName("Test");
        buyer.setSurname("Buyer");
        buyer.setEmail("test@buyer.com");
        buyer.setPhone("1234567890");
        buyer.setBirthdate(LocalDate.parse("1990-12-01"));
        buyer.setAddresses(Collections.emptyList());
        buyer.setReviews(Collections.emptyList());

        buyer.setEnabled(true);
        buyer.setDeleted(false);
        return buyer;
    }

    public static Review review() {
        var id = random.nextLong();
        while (idsReview.contains(id)) {
            id = random.nextLong();
        }
        idsReview.add(id);
        return review(id);
    }

    public static Review review(Long id) {
        Review review = new Review();

        review.setId(id);
        review.setBuyer(buyer());
        review.setStore(store());
        review.setRating(Rating.EXCELLENT);
        review.setExperience("Good");

        review.setEnabled(true);
        review.setDeleted(false);
        return review;
    }

    public static Review review(Buyer buyer, Store store) {
        var review = review();
        review.setBuyer(buyer);
        review.setStore(store);
        return review;
    }

    public static AddressSaveRequest addressSaveRequest() {
        return AddressSaveRequest.builder()
                .latitude(BigDecimal.valueOf(40.0))
                .longitude(BigDecimal.valueOf(40.0))
                .countryCode("TUR")
                .city("Istanbul")
                .region("Kadikoy")
                .addressLine("Sahrayicedit")
                .contactPhone("1234567890")
                .build();
    }

    public static StoreSaveRequest storeSaveRequest() {
        var id = random.nextLong(0, idsAddress.size());
        return StoreSaveRequest.builder()
                .name("Test Store")
                .addressId(id)
                .email("store@at.com")
                .phone("1234567890")
                .storeType(StoreType.FOOD_STORE)
                .build();
    }

    public static StoreSaveRequest storeSaveRequest(Long addressId) {
        return StoreSaveRequest.builder()
                .name("Test Store")
                .addressId(addressId)
                .email("store@at.com")
                .phone("1234567890")
                .storeType(StoreType.FOOD_STORE)
                .build();
    }

    public static BuyerSaveRequest buyerSaveRequest() {
        return BuyerSaveRequest.builder()
                .name("Test")
                .surname("Buyer")
                .email("buyer@walmart.com")
                .phone("1234567890")
                .build();
    }

    public static ReviewSaveRequest reviewSaveRequest() {
        var buyerId = random.nextLong(0, idsBuyer.size());
        var storeId = random.nextLong(0, idsStore.size());
        return ReviewSaveRequest.builder()
                .buyerId(buyerId)
                .storeId(storeId)
                .rating(Rating.EXCELLENT)
                .experience("Good")
                .build();
    }

    public static ReviewSaveRequest reviewSaveRequest(Buyer buyer, Store store) {
        return ReviewSaveRequest.builder()
                .buyerId(buyer.getId())
                .storeId(store.getId())
                .rating(Rating.EXCELLENT)
                .experience("Good")
                .build();
    }
}