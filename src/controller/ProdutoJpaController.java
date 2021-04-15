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
import model.Produto;

/**
 *
 * @author Tembe
 */
public class ProdutoJpaController implements Serializable {

    public ProdutoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Produto produto) {
        if (produto.getEncomendaCollection() == null) {
            produto.setEncomendaCollection(new ArrayList<Encomenda>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Encomenda> attachedEncomendaCollection = new ArrayList<Encomenda>();
            for (Encomenda encomendaCollectionEncomendaToAttach : produto.getEncomendaCollection()) {
                encomendaCollectionEncomendaToAttach = em.getReference(encomendaCollectionEncomendaToAttach.getClass(), encomendaCollectionEncomendaToAttach.getId());
                attachedEncomendaCollection.add(encomendaCollectionEncomendaToAttach);
            }
            produto.setEncomendaCollection(attachedEncomendaCollection);
            em.persist(produto);
            for (Encomenda encomendaCollectionEncomenda : produto.getEncomendaCollection()) {
                Produto oldProdutoidOfEncomendaCollectionEncomenda = encomendaCollectionEncomenda.getProdutoid();
                encomendaCollectionEncomenda.setProdutoid(produto);
                encomendaCollectionEncomenda = em.merge(encomendaCollectionEncomenda);
                if (oldProdutoidOfEncomendaCollectionEncomenda != null) {
                    oldProdutoidOfEncomendaCollectionEncomenda.getEncomendaCollection().remove(encomendaCollectionEncomenda);
                    oldProdutoidOfEncomendaCollectionEncomenda = em.merge(oldProdutoidOfEncomendaCollectionEncomenda);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Produto produto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Produto persistentProduto = em.find(Produto.class, produto.getId());
            Collection<Encomenda> encomendaCollectionOld = persistentProduto.getEncomendaCollection();
            Collection<Encomenda> encomendaCollectionNew = produto.getEncomendaCollection();
            List<String> illegalOrphanMessages = null;
            for (Encomenda encomendaCollectionOldEncomenda : encomendaCollectionOld) {
                if (!encomendaCollectionNew.contains(encomendaCollectionOldEncomenda)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Encomenda " + encomendaCollectionOldEncomenda + " since its produtoid field is not nullable.");
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
            produto.setEncomendaCollection(encomendaCollectionNew);
            produto = em.merge(produto);
            for (Encomenda encomendaCollectionNewEncomenda : encomendaCollectionNew) {
                if (!encomendaCollectionOld.contains(encomendaCollectionNewEncomenda)) {
                    Produto oldProdutoidOfEncomendaCollectionNewEncomenda = encomendaCollectionNewEncomenda.getProdutoid();
                    encomendaCollectionNewEncomenda.setProdutoid(produto);
                    encomendaCollectionNewEncomenda = em.merge(encomendaCollectionNewEncomenda);
                    if (oldProdutoidOfEncomendaCollectionNewEncomenda != null && !oldProdutoidOfEncomendaCollectionNewEncomenda.equals(produto)) {
                        oldProdutoidOfEncomendaCollectionNewEncomenda.getEncomendaCollection().remove(encomendaCollectionNewEncomenda);
                        oldProdutoidOfEncomendaCollectionNewEncomenda = em.merge(oldProdutoidOfEncomendaCollectionNewEncomenda);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = produto.getId();
                if (findProduto(id) == null) {
                    throw new NonexistentEntityException("The produto with id " + id + " no longer exists.");
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
            Produto produto;
            try {
                produto = em.getReference(Produto.class, id);
                produto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The produto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Encomenda> encomendaCollectionOrphanCheck = produto.getEncomendaCollection();
            for (Encomenda encomendaCollectionOrphanCheckEncomenda : encomendaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Produto (" + produto + ") cannot be destroyed since the Encomenda " + encomendaCollectionOrphanCheckEncomenda + " in its encomendaCollection field has a non-nullable produtoid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(produto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Produto> findProdutoEntities() {
        return findProdutoEntities(true, -1, -1);
    }

    public List<Produto> findProdutoEntities(int maxResults, int firstResult) {
        return findProdutoEntities(false, maxResults, firstResult);
    }

    private List<Produto> findProdutoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Produto.class));
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

    public Produto findProduto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Produto.class, id);
        } finally {
            em.close();
        }
    }

    public int getProdutoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Produto> rt = cq.from(Produto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    // metodos de pesquisa na base de dados
    public List<Produto> findProdutoByCategoria(String categoria){
        
        List<Produto> lista = null;
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Produto.findByCategoria");
        query.setParameter("categoria", "%"+categoria+"%");
        
        try {
           lista = query.getResultList();
        } catch (Exception e) {
           System.out.println("Ocorreu um erro na Pesquisa pelo produto : \n "+e);
        } finally{
            em.close();
        }
        
        return lista;
    }
    
}
