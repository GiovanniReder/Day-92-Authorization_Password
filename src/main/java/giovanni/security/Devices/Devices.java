package giovanni.security.Devices;

import giovanni.security.Employees.Employees;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "devices")
public class Devices {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String type;
  @Autowired
    @Enumerated(EnumType.STRING)
    private DevicesStateEnum state;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employees employee;






    public Devices(DevicesStateEnum state, String type) {
    }
}
