package by.homework.springboot.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import by.homework.springboot.exception.ServiceException;
import by.homework.springboot.model.Product;
import by.homework.springboot.service.ProductService;

@Controller
public class StartController {
	public List<Product> list = new ArrayList<Product>();
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService service;


	@GetMapping("/")
	public String welcomePage(Model model) throws ServiceException {//исключение пробрасываем,чтобы срабатывал AdviceException(если есть catch то почемуто не срабатывает )
		list = service.loadAll();
		model.addAttribute("list", list);
		return "index";
	}
	
	@GetMapping("/add")
	public String addNewProduct(Model model) {
		model.addAttribute("product", new Product());
		return "add";
	}
	
	@PostMapping("/add")
	public String saveNewProduct(@Valid @ModelAttribute Product product,BindingResult result,RedirectAttributes redirectAttributes) throws ServiceException {
		if(!result.hasErrors()) {
			service.add(product);
			redirectAttributes.addFlashAttribute("message", "New product saved");
			return "redirect:/";
		}
		return "add";
	}
	
	@RequestMapping("{id}/info")
	public String personalPage(@ModelAttribute Product product) {
		return "personalPage";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id,RedirectAttributes redirectAttributes) throws ServiceException {
		service.delete(id);
		redirectAttributes.addFlashAttribute("message", "Product deleted");
		return "redirect:/";
	}
}
