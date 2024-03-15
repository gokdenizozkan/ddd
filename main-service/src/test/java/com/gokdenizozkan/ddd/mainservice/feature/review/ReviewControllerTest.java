package com.gokdenizozkan.ddd.mainservice.feature.review;

import com.gokdenizozkan.ddd.mainservice.config.response.ResponseTemplates;
import com.gokdenizozkan.ddd.mainservice.feature.review.dto.request.ReviewSaveRequest;
import com.gokdenizozkan.ddd.mainservice.feature.review.dto.response.ReviewResponseMirror;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReviewControllerTest {
    @InjectMocks
    private ReviewController underTest;

    @Mock
    private ReviewResponser responser;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    // find all
    @Test
    void whenReviewsExist_thenReturnReviewResponseMirrorList() {
        // Arrange
        var reviewResponseMirror = mock(ReviewResponseMirror.class);
        var reviewResponseMirrorList = Collections.singletonList(reviewResponseMirror);
        when(responser.findAll()).thenReturn(ResponseTemplates.ok(reviewResponseMirrorList));

        // Act
        var response = underTest.findAll();

        // Assert
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().data().size());
        verify(responser, times(1)).findAll();
    }

    // find by id
    @Test
    void whenReviewExist_thenReturnReviewResponseMirror() {
        // Arrange
        var reviewResponseMirror = mock(ReviewResponseMirror.class);
        when(responser.findById(1L)).thenReturn(ResponseTemplates.ok(reviewResponseMirror));

        // Act
        var response = underTest.findById(1L);

        // Assert
        assertNotNull(response.getBody());
        verify(responser, times(1)).findById(1L);
    }

    // save
    @Test
    void whenReviewIsSaved_thenReturnReviewResponseMirror() {
        // Arrange
        var reviewResponseMirror = mock(ReviewResponseMirror.class);
        when(responser.save(any())).thenReturn(ResponseTemplates.created(reviewResponseMirror));

        // Act
        var response = underTest.save(any());

        // Assert
        assertNotNull(response.getBody());
        verify(responser, times(1)).save(any());
    }

    // update
    @Test
    void whenReviewIsUpdated_thenReturnReviewResponseMirror() {
        // Arrange
        var reviewResponseMirror = new ReviewResponseMirror(1L, Rating.EXCELLENT, "Great", 1L, "John Doe", 1L, "Store Name");
        var response = ResponseTemplates.ok(reviewResponseMirror);
        var request = new ReviewSaveRequest(Rating.EXCELLENT, "Great", 1L, 1L);
        when(responser.update(1L, request)).thenReturn(response);

        // Act
        var controllerResponse = underTest.update(1L, request);

        // Assert
        assertNotNull(controllerResponse.getBody());
        verify(responser, times(1)).update(anyLong(), any(ReviewSaveRequest.class));
    }

    // delete
    @Test
    void whenReviewIsDeleted_thenReturnNoContent() {
        // Arrange
        when(responser.delete(1L)).thenReturn(ResponseTemplates.noContent());

        // Act
        var response = underTest.delete(1L);

        // Assert
        assertNotNull(response.getBody());
        verify(responser, times(1)).delete(1L);
    }

    // patch
    @Test
    void whenReviewIsPatched_thenReturnNoContent() {
        // Arrange
        when(responser.patch(1L, "Great", "EXCELLENT"))
                .thenReturn(ResponseTemplates.noContent());

        // Act
        var response = underTest.patch(1L, "Great", "EXCELLENT");

        // Assert
        assertNotNull(response.getBody());
        verify(responser, times(1)).patch(1L, "Great", "EXCELLENT");
    }
}