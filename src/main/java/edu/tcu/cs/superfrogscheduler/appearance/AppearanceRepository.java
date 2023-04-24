package edu.tcu.cs.superfrogscheduler.appearance;

import edu.tcu.cs.superfrogscheduler.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppearanceRepository extends JpaRepository<Appearance, Long> {
    List<Appearance> findByRequestIdIn(List<Long> appearanceRequestIdList);

}
