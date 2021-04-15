/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Usuario;
import model.Cliente;
import model.Produto;
import model.Actividade;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Encomenda;

/**
 *
 * @author Tembe
 */
public class EncomendaJpaController implements Serializable {

    public EncomendaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Encomenda encomenda) {
        if (encomenda.getActividadeCollection() == null) {
            encomenda.setActividadeCollection(new ArrayList<Actividade>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuarioId = encomenda.getUsuarioId();
            if (usuarioId != null) {
                usuarioId = em.getReference(usuarioId.getClass(), usuarioId.getId());
                encomenda.setUsuarioId(usuarioId);
            }
            Cliente clienteId = encomenda.getClienteId();
            if (clienteId != null) {
                clienteId = em.getReference(clienteId.getClass(), clienteId.getId());
                encomenda.setClienteId(clienteId);
            }
            Produto produtoid = encomenda.getProdutoid();
            if (produtoid != null) {
                produtoid = em.getReference(produtoid.getClass(), produtoid.getId());
                encomenda.setProdutoid(produtoid);
            }
            Collection<Actividade> attachedActividadeCollection = new ArrayList<Actividade>();
            for (Actividade actividadeCollectionActividadeToAttach : encomenda.getActividadeCollection()) {
                actividadeCollectionActividadeToAttach = em.getReference(actividadeCollectionActividadeToAttach.getClass(), actividadeCollectionActividadeToAttach.getId());
                attachedActividadeCollection.add(actividadeCollectionActividadeToAttach);
            }
            encomenda.setActividadeCollection(attachedActividadeCollection);
            em.persist(encomenda);
            if (usuarioId != null) {
                usuarioId.getEncomendaCollection().add(encomenda);
                usuarioId = em.merge(usuarioId);
            }
            if (clienteId != null) {
                clienteId.getEncomendaCollection().add(encomenda);
                clienteId = em.merge(clienteId);
            }
            if (produtoid != null) {
                produtoid.getEncomendaCollection().add(encomenda);
                produtoid = em.merge(produtoid);
            }
            for (Actividade actividadeCollectionActividade : encomenda.getActividadeCollection()) {
                Encomenda oldEncomendaIdOfActividadeCollectionActividade = actividadeCollectionActividade.getEncomendaId();
                actividadeCollectionActividade.setEncomendaId(encomenda);
                actividadeCollectionActividade = em.merge(actividadeCollectionActividade);
                if (oldEncomendaIdOfActividadeCollectionActividade != null) {
                    oldEncomendaIdOfActividadeCollectionActividade.getActividadeCollection().remove(actividadeCollectionActividade);
                    oldEncomendaIdOfActividadeCollectionActividade = em.merge(oldEncomendaIdOfActividadeCollectionActividade);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Encomenda encomenda) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Encomenda persistentEncomenda = em.find(Encomenda.class, encomenda.getId());
            Usuario usuarioIdOld = persistentEncomenda.getUsuarioId();
            Usuario usuarioIdNew = encomenda.getUsuarioId();
            Cliente clienteIdOld = persistentEncomenda.getClienteId();
            Cliente clienteIdNew = encomenda.getClienteId();
            Produto produtoidOld = persistentEncomenda.getProdutoid();
            Produto produtoidNew = encomenda.getProdutoid();
            Collection<Actividade> actividadeCollectionOld = persistentEncomenda.getActividadeCollection();
            Collection<Actividade> actividadeCollectionNew = encomenda.getActividadeCollection();
            List<String> illegalOrphanMessages = null;
            for (Actividade actividadeCollectionOldActividade : actividadeCollectionOld) {
                if (!actividadeCollectionNew.contains(actividadeCollectionOldActividade)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Actividade " + actividadeCollectionOldActividade + " since its encomendaId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (usuarioIdNew != null) {
                usuarioIdNew = em.getReference(usuarioIdNew.getClass(), usuarioIdNew.getId());
                encomenda.setUsuarioId(usuarioIdNew);
            }
            if (clienteIdNew != null) {
                clienteIdNew = em.getReference(clienteIdNew.getClass(), clienteIdNew.getId());
                encomenda.setClienteId(clienteIdNew);
            }
            if (produtoidNew != null) {
                produtoidNew = em.getReference(produtoidNew.getClass(), produtoidNew.getId());
                encomenda.setProdutoid(produtoidNew);
            }
            Collection<Actividade> attachedActividadeCollectionNew = new ArrayList<Actividade>();
            for (Actividade actividadeCollectionNewActividadeToAttach : actividadeCollectionNew) {
                actividadeCollectionNewActividadeToAttach = em.getReference(actividadeCollectionNewActividadeToAttach.getClass(), actividadeCollectionNewActividadeToAttach.getId());
                attachedActividadeCollectionNew.add(actividadeCollectionNewActividadeToAttach);
            }
            actividadeCollectionNew = attachedActividadeCollectionNew;
            encomenda.setActividadeCollection(actividadeCollectionNew);
            encomenda = em.merge(encomenda);
            if (usuarioIdOld != null && !usuarioIdOld.equals(usuarioIdNew)) {
                usuarioIdOld.getEncomendaCollection().remove(encomenda);
                usuarioIdOld = em.merge(usuarioIdOld);
            }
            if (usuarioIdNew != null && !usuarioIdNew.equals(usuarioIdOld)) {
                usuarioIdNew.getEncomendaCollection().add(encomenda);
                usuarioIdNew = em.merge(usuarioIdNew);
            }
            if (clienteIdOld != null && !clienteIdOld.equals(clienteIdNew)) {
                clienteIdOld.getEncomendaCollection().remove(encomenda);
                clienteIdOld = em.merge(clienteIdOld);
            }
            if (clienteIdNew != null && !clienteIdNew.equals(clienteIdOld)) {
                clienteIdNew.getEncomendaCollection().add(encomenda);
                clienteIdNew = em.merge(clienteIdNew);
            }
            if (produtoidOld != null && !produtoidOld.equals(produtoidNew)) {
                produtoidOld.getEncomendaCollection().remove(encomenda);
                produtoidOld = em.merge(produtoidOld);
            }
            if (produtoidNew != null && !produtoidNew.equals(produtoidOld)) {
                produtoidNew.getEncomendaCollection().add(encomenda);
                produtoidNew = em.merge(produtoidNew);
            }
            for (Actividade actividadeCollectionNewActividade : actividadeCollectionNew) {
                if (!actividadeCollectionOld.contains(actividadeCollectionNewActividade)) {
                    Encomenda oldEncomendaIdOfActividadeCollectionNewActividade = actividadeCollectionNewActividade.getEncomendaId();
                    actividadeCollectionNewActividade.setEncomendaId(encomenda);
                    actividadeCollectionNewActividade = em.merge(actividadeCollectionNewActividade);
                    if (oldEncomendaIdOfActividadeCollectionNewActividade != null && !oldEncomendaIdOfActividadeCollectionNewActividade.equals(encomenda)) {
                        oldEncomendaIdOfActividadeCollectionNewActividade.getActividadeCollection().remove(actividadeCollectionNewActividade);
                        oldEncomendaIdOfActividadeCollectionNewActividade = em.merge(oldEncomendaIdOfActividadeCollectionNewActividade);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = encomenda.getId();
                if (findEncomenda(id) == null) {
                    throw new NonexistentEntityException("The encomenda with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Encomenda encomenda;
            try {
                encomenda = em.getReference(Encomenda.class, id);
                encomenda.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The encomenda with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Actividade> actividadeCollectionOrphanCheck = encomenda.getActividadeCollection();
            for (Actividade actividadeCollectionOrphanCheckActividade : actividadeCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Encomenda (" + encomenda + ") cannot be destroyed since the Actividade " + actividadeCollectionOrphanCheckActividade + " in its actividadeCollection field has a non-nullable encomendaId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuario usuarioId = encomenda.getUsuarioId();
            if (usuarioId != null) {
                usuarioId.getEncomendaCollection().remove(encomenda);
                usuarioId = em.merge(usuarioId);
            }
            Cliente clienteId = encomenda.getClienteId();
            if (clienteId != null) {
                clienteId.getEncomendaCollection().remove(encomenda);
                clienteId = em.merge(clienteId);
            }
            Produto produtoid = encomenda.getProdutoid();
            if (produtoid != null) {
                produtoid.getEncomendaCollection().remove(encomenda);
                produtoid = em.merge(produtoid);
            }
            em.remove(encomenda);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Encomenda> findEncomendaEntities() {
        return findEncomendaEntities(true, -1, -1);
    }

    public List<Encomenda> findEncomendaEntities(int maxResults, int firstResult) {
        return findEncomendaEntities(false, maxResults, firstResult);
    }

    private List<Encomenda> findEncomendaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Encomenda.class));
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

    public Encomenda findEncomenda(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Encomenda.class, id);
        } finally {
            em.close();
        }
    }

    public int getEncomendaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Encomenda> rt = cq.from(Encomenda.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
