/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baitaplon.repository;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Computer
 */
public interface StatsRepository {
    List<Object[]> statsRepository();
    List<Object[]> statsRevenue(Date fromDate, Date toDate);
}
