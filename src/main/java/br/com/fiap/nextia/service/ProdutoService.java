package br.com.fiap.nextia.service;

import br.com.fiap.nextia.model.Produto; // Certifique-se de ter o modelo Produto
import br.com.fiap.nextia.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private ProdutoRepository produtoRepository;

    public void processarProduto(Produto produto) {
        Produto produtoExistente = produtoRepository.findById(produto.getId()).orElse(null);
        
        if (produtoExistente != null) {
            produtoExistente.setNome(produto.getNome());
            produtoExistente.setCategoria(produto.getCategoria());
            produtoExistente.setValor(produto.getValor());

            produtoRepository.save(produtoExistente);

            String emailPara = "nextiamateus@gmail.com"; 
            String assunto = "Produto Atualizado";
            String conteudo = "O produto com ID " + produto.getId() + " foi atualizado com sucesso.";
            emailService.enviarEmail(emailPara, assunto, conteudo);
        } else {
            produtoRepository.save(produto);

            String emailPara = "nextiamateus@gmail.com"; 
            String assunto = "Novo Produto Adicionado";
            String conteudo = "O produto com ID " + produto.getId() + " foi adicionado com sucesso.";
            emailService.enviarEmail(emailPara, assunto, conteudo);
        }
    }
}
