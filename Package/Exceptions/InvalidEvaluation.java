package Package.Exceptions;

public class InvalidEvaluation extends RuntimeException {
    public InvalidEvaluation() {
        super(ExceptionsMessagesEnum.INVALID_EVALUATION.getMessage());
    }
}
