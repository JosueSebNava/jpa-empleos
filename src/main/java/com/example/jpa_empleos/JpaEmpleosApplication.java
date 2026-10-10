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

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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

		crearUsuarioConPerfiles();
	}


	/*
	 * =====================================================
	 * CREAR PERFILES
	 * =====================================================
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

	private List<Perfil> obtenerPerfiles() {

		List<Perfil> perfiles =
				new LinkedList<>();


		Perfil perfil1 =
				new Perfil();

		perfil1.setPerfil(
				"SUPERVISOR"
		);


		Perfil perfil2 =
				new Perfil();

		perfil2.setPerfil(
				"ADMINISTRADOR"
		);


		Perfil perfil3 =
				new Perfil();

		perfil3.setPerfil(
				"USUARIO"
		);


		perfiles.add(
				perfil1
		);

		perfiles.add(
				perfil2
		);

		perfiles.add(
				perfil3
		);


		return perfiles;
	}


	/*
	 * =====================================================
	 * CREAR USUARIO CON DOS PERFILES
	 * =====================================================
	 */

	/**
	 * Crear usuario con 2 perfiles
	 * ADMINISTRADOR = 2
	 * USUARIO = 3
	 */
	private void crearUsuarioConPerfiles() {

		/*
		 * Crear usuario
		 */
		Usuario nuevoUsuario =
				new Usuario();


		/*
		 * Datos del usuario
		 */
		nuevoUsuario.setNombre(
				"Josue Sebastian Navarrete Garcia"
		);


		nuevoUsuario.setEmail(
				"navarretegarciasebas@gmail.com"
		);


		nuevoUsuario.setUsername(
				"JosueGarcia"
		);


		nuevoUsuario.setPassword(
				"12345"
		);


		nuevoUsuario.setEstatus(
				1
		);


		nuevoUsuario.setFechaRegistro(
				LocalDate.now()
		);


		/*
		 * =================================================
		 * PERFIL ADMINISTRADOR
		 * =================================================
		 */

		Perfil perfil1 =
				new Perfil();

		perfil1.setId(
				2
		);


		/*
		 * =================================================
		 * PERFIL USUARIO
		 * =================================================
		 */

		Perfil perfil2 =
				new Perfil();

		perfil2.setId(
				3
		);


		/*
		 * =================================================
		 * CREAR CONJUNTO DE PERFILES
		 * =================================================
		 */

		Set<Perfil> perfilesUsuario =
				new HashSet<>();


		perfilesUsuario.add(
				perfil1
		);


		perfilesUsuario.add(
				perfil2
		);


		/*
		 * Asignar perfiles al usuario
		 */
		nuevoUsuario.setPerfiles(
				perfilesUsuario
		);


		/*
		 * Guardar usuario
		 */
		Usuario usuarioGuardado =
				usuarioRepo.save(
						nuevoUsuario
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
				"       USUARIO GUARDADO CORRECTAMENTE"
		);

		System.out.println(
				"=============================================="
		);


		System.out.println(
				"ID: "
						+ usuarioGuardado.getId()
		);


		System.out.println(
				"Nombre: "
						+ usuarioGuardado.getNombre()
		);


		System.out.println(
				"Email: "
						+ usuarioGuardado.getEmail()
		);


		System.out.println(
				"Username: "
						+ usuarioGuardado.getUsername()
		);


		System.out.println(
				"Fecha registro: "
						+ usuarioGuardado.getFechaRegistro()
		);


		System.out.println(
				"Perfiles asignados:"
		);


		for (
				Perfil perfil :
				usuarioGuardado.getPerfiles()
		) {

			System.out.println(
					"Perfil ID: "
							+ perfil.getId()
			);
		}


		System.out.println(
				"=============================================="
		);
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


		System.out.println(
				"Vacante guardada correctamente: "
						+ vacanteGuardada.getId()
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