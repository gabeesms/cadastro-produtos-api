package com.gabeesms.cadastroprodutos.service;

import com.gabeesms.cadastroprodutos.dto.ProdutoRequest;
import com.gabeesms.cadastroprodutos.dto.ProdutoResponse;
import com.gabeesms.cadastroprodutos.exception.RecursoNaoEncontradoException;
import com.gabeesms.cadastroprodutos.model.Produto;
import com.gabeesms.cadastroprodutos.repository.ProdutoRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<ProdutoResponse> listar() {
        return produtoRepository.findAll().stream()
                .map(ProdutoService::paraResponse)
                .toList();
    }

    public ProdutoResponse buscarPorId(Long id) {
        return paraResponse(encontrarProduto(id));
    }

    @Transactional
    public ProdutoResponse criar(ProdutoRequest request) {
        Produto produto = new Produto();
        aplicarDados(produto, request);
        return paraResponse(produtoRepository.save(produto));
    }

    @Transactional
    public ProdutoResponse atualizar(Long id, ProdutoRequest request) {
        Produto produto = encontrarProduto(id);
        aplicarDados(produto, request);
        return paraResponse(produtoRepository.save(produto));
    }

    @Transactional
    public void excluir(Long id) {
        Produto produto = encontrarProduto(id);
        produtoRepository.delete(produto);
    }

    private Produto encontrarProduto(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Produto com id " + id + " não encontrado"));
    }

    private void aplicarDados(Produto produto, ProdutoRequest request) {
        produto.setNome(request.nome().trim());
        produto.setDescricao(request.descricao());
        produto.setPreco(request.preco());
        produto.setQuantidadeEstoque(request.quantidadeEstoque());
    }

    private static ProdutoResponse paraResponse(Produto produto) {
        return new ProdutoResponse(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getQuantidadeEstoque(),
                produto.getDataCriacao());
    }
}
