package com.gokdenizozkan.ddd.mainservice.feature.review;

import com.gokdenizozkan.ddd.mainservice.config.exception.ResourceNotFoundWithIdException;
import com.gokdenizozkan.ddd.mainservice.feature.review.dto.ReviewDtoMapper;
import com.gokdenizozkan.ddd.mainservice.feature.review.dto.mapper.ReviewToReviewResponseMirror;
import com.gokdenizozkan.ddd.mainservice.feature.review.dto.request.ReviewSaveRequest;
import com.gokdenizozkan.ddd.mainservice.feature.review.dto.response.ReviewResponseMirror;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class ReviewResponserTest {
    private ReviewResponser underTest;
    private ReviewService service;
    private ReviewDtoMapper dtoMapper;
    private ReviewToReviewResponseMirror reviewToReviewResponseMirror;

    @BeforeEach
    void setUp() {
        service = mock(ReviewService.class);
        reviewToReviewResponseMirror = mock(ReviewToReviewResponseMirror.class);
        dtoMapper = new ReviewDtoMapper(reviewToReviewResponseMirror);
        underTest = new ReviewResponser(service, dtoMapper);
    }

    @AfterEach
    void tearDown() {
        reset(service, reviewToReviewResponseMirror);
    }

    // find all
    @Test
    void whenReviewsExist_thenReturnReviewResponseMirrorListWrapped() {
        // Arrange
        var review = new Review();
        var reviewList = Collections.singletonList(review);
        var reviewDto = mock(ReviewResponseMirror.class);

        when(service.findAll()).thenReturn(reviewList);
        when(dtoMapper.toResponseMirror.apply(review)).thenReturn(reviewDto);

        // Act
        var response = underTest.findAll();

        // Assert
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().data().size());

        verify(service, times(1)).findAll();
        verify(dtoMapper.toResponseMirror, times(1)).apply(any(Review.class));
    }

    @Test
    void whenNoReviewExists_thenReturnEmptyListWrapped() {
        // Arrange
        when(service.findAll()).thenReturn(Collections.emptyList());

        // Act
        var response = underTest.findAll();

        // Assert
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().data().size());

        verify(service, times(1)).findAll();
        verify(dtoMapper.toResponseMirror, times(0)).apply(any(Review.class));
    }

    // find by id
    @Test
    void whenReviewExistsWithId_thenReturnReviewResponseMirrorWrapped() {
        // Arrange
        var review = new Review();
        var reviewDto = mock(ReviewResponseMirror.class);

        when(service.findById(any(Long.class))).thenReturn(review);
        when(dtoMapper.toResponseMirror.apply(any(Review.class))).thenReturn(reviewDto);

        // Act
        var response = underTest.findById(anyLong());

        // Assert
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().data());
        assertInstanceOf(ReviewResponseMirror.class, response.getBody().data());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(service, times(1)).findById(anyLong());
        verify(dtoMapper.toResponseMirror, times(1)).apply(any(Review.class));
    }

    @Test
    void whenNoReviewExistsWithId_thenExceptionPropagates() {
        // Arrange
        when(service.findById(any(Long.class))).thenThrow(ResourceNotFoundWithIdException.class);

        // Act
        assertThrows(ResourceNotFoundWithIdException.class, () -> underTest.findById(anyLong()));

        // Assert
        verify(service, times(1)).findById(anyLong());
        verify(dtoMapper.toResponseMirror, times(0)).apply(any(Review.class));
    }

    // save
    @Test
    void whenSaveWithValidRequest_thenReturnSavedReviewAsReviewResponseMirrorWrapped() {
        // Arrange
        var request = mock(ReviewSaveRequest.class);
        var savedReview = mock(Review.class);
        var reviewResponseMirror = mock(ReviewResponseMirror.class);

        when(service.save(request)).thenReturn(savedReview);
        when(dtoMapper.toResponseMirror.apply(savedReview)).thenReturn(reviewResponseMirror);

        // Act
        var response = underTest.save(request);

        // Assert
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().data());
        assertInstanceOf(ReviewResponseMirror.class, response.getBody().data());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        verify(service, times(1)).save(request);
        verify(dtoMapper.toResponseMirror, times(1)).apply(savedReview);
    }

    // update
    @Test
    void whenUpdateWithValidRequest_thenReturnUpdatedReviewAsReviewResponseMirrorWrapped() {
        // Arrange
        var request = mock(ReviewSaveRequest.class);
        var updatedReview = mock(Review.class);
        var reviewResponseMirror = mock(ReviewResponseMirror.class);
        when(service.update(1L, request)).thenReturn(updatedReview);
        when(dtoMapper.toResponseMirror.apply(updatedReview)).thenReturn(reviewResponseMirror);

        // Act
        var response = underTest.update(1L, request);

        // Assert
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().data());
        assertInstanceOf(ReviewResponseMirror.class, response.getBody().data());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(service, times(1)).update(anyLong(), any(ReviewSaveRequest.class));
        verify(dtoMapper.toResponseMirror, times(1)).apply(any(Review.class));
    }

    // delete
    @Test
    void whenDeleteWithValidId_thenReturnNoContent() {
        // Arrange
        doNothing().when(service).delete(anyLong());

        // Act
        var response = underTest.delete(1L);

        // Assert
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(service, times(1)).delete(1L);
    }

    // patch
    @Test
    void whenPatchWithValidIdAndRating_thenReturnNoContent() {
        // Arrange
        doNothing().when(service).patch(anyLong(), anyString(), anyString());

        // Act
        var response = underTest.patch(1L, "experience", "5");

        // Assert
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(service, times(1)).patch(1L, "experience", "5");
    }
}