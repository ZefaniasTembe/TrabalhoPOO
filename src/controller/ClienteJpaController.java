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
import model.Encomenda;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Cliente;

/**
 *
 * @author Tembe
 */
public class ClienteJpaController implements Serializable {

    public ClienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cliente cliente) {
        if (cliente.getEncomendaCollection() == null) {
            cliente.setEncomendaCollection(new ArrayList<Encomenda>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Encomenda> attachedEncomendaCollection = new ArrayList<Encomenda>();
            for (Encomenda encomendaCollectionEncomendaToAttach : cliente.getEncomendaCollection()) {
                encomendaCollectionEncomendaToAttach = em.getReference(encomendaCollectionEncomendaToAttach.getClass(), encomendaCollectionEncomendaToAttach.getId());
                attachedEncomendaCollection.add(encomendaCollectionEncomendaToAttach);
            }
            cliente.setEncomendaCollection(attachedEncomendaCollection);
            em.persist(cliente);
            for (Encomenda encomendaCollectionEncomenda : cliente.getEncomendaCollection()) {
                Cliente oldClienteIdOfEncomendaCollectionEncomenda = encomendaCollectionEncomenda.getClienteId();
                encomendaCollectionEncomenda.setClienteId(cliente);
                encomendaCollectionEncomenda = em.merge(encomendaCollectionEncomenda);
                if (oldClienteIdOfEncomendaCollectionEncomenda != null) {
                    oldClienteIdOfEncomendaCollectionEncomenda.getEncomendaCollection().remove(encomendaCollectionEncomenda);
                    oldClienteIdOfEncomendaCollectionEncomenda = em.merge(oldClienteIdOfEncomendaCollectionEncomenda);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cliente cliente) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente persistentCliente = em.find(Cliente.class, cliente.getId());
            Collection<Encomenda> encomendaCollectionOld = persistentCliente.getEncomendaCollection();
            Collection<Encomenda> encomendaCollectionNew = cliente.getEncomendaCollection();
            List<String> illegalOrphanMessages = null;
            for (Encomenda encomendaCollectionOldEncomenda : encomendaCollectionOld) {
                if (!encomendaCollectionNew.contains(encomendaCollectionOldEncomenda)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Encomenda " + encomendaCollectionOldEncomenda + " since its clienteId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Encomenda> attachedEncomendaCollectionNew = new ArrayList<Encomenda>();
            for (Encomenda encomendaCollectionNewEncomendaToAttach : encomendaCollectionNew) {
                encomendaCollectionNewEncomendaToAttach = em.getReference(encomendaCollectionNewEncomendaToAttach.getClass(), encomendaCollectionNewEncomendaToAttach.getId());
                attachedEncomendaCollectionNew.add(encomendaCollectionNewEncomendaToAttach);
            }
            encomendaCollectionNew = attachedEncomendaCollectionNew;
            cliente.setEncomendaCollection(encomendaCollectionNew);
            cliente = em.merge(cliente);
            for (Encomenda encomendaCollectionNewEncomenda : encomendaCollectionNew) {
                if (!encomendaCollectionOld.contains(encomendaCollectionNewEncomenda)) {
                    Cliente oldClienteIdOfEncomendaCollectionNewEncomenda = encomendaCollectionNewEncomenda.getClienteId();
                    encomendaCollectionNewEncomenda.setClienteId(cliente);
                    encomendaCollectionNewEncomenda = em.merge(encomendaCollectionNewEncomenda);
                    if (oldClienteIdOfEncomendaCollectionNewEncomenda != null && !oldClienteIdOfEncomendaCollectionNewEncomenda.equals(cliente)) {
                        oldClienteIdOfEncomendaCollectionNewEncomenda.getEncomendaCollection().remove(encomendaCollectionNewEncomenda);
                        oldClienteIdOfEncomendaCollectionNewEncomenda = em.merge(oldClienteIdOfEncomendaCollectionNewEncomenda);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cliente.getId();
                if (findCliente(id) == null) {
                    throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.");
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
            Cliente cliente;
            try {
                cliente = em.getReference(Cliente.class, id);
                cliente.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Encomenda> encomendaCollectionOrphanCheck = cliente.getEncomendaCollection();
            for (Encomenda encomendaCollectionOrphanCheckEncomenda : encomendaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cliente (" + cliente + ") cannot be destroyed since the Encomenda " + encomendaCollectionOrphanCheckEncomenda + " in its encomendaCollection field has a non-nullable clienteId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(cliente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cliente> findClienteEntities() {
        return findClienteEntities(true, -1, -1);
    }

    public List<Cliente> findClienteEntities(int maxResults, int firstResult) {
        return findClienteEntities(false, maxResults, firstResult);
    }

    private List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cliente.class));
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

    public Cliente findCliente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }
    
    // criando metodos de busca de dados no banco 
    
    public List<Cliente> findClienteByNome(String nome){
        
        List<Cliente> lista = null;
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Cliente.findByNome");
        query.setParameter("nome", "%"+nome+"%");
        
        try {
           lista = query.getResultList();
        } catch (Exception e) {
           System.out.println("Ocorreu um erro na Pesquisa pelo nome do cliente: \n "+e);
        } finally{
            em.close();
        }
        
        return lista;
    }

    public int getClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cliente> rt = cq.from(Cliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Cliente findCliente(String query) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, query);
        } finally {
            em.close();
        }
    }
    
    
    
}
