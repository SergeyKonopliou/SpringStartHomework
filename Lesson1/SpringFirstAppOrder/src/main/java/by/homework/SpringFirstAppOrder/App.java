package by.homework.SpringFirstAppOrder;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        @SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        
        
        Product productOne = context.getBean("bread",Product.class);
        System.out.println(productOne);
        Product productTwo = context.getBean("butter",Product.class);
        System.out.println(productTwo);
        Product productThree = context.getBean("cheese",Product.class);
        System.out.println(productThree);
        Product productFour = context.getBean("tea",Product.class);
        System.out.println(productFour);
        Product productFive = context.getBean("chock",Product.class);
        System.out.println(productFive);
        
        Order orderOne = (Order) context.getBean("order1");
        System.out.println(orderOne);
        Order orderTwo = (Order) context.getBean("order2");
        System.out.println(orderTwo);
        Order orderThree = (Order) context.getBean("order3");
        System.out.println(orderThree);
        
        Seller sellerOne  = (Seller) context.getBean("seller1");
        System.out.println(sellerOne.takeOrder(orderOne));
    	Seller sellerTwo  = (Seller) context.getBean("seller2");
    	System.out.println(sellerTwo.takeOrder(orderTwo));
    	System.out.println(sellerOne.takeOrder(orderThree));
    	}
}
