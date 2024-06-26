package org.example.BackEnd;

import java.io.*;
import java.util.ArrayList;

public class GerirRequisitar {
    private static final String REQUISICOES_FILE = "requisitar.ser";
    private static final String RESERVAS_FILE = "reservar.ser";

    public static ArrayList<Requisitar> carregarRequisicoes() {
        return carregarObjetos(REQUISICOES_FILE);
    }

    public static void salvarRequisicoes(ArrayList<Requisitar> requisicoes) {
        salvarObjetos(requisicoes, REQUISICOES_FILE);
    }

    public static ArrayList<Reserva> carregarReservas() {
        return carregarObjetos(RESERVAS_FILE);
    }

    public static void salvarReservas(ArrayList<Reserva> reservas) {
        salvarObjetos(reservas, RESERVAS_FILE);
    }

    public static void adicionarRequisicao(Requisitar requisicao) {
        ArrayList<Requisitar> requisicoes = carregarRequisicoes();
        requisicoes.add(requisicao);
        salvarRequisicoes(requisicoes);
    }

    public static void adicionarReserva(Reserva reserva) {
        ArrayList<Reserva> reservas = carregarReservas();
        reservas.add(reserva);
        salvarReservas(reservas);
    }

    public static int contarReservasPorSocio(Socio socio) {
        ArrayList<Reserva> reservas = carregarReservas();
        int count = 0;
        for (Reserva reserva : reservas) {
            if (reserva.getSocio().getNif().equals(socio.getNif())) {
                count++;
            }
        }
        return count;
    }

    public static int contarRequisicoesPorSocio(Socio socio) {
        ArrayList<Requisitar> requisicoes = carregarRequisicoes();
        int count = 0;
        for (Requisitar requisicao : requisicoes) {
            if (requisicao.getSocio().getNif().equals(socio.getNif())) {
                count++;
            }
        }
        return count;
    }

    @SuppressWarnings("unchecked")
    private static <T> ArrayList<T> carregarObjetos(String fileName) {
        ArrayList<T> objetos = new ArrayList<>();
        File file = new File(fileName);
        if (!file.exists() || file.length() == 0) {
            salvarObjetos(objetos, fileName); // Salvar um novo arquivo vazio se não existir ou estiver vazio
            return objetos;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            objetos = (ArrayList<T>) ois.readObject();
        } catch (FileNotFoundException e) {
            salvarObjetos(objetos, fileName);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return objetos;
    }

    private static <T> void salvarObjetos(ArrayList<T> objetos, String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(objetos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
