package backend.java.edu.tcu.cs.superfrogscheduler.appearance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppearanceRepository extends JpaRepository<Appearance, Long> {
    public List<Appearance> findAllByStatus(String status);
    public List<Appearance> findAllByReqUserEmail(String email);
}
