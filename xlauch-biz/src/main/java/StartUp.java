import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * <p>
 * 类描述：REST启动文件
 * </p>
 *
 * @author huangxy
 * @version 0.1
 * @since 2017/12/21.
 */
@ServletComponentScan
@SpringBootApplication
public class StartUp extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(this.getClass());
	}

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(StartUp.class);
	    app.run(args);
	}

}