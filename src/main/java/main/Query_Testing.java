package main;

import dao.Project_DAO;
import dao.Staff_DAO;
import entities.*
;public class Query_Testing {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Project_DAO project_DAO=new Project_DAO();
		Staff_DAO staff_DAO=new Staff_DAO();
		
		System.out.println("List Project");
		System.out.println(project_DAO.getStringListProject());
		
		
		System.out.println("List Staff");
		System.out.println(staff_DAO.getStringListStaff());
		
		//Project p=new Project();
		//p.setCode("Pr3");
		//project_DAO.deleteProject(p);
		staff_DAO.deleteStaff("Stf8");
		
		System.out.println("List Staff");
		System.out.println(staff_DAO.getStringListStaff());
	}

}
