package backend.java.edu.tcu.cs.superfrogscheduler.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
