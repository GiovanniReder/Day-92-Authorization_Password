package giovanni.security.Devices;

import giovanni.security.Payloads.NewDevicesDTO;
import giovanni.security.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class DevicesService {

    @Autowired
    private DevicesRepository devicesRepository;

    public Page<Devices> getDevices(int pageNumber, int pageSize, String sortBy) {
        if (pageSize > 100) pageSize = 100;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return devicesRepository.findAll(pageable);
    }

    public Devices saveNewDevice (NewDevicesDTO body) throws IOException {
        Devices newDevice = new Devices(body.state() , body.type());

        return devicesRepository.save(newDevice);
    }

    public Devices findDeviceById(long id){
        return devicesRepository.findById(id).orElseThrow(()-> new NotFoundException(id));

    }

    public Devices findDeviceByIdAndUpdate(long id , Devices modifiedDevice){
        Devices found = this.findDeviceById(id);
        found.setType(modifiedDevice.getType());
        found.setState(modifiedDevice.getState());

        return devicesRepository.save(found);
    }


    public void findDeviceByIdAndDelete(long id){
        Devices found = this.findDeviceById(id);
        devicesRepository.delete(found);
    }


}

