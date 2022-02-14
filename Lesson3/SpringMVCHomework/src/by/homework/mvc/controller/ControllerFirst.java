package by.homework.mvc.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import by.homework.mvc.model.User;

@Controller
public class ControllerFirst {

	/**
	 * Отслеживает запрос на адрес http://localhost:8080/mvc/
	 */

	
	@RequestMapping("")
	public String start(Model model) {
		return "index";
	}

	@PostMapping("/logg")
	public String enterUser(@ModelAttribute @Valid User user, BindingResult binding,
			RedirectAttributes redirectAttributes) {
		if (binding.hasErrors()) {
//			throw new NullPointerException("ошибочка екмакарек");
			return "index";
		}
		// user добавляется в модель,но при redirect с метода POST на метод GET модель
		// приходит с null полями,а с redirectAttributes.addFlashAttribute все работает
		redirectAttributes.addFlashAttribute("user", user);
		return "redirect:/userpage";
	}

	@GetMapping("/userpage")
	public String enterUserPage(@ModelAttribute("user") User user, Model model) {
		model.addAttribute("user", user);
		return "userPage";

	}

	// выход со страницы пользователя.
	@GetMapping("/exit")
	public String out() {
		return "redirect:/";
	}
}
