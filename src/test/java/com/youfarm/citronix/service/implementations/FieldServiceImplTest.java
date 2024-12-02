package com.youfarm.citronix.service.implementations;

import com.youfarm.citronix.common.config.FarmConfigProperties;
import com.youfarm.citronix.domain.entity.Farm;
import com.youfarm.citronix.domain.entity.Field;
import com.youfarm.citronix.domain.entity.User;
import com.youfarm.citronix.exception.UnAuthorizedException;
import com.youfarm.citronix.repository.FarmRepository;
import com.youfarm.citronix.repository.FieldRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FieldServiceImplTest {

    @Mock
    private FieldRepository fieldRepository;

    @Mock
    private FarmRepository farmRepository;

    @Mock
    private FarmConfigProperties constraints;

    @InjectMocks
    private FieldServiceImpl fieldService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testCreateFieldSuccessfully() {

         Long authId = 1L;
        Farm farm = Farm.builder()
                .id(1L)
                .manager(User.builder().id(authId).build())
                .surfaceArea(100.0)
                .fields(new ArrayList<>())
                .build();

        Field field = Field.builder()
                .farm(farm)
                .area(10.0)
                .build();

        when(farmRepository.findById(farm.getId())).thenReturn(Optional.of(farm));
        when(constraints.getFieldMinAreaHa()).thenReturn(0.1);
        when(constraints.getMaxFieldsInFarm()).thenReturn(5);
        when(constraints.getFieldFarmPercentage()).thenReturn(0.5);
        when(fieldRepository.save(field)).thenReturn(field);

        Field createdField = fieldService.create(field, authId);

        assertNotNull(createdField);
        verify(fieldRepository).save(field);
    }

    @Test
    void testCreateFieldWithInsufficientArea() {
        Field field = Field.builder()
                .area(0.05)
                .build();

        when(constraints.getFieldMinAreaHa()).thenReturn(0.1);

        assertThrows(UnAuthorizedException.class,
                () -> fieldService.create(field, 1L));
    }

    @Test
    void testCreateFieldExceedingMaxFieldsInFarm() {
        Long authId = 1L;
        Farm farm = Farm.builder()
                .id(1L)
                .manager(User.builder().id(authId).build())
                .surfaceArea(100.0)
                .fields(new ArrayList<>())
                .build();

        for (int i = 0; i < 5; i++) {
            farm.getFields().add(Field.builder().build());
        }

        Field field = Field.builder()
                .farm(farm)
                .area(10.0)
                .build();

        when(farmRepository.findById(farm.getId())).thenReturn(Optional.of(farm));
        when(constraints.getFieldMinAreaHa()).thenReturn(0.1);
        when(constraints.getMaxFieldsInFarm()).thenReturn(10);

        assertThrows(Throwable.class,
                () -> fieldService.create(field, authId));
    }


    @Test
    void testCreateFieldByUnauthorizedUser() {
        Long authId = 1L;
        Farm farm = Farm.builder()
                .id(1L)
                .manager(User.builder().id(2L).build())
                .surfaceArea(100.0)
                .fields(new ArrayList<>())
                .build();

        Field field = Field.builder()
                .farm(farm)
                .area(10.0)
                .build();

        when(farmRepository.findById(farm.getId())).thenReturn(Optional.of(farm));
        when(constraints.getFieldMinAreaHa()).thenReturn(0.1);

        assertThrows(UnAuthorizedException.class,
                () -> fieldService.create(field, authId));
    }

    @Test
    void testCreateFieldExceedingFarmArea() {

        Long authId = 1L;
        Farm farm = Farm.builder()
                .id(1L)
                .manager(User.builder().id(authId).build())
                .surfaceArea(100.0)
                .fields(new ArrayList<>())
                .build();

        Field field = Field.builder()
                .farm(farm)
                .area(150.0)
                .build();

        when(farmRepository.findById(farm.getId())).thenReturn(Optional.of(farm));
        when(constraints.getFieldMinAreaHa()).thenReturn(0.1);
        when(constraints.getMaxFieldsInFarm()).thenReturn(10);
        when(constraints.getFieldFarmPercentage()).thenReturn(0.5);

        assertThrows(Throwable.class,
                () -> fieldService.create(field, authId));
    }
}