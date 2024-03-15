package com.gokdenizozkan.ddd.mainservice.feature.review;

import com.gokdenizozkan.ddd.mainservice.client.recommendation.RecommendationClient;
import com.gokdenizozkan.ddd.mainservice.config.Specifications;
import com.gokdenizozkan.ddd.mainservice.config.exception.ResourceNotActiveException;
import com.gokdenizozkan.ddd.mainservice.config.exception.ResourceNotFoundWithIdException;
import com.gokdenizozkan.ddd.mainservice.core.dtoprojection.ActiveDetermingFields;
import com.gokdenizozkan.ddd.mainservice.core.dtoprojection.StoreReviewFields;
import com.gokdenizozkan.ddd.mainservice.feature.review.dto.ReviewEntityMapper;
import com.gokdenizozkan.ddd.mainservice.feature.review.dto.mapper.ReviewSaveRequestToReview;
import com.gokdenizozkan.ddd.mainservice.feature.review.dto.request.ReviewSaveRequest;
import com.gokdenizozkan.ddd.mainservice.feature.store.StoreRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ReviewServiceActivesTest {
    @InjectMocks
    private ReviewServiceActives underTest;
    @Mock
    private ReviewRepository repository;
    @Mock
    private Specification<Review> specification;
    @Mock
    private ReviewEntityMapper reviewEntityMapper;
    @Mock
    private ReviewSaveRequestToReview reviewSaveRequestToReview;
    @Mock
    private RecommendationClient recommendationClient;
    @Mock
    private StoreRepository storeRepository;

    @Mock
    private MockedStatic<ActiveDetermingFields> adfMockedStatic;
    @Mock
    private MockedStatic<StoreReviewFields> srfMockedStatic;

    @BeforeEach
    void setUp() {
        reviewSaveRequestToReview = mock(ReviewSaveRequestToReview.class);
        reviewEntityMapper = new ReviewEntityMapper(reviewSaveRequestToReview);

        storeRepository = mock(StoreRepository.class);
        recommendationClient = mock(RecommendationClient.class);
        specification = Specifications.isActive(Review.class);
        repository = mock(ReviewRepository.class);

        adfMockedStatic = mockStatic(ActiveDetermingFields.class);
        srfMockedStatic = mockStatic(StoreReviewFields.class);

        underTest = new ReviewServiceActives(repository, reviewEntityMapper, recommendationClient, storeRepository);
    }

    @AfterEach
    void tearDown() {
        reset(repository, reviewEntityMapper, recommendationClient, storeRepository, reviewSaveRequestToReview, adfMockedStatic, srfMockedStatic);
    }

    // find all
    @Test
    void whenReviewsExist_thenReturnReviewsList() {
        // Arrange
        var review = new Review();
        var reviewList = Collections.singletonList(review);

        when(repository.findAll(specification)).thenReturn(reviewList);

        // Act
        var result = underTest.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertInstanceOf(List.class, result);
        assertInstanceOf(Review.class, result.getFirst());
        verify(repository, times(1)).findAll();
    }

    @Test
    void whenReviewsNotExist_thenReturnEmptyList() {
        // Arrange
        when(repository.findAll(specification)).thenReturn(Collections.emptyList());

        // Act
        var result = underTest.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(0, result.size());
        assertInstanceOf(List.class, result);
        verify(repository, times(1)).findAll();
    }

    // find by id
    @Test
    void whenReviewExistsWithId_thenReturnReview() {
        // Arrange
        var id = 1L;
        var review = new Review();

        when(repository.findById(specification, id)).thenReturn(Optional.of(review));

        // Act
        var result = underTest.findById(id);

        // Assert
        assertNotNull(result);
        assertInstanceOf(Review.class, result);
        verify(repository, times(1)).findById(specification, id);
    }

    @Test
    void whenNoReviewExistsWithId_thenThrowResourceNotFoundWithIdException() {
        // Arrange
        var id = 1L;

        when(repository.existsById(id)).thenReturn(false);

        // Act
        // Assert
        assertThrows(ResourceNotFoundWithIdException.class, () -> underTest.findById(id));
        verify(repository, times(1)).findById(specification, id);
    }

    @Test
    void whenReviewExistsWithIdAndNotAcitve_thenThrowResourceNotActiveException() {
        // Arrange
        var id = 1L;
        var review = new Review();
        review.setEnabled(false);
        review.setDeleted(true);

        when(repository.findById(specification, id)).thenReturn(Optional.of(review));

        // Act
        // Assert
        assertThrows(ResourceNotActiveException.class, () -> underTest.findById(id));
        verify(repository, times(1)).findById(specification, id);
    }

    // save
    @Test
    void whenReviewSaveRequestIsValid_thenReturnSavedReview() {
        // Arrange
        var request = mock(ReviewSaveRequest.class);
        var review = new Review();

        when(reviewEntityMapper.fromSaveRequest.apply(request)).thenReturn(review);
        when(recommendationClient.updateStoreRating(any(), any(), any())).thenReturn(null);
        when(repository.save(review)).thenReturn(review);

        // Act
        var result = underTest.save(request);

        // Assert
        assertNotNull(result);
        assertInstanceOf(Review.class, result);
        verify(reviewEntityMapper.fromSaveRequest, times(1)).apply(request);
        verify(recommendationClient, times(1)).updateStoreRating(any(), any(), any());
        verify(repository, times(1)).save(review);
    }

    // update


    // delete
    @Test
    void whenPatchWithValidIdAndExperience_thenUpdateExperience() {
        var id = 1L;
        var review = new Review();
        var experience = "Great experience";

        when(repository.findById(id)).thenReturn(Optional.of(review));

        underTest.patch(id, experience, "");

        assertEquals(experience, review.getExperience());
        verify(repository, times(1)).save(review);
    }

    @Test
    void whenPatchWithValidIdAndRating_thenUpdateRating() {
        var id = 1L;
        var review = new Review();
        var ratingString = "FIVE";

        when(repository.findById(id)).thenReturn(Optional.of(review));

        underTest.patch(id, "", ratingString);

        assertEquals(Rating.valueOf(ratingString), review.getRating());
        verify(repository, times(1)).save(review);
    }

    @Test
    void whenPatchWithInvalidId_thenThrowResourceNotFoundWithIdException() {
        var id = 1L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundWithIdException.class, () -> underTest.patch(id, "", ""));
        verify(repository, times(0)).save(any());
    }

    @Test
    void whenPatchWithValidIdAndBlankExperienceAndRating_thenDoNotUpdateReview() {
        var id = 1L;
        var review = new Review();

        when(repository.findById(id)).thenReturn(Optional.of(review));

        underTest.patch(id, "", "");

        assertNull(review.getExperience());
        assertNull(review.getRating());
        verify(repository, times(0)).save(any());
    }

    // patch

}