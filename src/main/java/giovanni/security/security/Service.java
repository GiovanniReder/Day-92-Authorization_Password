package giovanni.security.security;

import giovanni.security.Employees.Employees;
import giovanni.security.Employees.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service

public class Service {
    @Autowired
    private EmployeesService employeesService;

    @Autowired
    private JWTTools jwtTools;

    public String authenticateUserAndGenerateToken(EmployeeLoginDTO payload){

        // 1. Controllo le credenziali
        // 1.1 Cerco nel db tramite email se esiste tale utente
        Employees employee = this.employeesService.findByEmail(payload.email());
        // 1.2 Verifico se la password combacia con quella ricevuta nel payload
        if(employee.getPassword().equals(payload.password())){
            // 2. Se tutto è OK --> Genero un token per tale utente e lo torno
            return jwtTools.createToken(employee);
        } else {
            // 3. Se le credenziali sono errate --> 401 (Unauthorized)
            throw new UnauthorizedException("Credenziali non corrette!");
        }
    }

}
