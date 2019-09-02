package pl.packagemanagement.model.content;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.packagemanagement.model.content.Content;

@Repository
public interface ContentRepository extends JpaRepository<Content,Long> {
}
