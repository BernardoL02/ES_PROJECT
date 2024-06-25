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
        properties.setProperty("diasLeitor", "10");
        properties.setProperty("diasAcademico", "20");
        properties.setProperty("diasEntusiasta", "30");
        properties.setProperty("cotasLeitor", "40.0");
        properties.setProperty("cotasAcademico", "50.0");
        properties.setProperty("cotasEntusiasta", "60.0");
        properties.setProperty("maxLivros", "70");
        properties.setProperty("danificacaoLivro", "80.0");
        properties.setProperty("multa", "90.0");
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
}
