package pl.packagemanagement.model.recipient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.packagemanagement.model.recipient.Recipient;

@Repository
public interface ReicpientRepository extends JpaRepository<Recipient, Long> {
}
