package br.dev.mozart.trabalho2.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class FabricaDeEntityManager {
    private static FabricaDeEntityManager fabrica = null;
    private EntityManagerFactory emf = null;

    private FabricaDeEntityManager()
    {
        try {	emf = Persistence.createEntityManagerFactory("trabalho2");
        }
        catch(Throwable e)
        {
            e.printStackTrace();
            System.out.println(">>>>>>>>>> Mensagem de erro: " + e.getMessage());
        }
    }

    public static EntityManager criarEntityManager() {
        if (fabrica == null) {
            fabrica = new FabricaDeEntityManager();
        }
        return fabrica.emf.createEntityManager();
    }
}
