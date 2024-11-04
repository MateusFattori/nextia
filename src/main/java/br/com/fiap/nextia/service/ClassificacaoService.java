package br.com.fiap.nextia.service;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.springframework.stereotype.Service;
import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.DenseInstance;

@Service
public class ClassificacaoService {

    private Classifier modelo;

    public ClassificacaoService() {
        carregarModelo();
    }

    private void carregarModelo() {
        try (InputStream modeloStream = getClass().getClassLoader().getResourceAsStream("br/com/fiap/nextia/ia/modeloTreinado.model")) {
            if (modeloStream == null) {
                throw new IllegalArgumentException("Arquivo modeloTreinado.model não encontrado no caminho especificado.");
            }

            try (ObjectInputStream ois = new ObjectInputStream(modeloStream)) {
                modelo = (Classifier) ois.readObject();
                System.out.println("Modelo carregado com sucesso!");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String classificarCliente(int pontos, int idade, int tempoFiliacao) {
        if (modelo == null) {
            throw new IllegalStateException("O modelo de IA não foi carregado corretamente.");
        }

        try {
            ArrayList<Attribute> attributes = getAttributes();

            Instances dataset = new Instances("clientes", attributes, 1);
            dataset.setClassIndex(dataset.numAttributes() - 1);

            double[] valores = new double[dataset.numAttributes()];
            valores[0] = pontos; 
            valores[1] = idade;
            valores[2] = tempoFiliacao; 
            valores[3] = 0; 

            Instance instance = new DenseInstance(1.0, valores);
            instance.setDataset(dataset);

            double resultado = modelo.classifyInstance(instance);
            return dataset.classAttribute().value((int) resultado);
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao classificar cliente";
        }
    }

    private ArrayList<Attribute> getAttributes() {
        ArrayList<Attribute> attributes = new ArrayList<>();
        attributes.add(new Attribute("pontos"));
        attributes.add(new Attribute("idade"));
        attributes.add(new Attribute("tempoFiliacao"));

        ArrayList<String> classValues = new ArrayList<>();
        classValues.add("frequente");
        classValues.add("inativo");
        classValues.add("novo");
        attributes.add(new Attribute("perfil", classValues));

        return attributes;
    }
}
