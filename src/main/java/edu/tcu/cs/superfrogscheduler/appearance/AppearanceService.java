package edu.tcu.cs.superfrogscheduler.appearance;

import edu.tcu.cs.superfrogscheduler.system.exception.ObjectNotFoundException;
import edu.tcu.cs.superfrogscheduler.user.User;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class AppearanceService {

    private final AppearanceRepository appearanceRepository;

    public AppearanceService(AppearanceRepository appearanceRepository) {
        this.appearanceRepository = appearanceRepository;
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

    public Appearance assign(Long requestId, User assignee){
        return this.appearanceRepository.findById(requestId)
                .map(oldAppearance -> {
                    oldAppearance.setAssignedSuperFrog(assignee);
                    return this.appearanceRepository.save(oldAppearance);
                })
                .orElseThrow(() -> new ObjectNotFoundException("appearance", requestId));
    }

    public Appearance unassign(Long requestId){
        return this.appearanceRepository.findById(requestId)
                .map(oldAppearance -> {
                    oldAppearance.setAssignedSuperFrog(null);
                    return this.appearanceRepository.save(oldAppearance);
                })
                .orElseThrow(() -> new ObjectNotFoundException("appearance", requestId));
    }

}
