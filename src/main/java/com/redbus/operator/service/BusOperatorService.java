package com.redbus.operator.service;

import com.redbus.operator.payload.BusOperatorDto;
import org.springframework.web.bind.annotation.RequestBody;

public interface BusOperatorService {
    public  BusOperatorDto scheduleBus(@RequestBody BusOperatorDto busOperatorDto);
}
