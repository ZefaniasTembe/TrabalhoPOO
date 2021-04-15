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
import model.Actividade;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Encomenda;
import model.Usuario;

/**
 *
 * @author Tembe
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) {
        if (usuario.getActividadeCollection() == null) {
            usuario.setActividadeCollection(new ArrayList<Actividade>());
        }
        if (usuario.getEncomendaCollection() == null) {
            usuario.setEncomendaCollection(new ArrayList<Encomenda>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Actividade> attachedActividadeCollection = new ArrayList<Actividade>();
            for (Actividade actividadeCollectionActividadeToAttach : usuario.getActividadeCollection()) {
                actividadeCollectionActividadeToAttach = em.getReference(actividadeCollectionActividadeToAttach.getClass(), actividadeCollectionActividadeToAttach.getId());
                attachedActividadeCollection.add(actividadeCollectionActividadeToAttach);
            }
            usuario.setActividadeCollection(attachedActividadeCollection);
            Collection<Encomenda> attachedEncomendaCollection = new ArrayList<Encomenda>();
            for (Encomenda encomendaCollectionEncomendaToAttach : usuario.getEncomendaCollection()) {
                encomendaCollectionEncomendaToAttach = em.getReference(encomendaCollectionEncomendaToAttach.getClass(), encomendaCollectionEncomendaToAttach.getId());
                attachedEncomendaCollection.add(encomendaCollectionEncomendaToAttach);
            }
            usuario.setEncomendaCollection(attachedEncomendaCollection);
            em.persist(usuario);
            for (Actividade actividadeCollectionActividade : usuario.getActividadeCollection()) {
                Usuario oldUsuarioIdOfActividadeCollectionActividade = actividadeCollectionActividade.getUsuarioId();
                actividadeCollectionActividade.setUsuarioId(usuario);
                actividadeCollectionActividade = em.merge(actividadeCollectionActividade);
                if (oldUsuarioIdOfActividadeCollectionActividade != null) {
                    oldUsuarioIdOfActividadeCollectionActividade.getActividadeCollection().remove(actividadeCollectionActividade);
                    oldUsuarioIdOfActividadeCollectionActividade = em.merge(oldUsuarioIdOfActividadeCollectionActividade);
                }
            }
            for (Encomenda encomendaCollectionEncomenda : usuario.getEncomendaCollection()) {
                Usuario oldUsuarioIdOfEncomendaCollectionEncomenda = encomendaCollectionEncomenda.getUsuarioId();
                encomendaCollectionEncomenda.setUsuarioId(usuario);
                encomendaCollectionEncomenda = em.merge(encomendaCollectionEncomenda);
                if (oldUsuarioIdOfEncomendaCollectionEncomenda != null) {
                    oldUsuarioIdOfEncomendaCollectionEncomenda.getEncomendaCollection().remove(encomendaCollectionEncomenda);
                    oldUsuarioIdOfEncomendaCollectionEncomenda = em.merge(oldUsuarioIdOfEncomendaCollectionEncomenda);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getId());
            Collection<Actividade> actividadeCollectionOld = persistentUsuario.getActividadeCollection();
            Collection<Actividade> actividadeCollectionNew = usuario.getActividadeCollection();
            Collection<Encomenda> encomendaCollectionOld = persistentUsuario.getEncomendaCollection();
            Collection<Encomenda> encomendaCollectionNew = usuario.getEncomendaCollection();
            List<String> illegalOrphanMessages = null;
            for (Actividade actividadeCollectionOldActividade : actividadeCollectionOld) {
                if (!actividadeCollectionNew.contains(actividadeCollectionOldActividade)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Actividade " + actividadeCollectionOldActividade + " since its usuarioId field is not nullable.");
                }
            }
            for (Encomenda encomendaCollectionOldEncomenda : encomendaCollectionOld) {
                if (!encomendaCollectionNew.contains(encomendaCollectionOldEncomenda)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Encomenda " + encomendaCollectionOldEncomenda + " since its usuarioId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Actividade> attachedActividadeCollectionNew = new ArrayList<Actividade>();
            for (Actividade actividadeCollectionNewActividadeToAttach : actividadeCollectionNew) {
                actividadeCollectionNewActividadeToAttach = em.getReference(actividadeCollectionNewActividadeToAttach.getClass(), actividadeCollectionNewActividadeToAttach.getId());
                attachedActividadeCollectionNew.add(actividadeCollectionNewActividadeToAttach);
            }
            actividadeCollectionNew = attachedActividadeCollectionNew;
            usuario.setActividadeCollection(actividadeCollectionNew);
            Collection<Encomenda> attachedEncomendaCollectionNew = new ArrayList<Encomenda>();
            for (Encomenda encomendaCollectionNewEncomendaToAttach : encomendaCollectionNew) {
                encomendaCollectionNewEncomendaToAttach = em.getReference(encomendaCollectionNewEncomendaToAttach.getClass(), encomendaCollectionNewEncomendaToAttach.getId());
                attachedEncomendaCollectionNew.add(encomendaCollectionNewEncomendaToAttach);
            }
            encomendaCollectionNew = attachedEncomendaCollectionNew;
            usuario.setEncomendaCollection(encomendaCollectionNew);
            usuario = em.merge(usuario);
            for (Actividade actividadeCollectionNewActividade : actividadeCollectionNew) {
                if (!actividadeCollectionOld.contains(actividadeCollectionNewActividade)) {
                    Usuario oldUsuarioIdOfActividadeCollectionNewActividade = actividadeCollectionNewActividade.getUsuarioId();
                    actividadeCollectionNewActividade.setUsuarioId(usuario);
                    actividadeCollectionNewActividade = em.merge(actividadeCollectionNewActividade);
                    if (oldUsuarioIdOfActividadeCollectionNewActividade != null && !oldUsuarioIdOfActividadeCollectionNewActividade.equals(usuario)) {
                        oldUsuarioIdOfActividadeCollectionNewActividade.getActividadeCollection().remove(actividadeCollectionNewActividade);
                        oldUsuarioIdOfActividadeCollectionNewActividade = em.merge(oldUsuarioIdOfActividadeCollectionNewActividade);
                    }
                }
            }
            for (Encomenda encomendaCollectionNewEncomenda : encomendaCollectionNew) {
                if (!encomendaCollectionOld.contains(encomendaCollectionNewEncomenda)) {
                    Usuario oldUsuarioIdOfEncomendaCollectionNewEncomenda = encomendaCollectionNewEncomenda.getUsuarioId();
                    encomendaCollectionNewEncomenda.setUsuarioId(usuario);
                    encomendaCollectionNewEncomenda = em.merge(encomendaCollectionNewEncomenda);
                    if (oldUsuarioIdOfEncomendaCollectionNewEncomenda != null && !oldUsuarioIdOfEncomendaCollectionNewEncomenda.equals(usuario)) {
                        oldUsuarioIdOfEncomendaCollectionNewEncomenda.getEncomendaCollection().remove(encomendaCollectionNewEncomenda);
                        oldUsuarioIdOfEncomendaCollectionNewEncomenda = em.merge(oldUsuarioIdOfEncomendaCollectionNewEncomenda);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuario.getId();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
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
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Actividade> actividadeCollectionOrphanCheck = usuario.getActividadeCollection();
            for (Actividade actividadeCollectionOrphanCheckActividade : actividadeCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Actividade " + actividadeCollectionOrphanCheckActividade + " in its actividadeCollection field has a non-nullable usuarioId field.");
            }
            Collection<Encomenda> encomendaCollectionOrphanCheck = usuario.getEncomendaCollection();
            for (Encomenda encomendaCollectionOrphanCheckEncomenda : encomendaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Encomenda " + encomendaCollectionOrphanCheckEncomenda + " in its encomendaCollection field has a non-nullable usuarioId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    // Metodo de Pesquisa pelo nome do user
    public List<Usuario> findUsuarioByCategoria(String categoria){
        
        List<Usuario> lista = null;
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Usuario.findByNome");
        query.setParameter("nome", "%"+categoria+"%");
        
        try {
           lista = query.getResultList();
        } catch (Exception e) {
           System.out.println("Ocorreu um erro na Pesquisa pelo Usuario : \n "+e);
        } finally{
            em.close();
        }
        
        return lista;
    }
}
