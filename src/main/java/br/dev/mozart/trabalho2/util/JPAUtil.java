package br.dev.mozart.trabalho2.util;
import br.dev.mozart.trabalho2.excecao.InfraestruturaException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JPAUtil {

    private static final Logger logger = LoggerFactory.getLogger(JPAUtil.class);

    private static JPAUtil jpaUtil = null;
    private final EntityManagerFactory entityManagerFactory;
    private static final ThreadLocal<EntityManager> threadEntityManager = new ThreadLocal<>();
    private static final ThreadLocal<EntityTransaction> threadTransaction = new ThreadLocal<>();
    private static final ThreadLocal<Integer> threadTransactionCount = new ThreadLocal<>();

    private JPAUtil() {
        entityManagerFactory = Persistence.createEntityManagerFactory("trabalho03");
    }

    public static void beginTransaction() {
        EntityTransaction transaction = threadTransaction.get();
        Integer transactionCount = threadTransactionCount.get();

        if (transaction == null) {
            try {
                transaction = getEntityManager().getTransaction();
                transaction.begin();
                transactionCount = 1;
                threadTransactionCount.set(transactionCount);
                threadTransaction.set(transaction);
                logger.info(">>>> Criou a transação");
            }
            catch (RuntimeException ex) {
                throw new InfraestruturaException(ex);
            }
        }
        else {
            transactionCount++;
            threadTransactionCount.set(transactionCount);
        }
    }

    public static EntityManager getEntityManager() {
        EntityManager entityManager;

        try {
            if (jpaUtil == null) {
                jpaUtil = new JPAUtil();
            }
            entityManager = threadEntityManager.get();
            if (entityManager == null) {
                entityManager = jpaUtil.entityManagerFactory.createEntityManager();
                threadEntityManager.set(entityManager);
                logger.info(">>>> Criou o entity manager");
            }
        }
        catch (RuntimeException ex) {
            throw new InfraestruturaException(ex);
        }

        return entityManager;
    }

    public static void commitTransaction() {
        EntityTransaction transaction = threadTransaction.get();
        Integer transactionCount = threadTransactionCount.get();

        try {
            if (transaction != null && transaction.isActive()) {
                transactionCount--;
                if (transactionCount == 0) {
                    transaction.commit();
                    threadTransaction.set(null);
                    threadTransactionCount.set(null);
                    logger.info(">>>> Comitou a transação");
                }
                else {
                    threadTransactionCount.set(transactionCount);
                }
            }
        }
        catch (RuntimeException exception) {
            try {
                rollbackTransaction();
            } catch (RuntimeException innerException) {
                throw new InfraestruturaException(innerException);
            }
            throw new InfraestruturaException(exception);
        }
    }

    public static void rollbackTransaction() {
        EntityTransaction transaction = threadTransaction.get();

        try {
            threadTransaction.set(null);
            threadTransactionCount.set(null);
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
                logger.info(">>>> Deu rollback na transação");
            }
        }
        catch (RuntimeException ex) {
            throw new InfraestruturaException(ex);
        }
        finally {
            closeEntityManager();
        }
    }

    public static void closeEntityManager() {
        EntityTransaction transaction = threadTransaction.get();
        Integer transactionCount = threadTransactionCount.get();
        if (transactionCount == null || transactionCount == 0) {
            if (transaction != null && transaction.isActive()) {
                rollbackTransaction();
            }

            EntityManager entityManager = threadEntityManager.get();

            threadEntityManager.set(null);
            threadTransactionCount.set(null);

            try {
                if (entityManager != null && entityManager.isOpen()) {
                    entityManager.close();
                    logger.info(">>>> Fechou o entity manager");
                }
            }
            catch (RuntimeException ex) {
                throw new InfraestruturaException(ex);
            }
        }
    }
}