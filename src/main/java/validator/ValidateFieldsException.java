package validator;

import java.util.List;
import entity.Error;

public class ValidateFieldsException extends Exception {
    private List<Error> errorMsg;

    public ValidateFieldsException(List<Error> errorMessage) {
        this.errorMsg = errorMessage;
    }

    public List<Error> getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(List<Error> errorMsg) {
        this.errorMsg = errorMsg;
    }
}