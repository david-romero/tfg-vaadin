/**
 * AdministradorService.java
 * appEducacional
 * 10/02/2014 19:46:01
 * Copyright David Romero Alcaide
 * com.app.applicationservices.services
 */
package com.app.applicationservices.services;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.app.domain.model.types.Administrador;
import com.app.domain.model.types.PadreMadreOTutor;
import com.app.domain.model.types.Persona;
import com.app.domain.model.types.Profesor;
import com.app.domain.repositories.AdministradorRepository;
import com.app.infrastructure.security.Authority;
import com.app.infrastructure.security.LoginService;
import com.app.infrastructure.security.UserAccount;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

@Transactional
@Service
/**
 * @author David Romero Alcaide
 *
 */
public class AdministradorService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2301222240849237937L;

	@Autowired
	/**
	 * 
	 */
	private AdministradorRepository administratorRepository;

	@Autowired
	/**
	 * 
	 */
	private ProfesorService profesorService;

	@Autowired
	/**
	 * 
	 */
	private PadreMadreOTutorService tutorService;

	/**
	 * Constructor
	 */
	public AdministradorService() {
		super();

	}

	// Métodos CRUD
	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public Administrador create() {
		Administrador tutor = new Administrador();
		tutor.setIdentidadConfirmada(false);
		UserAccount account = new UserAccount();
		List<Authority> authorities = Lists.newArrayList();
		Authority auth = new Authority();
		auth.setAuthority(Authority.ADMINISTRADOR);
		authorities.add(auth);
		account.setAuthorities(authorities);
		tutor.setUserAccount(account);

		return tutor;
	}

	/**
	 * 
	 * @author David Romero Alcaide
	 * @return
	 */
	public Collection<Administrador> findAll() {
		List<Administrador> ite;
		ite = administratorRepository.findAll();

		return ite;
	}

	// Other Business methods

	/**
	 * @author David Romero Alcaide
	 * @return
	 */
	public Administrador findPrincipal() {
		Administrador result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;
	}

	public Administrador findByUserAccount(UserAccount userAccount) {
		Assert.notNull(userAccount);

		Administrador result;

		result = administratorRepository.findByUserAccountId(userAccount
				.getId());

		return result;
	}

	/**
	 * 
	 * @author David Romero Alcaide
	 * @param PadreMadreOTutor
	 */
	public void save(Administrador admin) {
		Assert.notNull(admin);
		administratorRepository.save(admin);
	}

	public Collection<Profesor> getAllProfesores() {
		return profesorService.findAll();
	}

	public Collection<PadreMadreOTutor> getAllTutores() {
		return tutorService.findAll();
	}

	public Collection<Profesor> getAllProfesoresSinIdentidad() {
		Collection<Profesor> all = profesorService.findAll();
		Iterable<Profesor> filtrados = Iterables.filter(all,
				new Predicate<Profesor>() {
					
					public boolean apply(Profesor p) {
						return !p.isIdentidadConfirmada();
					}
				});
		return Lists.newArrayList(filtrados);
	}

	public Collection<PadreMadreOTutor> getAllTutoresSinIdentidad() {
		Collection<PadreMadreOTutor> all = tutorService.findAll();
		Iterable<PadreMadreOTutor> filtrados = Iterables.filter(all,
				new Predicate<PadreMadreOTutor>() {
					
					public boolean apply(PadreMadreOTutor p) {
						return !p.isIdentidadConfirmada();
					}
				});
		return Lists.newArrayList(filtrados);
	}

	/**
	 * @author David Romero Alcaide
	 * @param p
	 * @return
	 */
	public int findPersonasSinConfirmar(Administrador p) {
		List<Persona> personasSinIdentidad = Lists.newArrayList();
		personasSinIdentidad.addAll(getAllProfesoresSinIdentidad());
		personasSinIdentidad.addAll(getAllTutoresSinIdentidad());
		return personasSinIdentidad.size();
	}

}
