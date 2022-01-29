package by.homework.mvc.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import by.homework.mvc.controller.util.DecimalValidator;
import by.homework.mvc.exception.ServiceException;
import by.homework.mvc.model.Product;
import by.homework.mvc.service.ProductService;

@Controller
public class ControllerFirst {


	@Autowired
	@Qualifier("productServiceImpl")
	public ProductService service;
	private String message;
	private String message_action;
	private List<Product> catalog = new ArrayList<>();
	@Autowired
	DecimalValidator validatorDec;
	
	/**
	 *  Отслеживает запрос на адрес 
	 *  http://localhost:8080/mvc/showall
	 */
	@RequestMapping(value = "/showall")
	public String searchProduct(Model model,HttpServletRequest request) {
		String name = request.getParameter("search-good");
		try {
			if (name != null && !name.isEmpty()) {
				catalog = service.loadFindProductByName(name);
				model.addAttribute("catalog", catalog);
				message_action = (catalog.isEmpty()) ? "Not found anything" : "Successfully searched";
			} else {
				catalog = service.loadAll();
				model.addAttribute("catalog", catalog);
				message_action = (String) request.getAttribute("message_action");
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
	
	@RequestMapping(value = "/logg", method = RequestMethod.POST)
	public String showUserPage(Model model,HttpServletRequest request) {
		
		String name = (String) request.getParameter("name");
		String password = (String) request.getParameter("pass");

		if (name != null && password != null || (!name.isEmpty() && !password.isEmpty())) {
			model.addAttribute("loginName", name);
			model.addAttribute("loginPass", password);
		} else {
			model.addAttribute("message", "Enter your login information");
		} 
		return "userPage"; // передает отобажение странице userPage.jsp
	}
	
	@RequestMapping(value = "/add")
	public String addProduct(Model model,HttpServletRequest request) {
		
		String name = request.getParameter("add-name");
		String price = request.getParameter("add-price");

		if (name != null && !name.isEmpty()) {
			try {
				/**
				 * валидатор validatorDec проверяет подходит ли цена под дробное число, если
				 * цену не ввели или ввели некорректные данные,то записываем 0.0
				 */
				service.add(new Product(name, Double.parseDouble(validatorDec.valid(price))));
				message = "Successfully added";
			} catch (NumberFormatException | ServiceException e) {
				model.addAttribute("message_action", "Having problems adding a new product.");
				return "catalog";
			}
		} else {
			message = "Incorrect data to add.Enter product name";
		} 
		model.addAttribute("message", message);
		return "catalog";
	}
	
	@RequestMapping(value = "/delete")
	public String deleteProduct(Model model,HttpServletRequest request) {
	
		Long id = Long.valueOf(request.getParameter("id"));
		try {
			service.delete(id);
			message = "Successfully deleted";
			model.addAttribute("message_action", message);
		} catch (ServiceException e) {
			model.addAttribute("message_action", "An error occurred while deleting the product.");
			return "catalog";
		}
		model.addAttribute("message", message);
		return "catalog"; 
	}
	
	@RequestMapping(value = "/updateProduct")
	public String refUpdateProductPage(Model model,HttpServletRequest request) {
		return "update"; 
	}
	
	@RequestMapping(value = "/update")
	public String updateProduct(Model model,HttpServletRequest request) {
	
		Long id = Long.valueOf(request.getParameter("id"));
		String nameNew = request.getParameter("update-nameNew");
		String priceNew = request.getParameter("update-priceNew");
		try {
			service.update(new Product(id, nameNew, Double.parseDouble(priceNew)));
			message = "Successfully updated";
			model.addAttribute("message_action", message);
		} catch (ServiceException e) {
			model.addAttribute("message_action", "Problems with changing product.");
			return "catalog";
		}
		return "catalog"; 
	}
	
}
