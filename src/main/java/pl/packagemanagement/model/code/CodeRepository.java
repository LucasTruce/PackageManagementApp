package pl.packagemanagement.model.code;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.packagemanagement.model.code.Code;

@Repository
public interface CodeRepository extends JpaRepository<Code, Long> {

}
