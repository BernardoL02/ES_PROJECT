package org.example;

import org.example.BackEnd.Livro;
import org.example.BackEnd.Socio;
import org.example.BackEnd.TipoDeSocio;
import org.example.FrontEnd.BiblioLiz;

public class Main {
    public static void main(String[] args) {

        Livro livro = new Livro(
                "A Formula de Deus",
                "78853330227",
                "2ª Edição",
                "Romance",
                "Romance Histórico",
                "2 - 10 - 1B",
                "José Rodrigues dos Santos",
                "Gradiva",
                2006,
                "2/5"
        );
        
        // Exibir informações do livro
        System.out.println(livro);

        // Criar um objeto da classe BiblioLiz
        BiblioLiz biblioLiz = new BiblioLiz();

        // Criar objetos da classe Socio e adicioná-los à biblioteca
        Socio socio1 = new Socio(
                "Bernardo",
                "123456789",
                "Rua A, 123 - Bairro B",
                "987654321",
                "bernardo@example.com",
                TipoDeSocio.ENTUSIASTA
        );

        Socio socio2 = new Socio(
                "Ana",
                "987654321",
                "Rua B, 456 - Bairro C",
                "123456789",
                "ana@example.com",
                TipoDeSocio.ACADEMICO
        );

        biblioLiz.addSocio(socio1);
        biblioLiz.addSocio(socio2);

        // Iniciar a aplicação
        BiblioLiz.main(args);
    }
}
