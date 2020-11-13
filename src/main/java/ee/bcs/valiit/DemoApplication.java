package ee.bcs.valiit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		// Skaneerib kogu projekti läbi, mis asub package'is ee.bcs.valiit ja võtab kõik klassid jne
		SpringApplication.run(DemoApplication.class, args);
	}

}
