package edu.tcu.cs.superfrogscheduler.appearance;

import edu.tcu.cs.superfrogscheduler.system.exception.ObjectNotFoundException;
import edu.tcu.cs.superfrogscheduler.user.User;
import edu.tcu.cs.superfrogscheduler.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AppearanceService {

    private final AppearanceRepository appearanceRepository;

    private final UserRepository userRepository;

    public AppearanceService(AppearanceRepository appearanceRepository, UserRepository userRepository) {
        this.appearanceRepository = appearanceRepository;
        this.userRepository = userRepository;
    }

    public Appearance save(Appearance newAppearance){
        return this.appearanceRepository.save(newAppearance);
    }

    public List<Appearance> findAll() {
        return this.appearanceRepository.findAll();
    }

    public Appearance findById(Long requestId) {
        return this.appearanceRepository.findById(requestId)
                .orElseThrow(() -> new ObjectNotFoundException("appearance", requestId));
    }

    public Appearance update(Long requestId, Appearance update) {
        return this.appearanceRepository.findById(requestId)
                .map(oldAppearance -> {
                    oldAppearance.setAddress(update.getAddress());
                    oldAppearance.setDesc(update.getDesc());
                    oldAppearance.setRequestId(requestId);
                    oldAppearance.setStatus(update.getStatus());
                    oldAppearance.setAppearanceType(update.getAppearanceType());
                    oldAppearance.setExpenses(update.getExpenses());
                    oldAppearance.setMileage(update.getMileage());
                    oldAppearance.setEndTime(update.getEndTime());
                    oldAppearance.setStartTime(update.getStartTime());
                    oldAppearance.setEventDate(update.getEventDate());
                    oldAppearance.setInstructions(update.getInstructions());
                    oldAppearance.setOnCampus(update.isOnCampus());
                    oldAppearance.setTitle(update.getTitle());
                    oldAppearance.setReqFirstName(update.getReqFirstName());
                    oldAppearance.setReqLastName(update.getReqLastName());
                    oldAppearance.setReqEmail(update.getReqEmail());
                    oldAppearance.setReqPhoneNumber(update.getReqPhoneNumber());
                    oldAppearance.setOrgName(update.getOrgName());
                    oldAppearance.setOutsideOrg(update.getOutsideOrg());
                    return this.appearanceRepository.save(oldAppearance);
                })
                .orElseThrow(() -> new ObjectNotFoundException("appearance", requestId));
    }

    public Appearance approve(Long requestId){
        return this.appearanceRepository.findById(requestId)
                .map(oldAppearance -> {
                    oldAppearance.setStatus(AppearanceStatus.APPROVED);
                    return this.appearanceRepository.save(oldAppearance);
                })
                .orElseThrow(() -> new ObjectNotFoundException("appearance", requestId));
    }

    public Appearance reject(Long requestId){
        return this.appearanceRepository.findById(requestId)
                .map(oldAppearance -> {
                    oldAppearance.setStatus(AppearanceStatus.REJECTED);
                    return this.appearanceRepository.save(oldAppearance);
                })
                .orElseThrow(() -> new ObjectNotFoundException("appearance", requestId));
    }

    public Appearance complete(Long requestId){
        return this.appearanceRepository.findById(requestId)
                .map(oldAppearance -> {
                    oldAppearance.setStatus(AppearanceStatus.COMPLETED);
                    return this.appearanceRepository.save(oldAppearance);
                })
                .orElseThrow(() -> new ObjectNotFoundException("appearance", requestId));
    }

    public List<Appearance> getOpenApprovedAppearances(){
        List<Appearance> approvedAppearances = this.appearanceRepository.findByStatus(AppearanceStatus.APPROVED);
        List<Appearance> openApprovedAppearances = new ArrayList<>();
        for(int i = 0; i < approvedAppearances.size(); i++){
            if(approvedAppearances.get(i).getAssignedSuperFrog() == null){
                openApprovedAppearances.add(approvedAppearances.get(i));
            }
        }
        return openApprovedAppearances;
    }

}
