package giovanni.security.Devices;

import giovanni.security.Payloads.NewDevicesDTO;
import giovanni.security.Payloads.NewDevicesResponseDTO;
import giovanni.security.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/devices")
public class DevicesController {

    @Autowired
    private DevicesService devicesService;

    // 1 . GET http://localhost:3001/devices
    @GetMapping
    public Page<Devices> getAllEmployees(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
        return this.devicesService.getDevices(page, size, sortBy);
    }

    // 2 . POST http://localhost:3001/devices
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewDevicesResponseDTO saveDevice(@RequestBody @Validated NewDevicesDTO body, BindingResult validationResult) throws IOException {

        if (validationResult.hasErrors()) {
            System.out.println(validationResult.getAllErrors());
            throw new BadRequestException(validationResult.getAllErrors());
        }
        return new NewDevicesResponseDTO(this.devicesService.saveNewDevice(body).getId());
    }

    // 3. GET http://localhost:3001/devices/{deviceId}
    @GetMapping("/{deviceId}")
    public Devices findDeviceById(@PathVariable long deviceId) {
        return this.devicesService.findDeviceById(deviceId);
    }

    // 4. PUT http://localhost:3001/devices/{deviceId}
    @PutMapping("/{deviceId}")
    public Devices findDeviceByIdAndUpdate(@PathVariable long deviceId, @RequestBody Devices body) {
        return this.devicesService.findDeviceByIdAndUpdate(deviceId , body);
    }

    // 5. DELETE http://localhost:3001/devices/{deviceId}
    @DeleteMapping("/{deviceId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findEmployeeByIdAndDelete(@PathVariable long deviceId) {
        this.devicesService.findDeviceByIdAndDelete(deviceId);
    }

}

