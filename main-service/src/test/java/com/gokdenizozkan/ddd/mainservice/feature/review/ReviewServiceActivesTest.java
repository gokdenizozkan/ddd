package com.gokdenizozkan.ddd.mainservice.feature.review;

import com.gokdenizozkan.ddd.mainservice.client.recommendation.RecommendationClient;
import com.gokdenizozkan.ddd.mainservice.config.Specifications;
import com.gokdenizozkan.ddd.mainservice.config.exception.ResourceNotActiveException;
import com.gokdenizozkan.ddd.mainservice.config.exception.ResourceNotFoundWithIdException;
import com.gokdenizozkan.ddd.mainservice.feature.review.dto.ReviewEntityMapper;
import com.gokdenizozkan.ddd.mainservice.feature.review.dto.mapper.ReviewSaveRequestToReview;
import com.gokdenizozkan.ddd.mainservice.feature.review.dto.request.ReviewSaveRequest;
import com.gokdenizozkan.ddd.mainservice.feature.store.Store;
import com.gokdenizozkan.ddd.mainservice.feature.store.StoreType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;



class ReviewServiceActivesTest {
    private ReviewServiceActives underTest;
    private ReviewEntityMapper entityMapper;
    private ReviewSaveRequestToReview reviewSaveRequestToReview;
    private ReviewRepositoryStub repository;
    private StoreRepositoryStrub storeRepository;
    private RecommendationClient recommendationClient;
    private Specification<Review> specification;

    @BeforeEach
    void setUp() {
        reviewSaveRequestToReview = mock(ReviewSaveRequestToReview.class);
        entityMapper = new ReviewEntityMapper(reviewSaveRequestToReview);

        repository = new ReviewRepositoryStub();
        storeRepository = new StoreRepositoryStrub();

        recommendationClient = mock(RecommendationClient.class);

        specification = Specifications.isActive(Review.class);

        underTest = new ReviewServiceActives(repository, entityMapper, recommendationClient, storeRepository);
    }

    @AfterEach
    void tearDown() {
        reset(reviewSaveRequestToReview, recommendationClient);

        underTest = null;
        entityMapper = null;
        reviewSaveRequestToReview = null;
        repository = null;
        storeRepository = null;
        recommendationClient = null;
    }

    // findAll
    @Test
    void first_whenReviewsExist_thenReturnAllReviews_second_whenNoReviewsExist_thenReturnEmptyList() {
        // Act
        List<Review> reviews = underTest.findAll();
        List<Review> reviewsEmpty = underTest.findAll();


        // Assert
        assertEquals(1, reviews.size());
        assertInstanceOf(Review.class, reviews.getFirst());
        assertEquals(0, reviewsEmpty.size());
    }

    // findById
    @Test
    void whenReviewExistsWithId_thenReturnReview() {
        // Arrange
        long id = 1L;

        // Act
        Review review = underTest.findById(id);

        // Assert
        assertNotNull(review);
        assertInstanceOf(Review.class, review);
    }

    @Test
    void whenReviewNotExistsWithId_thenThrowResourceNotFoundWithIdException() {
        // Arrange
        long id = 10L;

        // Act
        assertThrows(ResourceNotFoundWithIdException.class, () -> underTest.findById(id));
    }

    @Test
    void whenReviewExistsWithIdAndNotActive_thenThrowResourceNotActiveException() {
        // Arrange
        long id = 20L;

        // Act
        assertThrows(ResourceNotActiveException.class, () -> underTest.findById(id));
    }

    // save
    @Test
    void whenReviewSaveRequestIsValid_thenReturnSavedReview() {
        // Arrange
        ReviewSaveRequest request = mock(ReviewSaveRequest.class);
        Store store = new Store();
        store.setId(1L);
        store.setStoreType(StoreType.FOOD_STORE);
        store.setReviewCount(20L);
        store.setStoreRatingAverage(4.5F);

        Review review = new Review();
        review.setExperience("exp");
        review.setRating(Rating.EXCELLENT);
        review.setStore(store);

        when(entityMapper.fromSaveRequest.apply(request)).thenReturn(review);


        // Act
        Review savedReview = underTest.save(request);

        // Assert
        assertNotNull(savedReview);
        assertInstanceOf(Review.class, savedReview);
    }

    // update
    @Test
    void whenReviewExistsWithId_thenReturnUpdatedReview() {
        // Arrange
        long id = 1L;
        ReviewSaveRequest request = mock(ReviewSaveRequest.class);
        Store store = new Store();
        store.setId(1L);
        store.setStoreType(StoreType.FOOD_STORE);
        store.setReviewCount(20L);
        store.setStoreRatingAverage(4.5F);

        Review review = new Review();
        review.setId(1L);
        review.setExperience("exp");
        review.setRating(Rating.EXCELLENT);
        review.setStore(store);

        when(entityMapper.fromSaveRequest.apply(request)).thenReturn(review);

        // Act
        Review updatedReview = underTest.update(id, request);

        // Assert
        assertNotNull(updatedReview);
        assertInstanceOf(Review.class, updatedReview);
    }

    // delete
    @Test
    void whenReviewExistsWithId_thenDeleteReview() {
        // Arrange
        long id = 61L;

        // Act
        underTest.delete(id);

        // Assert
        assertDoesNotThrow(() -> underTest.delete(id));
    }

    // patch
    @Test
    void whenReviewExistsWithId_thenPatchReview() {
        // Arrange
        long id = 1L;
        String experience = "exp";
        String ratingString = "EXCELLENT";

        // Act
        underTest.patch(id, experience, ratingString);

        // Assert
        assertDoesNotThrow(() -> underTest.patch(id, experience, ratingString));
    }
}