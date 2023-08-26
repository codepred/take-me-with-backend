package codepred.ride.service;

import codepred.customer.model.AppUser;
import codepred.ride.dto.RideDataRequest;
import codepred.ride.dto.RideResponse;
import codepred.ride.dto.SubmitRideRequest;
import codepred.ride.model.RideEntity;
import codepred.ride.repository.RideRepository;
import codepred.customer.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class RideService {

    @Autowired
    UserService userService;

    @Autowired
    RideRepository rideRepository;

    public RideEntity submitRide(SubmitRideRequest submitRideRequest, AppUser appUser) {
        RideEntity rideEntity = new RideEntity();
        rideEntity.setAppUser(appUser);

        rideEntity.setStart(submitRideRequest.start());
        rideEntity.setDestination(submitRideRequest.destination());
        rideEntity.setStartDate(submitRideRequest.startDate());
        rideEntity.setStartHour(submitRideRequest.startHour());
        return rideRepository.save(rideEntity);
    }

    public List<RideResponse> getRideList(RideDataRequest rideDataRequest) {
        List<RideEntity> rideEntities = rideRepository.findAll();
        return rideEntities.stream()
            .map(r -> new RideResponse(r.getId(),
                                       r.getStart(),
                                       r.getDestination(),
                                       r.getStartDate(),
                                       r.getStartHour(),
                                       r.getAppUser().getName(),
                                       r.getAppUser().getPhoneNumber(),
                                       "photo",
                                       10))
            .collect(Collectors.toList());
    }
}
