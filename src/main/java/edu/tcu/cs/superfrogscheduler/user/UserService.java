package edu.tcu.cs.superfrogscheduler.user;

import edu.tcu.cs.superfrogscheduler.system.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final IdWorker idWorker;

    public UserService(UserRepository userRepository, IdWorker idWorker) {

        this.userRepository = userRepository;
        this.idWorker = idWorker;

    }

    public void updateById(Long userID, String email, String firstName, String lastName, String phoneNumber) {
        User user = userRepository.getOne(userID);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhoneNumber(phoneNumber);
        userRepository.save(user);
    }
}
