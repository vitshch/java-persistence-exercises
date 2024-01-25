package com.bobocode.dao;

import com.bobocode.model.Photo;
import com.bobocode.model.PhotoComment;
import com.bobocode.util.ExerciseNotCompletedException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Please note that you should not use auto-commit mode for your implementation.
 */
public class PhotoDaoImpl implements PhotoDao {

    private EntityManagerFactory entityManagerFactory;

    public PhotoDaoImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void save(Photo photo) {
        doInTxWithoutResult(em -> em.persist(photo));
    }

    @Override
    public Photo findById(long id) {
        return doInTx(em -> em.find(Photo.class, id));
    }

    @Override
    public List<Photo> findAll() {
        return doInTx(em -> em.createQuery("select p from Photo p", Photo.class).getResultList());
    }

    @Override
    public void remove(Photo photo) {
        doInTxWithoutResult(em -> {
            Photo reference = em.getReference(Photo.class, photo.getId());
            em.remove(reference);
        });
    }

    @Override
    public void addComment(long photoId, String comment) {
        doInTxWithoutResult(em -> {
            var photo = em.getReference(Photo.class, photoId);
            PhotoComment photoComment = new PhotoComment();
            photoComment.setText(comment);
            photoComment.setPhoto(photo);
            em.persist(photoComment);
        });
    }

    private void doInTxWithoutResult(Consumer<EntityManager> action) {
        doInTx(em -> {
            action.accept(em);
            return null;
        });
    }

    private <T> T doInTx(Function<EntityManager, T> action) {
        var entityManager = entityManagerFactory.createEntityManager();
        var transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            var result = action.apply(entityManager);
            transaction.commit();
            return result;
        } catch (Throwable ex) {
            transaction.rollback();
            throw new RuntimeException("Unable to perform action in transaction");
        } finally {
            entityManager.close();
        }
    }
}
