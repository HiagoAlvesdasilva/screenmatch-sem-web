//package br.com.hiago.screematch;
//
//import br.com.hiago.screematch.principal.MenuPrincipal;
//import br.com.hiago.screematch.repository.EpisodioRepository;
//import br.com.hiago.screematch.repository.SerieRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//@SpringBootApplication
//public class ScreematchApplicationSemWeb implements CommandLineRunner {
//
//	@Autowired
//	private SerieRepository serieRepository;
//
//	@Autowired
//	private EpisodioRepository episodioRepository;
//
//	public static void main(String[] args) {
//		SpringApplication.run(ScreematchApplicationSemWeb.class, args);
//	}
//
//	@Override
//	public void run(String... args) throws Exception {
//        MenuPrincipal principal = new MenuPrincipal(serieRepository,episodioRepository);
//		principal.exibeMenu();
//    }
//}
