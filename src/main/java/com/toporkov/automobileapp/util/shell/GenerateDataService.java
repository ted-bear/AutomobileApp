package com.toporkov.automobileapp.util.shell;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.toporkov.automobileapp.client.GraphHopperHttpClient;
import com.toporkov.automobileapp.model.Condition;
import com.toporkov.automobileapp.model.Driver;
import com.toporkov.automobileapp.model.DriverAssignment;
import com.toporkov.automobileapp.model.Enterprise;
import com.toporkov.automobileapp.model.Trip;
import com.toporkov.automobileapp.model.Vehicle;
import com.toporkov.automobileapp.model.VehicleModel;
import com.toporkov.automobileapp.repository.DriverAssignmentRepository;
import com.toporkov.automobileapp.repository.DriverRepository;
import com.toporkov.automobileapp.repository.EnterpriseRepository;
import com.toporkov.automobileapp.repository.VehicleModelRepository;
import com.toporkov.automobileapp.repository.VehicleRepository;
import com.toporkov.automobileapp.service.TripService;
import com.toporkov.automobileapp.service.VehicleCoordinateService;
import com.toporkov.automobileapp.web.dto.domain.coordinate.CreateCoordinateDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class GenerateDataService {

    private final List<String> vehicleNumbers = List.of(
        "А123ВГ", "Б456ЕЖ", "В789ЗИ", "Г321КЛ", "Д654МН",
        "Е987ОП", "Ж234РС", "З567ТУ", "И890ФХ", "К321ЦЧ",
        "Л654ШЩ", "М987ЫЭ", "Н234ЮЯ", "О567АБ", "П890ВГ",
        "Р321ДЕ", "С654ЖЗ", "Т987ИЙ", "У234КЛ", "Ф567МН"
    );

    private final List<String> vehicleColors = List.of(
        "Красный", "Синий", "Зеленый", "Желтый", "Оранжевый",
        "Фиолетовый", "Розовый", "Коричневый", "Серый", "Белый"
    );

    private final List<String> firstnames = List.of(
        "Александр", "Елена", "Дмитрий", "Ольга", "Сергей",
        "Анна", "Иван", "Мария", "Андрей", "Татьяна", "Николай",
        "Ирина", "Алексей", "Екатерина"
    );

    private final List<String> lastnames = List.of(
        "Иванов", "Петров", "Сидоров", "Смирнов", "Кузнецов",
        "Васильев", "Попов", "Соколов", "Михайлов", "Федоров",
        "Морозов", "Волков", "Алексеев", "Лебедев", "Семенов", "Егоров"
    );

    private final Random random = new Random();
    private final VehicleRepository vehicleRepository;
    private final DriverRepository driverRepository;
    private final DriverAssignmentRepository driverAssignmentRepository;
    private final EnterpriseRepository enterpriseRepository;
    private final VehicleModelRepository vehicleModelRepository;
    private final VehicleCoordinateService vehicleCoordinateService;
    private final TripService tripService;

    public GenerateDataService(
        final VehicleRepository vehicleRepository,
        final DriverRepository driverRepository,
        final DriverAssignmentRepository driverAssignmentRepository,
        final EnterpriseRepository enterpriseRepository,
        final VehicleModelRepository vehicleModelRepository, VehicleCoordinateService vehicleCoordinateService,
        TripService tripService
    ) {
        this.vehicleRepository = vehicleRepository;
        this.driverRepository = driverRepository;
        this.driverAssignmentRepository = driverAssignmentRepository;
        this.enterpriseRepository = enterpriseRepository;
        this.vehicleModelRepository = vehicleModelRepository;
        this.vehicleCoordinateService = vehicleCoordinateService;
        this.tripService = tripService;
    }

    public void generateVehiclesAndDrivers(
        Integer number, List<UUID> enterpriseIds
    ) {
        final List<VehicleModel> vehicleModels = vehicleModelRepository.findAllByIsActiveTrue();
        for (var id : enterpriseIds) {
            enterpriseRepository.findById(id)
                .ifPresent(enterprise -> {
                    for (int i = 0; i < number; i++) {
                        final Vehicle vehicle = generateRandomVehicle(enterprise, vehicleModels);
                        final Driver driver = generateRandomDriver(enterprise);
                        final DriverAssignment driverAssignment = new DriverAssignment(
                            driver,
                            vehicle,
                            getRandomBooleanWithFactor(0.1)
                        );
                        driverAssignment.setId(UUID.randomUUID());

                        vehicleRepository.save(vehicle);
                        driverRepository.save(driver);
                        driverAssignmentRepository.save(driverAssignment);
                    }
                });
        }
    }

    public void generateTrack(
        final Double minLatitude,
        final Double maxLatitude,
        final Double minLongitude,
        final Double maxLongitude
    ) {
        var allIds = vehicleRepository.findAll().stream().map(Vehicle::getId).toList();
        for (var vehicleId : allIds) {
            for (int i = 0; i < 10; i++) {

                var startLatitude = Math.round(random.nextDouble(minLatitude, maxLatitude) * 1e6) / 1_000_000.0d;
                var startLongitude = Math.round(random.nextDouble(minLongitude, maxLongitude) * 1e6) / 1_000_000.0d;

                var endLatitude = Math.round(random.nextDouble(minLatitude, maxLatitude) * 1e6) / 1_000_000.0d;
                var endLongitude = Math.round(random.nextDouble(minLongitude, maxLongitude) * 1e6) / 1_000_000.0d;

                var path = GraphHopperHttpClient.getTrack(startLatitude,
                    startLongitude,
                    endLatitude,
                    endLongitude
                );

                var track = path.getPoints().getCoordinates();
                var batch = new ArrayList<CreateCoordinateDTO>();
                var trip = new Trip();
                var month = random.nextInt(11) + 1;
                var dayOfMonth = random.nextInt(25) + 1;
                var year = random.nextInt(2000, 2024);
                var currentTime = LocalDateTime.of(year, month, dayOfMonth, 15, 14).toInstant(ZoneOffset.UTC);
                trip.setVehicle(vehicleRepository.findById(vehicleId).orElseThrow(RuntimeException::new));
                trip.setStartedAt(currentTime);

                for (int j = 0; j < track.size(); j++) {
                    currentTime.plus(500, ChronoUnit.SECONDS);
                    batch.add(
                        new CreateCoordinateDTO(
                            currentTime,
                            track.get(j).get(0),
                            track.get(j).get(1),
                            vehicleId.toString()
                        ));
                    if (j != track.size() - 1) {
                        currentTime = Instant.now();
                    }
                }
                trip.setEndedAt(currentTime);

                tripService.saveTrip(trip);
                vehicleCoordinateService.saveAllCoordinates(batch);
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    private Vehicle generateRandomVehicle(Enterprise enterprise, List<VehicleModel> models) {
        final Vehicle vehicle = new Vehicle();
        vehicle.setId(UUID.randomUUID());
        vehicle.setNumber(vehicleNumbers.get(random.nextInt(vehicleNumbers.size())));
        vehicle.setColor(vehicleColors.get(random.nextInt(vehicleColors.size())));
        vehicle.setMileage(random.nextInt(0, 100_000));
        vehicle.setPrice(random.nextDouble(0.0, 1_000_000.0));
        vehicle.setYear(random.nextInt(1901, 2025));
        vehicle.setCondition(getRandomCondition());
        vehicle.setEnterprise(enterprise);
        vehicle.setActive(true);
        vehicle.setVehicleModel(models.get(random.nextInt(0, models.size())));
        vehicle.setPurchaseDate(Instant.now());

        return vehicle;
    }

    private Driver generateRandomDriver(Enterprise enterprise) {
        final Driver driver = new Driver();
        driver.setId(UUID.randomUUID());
        driver.setEnterprise(enterprise);
        driver.setFirstname(firstnames.get(random.nextInt(0, firstnames.size())));
        driver.setLastname(lastnames.get(random.nextInt(0, lastnames.size())));
        driver.setSalary(new BigDecimal(random.nextLong(0, 100_000)));
        driver.setDrivingExperience(random.nextInt(0, 30));

        return driver;
    }


    private Condition getRandomCondition() {
        return random.nextBoolean() ? Condition.NEW : Condition.USED;
    }

    private boolean getRandomBooleanWithFactor(double probability) {
        return random.nextDouble() < probability;
    }
}
