package org.example;

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

        // Criar um objeto da classe Socio
        Socio socio = new Socio(
                "Bernardo",
                "123456789",
                "Rua A, 123 - Bairro B",
                "987654321",
                "bernardo@example.com",
                TipoDeSocio.ENTUSIASTA
        );

        // Exibir informações do sócio
        System.out.println(socio);

        BiblioLiz.main(args);
    }
}