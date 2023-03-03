/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baitaplon.hibernatedemo;

import baitaplon.pojo.Category;
import baitaplon.pojo.OrderDetail;
import baitaplon.pojo.ProTag;
import baitaplon.pojo.Product;
import baitaplon.pojo.SaleOrder;
import baitaplon.pojo.Tag;
import baitaplon.pojo.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 *
 * @author Computer
 */
public class HibernateUtils {
    private static final SessionFactory FACTORY;
    
    static {
        Configuration conf = new Configuration();
        conf.configure("hibernate.conf.xml");
        conf.addAnnotatedClass(Category.class);
        conf.addAnnotatedClass(Product.class);
        conf.addAnnotatedClass(SaleOrder.class);
        conf.addAnnotatedClass(User.class);
        conf.addAnnotatedClass(OrderDetail.class);
        conf.addAnnotatedClass(Tag.class);
        conf.addAnnotatedClass(ProTag.class);
        ServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySettings(conf.getProperties()).build();
        
        FACTORY = conf.buildSessionFactory(registry);
    }

    /**
     * @return the FACTORY
     */
    public static SessionFactory getFACTORY() {
        return FACTORY;
    }
}
