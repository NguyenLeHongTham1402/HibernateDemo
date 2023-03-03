/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baitaplon.reponsitory.Impl;

import baitaplon.hibernatedemo.HibernateUtils;
import baitaplon.pojo.Product;
import baitaplon.repository.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.Session;

/**
 *
 * @author Computer
 */
public class ProductReponsitoryImpl implements ProductRepository{

    @Override
    public List<Product> getProducts(Map<String, String> params) {
        try (Session s = HibernateUtils.getFACTORY().openSession()){
            CriteriaBuilder b = s.getCriteriaBuilder();
            
            CriteriaQuery<Product> q = b.createQuery(Product.class);
            Root root = q.from(Product.class);
            q.select(root);

            String kw = params.get("kw");
            List<Predicate> predicates = new ArrayList<>();
            if(kw!=null && !kw.isEmpty()){
                Predicate p = b.like(root.get("name").as(String.class),
                        String.format(("%%%s%%"), kw));
                predicates.add(p);
            }
            
            String fromPrice = params.get("fromPrice");
            if(fromPrice!=null && !fromPrice.isEmpty()){
                Predicate p = b.greaterThanOrEqualTo(root.get("price")
                        .as(Long.class), Long.parseLong(fromPrice));
                predicates.add(p);
            }
            
            String toPrice = params.get("toPrice");
            if(toPrice!=null&&!toPrice.isEmpty()){
                Predicate p = b.lessThanOrEqualTo(root.get("price").as(Long.class)
                        , Long.parseLong(toPrice));
                predicates.add(p);
            }
            
            String cateId = params.get("cateId");
            if(cateId!=null&&!cateId.isEmpty()){
                Predicate p = b.equal(root.get("categoryId").get("id").as(int.class), 
                        Integer.parseInt(cateId));
                predicates.add(p);
            }
            q.where(predicates.toArray(new Predicate[]{}));
            Query query = s.createQuery(q);
            return query.getResultList();
            
        }
    }
    
}
