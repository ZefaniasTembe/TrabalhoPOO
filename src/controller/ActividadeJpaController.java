/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Actividade;
import model.Encomenda;
import model.Usuario;

/**
 *
 * @author Tembe
 */
public class ActividadeJpaController implements Serializable {

    public ActividadeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Actividade actividade) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Encomenda encomendaId = actividade.getEncomendaId();
            if (encomendaId != null) {
                encomendaId = em.getReference(encomendaId.getClass(), encomendaId.getId());
                actividade.setEncomendaId(encomendaId);
            }
            Usuario usuarioId = actividade.getUsuarioId();
            if (usuarioId != null) {
                usuarioId = em.getReference(usuarioId.getClass(), usuarioId.getId());
                actividade.setUsuarioId(usuarioId);
            }
            em.persist(actividade);
            if (encomendaId != null) {
                encomendaId.getActividadeCollection().add(actividade);
                encomendaId = em.merge(encomendaId);
            }
            if (usuarioId != null) {
                usuarioId.getActividadeCollection().add(actividade);
                usuarioId = em.merge(usuarioId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Actividade actividade) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Actividade persistentActividade = em.find(Actividade.class, actividade.getId());
            Encomenda encomendaIdOld = persistentActividade.getEncomendaId();
            Encomenda encomendaIdNew = actividade.getEncomendaId();
            Usuario usuarioIdOld = persistentActividade.getUsuarioId();
            Usuario usuarioIdNew = actividade.getUsuarioId();
            if (encomendaIdNew != null) {
                encomendaIdNew = em.getReference(encomendaIdNew.getClass(), encomendaIdNew.getId());
                actividade.setEncomendaId(encomendaIdNew);
            }
            if (usuarioIdNew != null) {
                usuarioIdNew = em.getReference(usuarioIdNew.getClass(), usuarioIdNew.getId());
                actividade.setUsuarioId(usuarioIdNew);
            }
            actividade = em.merge(actividade);
            if (encomendaIdOld != null && !encomendaIdOld.equals(encomendaIdNew)) {
                encomendaIdOld.getActividadeCollection().remove(actividade);
                encomendaIdOld = em.merge(encomendaIdOld);
            }
            if (encomendaIdNew != null && !encomendaIdNew.equals(encomendaIdOld)) {
                encomendaIdNew.getActividadeCollection().add(actividade);
                encomendaIdNew = em.merge(encomendaIdNew);
            }
            if (usuarioIdOld != null && !usuarioIdOld.equals(usuarioIdNew)) {
                usuarioIdOld.getActividadeCollection().remove(actividade);
                usuarioIdOld = em.merge(usuarioIdOld);
            }
            if (usuarioIdNew != null && !usuarioIdNew.equals(usuarioIdOld)) {
                usuarioIdNew.getActividadeCollection().add(actividade);
                usuarioIdNew = em.merge(usuarioIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = actividade.getId();
                if (findActividade(id) == null) {
                    throw new NonexistentEntityException("The actividade with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Actividade actividade;
            try {
                actividade = em.getReference(Actividade.class, id);
                actividade.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The actividade with id " + id + " no longer exists.", enfe);
            }
            Encomenda encomendaId = actividade.getEncomendaId();
            if (encomendaId != null) {
                encomendaId.getActividadeCollection().remove(actividade);
                encomendaId = em.merge(encomendaId);
            }
            Usuario usuarioId = actividade.getUsuarioId();
            if (usuarioId != null) {
                usuarioId.getActividadeCollection().remove(actividade);
                usuarioId = em.merge(usuarioId);
            }
            em.remove(actividade);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Actividade> findActividadeEntities() {
        return findActividadeEntities(true, -1, -1);
    }

    public List<Actividade> findActividadeEntities(int maxResults, int firstResult) {
        return findActividadeEntities(false, maxResults, firstResult);
    }

    private List<Actividade> findActividadeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Actividade.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Actividade findActividade(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Actividade.class, id);
        } finally {
            em.close();
        }
    }

    public int getActividadeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Actividade> rt = cq.from(Actividade.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
