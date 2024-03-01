package com.redbus.operator.service;

import com.redbus.operator.entity.BusOperator;
import com.redbus.operator.entity.TicketCost;
import com.redbus.operator.payload.BusOperatorDto;
import com.redbus.operator.repository.BusOperatorRepository;
import com.redbus.operator.repository.TicketCostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BusOperatorServiceImpl implements BusOperatorService{


    private BusOperatorRepository busOperatorRepository;

    private TicketCostRepository ticketCostRepository;
    private ModelMapper modelMapper;
    public BusOperatorServiceImpl(BusOperatorRepository busOperatorRepository,TicketCostRepository ticketCostRepository,ModelMapper modelMapper) {
        this.busOperatorRepository = busOperatorRepository;
        this.ticketCostRepository = ticketCostRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public BusOperatorDto scheduleBus(BusOperatorDto busOperatorDto) {
        // Map DTO to entity
        BusOperator busOperator = mapToEntity(busOperatorDto);

        // Create a new TicketCost entity
        TicketCost ticketCost = new TicketCost();
        ticketCost.setTicketId(busOperatorDto.getTicketCost().getTicketId());
        ticketCost.setCost(busOperatorDto.getTicketCost().getCost());
        ticketCost.setCode(busOperatorDto.getTicketCost().getCode());
        ticketCost.setDiscountAmount(busOperatorDto.getTicketCost().getDiscountAmount());

        TicketCost savedTicketCost = ticketCostRepository.save(ticketCost);

        // Associate the saved TicketCost with the BusOperator
        busOperator.setTicketCost(savedTicketCost);


        // Generate a new busId
        String busId = UUID.randomUUID().toString();
        busOperator.setBusId(busId);

        // Save BusOperator entity
        BusOperator savedBusOperator = busOperatorRepository.save(busOperator);

        // Map the saved entity back to DTO
        BusOperatorDto savedBusOperatorDto = mapToDto(savedBusOperator);

        return savedBusOperatorDto;
    }

// The rest of your existing code (mapToEntity, mapToDto) remains the same.

    BusOperator mapToEntity(BusOperatorDto busOperatorDto){
       BusOperator busOperator =  modelMapper.map(busOperatorDto,BusOperator.class);
       return busOperator;
    }
    BusOperatorDto mapToDto(BusOperator busOperator){
        BusOperatorDto busOperatorDto = modelMapper.map(busOperator, BusOperatorDto.class);
       return busOperatorDto;
    }

}
