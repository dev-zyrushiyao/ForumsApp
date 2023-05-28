package com.codingdojo.ForumsApp.auth;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="roles")
public class UserRoleModel {

	
		@Id
	    @GeneratedValue(strategy=GenerationType.IDENTITY)
	    private Long id;
		
	    private String name;
	    
	  //Authentication
	    @ManyToMany(mappedBy = "roles")
	    private List<UserModel> users;

		public UserRoleModel() {
			super();
			// TODO Auto-generated constructor stub
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public UserRoleModel(String name, List<UserModel> users) {
			super();
			this.name = name;
			this.users = users;
		}

		public List<UserModel> getUsers() {
			return users;
		}

		public void setUsers(List<UserModel> users) {
			this.users = users;
		}
		
		


		
	    
	    
}
