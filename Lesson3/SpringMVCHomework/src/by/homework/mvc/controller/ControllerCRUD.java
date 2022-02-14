package by.homework.mvc.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import by.homework.mvc.exception.ServiceException;
import by.homework.mvc.model.Product;
import by.homework.mvc.service.ProductService;

@Controller
@RequestMapping("/products")
public class ControllerCRUD {

	@Autowired
	@Qualifier("productServiceImpl")
	public ProductService service;
	private String message;
	private String message_action;
	private List<Product> catalog = new ArrayList<>();
	
	@RequestMapping(value = "")
	public String searchProduct(@RequestParam(defaultValue = "")String search_product,@RequestParam(value = "message_action",defaultValue = "View all products catalog")String message_act, Model model) {
		try {
			if (!search_product.isEmpty()) {
				catalog = service.loadFindProductByName(search_product);
				message_action = (catalog.isEmpty()) ? "Not found anything" : "Successfully searched";
				model.addAttribute("flag", true);
			} else {
				catalog = service.loadAll();
				message_action = message_act;
				model.addAttribute("flag", false);
			}
			message = "Found " + catalog.size() + " product(s)";
			model.addAttribute("catalog", catalog);
			model.addAttribute("message", message);
			model.addAttribute("message_action", message_action);
		} catch (ServiceException e) {
			model.addAttribute("message_action", "Problems with search.");
			return "catalog";
		}
		return "catalog";
	}
	
	@RequestMapping(value = "/add")
	public String addProduct(@ModelAttribute @Valid Product product,BindingResult binding,Model model) {
		if (!binding.hasErrors()) {
			try {
				service.add(product);
				message_action = "Successfully added";
			} catch (NumberFormatException | ServiceException e) {
				model.addAttribute("message_action", "Having problems adding a new product.");
				return "redirect:/products"; //redirect создает новый запрос; forward перенаправляет пришедший запрос
			}
		} else {
			message_action = binding.getFieldError().getDefaultMessage();
		} 
		model.addAttribute("message_action", message_action);
		return "redirect:/products";
	}
	
	@RequestMapping(value = "/delete")
	public String deleteProduct(@ModelAttribute Product product,Model model) {
		try {
			service.delete(product.getId());
			message_action = "Successfully deleted";
			model.addAttribute("message_action", message_action);
		} catch (ServiceException e) {
			model.addAttribute("message_action", "An error occurred while deleting the product.");
			return "redirect:/products";
		}
		model.addAttribute("message_action", message_action);
		return "redirect:/products"; 
	}
	
	@RequestMapping(value = "/{id}/updateProduct")
	public String refUpdateProductPage(@ModelAttribute Product product) {//эта аннотация автоматически запишет в Model объект product и передаст дальше
		return "update"; //на этой странице мы можем получить данные о переданном объекте
	}
	@GetMapping(value = "/{id}/update")
	public String updateProduct(@ModelAttribute Product product,Model model) {//здесь мы уже принимаем данные из формы изменения объекта
		try {
			service.update(product);
			message_action = "Successfully updated";
			model.addAttribute("message_action", message_action);
		} catch (ServiceException e) {
			model.addAttribute("message_action", "Problems with changing product.");
			return "redirect:/products";
		}
		return "redirect:/products"; 
	}
}
