package com.example.jpa_empleos;

import com.example.jpa_empleos.models.Categoria;
import com.example.jpa_empleos.models.EstatusVacante;
import com.example.jpa_empleos.models.Vacante;

import com.example.jpa_empleos.repository.CategoriasJPARepository;
import com.example.jpa_empleos.repository.CategoriasRepository;
import com.example.jpa_empleos.repository.VacantesRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.util.List;

@SpringBootApplication
public class JpaEmpleosApplication implements CommandLineRunner {

	/*
	 * =====================================================
	 * REPOSITORIOS
	 * =====================================================
	 */

	private final CategoriasRepository categoriasRepo;

	private final CategoriasJPARepository categoriasJPARepo;

	private final VacantesRepository vacantesRepo;

	private final JdbcTemplate jdbcTemplate;


	/*
	 * =====================================================
	 * CONSTRUCTOR
	 * =====================================================
	 */

	public JpaEmpleosApplication(
			CategoriasRepository categoriasRepo,
			CategoriasJPARepository categoriasJPARepo,
			VacantesRepository vacantesRepo,
			JdbcTemplate jdbcTemplate
	) {

		this.categoriasRepo = categoriasRepo;
		this.categoriasJPARepo = categoriasJPARepo;
		this.vacantesRepo = vacantesRepo;
		this.jdbcTemplate = jdbcTemplate;
	}


	/*
	 * =====================================================
	 * MAIN
	 * =====================================================
	 */

	public static void main(String[] args) {

		SpringApplication.run(
				JpaEmpleosApplication.class,
				args
		);
	}


	/*
	 * =====================================================
	 * RUN
	 * =====================================================
	 */

	@Override
	public void run(String... args) throws Exception {

		/*
		 * OPCIONES
		 *
		 * 1 = Consultar todas las vacantes
		 * 2 = Guardar una nueva vacante
		 *
		 * Solamente cambia este número.
		 */

		int opcion = 2;


		switch (opcion) {

			case 1:

				buscarVacantes();

				break;


			case 2:

				guardarVacante();

				break;


			default:

				System.out.println(
						"Opcion no valida."
				);

				break;
		}
	}


	/*
	 * =====================================================
	 * BUSCAR VACANTES
	 * =====================================================
	 */

	/**
	 * Método findAll - Interfaz JpaRepository
	 */
	public void buscarVacantes() {

		List<Vacante> vacantes =
				vacantesRepo.findAll(
						Sort.by("id").ascending()
				);


		System.out.println();

		System.out.println(
				"=============================================="
		);

		System.out.println(
				"           VACANTES REGISTRADAS"
		);

		System.out.println(
				"=============================================="
		);


		if (vacantes.isEmpty()) {

			System.out.println(
					"No existen vacantes registradas."
			);

			return;
		}


		for (Vacante vacante : vacantes) {

			String nombreCategoria =
					vacante.getCategoria() != null
							? vacante.getCategoria().getNombre()
							: "Sin categoria";


			System.out.println(
					vacante.getId()
							+ ". "
							+ vacante.getNombre()
							+ " -> "
							+ nombreCategoria
			);
		}


		System.out.println(
				"=============================================="
		);
	}


	/*
	 * =====================================================
	 * OBTENER ÚLTIMO ID
	 * =====================================================
	 */

	private int obtenerSiguienteId() {

		Integer ultimoId =
				vacantesRepo.obtenerUltimoId();


		/*
		 * Si no existen registros,
		 * inicia desde 1.
		 */
		if (ultimoId == null) {

			return 1;
		}


		/*
		 * Si el último es 13,
		 * devuelve 14.
		 */
		return ultimoId + 1;
	}


	/*
	 * =====================================================
	 * AJUSTAR AUTO_INCREMENT
	 * =====================================================
	 */

	private void ajustarAutoIncremento() {

		int siguienteId =
				obtenerSiguienteId();


		/*
		 * Ejemplo:
		 *
		 * Si MAX(id) = 13
		 *
		 * ejecuta:
		 *
		 * ALTER TABLE vacantes AUTO_INCREMENT = 14
		 */

		String sql =
				"ALTER TABLE vacantes AUTO_INCREMENT = "
						+ siguienteId;


		jdbcTemplate.execute(sql);


		System.out.println(
				"Siguiente ID disponible: "
						+ siguienteId
		);
	}


	/*
	 * =====================================================
	 * GUARDAR VACANTE
	 * =====================================================
	 */

	/**
	 * Guardar una nueva vacante.
	 */
	public void guardarVacante() {

		/*
		 * Antes de guardar se ajusta
		 * el AUTO_INCREMENT.
		 */
		ajustarAutoIncremento();


		/*
		 * Crear objeto Vacante.
		 */
		Vacante vacante =
				new Vacante();


		/*
		 * Nombre
		 */
		vacante.setNombre(
				"Desarrollador Java PRO"
		);


		/*
		 * Descripcion
		 */
		vacante.setDescripcion(
				"Se busca desarrollador con experiencia "
						+ "en Spring Boot y JPA."
		);


		/*
		 * Fecha actual
		 */
		vacante.setFecha(
				new Date()
		);


		/*
		 * Salario
		 */
		vacante.setSalario(
				25000.0
		);


		/*
		 * Estatus
		 */
		vacante.setEstatus(
				EstatusVacante.Creada
		);


		/*
		 * Destacado
		 */
		vacante.setDestacado(
				1
		);


		/*
		 * Imagen
		 */
		vacante.setImagen(
				"logo_empresa.png"
		);


		/*
		 * Detalles
		 */
		vacante.setDetalles(
				"Trabajo remoto con horario flexible. "
						+ "Beneficios y capacitacion incluidos."
		);


		/*
		 * =================================================
		 * CATEGORIA
		 * =================================================
		 *
		 * Se utiliza una categoría existente.
		 *
		 * En este ejemplo:
		 *
		 * idCategoria = 1
		 */

		Categoria categoria =
				new Categoria();


		categoria.setId(
				1
		);


		vacante.setCategoria(
				categoria
		);


		/*
		 * =================================================
		 * GUARDAR
		 * =================================================
		 */

		Vacante vacanteGuardada =
				vacantesRepo.save(
						vacante
				);


		/*
		 * =================================================
		 * RESULTADO
		 * =================================================
		 */

		System.out.println();

		System.out.println(
				"=============================================="
		);

		System.out.println(
				"         VACANTE GUARDADA CORRECTAMENTE"
		);

		System.out.println(
				"=============================================="
		);


		System.out.println(
				"ID asignado: "
						+ vacanteGuardada.getId()
		);


		System.out.println(
				"Nombre: "
						+ vacanteGuardada.getNombre()
		);


		System.out.println(
				"Descripcion: "
						+ vacanteGuardada.getDescripcion()
		);


		System.out.println(
				"Salario: $"
						+ vacanteGuardada.getSalario()
		);


		System.out.println(
				"Estatus: "
						+ vacanteGuardada.getEstatus()
		);


		System.out.println(
				"Categoria ID: "
						+ vacanteGuardada
						.getCategoria()
						.getId()
		);


		System.out.println(
				"=============================================="
		);
	}


	/*
	 * =====================================================
	 * MÉTODOS ANTERIORES DE CATEGORÍAS
	 * =====================================================
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


	/*
	 * =====================================================
	 * BORRAR TODAS LAS CATEGORÍAS
	 * =====================================================
	 */

	private void borrarTodasEnBloque() {

		categoriasJPARepo.deleteAllInBatch();
	}


	/*
	 * =====================================================
	 * BUSCAR CATEGORÍAS ORDENADAS
	 * =====================================================
	 */

	private void buscarTodasJPAOrdenadas() {

		List<Categoria> categorias =
				categoriasJPARepo.findAll(
						Sort.by("nombre")
								.descending()
				);


		for (Categoria categoria : categorias) {

			System.out.println(
					categoria.getId()
							+ " "
							+ categoria.getNombre()
			);
		}
	}


	/*
	 * =====================================================
	 * BUSCAR CATEGORÍAS PAGINADAS
	 * =====================================================
	 */

	private void buscarTodasJPAPaginadas() {

		Page<Categoria> page =
				categoriasJPARepo.findAll(
						PageRequest.of(
								3,
								5
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


		for (
				Categoria categoria :
				page.getContent()
		) {

			System.out.println(
					categoria.getId()
							+ " "
							+ categoria.getNombre()
			);
		}
	}


	/*
	 * =====================================================
	 * BUSCAR CATEGORÍAS PAGINADAS Y ORDENADAS
	 * =====================================================
	 */

	private void buscarTodasJPAPaginadasOrdenadas() {

		Page<Categoria> page =
				categoriasJPARepo.findAll(
						PageRequest.of(
								0,
								5,
								Sort.by("nombre")
										.descending()
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


		for (
				Categoria categoria :
				page.getContent()
		) {

			System.out.println(
					categoria.getId()
							+ " "
							+ categoria.getNombre()
			);
		}
	}
}