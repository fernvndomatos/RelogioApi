package com.github.fernvndomatos.RelogioApi.repository;

import com.github.fernvndomatos.RelogioApi.entity.Relogio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface RelogioRepository extends JpaRepository <Relogio, UUID> , JpaSpecificationExecutor<Relogio> {

}
