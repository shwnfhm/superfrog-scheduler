package backend.java.edu.tcu.cs.superfrogscheduler.appearance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppearanceRepository extends JpaRepository<Appearance, Long> {
}
