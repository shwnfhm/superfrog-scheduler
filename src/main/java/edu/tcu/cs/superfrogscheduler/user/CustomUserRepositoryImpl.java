package edu.tcu.cs.superfrogscheduler.user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomUserRepositoryImpl implements CustomUserRepository{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> searchByCriteria(UserQuery query) {
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        List<String> whereClause = new ArrayList<String>();

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("Select a from User a");

        if(query.firstName() != "" && query.firstName() != null){
            whereClause.add(" a.firstName = :firstName ");
            parameterMap.put("firstName", query.firstName());
        }
        if(query.lastName() != "" && query.lastName() != null){
            whereClause.add(" a.lastName = :lastName ");
            parameterMap.put("lastName", query.lastName());
        }
        if(query.email() != "" && query.email() != null){
            whereClause.add(" a.email = :email ");
            parameterMap.put("email", query.email());
        }
        if(query.phoneNumber() != "" && query.phoneNumber() != null){
            whereClause.add(" a.phoneNumber = :phoneNumber ");
            parameterMap.put("phoneNumber", query.phoneNumber());
        }

        queryBuilder.append(" where " + String.join(" and ", whereClause));
        Query jpaQuery = entityManager.createQuery(queryBuilder.toString());
        for(String key : parameterMap.keySet()){
            jpaQuery.setParameter(key, parameterMap.get(key));
        }
        return jpaQuery.getResultList();
    }
}
