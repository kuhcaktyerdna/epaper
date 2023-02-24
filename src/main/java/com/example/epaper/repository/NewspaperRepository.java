package com.example.epaper.repository;

import com.example.epaper.model.Newspaper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository layer for {@link Newspaper}s
 */
@Repository
public interface NewspaperRepository extends JpaRepository<Newspaper, Long> {

}
