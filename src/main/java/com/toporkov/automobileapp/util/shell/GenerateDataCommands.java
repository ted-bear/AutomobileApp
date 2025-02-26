package com.toporkov.automobileapp.util.shell;

import com.toporkov.automobileapp.model.Condition;
import com.toporkov.automobileapp.model.Enterprise;
import com.toporkov.automobileapp.model.Vehicle;
import com.toporkov.automobileapp.model.VehicleModel;
import com.toporkov.automobileapp.repository.EnterpriseRepository;
import com.toporkov.automobileapp.repository.VehicleModelRepository;
import com.toporkov.automobileapp.repository.VehicleRepository;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

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

    private final Random random = new Random();

    private final VehicleRepository vehicleRepository;
    private final EnterpriseRepository enterpriseRepository;
    private final VehicleModelRepository vehicleModelRepository;

    public GenerateDataCommands(final VehicleRepository vehicleRepository,
                                final EnterpriseRepository enterpriseRepository,
                                final VehicleModelRepository vehicleModelRepository) {
        this.vehicleRepository = vehicleRepository;
        this.enterpriseRepository = enterpriseRepository;
        this.vehicleModelRepository = vehicleModelRepository;
    }

    @ShellMethod(key = "generate-vehicles",
            value = "Generate N random vehicles for specific enterprise.")
    public String generateVehicles(
            @ShellOption(defaultValue = "1") Integer number,
            @ShellOption(defaultValue = "") List<Integer> enterpriseIds
    ) {
        final List<VehicleModel> vehicleModels = vehicleModelRepository.findAllByIsActiveTrue();
        for (var id : enterpriseIds) {
            enterpriseRepository.findById(id)
                    .ifPresent(enterprise -> {
                        for (int i = 0; i < number; i++) {
                            vehicleRepository.save(generateRandomVehicle(enterprise, vehicleModels));
                        }
                    });
        }
        return "All vehicles:  " + vehicleRepository.findAll();
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


    private Condition getRandomCondition() {
        return random.nextBoolean() ? Condition.NEW : Condition.USED;
    }

}
