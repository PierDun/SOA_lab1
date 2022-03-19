package validator;

import entity.xml.JaxBDragon;
import entity.Error;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class DragonValidator {
    private final CaveValidator caveValidator = new CaveValidator();
    private final CoordinatesValidator coordinatesValidator = new CoordinatesValidator();

    public List<Error> validate(JaxBDragon dragon) throws IllegalAccessException, ValidateFieldsException {
        List<Error> errorList = new ArrayList<>();
        for (Field f : JaxBDragon.class.getDeclaredFields()) {
            f.setAccessible(true);
            if (f.get(dragon) == null)
                errorList.add(new Error(700, f.getName(), (String.format("Dragon's %s is not specified", f.getName()))));
        }

        if (errorList.size() == 0) {
            if (dragon.getName() != null && dragon.getName().isEmpty())
                errorList.add(new Error(701,"name", "Dragon's name should not be empty"));

            if (dragon.getAge() <= 0)
                errorList.add(new Error(701, "age","Dragon's age should be bigger than 0"));

            if (dragon.getCreationDate() == null)
                errorList.add(new Error(701, "time","Dragon's creation date should not be empty"));
        }

        errorList.addAll(caveValidator.validate(dragon.getCave()));
        errorList.addAll(coordinatesValidator.validate(dragon.getCoordinates()));
        if (errorList.size() > 0) {
            throw new ValidateFieldsException(errorList);
        }
        return errorList;
    }
}