package edu.tcu.cs.superfrogscheduler.user;

import edu.tcu.cs.superfrogscheduler.appearance.AppearanceStatus;
import edu.tcu.cs.superfrogscheduler.system.exception.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    public User findById(Long userId) {
        return this.userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("user", userId));
    }

    public User save(User newUser){
        newUser.setPassword(this.passwordEncoder.encode(newUser.getPassword()));
        return this.userRepository.save(newUser);
    }

    /**
     * We are not using this update to change user password.
     *
     * @param userId
     * @param update
     * @return
     */
    public User update(Long userId, User update) {
        return this.userRepository.findById(userId)
                .map(oldUser -> {
                    oldUser.setEmail(update.getEmail());
                    oldUser.setActive(update.isActive());
                    oldUser.setRoles(update.getRoles());
                    oldUser.setFirstName(update.getFirstName());
                    oldUser.setLastName(update.getLastName());
                    oldUser.setAddress(update.getAddress());
                    oldUser.setPhoneNumber(update.getPhoneNumber());
                    oldUser.setInternational(update.isInternational());
                    oldUser.setPaymentPreference(update.getPaymentPreference());
                    return this.userRepository.save(oldUser);
                })
                .orElseThrow(() -> new ObjectNotFoundException("user", userId));
    }

    public User deactivate(Long userId){
        return this.userRepository.findById(userId)
                .map(oldUser -> {
                    oldUser.setActive(false);
                    return this.userRepository.save(oldUser);
                })
                .orElseThrow(() -> new ObjectNotFoundException("user", userId));
    }

    public User activate(Long userId){
        return this.userRepository.findById(userId)
                .map(oldUser -> {
                    oldUser.setActive(true);
                    return this.userRepository.save(oldUser);
                })
                .orElseThrow(() -> new ObjectNotFoundException("user", userId));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.userRepository.findByEmail(email) // First, we need to find this user from database.
                .map(user -> new MyUserPrincipal(user)) // If found, wrap the returned user instance in a MyUserPrincipal instance.
                .orElseThrow(() -> new UsernameNotFoundException("email " + email + " is not found.")); // Otherwise, throw an exception.
    }
}
