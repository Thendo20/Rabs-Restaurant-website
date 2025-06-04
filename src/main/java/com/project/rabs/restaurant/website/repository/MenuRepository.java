package com.project.rabs.restaurant.website.repository;

import com.project.rabs.restaurant.website.entity.Menu;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuRepository extends CrudRepository<Menu, Long> {
    Optional<Menu> findByItemName(String name);

    List<Menu> findAllBy();

    Optional<Menu> findByItemId(Long id);

    void deleteByItemName(String itemName);

    Boolean existsByItemName(String itemName);
}
