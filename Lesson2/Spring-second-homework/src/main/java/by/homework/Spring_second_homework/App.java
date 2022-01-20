package by.homework.Spring_second_homework;

import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
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
//        AbstractApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
    	AbstractApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        ProductService p = context.getBean("productService",ProductService.class);
        List<Product> prod = null;
        try {
			prod = (List<Product>) p.loadFindProductByName("Bike");
			if(prod != null) {
			System.out.println("Find product:");
			prod.stream().forEach(System.out::println);
			}else {
			System.out.println("This product not found");
			}
		} catch (ServiceException e) {
			System.err.println("\nProblems with search by name " + e.getMessage() + "\n");
		}
        
        
        try {
			p.delete("Hammer");
			System.out.println("Deleted");
			prod = (List<Product>) p.loadAll();
			prod.stream().forEach(System.out::println);
		} catch (ServiceException e) {
			System.out.println("Problems with deleted " + e.getMessage());
		}
        
        Product product1 = context.getBean("product",Product.class);
        product1.setName("Hammer");
        product1.setPrice(19.99);
        try {
			p.add(product1);
			System.out.println("Added");
			prod = (List<Product>) p.loadAll();
			prod.stream().forEach(System.out::println);
		} catch (ServiceException e) {
			System.out.println("Problems with added " + e.getMessage());
		}
    
        context.registerShutdownHook();
    }
}
