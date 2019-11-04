package pl.packagemanagement.model.history;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.packagemanagement.model.pack.Package;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {
}
