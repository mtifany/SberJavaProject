package ru.sberjavaproject.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sberjavaproject.dto.SocksDto;
import ru.sberjavaproject.models.Color;
import ru.sberjavaproject.models.Socks;
import ru.sberjavaproject.models.SocksRequest;
import ru.sberjavaproject.repositories.ColorRepository;
import ru.sberjavaproject.repositories.SocksRepository;
import ru.sberjavaproject.services.SocksServiceImpl;
import ru.sberjavaproject.util.InvalidRequestException;
import ru.sberjavaproject.util.SocksNotFoundException;

import java.util.*;

import static org.mockito.Mockito.*;


public class SocksServiceImplTest {

    private final ColorRepository colorRepository;

    private final SocksRepository socksRepository;

    private final SocksServiceImpl socksService;

    private final List<SocksDto> socksDtoList = new ArrayList<>();

    public SocksServiceImplTest(){
        colorRepository = mock(ColorRepository.class);
        socksRepository = mock(SocksRepository.class);
        socksService = new SocksServiceImpl(socksRepository,colorRepository);
    }

    @BeforeEach
    public void createSocksDto(){
        Collections.addAll(socksDtoList,
                new SocksDto("red", 10, 10),
                new SocksDto("red", 20, 20),
                new SocksDto("black", 100, 100),
                new SocksDto("white", 50, 50),
                new SocksDto("red", 1000, 20));
    }

    @Test
    public void incomeTest() {

        Color color = new Color(0, socksDtoList.get(2).getColor());
        Socks socks = new Socks(0, socksDtoList.get(2).getQuantity(),
                color, socksDtoList.get(2).getCottonPart());

        when(colorRepository.findColorByColorName(color.getColorName()))
                .thenReturn(Optional.of(color));
        when(socksRepository.findSocksByCottonPartAndColor(socksDtoList.get(0).getCottonPart(), color))
                .thenReturn(Optional.of(socks));
         SocksDto outputDTO = socksService.income(socksDtoList.get(2));


        verify(colorRepository, times(1)).findColorByColorName(color.getColorName());
        verify(socksRepository, times(1)).findSocksByCottonPartAndColor(socksDtoList.get(2).getCottonPart(), color);
        verify(socksRepository, times(1)).save(socks);

        Assertions.assertEquals(socksDtoList.get(2).getColor(), outputDTO.getColor());
        Assertions.assertEquals(socksDtoList.get(2).getCottonPart(), outputDTO.getCottonPart());
        Assertions.assertEquals(socksDtoList.get(2).getQuantity(), (outputDTO.getQuantity()));
    }

    @Test
    public void outcomeTest() throws SocksNotFoundException, InvalidRequestException {
        Color color = new Color(0, socksDtoList.get(2).getColor());
        Socks socks = new Socks(0, socksDtoList.get(2).getQuantity(),
                color, socksDtoList.get(2).getCottonPart());

        when(colorRepository.findColorByColorName(color.getColorName()))
                .thenReturn(Optional.of(color));
        when(socksRepository.findSocksByCottonPartAndColor(socksDtoList.get(2).getCottonPart(), color))
                .thenReturn(Optional.of(socks));

        SocksDto socksDto = socksService.outcome(socksDtoList.get(2));

        verify(colorRepository, times(1)).findColorByColorName(color.getColorName());
        verify(socksRepository, times(1)).findSocksByCottonPartAndColor(socksDtoList.get(2).getCottonPart(), color);
        verify(socksRepository, times(1)).save(socks);

        socksDto.setQuantity(socksDto.getQuantity() + socksDtoList.get(2).getQuantity());
        Assertions.assertEquals(socksDtoList.get(2), socksDto);

    }

    @Test
    public void outcomeSocksNotFoundExeptionTest(){

        Assertions.assertThrows(SocksNotFoundException.class,() -> socksService.outcome(socksDtoList.get(2)));
    }

    @Test
    public void getSocksRequestTest(){
       Color color = new Color(0, socksDtoList.get(1).getColor());
       List<Socks> socksList = new ArrayList<>();
       socksList.add(new Socks(0, 10,color,55));
        socksList.add(new Socks(0, 25,color,60));
        socksList.add(new Socks(0, 35,color,70));

        SocksRequest socksRequest = new SocksRequest("red","moreThan",50);

        when(colorRepository.findColorByColorName(color.getColorName()))
                .thenReturn(Optional.of(color));
        when(socksRepository.findSocksByColorAndCottonPartGreaterThan(color,socksRequest.getCottonPart())).thenReturn(socksList);
        long result = socksService.getSocksRequest(socksRequest);

        verify(colorRepository, times(1)).findColorByColorName(color.getColorName());
        verify(socksRepository, times(1)).
                findSocksByColorAndCottonPartGreaterThan(color,socksRequest.getCottonPart());

        Assertions.assertEquals(70, result);
    }

    @Test
    public void InvalidRequestExceptionTest(){

        Assertions.assertThrows(InvalidRequestException.class,() -> socksService.findSocksByCottonPart(new SocksRequest("red", "lol", 11)));
    }





}
