package com.test.web.app.test_wep_app.repositories;

import com.test.web.app.test_wep_app.entities.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ImagesRepository extends JpaRepository<ImageEntity, Long> {

	Optional<ImageEntity> findImageEntitiesByName(String name);
	boolean existsByName(String name);

	@Query(nativeQuery = true, value = "SELECT * FROM images ORDER BY RAND() limit 1;")
	Optional<ImageEntity> findRandomEntity();
}
