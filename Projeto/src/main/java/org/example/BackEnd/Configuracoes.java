package org.example.BackEnd;

public class Configuracoes {

    private static int diasLeitor;
    private static int diasAcademico;
    private static int diasEntusiasta;

    private static float valorLeitor;
    private static float valorAcademico;
    private static float valorEntusiasta;

    private static int maximoLivros;
    private static float multaDiaria;
    private static float danificacaoLivro;

    // Método estático para inicializar as configurações
    public static void inicializarConfiguracoes(int diasLeitor, int diasAcademico, int diasEntusiasta, float valorLeitor, float valorAcademico, float valorEntusiasta, int maximoLivros, float multaDiaria, float danificacaoLivro) {
        Configuracoes.diasLeitor = diasLeitor;
        Configuracoes.diasAcademico = diasAcademico;
        Configuracoes.diasEntusiasta = diasEntusiasta;
        Configuracoes.valorLeitor = valorLeitor;
        Configuracoes.valorAcademico = valorAcademico;
        Configuracoes.valorEntusiasta = valorEntusiasta;
        Configuracoes.maximoLivros = maximoLivros;
        Configuracoes.multaDiaria = multaDiaria;
        Configuracoes.danificacaoLivro = danificacaoLivro;
    }

    public static int getDiasLeitor() {
        return diasLeitor;
    }

    public static int getDiasAcademico() {
        return diasAcademico;
    }

    public static int getDiasEntusiasta() {
        return diasEntusiasta;
    }

    public static float getValorLeitor() {
        return valorLeitor;
    }

    public static float getValorAcademico() {
        return valorAcademico;
    }

    public static float getValorEntusiasta() {
        return valorEntusiasta;
    }

    public static int getMaximoLivros() {
        return maximoLivros;
    }

    public static float getMultaDiaria() {
        return multaDiaria;
    }

    public static float getDanificacaoLivro() {
        return danificacaoLivro;
    }

    public static void setDiasLeitor(int diasLeitor) {
        Configuracoes.diasLeitor = diasLeitor;
    }

    public static void setDiasAcademico(int diasAcademico) {
        Configuracoes.diasAcademico = diasAcademico;
    }

    public static void setDiasEntusiasta(int diasEntusiasta) {
        Configuracoes.diasEntusiasta = diasEntusiasta;
    }

    public static void setValorLeitor(float valorLeitor) {
        Configuracoes.valorLeitor = valorLeitor;
    }

    public static void setValorAcademico(float valorAcademico) {
        Configuracoes.valorAcademico = valorAcademico;
    }

    public static void setValorEntusiasta(float valorEntusiasta) {
        Configuracoes.valorEntusiasta = valorEntusiasta;
    }

    public static void setMaximoLivros(int maximoLivros) {
        Configuracoes.maximoLivros = maximoLivros;
    }

    public static void setMultaDiaria(float multaDiaria) {
        Configuracoes.multaDiaria = multaDiaria;
    }

    public static void setDanificacaoLivro(float danificacaoLivro) {
        Configuracoes.danificacaoLivro = danificacaoLivro;
    }
}
