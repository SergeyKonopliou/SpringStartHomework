package by.homework.mvc.util;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 
 * Класс по принципу АОП для отлавливания ошибок,возникающих при работе
 * приложения.
 * 
 * @ControllerAdvice - все методы класса будут срабатывать после каждого метода
 *                   контроллера
 * @ExceptionHandler - метод будет отлавливать указанные ошибки
 * @ResponseStatus - при ошибке вернет в ответе статус по указанному code,а не
 *                 200Ok(так правильно делать)
 * 
 */

@ControllerAdvice
public class AdviceException {

	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = Exception.class)
	public String exceptionPoint(Exception e, Model model) {
		model.addAttribute("message", e.getMessage());
		return "error";
	}

	/**
	 * Отлавливает ошибку 404(настройка в web.xml для DispacherServlet)
	 */
	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handle() {
		return "error";
	}
}
