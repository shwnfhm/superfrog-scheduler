package edu.tcu.cs.superfrogscheduler.appearance;

import edu.tcu.cs.superfrogscheduler.appearance.converter.AppearanceDtoToAppearanceConverter;
import edu.tcu.cs.superfrogscheduler.appearance.converter.AppearanceToAppearanceDtoConverter;
import edu.tcu.cs.superfrogscheduler.appearance.dto.AppearanceDto;
import edu.tcu.cs.superfrogscheduler.system.Result;
import edu.tcu.cs.superfrogscheduler.system.StatusCode;
import edu.tcu.cs.superfrogscheduler.user.User;
import edu.tcu.cs.superfrogscheduler.user.UserRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.endpoint.base-url}/appearances")
public class AppearanceController {

    private AppearanceService appearanceService;

    private AppearanceDtoToAppearanceConverter appearanceDtoToAppearanceConverter;

    private AppearanceToAppearanceDtoConverter appearanceToAppearanceDtoConverter;

    public AppearanceController(AppearanceService appearanceService, AppearanceDtoToAppearanceConverter appearanceDtoToAppearanceConverter, AppearanceToAppearanceDtoConverter appearanceToAppearanceDtoConverter) {
        this.appearanceService = appearanceService;
        this.appearanceDtoToAppearanceConverter = appearanceDtoToAppearanceConverter;
        this.appearanceToAppearanceDtoConverter = appearanceToAppearanceDtoConverter;
    }

    @PostMapping
    public Result addRequest(@Valid @RequestBody AppearanceDto req){
        Appearance newAppearance = this.appearanceDtoToAppearanceConverter.convert(req);
        Appearance savedNewAppearance = this.appearanceService.save(newAppearance);
        AppearanceDto savedNewAppearanceDto = this.appearanceToAppearanceDtoConverter.convert(savedNewAppearance);
        return new Result(true, StatusCode.SUCCESS, "Add Success", savedNewAppearanceDto);
    }

    @GetMapping
    public Result findAllAppearances(){
        List<Appearance> foundAppearances = this.appearanceService.findAll();
        // Convert foundArtifacts to a list of artifactDtos
        List<AppearanceDto> appearanceDtos = foundAppearances.stream()
                .map(this.appearanceToAppearanceDtoConverter::convert)
                .collect(Collectors.toList());
        return new Result(true, StatusCode.SUCCESS, "Find All Success", appearanceDtos);
    }

    @GetMapping("/{requestId}")
    public Result findArtifactById(@PathVariable Long requestId){
        Appearance foundAppearance = this.appearanceService.findById(requestId);
        AppearanceDto appearanceDto = this.appearanceToAppearanceDtoConverter.convert(foundAppearance);
        return new Result(true, StatusCode.SUCCESS, "Find One Success", appearanceDto);
    }

    @PutMapping("/{requestId}")
    public Result updateArtifact(@PathVariable Long requestId, @Valid @RequestBody AppearanceDto appearanceDto){
        Appearance update = this.appearanceDtoToAppearanceConverter.convert(appearanceDto);
        Appearance updatedAppearance = this.appearanceService.update(requestId, update);
        AppearanceDto updatedAppearanceDto = this.appearanceToAppearanceDtoConverter.convert(updatedAppearance);
        return new Result(true, StatusCode.SUCCESS, "Update Success", updatedAppearanceDto);
    }

}
