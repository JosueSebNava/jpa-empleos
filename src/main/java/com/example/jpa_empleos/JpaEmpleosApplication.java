package com.example.jpa_empleos;

import com.example.jpa_empleos.models.Categoria;
import com.example.jpa_empleos.repository.CategoriasRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JpaEmpleosApplication implements CommandLineRunner {

	private final CategoriasRepository categoriasRepo;

	public JpaEmpleosApplication(CategoriasRepository categoriasRepo) {
		this.categoriasRepo = categoriasRepo;
	}

	public static void main(String[] args) {
		SpringApplication.run(JpaEmpleosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		guardar();
	}
	private void guardar() {
		System.out.println("Guardando...");
		Categoria nuevaCategoria = new Categoria();
		nuevaCategoria.setNombre("Finanzas");
		nuevaCategoria.setDescripcion("Trabajos relacionados con finanzas y contabilidad");
		categoriasRepo.save(nuevaCategoria);
		System.out.println(nuevaCategoria);
	}
	private void eliminar() {
		System.out.println("Eliminando...");
	}
}