/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baitaplon.reponsitory.Impl;

import baitaplon.hibernatedemo.HibernateUtils;
import baitaplon.pojo.Category;
import baitaplon.pojo.OrderDetail;
import baitaplon.pojo.Product;
import baitaplon.pojo.SaleOrder;
import baitaplon.repository.StatsRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.Session;

/**
 *
 * @author Computer
 */
public class StatsReponsitoryImpl implements StatsRepository{

    @Override
    public List<Object[]> statsRepository() {
        try(Session session = HibernateUtils.getFACTORY().openSession()){
            CriteriaBuilder b = session.getCriteriaBuilder();
            CriteriaQuery<Object[]> q = b.createQuery(Object[].class);
            Root rootP = q.from(Product.class);
            Root rootC = q.from(Category.class);
            q.where(b.equal( rootP.get("categoryId"),rootC.get("id")));
            q.multiselect(rootC.get("id"),rootC.get("name"),b.count(rootP.get("id")));
            q.groupBy(rootC.get("id"));
            
            Query query = session.createQuery(q);
            return query.getResultList();
            
        }
    }

    @Override
    public List<Object[]> statsRevenue(Date fromDate, Date toDate) {
        try(Session session = HibernateUtils.getFACTORY().openSession()){
            CriteriaBuilder b = session.getCriteriaBuilder();
            CriteriaQuery<Object[]> q = b.createQuery(Object[].class);
            Root rootP = q.from(Product.class);
            Root rootS = q.from(SaleOrder.class);
            Root rootO = q.from(OrderDetail.class);
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(b.equal(rootS.get("id"), rootO.get("orderId")));
            predicates.add(b.equal(rootP.get("id"), rootO.get("productId")));
            
            q.multiselect(rootP.get("id"),rootP.get("name"), b.sum(b.prod(rootO.get("unitPrice"), rootO.get("num"))));
            
            if(fromDate!=null)
                predicates.add(b.greaterThanOrEqualTo(rootS.get("createdDate").as(Date.class), fromDate));
            
            if(toDate!=null)
                predicates.add(b.lessThanOrEqualTo(rootS.get("createdDate").as(Date.class), toDate));
            
            q.where(predicates.toArray(new Predicate[]{}));
            q.groupBy(rootP.get("id"));
            q.orderBy(b.desc(rootP.get("id")));
            Query query = session.createQuery(q);
            return query.getResultList();
        }
    }

    
    
    
    
}
