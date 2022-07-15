package ru.sberjavaproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sberjavaproject.models.Color;

import java.util.Optional;

@Repository
public interface ColorRepository extends JpaRepository<Color, Integer> {

    Optional<Color> findColorByColorName(String colorName);


}
