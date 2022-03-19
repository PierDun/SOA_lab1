package validator;

import entity.Error;
import entity.xml.JaxBCave;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CaveValidator {
    public List<Error> validate(JaxBCave cave) throws IllegalAccessException, ValidateFieldsException {
        List<Error> errorList = new ArrayList<>();
        if (cave == null) return errorList;

        for (Field f : JaxBCave.class.getDeclaredFields()) {
            f.setAccessible(true);
            if (f.get(cave) == null) {
                errorList.add(new Error(700, f.getName(), String.format("Cave's %s is not specified", f.getName())));
            }
        }

        if (errorList.size() == 0) {
            if (cave.getDepth() != null && cave.getDepth() < 0)
                errorList.add(new Error(701, "depth", "Cave's depth should be more than 0"));
        }

        if (errorList.size() > 0) {
            throw new ValidateFieldsException(errorList);
        }

        return errorList;
    }
}