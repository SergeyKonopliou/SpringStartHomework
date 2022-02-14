package by.homework.springboot.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import by.homework.springboot.model.Product;

@Controller
public class StartController {
	public List<Product> list = new ArrayList<Product>();
	private static Long id = 0L;


	@GetMapping("/")
	public String welcomePage() {
		return "index";
	}
	
	@GetMapping("/add")
	public String addNewProduct(Model model) {
		model.addAttribute("product", new Product());
		return "add";
	}
	
	@PostMapping("/add")
	public String saveNewProduct(@Valid @ModelAttribute Product product,BindingResult result,RedirectAttributes redirectAttributes) {
		if(!result.hasErrors()) {
			product.setId(++id);
			list.add(product);
			redirectAttributes.addFlashAttribute("list", list);
			redirectAttributes.addFlashAttribute("message", "New product saved");
			return "redirect:/";
		}
		return "add";
	}
	
	@RequestMapping("{id}/info")
	public String personalPage(@ModelAttribute Product product) {
		return "personalPage";
	}
}
