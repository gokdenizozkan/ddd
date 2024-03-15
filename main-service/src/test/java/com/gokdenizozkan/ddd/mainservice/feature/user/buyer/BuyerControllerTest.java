package com.gokdenizozkan.ddd.mainservice.feature.user.buyer;

import com.gokdenizozkan.ddd.mainservice.config.response.ResponseTemplates;
import com.gokdenizozkan.ddd.mainservice.feature.user.buyer.dto.request.BuyerSaveRequest;
import com.gokdenizozkan.ddd.mainservice.feature.user.buyer.dto.response.BuyerDetails;
import com.gokdenizozkan.ddd.mainservice.config.response.Structured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class BuyerControllerTest {
    @InjectMocks
    private BuyerController underTest;

    @Mock
    private BuyerResponser responser;

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
    void whenBuyerExist_thenReturnBuyerDetailsList() {
        // Arrange
        BuyerDetails buyerDetails = mock(BuyerDetails.class);
        List<BuyerDetails> buyerDetailsList = Collections.singletonList(buyerDetails);
        when(responser.findAll()).thenReturn(ResponseTemplates.ok(buyerDetailsList));

        // Act
        ResponseEntity<Structured<List<BuyerDetails>>> response = underTest.findAll();

        // Assert
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().data().size());
        verify(responser, times(1)).findAll();
    }

    @Test
    void whenNoBuyerIsPresent_thenReturnEmptyList() {
        // Arrange
        List<BuyerDetails> emptyBuyerDetailsList = Collections.emptyList();
        when(responser.findAll()).thenReturn(ResponseTemplates.ok(emptyBuyerDetailsList));

        // Act
        var response = underTest.findAll();

        // Assert
        assertNotNull(response.getBody());
        assertTrue(response.getBody().data().isEmpty());
        verify(responser, times(1)).findAll();
    }

    // find by id
    @Test
    void whenExistentIdIsUsed_thenReturnBuyerDetails() {
        // Arrange
        var id = 1L;
        var buyerDetails = new BuyerDetails(1L, "John", "Doe", "john.doe@example.com", "1234567890", LocalDate.of(2023, 10, 1));
        when(responser.findById(id)).thenReturn(ResponseTemplates.ok(buyerDetails));

        // Act
        var response = underTest.findById(id);

        // Assert
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().data());
        assertEquals(buyerDetails.id(), response.getBody().data().id());
        verify(responser, times(1)).findById(id);
    }

    // save
    @Test
    void whenSaveWithValidRequest_thenReturnSavedBuyerDetails() {
        // Arrange
        var saveRequest = new BuyerSaveRequest("John", "Doe", "john.doe@example.com", "1234567890", LocalDate.of(2023, 10, 1));
        var savedDetails = new BuyerDetails(1L, "John", "Doe", "john.doe@example.com", "1234567890", LocalDate.of(2023, 10, 1));
        when(responser.save(saveRequest)).thenReturn(ResponseTemplates.created(savedDetails));

        // Act
        var response = responser.save(saveRequest);

        // Assert
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        var responseBody = response.getBody().data();
        assertEquals(savedDetails.id(), responseBody.id());
        assertEquals(responseBody.name(), savedDetails.name());
        assertEquals(responseBody.surname(), savedDetails.surname());
        assertEquals(responseBody.email(), savedDetails.email());
        assertEquals(responseBody.phone(), savedDetails.phone());
        assertEquals(responseBody.birthdate(), savedDetails.birthdate());

        // Verify interaction with mock
        verify(responser, times(1)).save(any(BuyerSaveRequest.class));
    }

    // update
    @Test
    void whenUpdateWithValidRequest_thenReturnUpdatedBuyerDetails() {
        // Arrange
        var id = 1L;
        var updateRequest = new BuyerSaveRequest("John", "Doe", "john.doe@example.com", "1234567890", LocalDate.of(2023, 10, 1));
        var updatedDetails = new BuyerDetails(1L, "CAN", "DAG", "john.doe@example.com", "1234567890", LocalDate.of(2023, 10, 1));
        when(responser.update(id, updateRequest)).thenReturn(ResponseTemplates.ok(updatedDetails));

        // Act
        var response = responser.update(id, updateRequest);

        // Assert
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        BuyerDetails responseBody = response.getBody().data();
        assertEquals(responseBody.id(), updatedDetails.id());
        assertEquals(responseBody.name(), updatedDetails.name());
        assertEquals(responseBody.surname(), updatedDetails.surname());
        assertEquals(responseBody.email(), updatedDetails.email());
        assertEquals(responseBody.phone(), updatedDetails.phone());
        assertEquals(responseBody.birthdate(), updatedDetails.birthdate());

        // Verify interaction with mock
        verify(responser, times(1)).update(id, updateRequest);
    }

    // delete
    @Test
    void whenDeleteWithValidId_thenReturnNoContent() {
        // Arrange
        var id = 1L;
        when(responser.delete(id)).thenReturn(ResponseTemplates.noContent());

        // Act
        var response = responser.delete(id);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        // Verify interaction with mock
        verify(responser, times(1)).delete(id);
    }
}
