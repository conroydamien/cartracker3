package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Maintrec;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Maintrec entity.
 */
@SuppressWarnings("unused")
public interface MaintrecRepository extends JpaRepository<Maintrec,Long> {

}
