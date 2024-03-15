package com.gokdenizozkan.ddd.generalservice.feature.user.buyer;

import com.gokdenizozkan.ddd.generalservice.config.response.ResponseTemplates;
import com.gokdenizozkan.ddd.generalservice.feature.user.buyer.dto.request.BuyerSaveRequest;
import com.gokdenizozkan.ddd.generalservice.feature.user.buyer.dto.response.BuyerDetails;
import com.gokdenizozkan.ddd.generalservice.config.response.Structured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class BuyerControllerTest {

    @Mock
    private BuyerResponser responser;

    @InjectMocks
    private BuyerController controller;

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
    void whenBuyerDetailsExist_thenReturnBuyerDetailsList() {
        // Arrange
        BuyerDetails buyerDetails = mock(BuyerDetails.class);
        List<BuyerDetails> buyerDetailsList = Collections.singletonList(buyerDetails);
        when(responser.findAll()).thenReturn(ResponseTemplates.ok(buyerDetailsList));

        // Act
        ResponseEntity<Structured<List<BuyerDetails>>> response = controller.findAll();

        // Assert
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().data().size());
        verify(responser, times(1)).findAll();
    }

    @Test
    void whenBuyerDetailsAreNotPresent_thenReturnEmptyList() {
        // Arrange
        List<BuyerDetails> emptyBuyerDetailsList = Collections.emptyList();
        when(responser.findAll()).thenReturn(ResponseTemplates.ok(emptyBuyerDetailsList));

        // Act
        ResponseEntity<Structured<List<BuyerDetails>>> response = controller.findAll();

        // Assert
        assertNotNull(response.getBody());
        assertTrue(response.getBody().data().isEmpty());
        verify(responser, times(1)).findAll();
    }

    // find by id
    @Test
    void whenExistentIdIsUsed_thenReturnBuyerDetails() {
        // Arrange
        Long id = 1L;
        BuyerDetails buyerDetails = new BuyerDetails(1L, "John", "Doe", "john.doe@example.com", "1234567890", LocalDate.of(2023, 10, 1));
        when(responser.findById(id)).thenReturn(ResponseTemplates.ok(buyerDetails));

        // Act
        ResponseEntity<Structured<BuyerDetails>> response = controller.findById(id);

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
        BuyerSaveRequest saveRequest = new BuyerSaveRequest("John", "Doe", "john.doe@example.com", "1234567890", LocalDate.of(2023, 10, 1));
        BuyerDetails savedDetails = new BuyerDetails(1L, "John", "Doe", "john.doe@example.com", "1234567890", LocalDate.of(2023, 10, 1));
        when(responser.save(saveRequest)).thenReturn(ResponseTemplates.created(savedDetails));

        // Act
        ResponseEntity<Structured<BuyerDetails>> response = responser.save(saveRequest);

        // Assert
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        BuyerDetails responseBody = response.getBody().data();
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
        Long id = 1L;
        BuyerSaveRequest updateRequest = new BuyerSaveRequest("John", "Doe", "john.doe@example.com", "1234567890", LocalDate.of(2023, 10, 1));
        BuyerDetails updatedDetails = new BuyerDetails(1L, "CAN", "DAG", "john.doe@example.com", "1234567890", LocalDate.of(2023, 10, 1));
        when(responser.update(id, updateRequest)).thenReturn(ResponseTemplates.ok(updatedDetails));

        // Act
        ResponseEntity<Structured<BuyerDetails>> response = responser.update(id, updateRequest);

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
        Long id = 1L;
        when(responser.delete(id)).thenReturn(ResponseTemplates.noContent());

        // Act
        ResponseEntity<Structured<Object>> response = responser.delete(id);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        // Verify interaction with mock
        verify(responser, times(1)).delete(id);
    }
}
