package edu.tcu.cs.superfrogscheduler.user;

import edu.tcu.cs.superfrogscheduler.system.Result;
import edu.tcu.cs.superfrogscheduler.system.StatusCode;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("{api.endpoint.base-url}/users")
public class UserController {

    @PostMapping
    public Result addUser(@Valid @RequestBody User user){
        return new Result(true, StatusCode.SUCCESS, "Success", user);
    }


}
