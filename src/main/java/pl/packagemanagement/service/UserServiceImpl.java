package pl.packagemanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.packagemanagement.entity.Position;
import pl.packagemanagement.entity.User;
import pl.packagemanagement.repository.PositionRepository;
import pl.packagemanagement.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    PositionRepository positionRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PositionRepository positionRepository) {
        this.userRepository = userRepository;
        this.positionRepository = positionRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return userRepository.findByPasswordLogin(login);
    }


    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        List<Position> positions = positionRepository.findByUsers(user);
        for(Position temp : positions){
            temp.setUsers(null);
        }
        userRepository.delete(user);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }





}
