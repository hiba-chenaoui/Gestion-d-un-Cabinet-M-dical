package ma.fsr.ms.consultationservice.exception;

public class ConsultationException extends RuntimeException {

    public ConsultationException(String message) {
        super(message);
    }

    public ConsultationException(String message, Throwable cause) {
        super(message, cause);
    }
}