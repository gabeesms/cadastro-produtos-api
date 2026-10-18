package com.gabeesms.cadastroprodutos.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.gabeesms.cadastroprodutos.dto.ProdutoRequest;
import com.gabeesms.cadastroprodutos.exception.RecursoNaoEncontradoException;
import com.gabeesms.cadastroprodutos.model.Produto;
import com.gabeesms.cadastroprodutos.repository.ProdutoRepository;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    @Test
    void criarDevePersistirEMapearProduto() {
        ProdutoRequest request = new ProdutoRequest(" Teclado ", "USB", new BigDecimal("149.90"), 10);
        when(produtoRepository.save(org.mockito.ArgumentMatchers.any(Produto.class)))
                .thenAnswer(invocacao -> {
                    Produto salvo = invocacao.getArgument(0);
                    salvo.setId(1L);
                    return salvo;
                });

        var resposta = produtoService.criar(request);

        assertEquals(1L, resposta.id());
        assertEquals("Teclado", resposta.nome());
        assertEquals(new BigDecimal("149.90"), resposta.preco());
        assertEquals(10, resposta.quantidadeEstoque());
    }

    @Test
    void buscarPorIdDeveFalharQuandoProdutoNaoExiste() {
        when(produtoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class, () -> produtoService.buscarPorId(99L));
    }

    @Test
    void excluirDeveRemoverProdutoEncontrado() {
        Produto produto = new Produto();
        produto.setId(1L);
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));

        produtoService.excluir(1L);

        verify(produtoRepository).delete(produto);
    }
}
