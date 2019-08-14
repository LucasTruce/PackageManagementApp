package pl.packagemanagement.service;

import pl.packagemanagement.entity.Position;
import pl.packagemanagement.entity.User;

import java.util.List;
import java.util.Optional;

public interface PositionService {
    List<Position> findPositionByUser(User user);
    Position save(Position position);
    void delete(Position position);
    List<Position> findAll();
    Optional<Position> findById(Long id);



}
