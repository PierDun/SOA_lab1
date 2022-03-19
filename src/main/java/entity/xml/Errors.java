package entity.xml;

import org.simpleframework.xml.ElementList;
import entity.Error;
import java.util.List;

public class Errors {
    @ElementList
    private List<Error> errors = null;

    public List<Error> getErrors() {
        return errors;
    }

    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }
}