package com.toporkov.automobileapp.util.shell;

import com.toporkov.automobileapp.model.Condition;
import com.toporkov.automobileapp.model.Driver;
import com.toporkov.automobileapp.model.DriverAssignment;
import com.toporkov.automobileapp.model.Enterprise;
import com.toporkov.automobileapp.model.Vehicle;
import com.toporkov.automobileapp.model.VehicleModel;
import com.toporkov.automobileapp.repository.DriverAssignmentRepository;
import com.toporkov.automobileapp.repository.DriverRepository;
import com.toporkov.automobileapp.repository.EnterpriseRepository;
import com.toporkov.automobileapp.repository.VehicleModelRepository;
import com.toporkov.automobileapp.repository.VehicleRepository;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@ShellComponent
public class GenerateDataCommands {

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

    public GenerateDataCommands(final VehicleRepository vehicleRepository,
                                final DriverRepository driverRepository,
                                final DriverAssignmentRepository driverAssignmentRepository,
                                final EnterpriseRepository enterpriseRepository,
                                final VehicleModelRepository vehicleModelRepository) {
        this.vehicleRepository = vehicleRepository;
        this.driverRepository = driverRepository;
        this.driverAssignmentRepository = driverAssignmentRepository;
        this.enterpriseRepository = enterpriseRepository;
        this.vehicleModelRepository = vehicleModelRepository;
    }

    @ShellMethod(key = "generate-data",
            value = "Generate N random vehicles and drivers for specific enterprise.")
    public String generateVehiclesAndDrivers(
            @ShellOption(defaultValue = "1") Integer number,
            @ShellOption(defaultValue = "") List<Integer> enterpriseIds
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

                            vehicleRepository.save(vehicle);
                            driverRepository.save(driver);
                            driverAssignmentRepository.save(driverAssignment);
                        }
                    });
        }
        return "Generation is successful";
    }

    private Vehicle generateRandomVehicle(Enterprise enterprise, List<VehicleModel> models) {
        final Vehicle vehicle = new Vehicle();
        vehicle.setNumber(vehicleNumbers.get(random.nextInt(vehicleNumbers.size())));
        vehicle.setColor(vehicleColors.get(random.nextInt(vehicleColors.size())));
        vehicle.setMileage(random.nextInt(0, 100_000));
        vehicle.setPrice(random.nextDouble(0.0, 1_000_000.0));
        vehicle.setYear(random.nextInt(1901, 2025));
        vehicle.setCondition(getRandomCondition());
        vehicle.setEnterprise(enterprise);
        vehicle.setActive(true);
        vehicle.setVehicleModel(models.get(random.nextInt(0, models.size())));

        return vehicle;
    }

    private Driver generateRandomDriver(Enterprise enterprise) {
        final Driver driver = new Driver();
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
