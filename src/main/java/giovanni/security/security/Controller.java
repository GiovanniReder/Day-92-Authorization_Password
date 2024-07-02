package giovanni.security.security;

import giovanni.security.Employees.EmployeesService;
import giovanni.security.Payloads.NewEmployeesDTO;
import giovanni.security.Payloads.NewEmployeesResponseDTO;
import giovanni.security.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class Controller {

    @Autowired
    private Service service;

    @Autowired
    private EmployeesService employeesService;


    @PostMapping("/login")
    public EmployyeLoginResponseDTO login(@RequestBody EmployeeLoginDTO payload){
        return new EmployyeLoginResponseDTO(service.authenticateUserAndGenerateToken(payload)) ;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public NewEmployeesResponseDTO saveUser(@RequestBody @Validated NewEmployeesDTO body, BindingResult validationResult) throws IOException {
        if (validationResult.hasErrors()) {
            throw new BadRequestException(validationResult.getAllErrors());
        }
        return new NewEmployeesResponseDTO(this.employeesService.saveNewEmployee(body).getId());
    }
}



