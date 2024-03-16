package com.gokdenizozkan.ddd.mainservice.feature.review;

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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

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

        underTest = null;
        service = null;
        dtoMapper = null;
        reviewToReviewResponseMirror = null;
    }

    @Test
    void whenReviewsExist_thenReturnReviewResponseMirrorListWrapped() {
        var review = new Review();
        var reviewList = Collections.singletonList(review);
        var reviewDto = mock(ReviewResponseMirror.class);

        when(service.findAll()).thenReturn(reviewList);
        when(dtoMapper.toResponseMirror.apply(review)).thenReturn(reviewDto);

        var response = underTest.findAll();

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().data().size());

        verify(service, times(1)).findAll();
        verify(dtoMapper.toResponseMirror, times(1)).apply(any(Review.class));
    }

    @Test
    void whenNoReviewExists_thenReturnEmptyListWrapped() {
        when(service.findAll()).thenReturn(Collections.emptyList());

        var response = underTest.findAll();

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().data().size());

        verify(service, times(1)).findAll();
        verify(dtoMapper.toResponseMirror, times(0)).apply(any(Review.class));
    }

    @Test
    void whenReviewExistsWithId_thenReturnReviewResponseMirrorWrapped() {
        var review = new Review();
        var reviewDto = mock(ReviewResponseMirror.class);

        when(service.findById(any(Long.class))).thenReturn(review);
        when(dtoMapper.toResponseMirror.apply(any(Review.class))).thenReturn(reviewDto);

        var response = underTest.findById(anyLong());

        assertNotNull(response.getBody());
        assertNotNull(response.getBody().data());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(service, times(1)).findById(anyLong());
        verify(dtoMapper.toResponseMirror, times(1)).apply(any(Review.class));
    }

    @Test
    void whenSaveWithValidRequest_thenReturnSavedReviewAsReviewResponseMirrorWrapped() {
        var request = mock(ReviewSaveRequest.class);
        var savedReview = mock(Review.class);
        var reviewDetails = mock(ReviewResponseMirror.class);

        when(service.save(request)).thenReturn(savedReview);
        when(dtoMapper.toResponseMirror.apply(savedReview)).thenReturn(reviewDetails);

        var response = underTest.save(request);

        assertNotNull(response.getBody());
        assertNotNull(response.getBody().data());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        verify(service, times(1)).save(request);
        verify(dtoMapper.toResponseMirror, times(1)).apply(savedReview);
    }

    @Test
    void whenUpdateWithValidRequest_thenReturnUpdatedReviewAsReviewResponseMirrorWrapped() {
        var id = 1L;
        var request = mock(ReviewSaveRequest.class);
        var updatedReview = mock(Review.class);
        var reviewDetails = mock(ReviewResponseMirror.class);

        when(service.update(id, request)).thenReturn(updatedReview);
        when(dtoMapper.toResponseMirror.apply(updatedReview)).thenReturn(reviewDetails);

        var response = underTest.update(id, request);

        assertNotNull(response.getBody());
        assertNotNull(response.getBody().data());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(service, times(1)).update(id, request);
        verify(dtoMapper.toResponseMirror, times(1)).apply(updatedReview);
    }

    @Test
    void whenDeleteWithValidId_thenReturnNoContent() {
        doNothing().when(service).delete(anyLong());

        var response = underTest.delete(anyLong());

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(service, times(1)).delete(anyLong());
    }
}