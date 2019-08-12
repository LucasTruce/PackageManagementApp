package pl.packagemanagement.service;

import pl.packagemanagement.entity.Position;
import pl.packagemanagement.entity.User;

import java.util.List;
import java.util.Optional;

public interface PositionService {
    List<Position> findPositionForUser(Long userId);
    Position save(Position position);
    void delete(Position position);
    List<Position> findAll();
    Position findById(Long id);

}
