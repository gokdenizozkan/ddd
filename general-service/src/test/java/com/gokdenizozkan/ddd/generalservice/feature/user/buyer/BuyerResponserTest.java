package com.gokdenizozkan.ddd.generalservice.feature.user.buyer;

import com.gokdenizozkan.ddd.generalservice.config.response.Structured;
import com.gokdenizozkan.ddd.generalservice.feature.user.buyer.dto.BuyerDtoMapper;
import com.gokdenizozkan.ddd.generalservice.feature.user.buyer.dto.request.BuyerSaveRequest;
import com.gokdenizozkan.ddd.generalservice.feature.user.buyer.dto.response.BuyerDetails;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BuyerResponserTest {

    @Mock
    private BuyerService service;

    @Mock
    private BuyerDtoMapper dtoMapper;

    @InjectMocks
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
    void whenBuyerDetailsExist_thenReturnBuyerDetailsList() {
        // Arrange
        BuyerDetails buyerDetails = mock(BuyerDetails.class);
        List<BuyerDetails> buyerDetailsList = Collections.singletonList(buyerDetails);
        when(service.findAll()).thenReturn(Collections.singletonList(mock(Buyer.class)));
        when(dtoMapper.toDetails.apply(any(Buyer.class))).thenReturn(buyerDetails);

        // Act
        ResponseEntity<Structured<List<BuyerDetails>>> response = responser.findAll();

        // Assert
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().data().size());
    }

    @Test
    void whenBuyerDetailsNotExist_thenReturnEmptyList() {
        // Arrange
        when(service.findAll()).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<Structured<List<BuyerDetails>>> response = responser.findAll();

        // Assert
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().data().size());
    }

    // find by id
    @Test
    void whenBuyerDetailsExist_thenReturnBuyerDetails() {
        // Arrange
        BuyerDetails buyerDetails = mock(BuyerDetails.class);
        when(service.findById(any(Long.class))).thenReturn(mock(Buyer.class));
        when(dtoMapper.toDetails.apply(any(Buyer.class))).thenReturn(buyerDetails);

        // Act
        ResponseEntity<Structured<BuyerDetails>> response = responser.findById(1L);

        // Assert
        assertNotNull(response.getBody());
    }

    @Test
    void whenBuyerDetailsNotExist_thenReturnEmpty() {
        // Arrange
        when(service.findById(any(Long.class))).thenReturn(null);

        // Act
        ResponseEntity<Structured<BuyerDetails>> response = responser.findById(1L);

        // Assert
        assertNull(response.getBody());
    }

    // save
    @Test
    void whenSaveWithValidRequest_thenReturnSavedBuyerDetails() {
        // Arrange
        BuyerDetails buyerDetails = mock(BuyerDetails.class);
        when(service.save(any(BuyerSaveRequest.class))).thenReturn(mock(Buyer.class));
        when(dtoMapper.toDetails.apply(any(Buyer.class))).thenReturn(buyerDetails);

        // Act
        ResponseEntity<Structured<BuyerDetails>> response = responser.save(mock(BuyerSaveRequest.class));

        // Assert
        assertNotNull(response.getBody());
    }

    // update
    @Test
    void whenUpdateWithValidRequest_thenReturnUpdatedBuyerDetails() {
        // Arrange
        BuyerDetails buyerDetails = mock(BuyerDetails.class);
        when(service.update(any(Long.class), any(BuyerSaveRequest.class))).thenReturn(mock(Buyer.class));
        when(dtoMapper.toDetails.apply(any(Buyer.class))).thenReturn(buyerDetails);

        // Act
        ResponseEntity<Structured<BuyerDetails>> response = responser.update(1L, mock(BuyerSaveRequest.class));

        // Assert
        assertNotNull(response.getBody());
    }

    // delete
    @Test
    void whenDeleteWithValidId_thenReturnNoContent() {
        // Act
        ResponseEntity<Structured<Object>> response = responser.delete(1L);

        // Assert
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(service, times(1)).delete(1L);
    }
}