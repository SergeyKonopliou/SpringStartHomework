package by.homework.Spring_second_homework;

import java.util.List;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.protobuf.ServiceException;

import model.Product;
import service.ProductService;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
	//only with annotation configurations
        AbstractApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        
        //annotation + xml configurations
//    	AbstractApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        
        ProductService servise = context.getBean("productService",ProductService.class);
        List<Product> products = null;
        try {
			products = (List<Product>) servise.loadFindProductByName("Bike");
			if(products != null) {
			System.out.println("Find product:");
			products.stream().forEach(System.out::println);
			}else {
			System.out.println("This product not found");
			}
		} catch (ServiceException e) {
			System.err.println("\nProblems with search by name " + e.getMessage() + "\n");
		}
        
        
        try {
			servise.delete("Hammer");
			System.out.println("Deleted");
			products = (List<Product>) servise.loadAll();
			products.stream().forEach(System.out::println);
		} catch (ServiceException e) {
			System.out.println("Problems with deleted " + e.getMessage());
		}
        
        Product product1 = context.getBean("product",Product.class);
        product1.setName("Hammer");
        product1.setPrice(19.99);
        try {
			servise.add(product1);
			System.out.println("Added");
			products = (List<Product>) servise.loadAll();
			products.stream().forEach(System.out::println);
		} catch (ServiceException e) {
			System.out.println("Problems with added " + e.getMessage());
		}
        
        try {
			servise.update("Hammer", "21.11");
			System.out.println("Update");
			products = (List<Product>) servise.loadAll();
			products.stream().forEach(System.out::println);
		} catch (ServiceException e) {
			System.out.println("Problems with updated " + e.getMessage());
		}
        
    
        context.registerShutdownHook();
    }
}
