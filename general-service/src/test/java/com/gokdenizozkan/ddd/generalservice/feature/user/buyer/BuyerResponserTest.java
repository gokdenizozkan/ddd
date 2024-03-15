package com.gokdenizozkan.ddd.generalservice.feature.user.buyer;

import com.gokdenizozkan.ddd.generalservice.feature.user.buyer.dto.BuyerDtoMapper;
import com.gokdenizozkan.ddd.generalservice.feature.user.buyer.dto.mapper.BuyerToBuyerDetails;
import com.gokdenizozkan.ddd.generalservice.feature.user.buyer.dto.mapper.BuyerToBuyerDetailsWithAddresses;
import com.gokdenizozkan.ddd.generalservice.feature.user.buyer.dto.request.BuyerSaveRequest;
import com.gokdenizozkan.ddd.generalservice.feature.user.buyer.dto.response.BuyerDetails;
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

class BuyerResponserTest {
    private BuyerResponser underTest;
    private BuyerService service;
    private BuyerDtoMapper dtoMapper;
    private BuyerToBuyerDetails buyerToBuyerDetails;
    private BuyerToBuyerDetailsWithAddresses buyerToBuyerDetailsWithAddresses;

    @BeforeEach
    void setUp() {
        service = mock(BuyerService.class);
        buyerToBuyerDetails = mock(BuyerToBuyerDetails.class);
        buyerToBuyerDetailsWithAddresses = mock(BuyerToBuyerDetailsWithAddresses.class);
        dtoMapper = new BuyerDtoMapper(buyerToBuyerDetails, buyerToBuyerDetailsWithAddresses);
        underTest = new BuyerResponser(service, dtoMapper);
    }

    @AfterEach
    void tearDown() {
        reset(service, buyerToBuyerDetails, buyerToBuyerDetailsWithAddresses);
    }

    @Test
    void whenBuyersExist_thenReturnBuyerDetailsListWrapped() {
        var buyer = new Buyer();
        var buyerList = Collections.singletonList(buyer);
        var buyerDto = mock(BuyerDetails.class);

        when(service.findAll()).thenReturn(buyerList);
        when(dtoMapper.toDetails.apply(buyer)).thenReturn(buyerDto);

        var response = underTest.findAll();

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().data().size());

        verify(service, times(1)).findAll();
        verify(dtoMapper.toDetails, times(1)).apply(any(Buyer.class));
    }

    @Test
    void whenNoBuyerExists_thenReturnEmptyListWrapped() {
        when(service.findAll()).thenReturn(Collections.emptyList());

        var response = underTest.findAll();

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().data().size());

        verify(service, times(1)).findAll();
        verify(dtoMapper.toDetails, times(0)).apply(any(Buyer.class));
    }

    @Test
    void whenBuyerExistsWithId_thenReturnBuyerDetailsWrapped() {
        var buyer = new Buyer();
        var buyerDto = mock(BuyerDetails.class);

        when(service.findById(any(Long.class))).thenReturn(buyer);
        when(dtoMapper.toDetails.apply(any(Buyer.class))).thenReturn(buyerDto);

        var response = underTest.findById(anyLong());

        assertNotNull(response.getBody());
        assertNotNull(response.getBody().data());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(service, times(1)).findById(anyLong());
        verify(dtoMapper.toDetails, times(1)).apply(any(Buyer.class));
    }

    @Test
    void whenSaveWithValidRequest_thenReturnSavedBuyerAsBuyerDetailsWrapped() {
        var request = mock(BuyerSaveRequest.class);
        var savedBuyer = mock(Buyer.class);
        var buyerDetails = mock(BuyerDetails.class);

        when(service.save(request)).thenReturn(savedBuyer);
        when(dtoMapper.toDetails.apply(savedBuyer)).thenReturn(buyerDetails);

        var response = underTest.save(request);

        assertNotNull(response.getBody());
        assertNotNull(response.getBody().data());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        verify(service, times(1)).save(request);
        verify(dtoMapper.toDetails, times(1)).apply(savedBuyer);
    }

    @Test
    void whenUpdateWithValidRequest_thenReturnUpdatedBuyerAsBuyerDetailsWrapped() {
        var id = 1L;
        var request = mock(BuyerSaveRequest.class);
        var updatedBuyer = mock(Buyer.class);
        var buyerDetails = mock(BuyerDetails.class);

        when(service.update(id, request)).thenReturn(updatedBuyer);
        when(dtoMapper.toDetails.apply(updatedBuyer)).thenReturn(buyerDetails);

        var response = underTest.update(id, request);

        assertNotNull(response.getBody());
        assertNotNull(response.getBody().data());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(service, times(1)).update(id, request);
        verify(dtoMapper.toDetails, times(1)).apply(updatedBuyer);
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