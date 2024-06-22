package com.example.demo.repository;

import com.example.demo.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WarehouseRepository  extends JpaRepository<Warehouse,Long> {
    boolean existsWarehouseByName(String name);
    Warehouse findWarehouseByWarehouseId(Long id);
    Optional<Warehouse> findWarehouseByWarehouseIdAndIsDeletedFalse(Long id);
    List<Warehouse> findWarehouseByIsDeletedFalse();
}
