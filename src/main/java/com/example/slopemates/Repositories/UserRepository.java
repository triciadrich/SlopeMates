package com.example.slopemates.Repositories;

import com.example.slopemates.Models.SearchCriteria;
import com.example.slopemates.Models.User;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.Predicate;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAll();
    Optional<User> findById(Long Id);
    User findByEmail(String email);
    List<User> findAll(Specification<User> specification);

    default Specification<User> buildQuery(SearchCriteria criteria){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.getSkillLevel() != null && !criteria.getSkillLevel().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("skillLevel"), criteria.getSkillLevel()));
            }
            if (criteria.getGender() !=null && !criteria.getGender().isEmpty() ){
                predicates.add(criteriaBuilder.equal(root.get("gender"),criteria.getGender()));
            }
            if (criteria.getState() != null && !criteria.getState().isEmpty()){
                predicates.add(criteriaBuilder.equal(root.get("state"),criteria.getState()));
            }
            if(criteria.getSnowSport() !=null && !criteria.getSnowSport().isEmpty()){
                predicates.add(criteriaBuilder.equal(root.get("snowSport"),criteria.getSnowSport()));
            }

            if(criteria.getStyle() != null && !criteria.getStyle().isEmpty()){
                predicates.add(criteriaBuilder.equal(root.get("style"), criteria.getStyle()));
            }
            if (criteria.getMinAge() !=null ){
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("age"),criteria.getMinAge()));
            }

            if(criteria.getMaxAge() !=null){
                predicates.add((criteriaBuilder.lessThanOrEqualTo(root.get("age"),criteria.getMaxAge())));
            }


            System.out.println(criteria.getMinAge());
            System.out.println(criteria.getMaxAge());
            
            return criteriaBuilder.and(predicates.toArray(predicates.toArray(new Predicate[0])));
        };
    }

}
