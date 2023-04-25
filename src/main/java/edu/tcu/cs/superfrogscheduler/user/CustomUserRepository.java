package edu.tcu.cs.superfrogscheduler.user;

import java.util.List;

public interface CustomUserRepository {

    List<User> searchByCriteria(UserQuery query);
}
