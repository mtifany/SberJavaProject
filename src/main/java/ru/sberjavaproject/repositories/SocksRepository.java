package ru.sberjavaproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sberjavaproject.models.Color;
import ru.sberjavaproject.models.Socks;

import java.util.List;
import java.util.Optional;

@Repository
public interface SocksRepository extends JpaRepository<Socks, Integer> {

    Optional<Socks> findSocksByCottonPartAndColor(Integer cottonPart, Color color);

    List<Socks> findSocksByColor(Color color);

    List<Socks> findSocksByCottonPartEquals (Integer cottonPart);
    List<Socks> findSocksByCottonPartGreaterThan(Integer cottonPart);
    List<Socks> findSocksByCottonPartLessThan(Integer cottonPart);

    List<Socks> findSocksByColorAndCottonPartEquals(Color color,Integer cottonPart);
    List<Socks> findSocksByColorAndCottonPartGreaterThan(Color color,Integer cottonPart);
    List<Socks> findSocksByColorAndCottonPartLessThan(Color color,Integer cottonPart);


}
