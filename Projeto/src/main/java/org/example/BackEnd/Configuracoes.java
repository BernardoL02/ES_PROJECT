package org.example.BackEnd;

import java.io.*;
import java.util.Properties;

public class Configuracoes {
    private static final String CONFIG_FILE = "config.properties";
    private Properties properties;

    public Configuracoes() {
        properties = new Properties();
        loadProperties();
    }

    private void loadProperties() {
        File configFile = new File(CONFIG_FILE);
        if (configFile.exists()) {
            try (InputStream inputStream = new FileInputStream(configFile)) {
                properties.load(inputStream);
            } catch (IOException e) {
                System.out.println("Erro ao carregar o arquivo de configuração: " + e.getMessage());
            }
        } else {
            createDefaultProperties();
            saveProperties();
        }
    }

    private void createDefaultProperties() {
        properties.setProperty("diasLeitor", "90");
        properties.setProperty("diasAcademico", "30");
        properties.setProperty("diasEntusiasta", "15");
        properties.setProperty("cotasLeitor", "100.0");
        properties.setProperty("cotasAcademico", "30.0");
        properties.setProperty("cotasEntusiasta", "12.5");
        properties.setProperty("maxLivros", "5");
        properties.setProperty("danificacaoLivro", "20.0");
        properties.setProperty("multa", "0.2");
    }

    public void saveProperties() {
        try (OutputStream outputStream = new FileOutputStream(CONFIG_FILE)) {
            properties.store(outputStream, "Configurações da aplicação");
        } catch (IOException e) {
            System.out.println("Erro ao salvar o arquivo de configuração: " + e.getMessage());
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }

    public double getValorCota(TipoDeSocio tipoDeSocio) {
        String key = "cotas" + tipoDeSocio.name();
        String value = properties.getProperty(key);
        if (value != null) {
            try {
                return Double.parseDouble(value);
            } catch (NumberFormatException e) {
                System.err.println("Valor inválido para a cota do tipo de sócio " + tipoDeSocio + ": " + value);
            }
        } else {
            System.err.println("Chave não encontrada no arquivo de configuração: " + key);
        }
        return 0.0;
    }
}
