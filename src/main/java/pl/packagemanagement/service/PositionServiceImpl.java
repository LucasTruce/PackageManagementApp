package pl.packagemanagement.service;

import org.springframework.stereotype.Service;
import pl.packagemanagement.entity.Position;
import pl.packagemanagement.entity.User;
import pl.packagemanagement.repository.PositionRepository;
import pl.packagemanagement.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PositionServiceImpl implements PositionService {

    private final PositionRepository positionRepository;
    private final UserRepository userRepository;

    public PositionServiceImpl(PositionRepository positionRepository, UserRepository userRepository) {
        this.positionRepository = positionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Position> findAll() {
        return positionRepository.findAll();
    }

    @Override
    public List<Position> findPositionByUser(User user) {
        return positionRepository.findByUsers(user);
    }

    @Override
    public Position save(Position position) {
        return positionRepository.save(position);
    }


    @Override
    public void delete(Position position) {
        List<User> users = userRepository.findByPosition(position);
        for(User user : users){
            user.setPosition(null);
        }
        positionRepository.delete(position);
    }


    @Override
    public Optional<Position> findById(Long id){
        return positionRepository.findById(id);
    }

    @Override
    public Position update(Position oldPosition, Position position) {
        if(position.getUsers() != null)
            oldPosition.setUsers(position.getUsers());
        if(position.getDescription() != null)
            oldPosition.setDescription(position.getDescription());
        if(position.getName() != null)
            oldPosition.setName(position.getName());

        return positionRepository.save(oldPosition);
    }


}
