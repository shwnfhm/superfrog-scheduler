package backend.java.edu.tcu.cs.superfrogscheduler.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
