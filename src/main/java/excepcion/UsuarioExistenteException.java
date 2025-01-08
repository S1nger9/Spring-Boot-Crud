package excepcion;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.http.HttpStatus;

public class UsuarioExistenteException extends RuntimeException {
    public UsuarioExistenteException(String mensaje) {
        super(mensaje);
    }
}
