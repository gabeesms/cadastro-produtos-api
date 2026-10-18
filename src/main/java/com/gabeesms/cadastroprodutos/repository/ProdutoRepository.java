package com.gabeesms.cadastroprodutos.repository;

import com.gabeesms.cadastroprodutos.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
