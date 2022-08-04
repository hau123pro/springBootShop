package com.laptrinhjava.demo.repository;

import java.util.List;
import java.util.Optional;

//import javax.persistence.EntityManager;
//import javax.persistence.NoResultException;
//import javax.persistence.PersistenceContext;
//import javax.persistence.Query;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.laptrinhjava.demo.DTO.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>{
	Optional<User> findUserByUserName(String username);
//	@PersistenceContext
//	private EntityManager entityManager;
//
//	public User findUserAccount() {
//		System.out.println("vo");
////		try {
////
//			String sql = "SELECT e FROM " + User.class.getName() + " e " //
//					+ " Where e.id = 1 ";
//			System.out.println(sql);
//			List<User> a = entityManager.createQuery(sql, User.class).getResultList();
//			if(a==null||a.size()==0) {
//				System.out.println("error");
//			}
////			
////			return (User) query.getSingleResult();
////		} catch (NoResultException e) {
////			return null;
////		}
//		return null;
	
//	}

}
