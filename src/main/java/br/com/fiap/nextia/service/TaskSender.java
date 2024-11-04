package br.com.fiap.nextia.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.fiap.nextia.config.RabbitConfig;
import br.com.fiap.nextia.model.Produto;

@Service
public class TaskSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void sendTask(Produto produto) {
        try {
            String mensagemJson = objectMapper.writeValueAsString(produto);
            rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, RabbitConfig.TASK_QUEUE, mensagemJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
