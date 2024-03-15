package com.gokdenizozkan.ddd.generalservice.feature.user.buyer;

import com.gokdenizozkan.ddd.generalservice.config.Specifications;
import com.gokdenizozkan.ddd.generalservice.config.exception.ResourceNotActiveException;
import com.gokdenizozkan.ddd.generalservice.config.exception.ResourceNotFoundWithIdException;
import com.gokdenizozkan.ddd.generalservice.core.auditableentity.AuditableEntity;
import com.gokdenizozkan.ddd.generalservice.core.dtoprojection.ActiveDetermingFields;
import com.gokdenizozkan.ddd.generalservice.feature.user.buyer.dto.BuyerEntityMapper;
import com.gokdenizozkan.ddd.generalservice.feature.user.buyer.dto.mapper.BuyerSaveRequestToBuyer;
import com.gokdenizozkan.ddd.generalservice.feature.user.buyer.dto.request.BuyerSaveRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BuyerServiceActivesTest {
    @InjectMocks
    private BuyerServiceActives underTest;

    @Mock
    private BuyerRepository repository;
    @Mock
    private BuyerSaveRequestToBuyer buyerSaveRequestToBuyer;
    @Mock
    private BuyerEntityMapper entityMapper;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        entityMapper = new BuyerEntityMapper(buyerSaveRequestToBuyer);
        underTest = new BuyerServiceActives(repository, entityMapper);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    // find all
    @Test
    void whenBuyersExist_thenReturnActiveBuyersList() {
        // Arrange
        var buyer = new Buyer();
        buyer.setEnabled(true);
        buyer.setDeleted(false);

        var specification = Specifications.isActive(Buyer.class);

        when(repository.findAll(specification)).thenReturn(List.of(buyer));

        // Act
        var foundBuyers = underTest.findAll();

        // Assert
        assertNotNull(foundBuyers);
        assertEquals(1, foundBuyers.size());
        assertTrue(foundBuyers.stream().allMatch(AuditableEntity::getEnabled));
        assertTrue(foundBuyers.stream().noneMatch(AuditableEntity::getDeleted));
        verify(repository, times(1)).findAll(specification);
    }

    @Test
    void whenBuyersAreNotPresent_thenReturnEmptyList() {
        // Arrange
        var specification = Specifications.isActive(Buyer.class);
        when(repository.findAll(specification)).thenReturn(Collections.emptyList());

        // Act
        var foundBuyers = underTest.findAll();

        // Assert
        assertNotNull(foundBuyers);
        assertEquals(0, foundBuyers.size());
        assertTrue(foundBuyers.stream().allMatch(AuditableEntity::getEnabled));
        assertTrue(foundBuyers.stream().noneMatch(AuditableEntity::getDeleted));
        verify(repository, times(1)).findAll(specification);
    }

    // find by id
    @Test
    void whenBuyerExistsAndActive_thenReturnBuyer() {
        // Arrange
        var buyer = new Buyer();
        buyer.setId(1L);
        buyer.setEnabled(true);
        buyer.setDeleted(false);

        var specification = Specifications.isActive(Buyer.class);
        when(repository.existsById(any(Long.class))).thenReturn(true);
        when(repository.findById(specification, buyer.getId())).thenReturn(java.util.Optional.of(buyer));

        // Act
        var foundBuyer = underTest.findById(1L);

        // Assert
        assertNotNull(foundBuyer);
        assertTrue(foundBuyer.getEnabled());
        assertFalse(foundBuyer.getDeleted());
        verify(repository, times(1)).existsById(buyer.getId());
        verify(repository, times(1)).findById(specification, buyer.getId());
    }

    @Test
    void whenBuyerExistsAndNotActive_thenThrowResourceNotActiveException() {
        // Arrange
        var buyer = new Buyer();
        buyer.setId(1L);
        buyer.setEnabled(false);
        buyer.setDeleted(true);

        var specification = Specifications.isActive(Buyer.class);
        when(repository.existsById(any(Long.class))).thenReturn(true);
        when(repository.findById(specification, buyer.getId())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotActiveException.class, () -> underTest.findById(1L));
        verify(repository, times(1)).findById(specification, buyer.getId());
    }

    // save
    @Test
    void whenSaveWithValidRequest_thenReturnSavedBuyer() {
        // Arrange
        var saveRequest = mock(BuyerSaveRequest.class);
        var buyer = mock(Buyer.class);

        var entityMapper = new BuyerEntityMapper(buyerSaveRequestToBuyer);
        when(entityMapper.fromSaveRequest.apply(saveRequest)).thenReturn(buyer);
        when(repository.save(buyer)).thenReturn(buyer);

        // Act
        var savedBuyer = underTest.save(saveRequest);

        // Assert
        assertNotNull(savedBuyer);
        verify(repository, times(1)).save(buyer);
        verify(entityMapper.fromSaveRequest, times(1)).apply(saveRequest);
    }

    // update
    @Test
    void whenUpdateWithValidRequest_thenReturnUpdatedBuyer() {
        // Arrange
        var id = 1L;
        var saveRequest = mock(BuyerSaveRequest.class);
        var buyer = mock(Buyer.class);

        when(repository.existsById(id)).thenReturn(true);
        when(repository.save(buyer)).thenReturn(buyer);

        when(repository.findActiveDetermingFields(id)).thenReturn(Optional.of(new ActiveDetermingFields(false, true)));
        when(entityMapper.fromSaveRequest.apply(saveRequest)).thenReturn(buyer);

        // Act
        var updatedBuyer = underTest.update(id, saveRequest);

        // Assert
        assertNotNull(updatedBuyer);
        verify(repository, times(1)).existsById(id);
        verify(repository, times(1)).save(buyer);
    }

    @Test
    void whenUpdateWithInvalidId_thenThrowResourceNotFoundWithIdException() {
        // Arrange
        var id = 1L;
        var saveRequest = mock(BuyerSaveRequest.class);

        when(repository.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundWithIdException.class, () -> underTest.update(id, saveRequest));
        verify(repository, times(1)).existsById(id);
    }

    // delete
    @Test
    void whenDeleteWithValidId_thenSoftDeleteBuyer() {
        // Arrange
        var id = 1L;
        var buyer = mock(Buyer.class);

        when(repository.findById(id)).thenReturn(Optional.of(buyer));
        when(repository.save(buyer)).thenReturn(buyer);

        // Act
        underTest.delete(id);

        // Assert
        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).save(buyer);
    }
}