package com.gokdenizozkan.ddd.mainservice.feature.review;

import com.gokdenizozkan.ddd.mainservice.config.response.ResponseTemplates;
import com.gokdenizozkan.ddd.mainservice.feature.review.dto.request.ReviewSaveRequest;
import com.gokdenizozkan.ddd.mainservice.feature.review.dto.response.ReviewResponseMirror;
import com.gokdenizozkan.ddd.mainservice.config.response.Structured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void whenReviewExist_thenReturnReviewResponseMirrorList() {
        var reviewResponseMirror = mock(ReviewResponseMirror.class);
        var reviewResponseMirrorList = Collections.singletonList(reviewResponseMirror);
        when(responser.findAll()).thenReturn(ResponseTemplates.ok(reviewResponseMirrorList));

        var response = underTest.findAll();

        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().data().size());
        verify(responser, times(1)).findAll();
    }

    @Test
    void whenNoReviewIsPresent_thenReturnEmptyList() {
        List<ReviewResponseMirror> emptyReviewResponseMirrorList = Collections.emptyList();
        when(responser.findAll()).thenReturn(ResponseTemplates.ok(emptyReviewResponseMirrorList));

        var response = underTest.findAll();

        assertNotNull(response.getBody());
        assertTrue(response.getBody().data().isEmpty());
        verify(responser, times(1)).findAll();
    }

    @Test
    void whenExistentIdIsUsed_thenReturnReviewResponseMirror() {
        var id = 1L;
        var reviewResponseMirror = new ReviewResponseMirror(1L, Rating.EXCELLENT, "Great!", 1L, "John Doe", 1L, "Gokdeniz's Kebab World");
        when(responser.findById(id)).thenReturn(ResponseTemplates.ok(reviewResponseMirror));

        var response = underTest.findById(id);

        assertNotNull(response.getBody());
        assertNotNull(response.getBody().data());
        assertEquals(reviewResponseMirror.id(), response.getBody().data().id());
        verify(responser, times(1)).findById(id);
    }

    @Test
    void whenSaveWithValidRequest_thenReturnSavedReviewResponseMirror() {
        var saveRequest = new ReviewSaveRequest(Rating.EXCELLENT, "Great!", 1L, 1L);
        var savedResponseMirror = new ReviewResponseMirror(1L, Rating.EXCELLENT, "Great!", 1L, "John Doe", 1L, "Gokdeniz's Kebab World");
        when(responser.save(saveRequest)).thenReturn(ResponseTemplates.created(savedResponseMirror));

        var response = underTest.save(saveRequest);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        var responseBody = response.getBody().data();
        assertEquals(savedResponseMirror.id(), responseBody.id());
        assertEquals(responseBody.experience(), savedResponseMirror.experience());
        assertEquals(responseBody.rating(), savedResponseMirror.rating());
        assertEquals(responseBody.buyerName(), savedResponseMirror.buyerName());
        assertEquals(responseBody.storeName(), savedResponseMirror.storeName());

        verify(responser, times(1)).save(any(ReviewSaveRequest.class));
    }

    @Test
    void whenUpdateWithValidRequest_thenReturnUpdatedReviewResponseMirror() {
        var id = 1L;
        var updateRequest = new ReviewSaveRequest(Rating.EXCELLENT, "Great!", 1L, 1L);
        var updatedResponseMirror = new ReviewResponseMirror(1L, Rating.EXCELLENT, "Great!", 1L, "John Doe", 1L, "Gokdeniz's Kebab World");
        when(responser.update(id, updateRequest)).thenReturn(ResponseTemplates.ok(updatedResponseMirror));

        var response = underTest.update(id, updateRequest);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        ReviewResponseMirror responseBody = response.getBody().data();
        assertEquals(responseBody.id(), updatedResponseMirror.id());
        assertEquals(responseBody.experience(), updatedResponseMirror.experience());
        assertEquals(responseBody.rating(), updatedResponseMirror.rating());
        assertEquals(responseBody.buyerName(), updatedResponseMirror.buyerName());
        assertEquals(responseBody.storeName(), updatedResponseMirror.storeName());

        verify(responser, times(1)).update(id, updateRequest);
    }

    @Test
    void whenDeleteWithValidId_thenReturnNoContent() {
        var id = 1L;
        when(responser.delete(id)).thenReturn(ResponseEntity.noContent().build());

        var response = underTest.delete(id);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(responser, times(1)).delete(id);
    }

    @Test
    void whenPatchWithValidId_thenReturnNoContent() {
        var id = 1L;
        var experience = "Great product";
        var rating = "FIVE";
        when(responser.patch(id, experience, rating)).thenReturn(ResponseEntity.noContent().build());

        var response = underTest.patch(id, experience, rating);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(responser, times(1)).patch(id, experience, rating);
    }
}