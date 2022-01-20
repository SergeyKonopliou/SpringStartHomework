package by.homework.Spring_second_homework;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


@Configuration
@ComponentScan({"dao","service","dao.util","model","util"})
public class Config {
	

}
