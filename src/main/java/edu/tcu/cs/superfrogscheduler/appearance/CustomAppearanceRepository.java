package edu.tcu.cs.superfrogscheduler.appearance;

import java.util.List;

public interface CustomAppearanceRepository {
    List<Appearance> searchByCriteria(AppearanceQuery query, Long uId);
}
