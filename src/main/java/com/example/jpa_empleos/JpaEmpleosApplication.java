package com.example.jpa_empleos;

import com.example.jpa_empleos.models.Categoria;
import com.example.jpa_empleos.repository.CategoriasJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@SpringBootApplication
public class JpaEmpleosApplication implements CommandLineRunner {

	@Autowired
	private CategoriasJPARepository categoriasJPARepo;

	public static void main(String[] args) {
		SpringApplication.run(JpaEmpleosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		buscarTodasJPAPaginadasOrdenadas();
	}

	private void buscarTodasJPA() {
		List<Categoria> categorias = categoriasJPARepo.findAll();

		for (Categoria categoria : categorias) {
			System.out.println(categoria.getId() + " " + categoria.getNombre());
		}
	}

	private void borrarTodasEnBloque() {
		categoriasJPARepo.deleteAllInBatch();
	}

	private void buscarTodasJPAOrdenadas() {
		List<Categoria> categorias = categoriasJPARepo.findAll(Sort.by("nombre").descending());

		for (Categoria categoria : categorias) {
			System.out.println(categoria.getId() + " " + categoria.getNombre());
		}
	}

	private void buscarTodasJPAPaginadas() {
		Page<Categoria> page = categoriasJPARepo.findAll(PageRequest.of(3, 5));
		System.out.println("Total registros: " + page.getTotalElements());
		System.out.println("Total paginas: " + page.getTotalPages());
		for (Categoria categoria : page.getContent()) {
			System.out.println(categoria.getId() + " " + categoria.getNombre());
		}
	}

	private void buscarTodasJPAPaginadasOrdenadas() {

		Page<Categoria> page = categoriasJPARepo.findAll(
				PageRequest.of(
						0,
						5,
						Sort.by("nombre").descending()
				)
		);

		System.out.println(
				"Total registros: " + page.getTotalElements()
		);

		System.out.println(
				"Total paginas: " + page.getTotalPages()
		);

		for (Categoria categoria : page.getContent()) {
			System.out.println(
					categoria.getId()
							+ " "
							+ categoria.getNombre()
			);
		}
	}
}