package com.example.jpa_empleos;

import com.example.jpa_empleos.models.Categoria;
import com.example.jpa_empleos.models.EstatusVacante;
import com.example.jpa_empleos.models.Perfil;
import com.example.jpa_empleos.models.Usuario;
import com.example.jpa_empleos.models.Vacante;

import com.example.jpa_empleos.repository.CategoriasJPARepository;
import com.example.jpa_empleos.repository.CategoriasRepository;
import com.example.jpa_empleos.repository.PerfilesRepository;
import com.example.jpa_empleos.repository.UsuarioRepository;
import com.example.jpa_empleos.repository.VacantesRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Date;
import java.util.LinkedList;
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

	private final PerfilesRepository perfilesRepo;

	private final UsuarioRepository usuarioRepo;


	/*
	 * =====================================================
	 * CONSTRUCTOR
	 * =====================================================
	 */

	public JpaEmpleosApplication(
			CategoriasRepository categoriasRepo,
			CategoriasJPARepository categoriasJPARepo,
			VacantesRepository vacantesRepo,
			PerfilesRepository perfilesRepo,
			UsuarioRepository usuarioRepo
	) {

		this.categoriasRepo = categoriasRepo;
		this.categoriasJPARepo = categoriasJPARepo;
		this.vacantesRepo = vacantesRepo;
		this.perfilesRepo = perfilesRepo;
		this.usuarioRepo = usuarioRepo;
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

		crearPerfiles();
	}


	/*
	 * =====================================================
	 * CREAR PERFILES
	 * =====================================================
	 */

	/**
	 * Metodo para crear los perfiles
	 */
	private void crearPerfiles() {

		perfilesRepo.saveAll(
				obtenerPerfiles()
		);
	}


	/*
	 * =====================================================
	 * OBTENER PERFILES
	 * =====================================================
	 */

	/**
	 * Metodo que regresa una lista de Perfiles
	 * que se tienen en la aplicacion de empleos
	 */
	private List<Perfil> obtenerPerfiles() {

		List<Perfil> perfiles =
				new LinkedList<>();


		/*
		 * Perfil 1
		 */
		Perfil perfil1 =
				new Perfil();

		perfil1.setPerfil(
				"SUPERVISOR"
		);


		/*
		 * Perfil 2
		 */
		Perfil perfil2 =
				new Perfil();

		perfil2.setPerfil(
				"ADMINISTRADOR"
		);


		/*
		 * Perfil 3
		 */
		Perfil perfil3 =
				new Perfil();

		perfil3.setPerfil(
				"USUARIO"
		);


		/*
		 * Agregar perfiles a la lista
		 */
		perfiles.add(
				perfil1
		);

		perfiles.add(
				perfil2
		);

		perfiles.add(
				perfil3
		);


		/*
		 * Regresar lista
		 */
		return perfiles;
	}


	/*
	 * =====================================================
	 * MÉTODOS ANTERIORES DE VACANTES
	 * =====================================================
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
	 * GUARDAR VACANTE
	 * =====================================================
	 */

	public void guardarVacante() {

		Vacante vacante =
				new Vacante();


		vacante.setNombre(
				"Desarrollador Java PRO"
		);


		vacante.setDescripcion(
				"Se busca desarrollador con experiencia "
						+ "en Spring Boot y JPA."
		);


		vacante.setFecha(
				new Date()
		);


		vacante.setSalario(
				25000.0
		);


		vacante.setEstatus(
				EstatusVacante.Creada
		);


		vacante.setDestacado(
				1
		);


		vacante.setImagen(
				"logo_empresa.png"
		);


		vacante.setDetalles(
				"Trabajo remoto con horario flexible. "
						+ "Beneficios y capacitacion incluidos."
		);


		Categoria categoria =
				new Categoria();


		categoria.setId(
				1
		);


		vacante.setCategoria(
				categoria
		);


		Vacante vacanteGuardada =
				vacantesRepo.save(
						vacante
				);


		System.out.println();

		System.out.println(
				"=============================================="
		);

		System.out.println(
				"VACANTE GUARDADA CORRECTAMENTE"
		);

		System.out.println(
				"ID: "
						+ vacanteGuardada.getId()
		);

		System.out.println(
				"Nombre: "
						+ vacanteGuardada.getNombre()
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