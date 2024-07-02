package giovanni.security.Employees;

import giovanni.security.Payloads.NewEmployeesDTO;
import giovanni.security.exceptions.BadRequestException;
import giovanni.security.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmployeesService {

    @Autowired
    private EmployeesRepository employeesRepository;
    @Autowired
    private PasswordEncoder bcrypt;

    public Page<Employees> getEmployees(int pageNumber, int pageSize, String sortBy) {
        if (pageSize > 100) pageSize = 100;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return employeesRepository.findAll(pageable);
    }


    public Employees saveNewEmployee (NewEmployeesDTO body) throws IOException {

        employeesRepository.findByEmail(body.email()).ifPresent(user -> {
            throw new BadRequestException("L'email " + body.email() + " risulta già utilizzata!");
        });
        Employees newEmployee = new Employees(body.username(), body.name(), body.surname(), body.email(), bcrypt.encode(body.password()));
        return employeesRepository.save(newEmployee);
    }

    public Employees findEmployeeById(long id){
        return employeesRepository.findById(id).orElseThrow(()-> new NotFoundException(id));
    }

    public Employees findEmployeeByIdAndUpdate(long id , Employees modifiedEmployee){
        Employees found = this.findEmployeeById(id);
        found.setName(modifiedEmployee.getName());
        found.setSurname(modifiedEmployee.getSurname());
        found.setUsername(modifiedEmployee.getUsername());
        found.setEmail(modifiedEmployee.getEmail());
        found.setPassword(modifiedEmployee.getPassword());
        return employeesRepository.save(found);
    }


    public void findEmployeeByIdAndDelete(long id){
        Employees found = this.findEmployeeById(id);
        employeesRepository.delete(found);
    }

    public Employees findByEmail(String email){
        return employeesRepository.findByEmail(email).orElseThrow(()-> new NotFoundException("Utente con email" + email + " non trovato"));
    }


}