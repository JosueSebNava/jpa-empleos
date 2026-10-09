package com.example.jpa_empleos;

import com.example.jpa_empleos.models.Categoria;
import com.example.jpa_empleos.models.Vacante;
import com.example.jpa_empleos.models.EstatusVacante;

import com.example.jpa_empleos.repository.CategoriasJPARepository;
import com.example.jpa_empleos.repository.CategoriasRepository;
import com.example.jpa_empleos.repository.VacantesRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Date;
import java.util.List;

@SpringBootApplication
public class JpaEmpleosApplication implements CommandLineRunner {

	/*
	 * Repositorio anterior de Categorias
	 */
	private final CategoriasRepository categoriasRepo;

	/*
	 * Repositorio JPA de Categorias
	 */
	private final CategoriasJPARepository categoriasJPARepo;

	/*
	 * Nuevo repositorio de Vacantes
	 */
	private final VacantesRepository vacantesRepo;


	/*
	 * Inyeccion de dependencias mediante constructor,
	 * tal como se muestra en el documento.
	 */
	public JpaEmpleosApplication(
			CategoriasRepository categoriasRepo,
			CategoriasJPARepository categoriasJPARepo,
			VacantesRepository vacantesRepo
	) {

		this.categoriasRepo = categoriasRepo;
		this.categoriasJPARepo = categoriasJPARepo;
		this.vacantesRepo = vacantesRepo;
	}


	public static void main(String[] args) {

		SpringApplication.run(
				JpaEmpleosApplication.class,
				args
		);
	}


	@Override
	public void run(String... args) throws Exception {

		/*
		 * Para esta parte de la practica
		 * ejecutamos la busqueda de Vacantes.
		 */
		guardarVacante();
	}


	/**
	 * Metodo findAll - Interfaz JPARepository
	 *
	 * Recupera todas las vacantes registradas
	 * en la base de datos.
	 */
	private void buscarVacantes() {
		List<Vacante> vacantes = vacantesRepo.findAll();
		for (Vacante vacante : vacantes) {
			System.out.println(vacante.getId() + ". " + vacante.getNombre() +
					" -> " + vacante.getCategoria().getNombre());
		}
	}


	/*
	 * ======================================================
	 * METODOS DE LA PRACTICA ANTERIOR DE CATEGORIAS
	 * ======================================================
	 */


	private void buscarTodasJPA() {

		List<Categoria> categorias =
				categoriasJPARepo.findAll();

		for (Categoria categoria : categorias) {

			System.out.println(
					categoria.getId()
							+ " "
							+ categoria.getNombre()
			);
		}
	}


	private void borrarTodasEnBloque() {

		categoriasJPARepo.deleteAllInBatch();
	}


	private void buscarTodasJPAOrdenadas() {

		List<Categoria> categorias =
				categoriasJPARepo.findAll(
						Sort.by("nombre").descending()
				);

		for (Categoria categoria : categorias) {

			System.out.println(
					categoria.getId()
							+ " "
							+ categoria.getNombre()
			);
		}
	}


	private void buscarTodasJPAPaginadas() {

		Page<Categoria> page =
				categoriasJPARepo.findAll(
						PageRequest.of(3, 5)
				);

		System.out.println(
				"Total registros: "
						+ page.getTotalElements()
		);

		System.out.println(
				"Total paginas: "
						+ page.getTotalPages()
		);

		for (Categoria categoria : page.getContent()) {

			System.out.println(
					categoria.getId()
							+ " "
							+ categoria.getNombre()
			);
		}
	}

	private void guardarVacante(){
		Vacante vacante = new Vacante();
		vacante.setNombre("Desarrollador Java PRO");
		vacante.setDescripcion("Se busca desarrollador con experiencia en Spring Boot y JPA");
		vacante.setFecha(new Date());
		vacante.setSalario(25000.0);
		vacante.setEstatus(EstatusVacante.Creada);
		vacante.setDestacado(1);
		vacante.setImagen("logo_empresa.png");
		vacante.setDetalles("Trabajo remoto con horario flexible. Beneficios y capacitacion incluidos");

		// Crear una categoria asociada
		Categoria categoria = new Categoria();
		categoria.setId(1); // Si ya existe en la BD, solo se asigna una ID
		// o se puede crear una nueva:
		// categoria.setNombre("Tecnologia");
		// categoria.setDescripcion("Empleos relacionados con desarrollo y TI.");

		vacante.setCategoria(categoria);
		vacantesRepo.save(vacante);
	}

	private void buscarTodasJPAPaginadasOrdenadas() {

		Page<Categoria> page =
				categoriasJPARepo.findAll(
						PageRequest.of(
								0,
								5,
								Sort.by("nombre").descending()
						)
				);

		System.out.println(
				"Total registros: "
						+ page.getTotalElements()
		);

		System.out.println(
				"Total paginas: "
						+ page.getTotalPages()
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