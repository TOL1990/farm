package model.dao.impl;

import java.io.Serializable;

/**
 * Created by Taras on 09.03.2017.
 */
public class AbstractDAO<PK extends Serializable, T> {

//    private final Class<T> persistentClass;
//    private SessionFactory sessionFactory;
//
//
//    @SuppressWarnings("unchecked")
//    public AbstractDAO() {
//        this.persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
//    }
//
//    protected Session getSession() {
//        Session session = null;
//
//        try {
//            session = HibernateUtil.getSessionFactory().openSession();
//        } catch (HibernateException e) {
//            session = sessionFactory.openSession();
//        }
//        return session;
//    }
//
//    @SuppressWarnings("unchecked")
//    public T getByKey(PK key) {
//        return (T) getSession().get(persistentClass, key);
//    }
//
//    @Transactional
//    public void persist(T entity) {
//        Session s = getSession();
//        s.beginTransaction();
//        s.persist(entity);
//        s.getTransaction().commit();
//        s.close();
//    }
//
//    public void delete(T entity) {
//        getSession().delete(entity);
//    }
//
//    protected Criteria createEntityCriteria() {
//        return getSession().createCriteria(persistentClass);
//    }
}
