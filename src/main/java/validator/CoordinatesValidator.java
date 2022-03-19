package validator;

import entity.xml.JaxBCoordinates;
import entity.Error;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CoordinatesValidator {
    public List<Error> validate(JaxBCoordinates coordinates) throws IllegalAccessException, ValidateFieldsException {
        List<Error> errorList = new ArrayList<>();
        if (coordinates == null) return errorList;

        for (Field f : JaxBCoordinates.class.getDeclaredFields()) {
            f.setAccessible(true);
            if (f.get(coordinates) == null) {
                errorList.add(new Error(700, f.getName(), String.format("Coordinates' %s is not specified", f.getName())));
            }
        }

        if (errorList.size() == 0) {
            if (coordinates.getY() != null && coordinates.getX() > 449)
                errorList.add(new Error(701, "x", "Coordinates' y maximum value is 449"));
        }

        if (errorList.size() > 0) {
            throw new ValidateFieldsException(errorList);
        }

        return errorList;
    }
}