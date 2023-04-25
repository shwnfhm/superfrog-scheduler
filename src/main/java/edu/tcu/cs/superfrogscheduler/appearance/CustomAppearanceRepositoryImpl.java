package edu.tcu.cs.superfrogscheduler.appearance;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomAppearanceRepositoryImpl implements CustomAppearanceRepository{

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<Appearance> searchByCriteria(AppearanceQuery query, Long uId) {
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        List<String> whereClause = new ArrayList<String>();

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("Select a from Appearance a");

        if(query.title() != "" && query.title() != null){
            whereClause.add(" a.title = :title ");
            parameterMap.put("title", query.title());
        }
        if(query.reqFirstName() != "" && query.reqFirstName() != null){
            whereClause.add(" a.reqFirstName = :reqFirstName ");
            parameterMap.put("reqFirstName", query.reqFirstName());
        }
        if(query.reqLastName() != "" && query.reqLastName() != null){
            whereClause.add(" a.reqLastName = :reqLastName ");
            parameterMap.put("reqLastName", query.reqLastName());
        }
        if(query.reqEmail() != "" && query.reqEmail() != null){
            whereClause.add(" a.reqEmail = :reqEmail ");
            parameterMap.put("reqEmail", query.reqEmail());
        }
        if(query.reqPhoneNumber() != "" && query.reqPhoneNumber() != null){
            whereClause.add(" a.reqPhoneNumber = :reqPhoneNumber ");
            parameterMap.put("reqPhoneNumber", query.reqPhoneNumber());
        }
        if(uId != -1 && uId != null){
            whereClause.add(" a.assignedSuperFrog.Id = :uId ");
            parameterMap.put("uId", uId);
        }
        if(query.startDate() != null && query.endDate() == null){
            whereClause.add(" a.eventDate = :startDate ");
            parameterMap.put("startDate", query.startDate());
        }
        if(query.startDate() != null && query.endDate() != null){
            whereClause.add(" a.eventDate >= :startDate and a.eventDate <= :endDate ");
            parameterMap.put("startDate", query.startDate());
            parameterMap.put("endDate", query.endDate());
        }
        if(query.status() != null){
            whereClause.add(" a.status = :status ");
            parameterMap.put("status", query.status());
        }

        queryBuilder.append(" where " + String.join(" and ", whereClause));
        Query jpaQuery = entityManager.createQuery(queryBuilder.toString());
        for(String key : parameterMap.keySet()){
            jpaQuery.setParameter(key, parameterMap.get(key));
        }
        return jpaQuery.getResultList();

    }
}
