package com.projects.plane.service.impl;

import com.projects.plane.dto.airplane.AirplaneRequestDto;
import com.projects.plane.dto.airplane.AirplaneResponseDto;
import com.projects.plane.exception.ResourceNotFoundException;
import com.projects.plane.mapper.AirplaneMapper;
import com.projects.plane.model.Airplane;
import com.projects.plane.repository.AirplaneRepository;
import com.projects.plane.service.AirplaneService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AirplaneServiceImpl implements AirplaneService {

    private AirplaneRepository airplaneRepository;

    @Override
    public AirplaneResponseDto createAirplane(AirplaneRequestDto airplaneRequestDto) {
        Airplane airplane = AirplaneMapper.mapToAirplane(airplaneRequestDto);
        Airplane savedAirplane = airplaneRepository.save(airplane);

        return AirplaneMapper.mapToAirplaneResponseDto(savedAirplane);
    }

    @Override
    public AirplaneResponseDto updateAirplane(UUID id, AirplaneRequestDto airplaneRequestDto) {
        Airplane existingAirplane = airplaneRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Airplane not found with ID: " + id));

        existingAirplane.setManufacturer(airplaneRequestDto.getManufacturer());
        existingAirplane.setModel(airplaneRequestDto.getModel());
        existingAirplane.setSeatingCapacity(airplaneRequestDto.getSeatingCapacity());
        existingAirplane.setMaximumTakeoffWeight(airplaneRequestDto.getMaximumTakeoffWeight());
        existingAirplane.setRange(airplaneRequestDto.getRange());

        Airplane updatedAirplane = airplaneRepository.save(existingAirplane);

        return AirplaneMapper.mapToAirplaneResponseDto(updatedAirplane);
    }

    @Override
    public AirplaneResponseDto getAirplaneById(UUID id) {
        Airplane airplane = airplaneRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Airplane not found with ID: " + id));
        return AirplaneMapper.mapToAirplaneResponseDto(airplane);
    }

    @Override
    public List<AirplaneResponseDto> getAllAirplanes() {
        return airplaneRepository
                .findAll()
                .stream()
                .map(AirplaneMapper::mapToAirplaneResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAirplane(UUID id) {
        if (!airplaneRepository.existsById(id)) {
            throw new ResourceNotFoundException("Airplane not found with ID: " + id);
        }
        airplaneRepository.deleteById(id);
    }

}
