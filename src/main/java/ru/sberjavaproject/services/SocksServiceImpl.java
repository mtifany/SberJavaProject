package ru.sberjavaproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sberjavaproject.dto.SocksDto;
import ru.sberjavaproject.models.Color;
import ru.sberjavaproject.models.Socks;
import ru.sberjavaproject.models.SocksRequest;
import ru.sberjavaproject.repositories.ColorRepository;
import ru.sberjavaproject.repositories.SocksRepository;
import ru.sberjavaproject.util.InvalidRequestException;
import ru.sberjavaproject.util.SocksNotFoundException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class SocksServiceImpl implements SocksService {

    private final SocksRepository socksRepository;
    private final ColorRepository colorRepository;

    @Autowired
    public SocksServiceImpl(SocksRepository socksRepository, ColorRepository colorRepository) {
        this.socksRepository = socksRepository;
        this.colorRepository = colorRepository;
    }

   @Transactional
   @Override
    public SocksDto income(@Valid SocksDto socksDto){
        Optional<Color> color = colorRepository.findColorByColorName(socksDto.getColor());
        if(color.isEmpty()){
            Socks socks = new Socks();
            Color newColor = new Color();
            newColor.setColorName(socksDto.getColor());
           colorRepository.save(newColor);

           socks.setColor(newColor);
            socks.setCottonPart(socksDto.getCottonPart());
            socks.setQuantity(socksDto.getQuantity());
            socksRepository.save(socks);
            return new SocksDto(socks.getColor().getColorName(),socks.getCottonPart(),socks.getQuantity());
        }
        Optional<Socks> socks = socksRepository.findSocksByCottonPartAndColor(socksDto.getCottonPart(), color.get());
        if(socks.isPresent()){
            socks.get().setQuantity(socks.get().getQuantity() + socksDto.getQuantity());
            socksRepository.save(socks.get());
        return new SocksDto(socks.get().getColor().getColorName(),socks.get().getCottonPart(),socks.get().getQuantity());
        }
        Socks newSocks = new Socks(0, socksDto.getQuantity(), color.get(), socksDto.getCottonPart());
        socksRepository.save(newSocks);
        return new SocksDto(newSocks.getColor().getColorName(),newSocks.getCottonPart(),newSocks.getQuantity());
    }

    @Transactional
    @Override
    public SocksDto outcome(@Valid SocksDto socksDto){
        Optional<Color> color =colorRepository.findColorByColorName(socksDto.getColor());
        if(color.isEmpty()){
            throw new SocksNotFoundException("color doesnt exist");
        }
        Optional<Socks> socks = socksRepository.findSocksByCottonPartAndColor(socksDto.getCottonPart(),
                color.get());
        if(socks.isEmpty()){
            throw new SocksNotFoundException("socks with this Cottonpart doesnt exist");
        }
        if (socks.get().getQuantity() - socksDto.getQuantity() < 0){
            throw new SocksNotFoundException("There are not enough socks, current quantity is " + socks.get().getQuantity());
        }
        socks.get().setQuantity(socks.get().getQuantity() - socksDto.getQuantity());
        socksRepository.save(socks.get());
        return new SocksDto(socks.get().getColor().getColorName(),socks.get().getCottonPart(),socks.get().getQuantity());
    }

    @Override
    public long getSocksRequest (@Valid SocksRequest request){
        //1.color + cottonPart

        if(request.getColor() != null && request.getCottonPart() != null && request.getOperation() != null){
            Optional<Color> color = colorRepository.findColorByColorName(request.getColor());
            if (color.isEmpty()) {
                return 0;
            }
        return counter(findSocksByColorAndCottonPart(color.get(),request));
        }

        //2. no color + cottonPart

        if (request.getCottonPart() != null){
        if (request.getOperation() == null){
            throw  new InvalidRequestException("Wrong operation parameter!");
        }
        return counter(findSocksByCottonPart(request));
        }

        //3. color + no cottonPart

        if(request.getColor() != null){
            Optional<Color> color = colorRepository.findColorByColorName(request.getColor());
            if (color.isEmpty()) {
                return 0;
            }
            return counter(socksRepository.findSocksByColor(color.get()));
        }

        //4. no color +  no cottonPart

        List<Socks> result = socksRepository.findAll();
        return counter(result);
    }

    public List<Socks> findSocksByColorAndCottonPart(Color color,SocksRequest request){
        switch (request.getOperation()){
            case "equals" -> {
            return socksRepository.findSocksByColorAndCottonPartEquals(color, request.getCottonPart());
            }
            case "moreThan" -> {
                return socksRepository.findSocksByColorAndCottonPartGreaterThan (color, request.getCottonPart());
            }
            case "lessThan" -> {
                return socksRepository.findSocksByColorAndCottonPartLessThan(color, request.getCottonPart());
            }
            default -> throw new InvalidRequestException("Wrong operation parameter!");
        }
    }

    public List<Socks> findSocksByCottonPart(SocksRequest request){
        switch (request.getOperation()){
            case "equals" -> {
                return socksRepository.findSocksByCottonPartEquals(request.getCottonPart());
            }
            case "moreThan" -> {
                return socksRepository.findSocksByCottonPartGreaterThan (request.getCottonPart());
            }
            case "lessThan" -> {
                return socksRepository.findSocksByCottonPartLessThan(request.getCottonPart());
            }
            default -> throw new InvalidRequestException("Wrong operation parameter!");
        }
    }

    public long counter(List<Socks> socksList){
return socksList.stream().mapToInt(Socks::getQuantity).sum();
    }



}
