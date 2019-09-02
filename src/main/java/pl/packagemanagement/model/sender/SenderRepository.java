package pl.packagemanagement.model.sender;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.packagemanagement.model.sender.Sender;

@Repository
public interface SenderRepository extends JpaRepository<Sender, Long> {
}
