package edu.tcu.cs.superfrogscheduler.appearance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppearanceRepository extends JpaRepository<Appearance, Long>, CustomAppearanceRepository {
    List<Appearance> findByRequestIdIn(List<Long> appearanceRequestIdList);

    List<Appearance> findByStatus(AppearanceStatus status);

}
