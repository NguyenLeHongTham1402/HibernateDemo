/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baitaplon.hibernatedemo;

import baitaplon.pojo.Category;
import baitaplon.pojo.Product;
import baitaplon.reponsitory.Impl.ProductReponsitoryImpl;
import baitaplon.reponsitory.Impl.StatsReponsitoryImpl;
import baitaplon.repository.ProductRepository;
import baitaplon.repository.StatsRepository;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Query;
import org.hibernate.Session;

/**
 *
 * @author Computer
 */
public class HibernateDemo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
//        Session s = HibernateUtils.getFACTORY().openSession();
//        Query q = s.createQuery("FROM Category");
//        List<Category> cats = q.getResultList();
//        cats.forEach(p -> System.out.println(p.getName()));
//        s.close();
        ProductRepository p = new ProductReponsitoryImpl();
        Map<String,String> params = new HashMap<>();
//        params.put("kw", "iphone");
//        params.put("fromPrice", "11000000");
//        params.put("toPrice", "23000000");
          params.put("cateId", "1");
//        
        List<Product> products = p.getProducts(params);
        products.forEach(x -> System.out.printf("%d - %s - %d\n",
                x.getId(),x.getName(),x.getPrice()));
        System.out.println("=========================");
//        StatsRepository s = new StatsReponsitoryImpl();
//        List<Object[]> results = s.statsRepository();
//        for(Object[]x:results)
//            System.out.printf("%s - %s: %s\n",x[0], x[1], x[2]);
        StatsRepository s = new StatsReponsitoryImpl();
        Date fromDate = new Date(2020, 1, 1);
        Date toDate = new Date(2020, 1, 30);
                
        List<Object[]> results = s.statsRevenue(fromDate, toDate);
        for(Object[]x:results)
            System.out.printf("%s - %s: %d\n",x[0], x[1], x[2]);
    }
    
}
