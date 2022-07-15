package ru.sberjavaproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import ru.sberjavaproject.dto.SocksDto;
import ru.sberjavaproject.models.SocksRequest;
import ru.sberjavaproject.services.SocksServiceImpl;
import ru.sberjavaproject.util.ErrorsUtil;
import ru.sberjavaproject.util.InvalidRequestException;
import ru.sberjavaproject.util.SocksNotFoundException;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;


@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
@RestController
@RequestMapping("/api/socks")
public class SocksController {

    private final SocksServiceImpl socksService;

    @Autowired
    public SocksController(SocksServiceImpl socksService) {
        this.socksService = socksService;
    }

    @GetMapping(value = "/helloworld")
    public ResponseEntity<String> helloWorld() {
        return new ResponseEntity<>("Hello, World!", HttpStatus.OK);
    }

    @PostMapping(value = "/income")
    public ResponseEntity<HttpStatus> income(@RequestBody @Valid SocksDto socksDto,
                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ErrorsUtil.ErrorReturning(bindingResult);
        }
        socksService.income(socksDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping(value = "/outcome")
    public ResponseEntity<HttpStatus> outcome(@RequestBody @Valid SocksDto socksDto,
                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ErrorsUtil.ErrorReturning(bindingResult);
        }
        socksService.outcome(socksDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<String> getSocks(
            @RequestParam(required = false) String color,
            @RequestParam(required = false) String operation,
            @RequestParam(required = false) String cottonPart
    ) {
        int cottonPartValue;
        try {
            cottonPartValue = Integer.parseInt(cottonPart);
        } catch (NumberFormatException e) {
            throw new InvalidRequestException("Wrong cottonPart value");

        }
        SocksRequest request = new SocksRequest(color, operation, cottonPartValue);
        try {
            long result = socksService.getSocksRequest(request);
            return new ResponseEntity<>(Long.toString(result), HttpStatus.OK);
        } catch (ConstraintViolationException e) {
            throw new InvalidRequestException("Cotton part can only be between 0 and 100");
        }

    }

    @ExceptionHandler
    private ResponseEntity<Object> handleInvalidDataExeption(InvalidRequestException e, WebRequest request) {
        return new ResponseEntity<>("Validation Failed. " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public final ResponseEntity<Object> handleHttpMessageNotReadableException(Exception e, WebRequest request) {
        return new ResponseEntity<>("Validation Failed. Not readable request.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<Object> handleSocksNotFoundException(SocksNotFoundException e, WebRequest request) {
        return new ResponseEntity<>("Can't find " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception e, WebRequest request) {

        return new ResponseEntity<>("Server Error" + e.getClass().getName() + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
