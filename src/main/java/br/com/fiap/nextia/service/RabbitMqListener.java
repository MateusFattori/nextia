package br.com.fiap.nextia.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.fiap.nextia.model.Produto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqListener {

    private final ProdutoService produtoService;
    private final ObjectMapper objectMapper;

    public RabbitMqListener(ProdutoService produtoService, ObjectMapper objectMapper) {
        this.produtoService = produtoService;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "task_queue")
    public void receberMensagem(String mensagemJson) {
        try {
            Produto produto = objectMapper.readValue(mensagemJson, Produto.class);
            produtoService.processarProduto(produto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
