package edu.tcu.cs.superfrogscheduler.user;

import edu.tcu.cs.superfrogscheduler.appearance.Appearance;
import edu.tcu.cs.superfrogscheduler.appearance.AppearanceRepository;
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

    private final AppearanceRepository appearanceRepository;

    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, AppearanceRepository appearanceRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.appearanceRepository = appearanceRepository;
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
                    if(update.getPassword() != "") {
                        oldUser.setPassword(this.passwordEncoder.encode(update.getPassword()));
                    }
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

    public Appearance assign(Long requestId, Long userId){
        Appearance appearanceToBeAssigned = this.appearanceRepository.findById(requestId)
                .orElseThrow(() -> new ObjectNotFoundException("appearance", requestId));

        User assignee = this.userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("user", userId));

        if (appearanceToBeAssigned.getAssignedSuperFrog() != null) {
            appearanceToBeAssigned.getAssignedSuperFrog().removeAssignedAppearance(appearanceToBeAssigned);
        }

        appearanceToBeAssigned.setStatus(AppearanceStatus.ASSIGNED);
        assignee.addAssignedAppearance(appearanceToBeAssigned);
        return this.appearanceRepository.save(appearanceToBeAssigned);
    }

    public Appearance unassign(Long requestId){
        Appearance appearanceToBeUnassigned = this.appearanceRepository.findById(requestId)
                .orElseThrow(() -> new ObjectNotFoundException("appearance", requestId));
        if(appearanceToBeUnassigned.getStatus() == AppearanceStatus.ASSIGNED) {
            appearanceToBeUnassigned.setStatus(AppearanceStatus.APPROVED);
        }
        appearanceToBeUnassigned.getAssignedSuperFrog().removeAssignedAppearance(appearanceToBeUnassigned);
        return this.appearanceRepository.save(appearanceToBeUnassigned);
    }

    public Appearance completeAppearance(Long requestId){
        Appearance appearanceToBeCompleted = this.appearanceRepository.findById(requestId)
                .orElseThrow(() -> new ObjectNotFoundException("appearance", requestId));
        appearanceToBeCompleted.getAssignedSuperFrog().addCompletedAppearance(appearanceToBeCompleted);
        return this.appearanceRepository.save(appearanceToBeCompleted);
    }

    public List<User> searchUsers(UserQuery query){
        return this.userRepository.searchByCriteria(query);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.userRepository.findByEmail(email) // First, we need to find this user from database.
                .map(user -> new MyUserPrincipal(user)) // If found, wrap the returned user instance in a MyUserPrincipal instance.
                .orElseThrow(() -> new UsernameNotFoundException("email " + email + " is not found.")); // Otherwise, throw an exception.
    }
}
