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
import br.com.fiap.nextia.service.ClassificacaoService;

@Configuration
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    ClienteRepository clienteRepository; 

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    ClassificacaoService classificacaoService;

    @Override
    public void run(String... args) throws Exception {
        clienteRepository.deleteAll();
        produtoRepository.deleteAll();

        List<Cliente> clientes = List.of(
            Cliente.builder()
                .nome("João")
                .cpf("32456768515")
                .email("joao@gmail.com")
                .senha("Joao@1234") 
                .genero("masculino")
                .dt_nascimento(LocalDate.parse("1990-04-12"))
                .telefone("(11) 91234-5678")
                .pontos(120)
                .fidelidade("AFILIADO")
                .dt_filiacao(LocalDate.parse("2021-01-01"))
                .build(),
            Cliente.builder()
                .nome("Gabi")
                .cpf("12345678528")
                .email("gabi@gmail.com")
                .senha("Gabi#2023")
                .genero("feminino")
                .dt_nascimento(LocalDate.parse("1995-04-09")) 
                .telefone("(11) 92345-6789")
                .pontos(95)
                .fidelidade("AFILIADO")
                .dt_filiacao(LocalDate.parse("2021-10-15"))
                .build(),
            Cliente.builder()
                .nome("Carlos")
                .cpf("98765432100")
                .email("carlos@gmail.com")
                .senha("C@rlos123")
                .genero("masculino")
                .dt_nascimento(LocalDate.parse("1985-05-20"))
                .telefone("(11) 93456-7890")
                .pontos(150)
                .fidelidade("AFILIADO")
                .dt_filiacao(LocalDate.parse("2019-08-10"))
                .build(),
            Cliente.builder()
                .nome("Ana")
                .cpf("32165498700")
                .email("ana@gmail.com")
                .senha("Ana$2021")
                .genero("feminino")
                .dt_nascimento(LocalDate.parse("1990-12-01"))
                .telefone("(11) 94567-8901") 
                .pontos(85)
                .fidelidade("AFILIADO")
                .dt_filiacao(LocalDate.parse("2020-06-01"))
                .build(),
            Cliente.builder()
                .nome("Pedro")
                .cpf("15975348625")
                .email("pedro@gmail.com")
                .senha("P3dro!2023")
                .genero("masculino")
                .dt_nascimento(LocalDate.parse("1995-01-15"))
                .telefone("(11) 95678-9012") 
                .pontos(30)
                .fidelidade("AFILIADO")
                .dt_filiacao(LocalDate.parse("2023-03-20"))
                .build(), 
            Cliente.builder()
                .nome("Mariana")
                .cpf("45678932100")
                .email("mariana@gmail.com")
                .senha("M@riana2020") 
                .genero("feminino")
                .dt_nascimento(LocalDate.parse("2000-07-22"))
                .telefone("(11) 96789-0123") 
                .pontos(40)
                .fidelidade("AFILIADO")
                .dt_filiacao(LocalDate.parse("2020-01-05"))
                .build(),
            Cliente.builder()
                .nome("Fernando")
                .cpf("11122233344")
                .email("fernando@gmail.com")
                .senha("F3rnando@2024")
                .genero("masculino")
                .dt_nascimento(LocalDate.parse("1992-02-14"))
                .telefone("(11) 97834-5678")
                .pontos(0) 
                .fidelidade("NÃOFILIADO")
                .dt_filiacao(LocalDate.parse("2024-02-01"))
                .build(),
            Cliente.builder()
                .nome("Luciana")
                .cpf("22233344455")
                .email("luciana@gmail.com")
                .senha("Luci@2024")
                .genero("feminino")
                .dt_nascimento(LocalDate.parse("1988-11-21"))
                .telefone("(11) 98876-5432")
                .pontos(300)
                .fidelidade("AFILIADO")
                .dt_filiacao(LocalDate.parse("2022-05-10"))
                .build(),
            Cliente.builder()
                .nome("Roberto")
                .cpf("33344455566")
                .email("roberto@gmail.com")
                .senha("R0berto#2024")
                .genero("masculino")
                .dt_nascimento(LocalDate.parse("1993-08-09"))
                .telefone("(11) 97789-0123")
                .pontos(20)
                .fidelidade("AFILIADO")
                .dt_filiacao(LocalDate.parse("2023-07-20"))
                .build(),
            Cliente.builder()
                .nome("Camila")
                .cpf("44455566677")
                .email("camila@gmail.com")
                .senha("C@mil@2024")
                .genero("feminino")
                .dt_nascimento(LocalDate.parse("1996-03-30"))
                .telefone("(11) 96678-9123")
                .pontos(0)
                .fidelidade("NÃOFILIADO")
                .dt_filiacao(LocalDate.parse("2024-03-15"))
                .build(),
            Cliente.builder()
                .nome("Thiago")
                .cpf("55566677788")
                .email("thiago@gmail.com")
                .senha("Thi@go2024")
                .genero("masculino")
                .dt_nascimento(LocalDate.parse("1991-09-19"))
                .telefone("(11) 95567-8910")
                .pontos(75)
                .fidelidade("AFILIADO")
                .dt_filiacao(LocalDate.parse("2021-11-01"))
                .build(),
            Cliente.builder()
                .nome("Patrícia")
                .cpf("66677788899")
                .email("patricia@gmail.com")
                .senha("P@tricia2024")
                .genero("feminino")
                .dt_nascimento(LocalDate.parse("1987-05-25"))
                .telefone("(11) 94456-7891")
                .pontos(180)
                .fidelidade("AFILIADO")
                .dt_filiacao(LocalDate.parse("2020-10-20"))
                .build()
        );

        for (Cliente cliente : clientes) {
            int idade = LocalDate.now().getYear() - cliente.getDt_nascimento().getYear();
            int tempoFiliacao = (int) LocalDate.now().until(cliente.getDt_filiacao()).toTotalMonths();
            
            String classificacao = classificacaoService.classificarCliente(cliente.getPontos(), idade, tempoFiliacao);
            cliente.setClassificacao(classificacao);
            cliente.setPerfilCompra(classificacao); 
        }

        // Salva os clientes e produtos no repositório
        clienteRepository.saveAll(clientes); 

        produtoRepository.saveAll(
            List.of(
                Produto.builder().nome("Sabão").categoria("Limpeza").valor(98.5f).build(),
                Produto.builder().nome("Pão").categoria("Padaria").valor(4.20f).build(),
                Produto.builder().nome("Salame").categoria("Frios").valor(32.5f).build(),
                Produto.builder().nome("Cereal").categoria("Cereal").valor(67.32f).build(),
                Produto.builder().nome("Água").categoria("Bebida").valor(2.00f).build(),
                Produto.builder().nome("Iogurte").categoria("Laticínios").valor(5.75f).build(),
                Produto.builder().nome("Queijo").categoria("Laticínios").valor(15.30f).build(),
                Produto.builder().nome("Maçã").categoria("Frutas").valor(3.10f).build(),
                Produto.builder().nome("Arroz").categoria("Alimentos").valor(20.45f).build(),
                Produto.builder().nome("Feijão").categoria("Alimentos").valor(10.90f).build()
            )
        );
    }
}
