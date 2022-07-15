package ru.sberjavaproject.services;

import org.springframework.validation.annotation.Validated;
import ru.sberjavaproject.dto.SocksDto;
import ru.sberjavaproject.models.SocksRequest;
import ru.sberjavaproject.util.InvalidRequestException;
import ru.sberjavaproject.util.SocksNotFoundException;

import javax.validation.Valid;

@Validated
public interface SocksService {

    SocksDto income(@Valid SocksDto socksDto);

    SocksDto outcome(@Valid SocksDto socksDto) throws SocksNotFoundException;

     long getSocksRequest (@Valid SocksRequest request) throws InvalidRequestException;
}
