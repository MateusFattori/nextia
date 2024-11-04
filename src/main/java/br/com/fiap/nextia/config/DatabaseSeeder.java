package br.com.fiap.nextia.config;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import br.com.fiap.nextia.model.Cliente;
import br.com.fiap.nextia.model.Produto;
import br.com.fiap.nextia.repository.ClienteRepository;
import br.com.fiap.nextia.repository.ProdutoRepository;

@Configuration
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    ProdutoRepository produtoRepository;

    @Override
    public void run(String... args) throws Exception {
        // Inserindo clientes com categorização
        clienteRepository.saveAll(
            List.of(
                Cliente.builder().id(1L).nome("João").cpf("32456768515").email("joao@gmail.com").senha("123").genero("masculino").dt_nascimento(LocalDate.parse("2024-04-12")).telefone("12345678945876").pontos(0).fidelidade("NÃOFILIADO").categoria(categorizarCliente(0)).build(),
                Cliente.builder().id(2L).nome("Gabi").cpf("12345678528").email("gabi@gmail.com").senha("456").genero("feminino").dt_nascimento(LocalDate.parse("2023-04-09")).telefone("65894236587526").pontos(1).fidelidade("FILIADO").categoria(categorizarCliente(1)).build(),
                Cliente.builder().id(3L).nome("Pedro").cpf("23456789496").email("pedro@gmail.com").senha("789").genero("masculino").dt_nascimento(LocalDate.parse("2015-03-24")).telefone("11986575235489").pontos(556).fidelidade("FILIADO").categoria(categorizarCliente(556)).build(),
                Cliente.builder().id(4L).nome("Otávio").cpf("33445566378").email("otavio@gmail.com").senha("2233").genero("masculino").dt_nascimento(LocalDate.parse("2024-09-02")).telefone("22486597621478").pontos(3).fidelidade("FILIADO").categoria(categorizarCliente(3)).build(),
                Cliente.builder().id(5L).nome("Giovanna").cpf("97554566378").email("giovanna@gmail.com").senha("1245").genero("feminino").dt_nascimento(LocalDate.parse("2023-05-12")).telefone("22894397621478").pontos(27).fidelidade("FILIADO").categoria(categorizarCliente(27)).build()
            ));

        // Inserindo produtos
        produtoRepository.saveAll(
            List.of(
                Produto.builder().id(1L).nome("Sabão").categoria("Limpeza").valor(98.5f).build(),
                Produto.builder().id(2L).nome("Pão").categoria("Padaria").valor(4.20f).build(),
                Produto.builder().id(3L).nome("Salame").categoria("Frios").valor(32.5f).build(),
                Produto.builder().id(4L).nome("Sereal").categoria("Sereal").valor(67.32f).build(),
                Produto.builder().id(5L).nome("Água").categoria("Bebida").valor(2.00f).build()
            ));
    }

    private String categorizarCliente(int pontos) {
        if (pontos >= 1000) {
            return "VIP";
        } else if (pontos >= 500) {
            return "Clientes Regulares";
        } else if (pontos > 0) {
            return "Potenciais Clientes";
        } else {
            return "Clientes Inativos";
        }
    }
}
