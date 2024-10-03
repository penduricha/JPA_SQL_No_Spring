package dao;


import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import database.*;
import javax.swing.JOptionPane;

import entities.Project;
import interface_DAO.I_Project_DAO;

import jakarta.persistence.TypedQuery;

public class Project_DAO implements I_Project_DAO {
	List<Project> listProject = new ArrayList<>();
	private Connect con = new Connect();
	/*
	@PersistenceContext
	private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("project_ms");
	private final EntityManager entityManager;
	private final EntityTransaction transaction;*/
	/*
	 * EntityManagerFactory emf = Persistence.createEntityManagerFactory("project_ms");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
	 */
	Hibernate hibernate=new Hibernate("project_ms");
	public Project_DAO() {
		super();
		// TODO Auto-generated constructor stub
		//this.entityManager =  emf.createEntityManager();
		//this.transaction = entityManager.getTransaction();
	}

	@Override
	public List<Project> getListProject() {
		// TODO Auto-generated method stub
		try {
			TypedQuery<Project> query = hibernate.getEntityManager().createQuery("SELECT p FROM Project p", Project.class);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
			return null;
		}
	}

	@Override
	public String getStringListProject() {
		String s = "";
		List<Project> listProject = getListProject();
		for (Project p : listProject) {
			s += p.toString() + "\n";
		}
		return s;
	}

	@Override
	public String getStringListProjectWithSize(double size) {
		String s = "";
		List<Project> listProject = getListProject();
		for (Project p : listProject) {
			if (p.getSize() == size)
				s += p.toString() + "\n";
		}
		return s;
	}

	@Override
	public String getStringListProjectWithYear(int year) {
		String s = "";
		List<Project> listProject = getListProject();
		for (Project p : listProject) {
			if (p.getStart_Date().toLocalDate().getYear() == year)
				s += p.toString() + "\n";
		}
		return s;
	}

	@Override
	public void addProject(Project p) {
		// EntityTransaction transaction = entityManager.getTransaction();

		try {
			hibernate.getTransaction().begin();

			// Kiểm tra xem khóa chính đã tồn tại hay chưa
			if (hibernate.getEntityManager().find(Project.class, p.getCode()) != null) {
				// Nếu ID đã tồn tại, không thêm vào cơ sở dữ liệu
				System.out.println("Code đã tồn tại trong cơ sở dữ liệu.");
				// return false;
			}
			// Nếu khóa chính chưa tồn tại, thực hiện thêm vào cơ sở dữ liệu
			hibernate.getEntityManager().persist(p);
			hibernate.getTransaction().commit();
			System.out.println("Thêm dữ liệu Project thành công!");
		} catch (Exception e) {
			if (hibernate.getTransaction() != null && hibernate.getTransaction().isActive()) {
				hibernate.getTransaction().rollback();
				// return false;
			}
			e.printStackTrace();
			System.out.println(e);
		}

	}

	@Override
	public void updateProject(String code,Project p) {
		try {
			hibernate.getTransaction().begin();

			// Lấy đối tượng từ cơ sở dữ liệu
			Project projectFind = hibernate.getEntityManager().find(Project.class, code);

			// Thực hiện các thay đổi trên đối tượng
			if (projectFind != null) {
				//public Project(String code, String description, Date start_Date, Date end_Date, double size, double budget)
				projectFind.setDescription(p.getDescription());
				projectFind.setStart_Date(p.getStart_Date());
				projectFind.setEnd_Date(p.getEnd_Date());
				projectFind.setSize(p.getSize());
				projectFind.setBudget(p.getBudget());
				
				hibernate.getEntityManager().merge(projectFind);
				hibernate.getTransaction().commit();
				System.out.println("Cập nhật Project thành công.");
			} else {
				System.out.println("Không tìm thấy.");
			}

		} catch (Exception e) {
			if (hibernate.getTransaction().isActive()) {
				hibernate.getTransaction().rollback();
			}
			e.printStackTrace();
		} finally {
			//entityManager.close();
		}

	}

	@Override
	public void deleteProject(Project p) {
		// TODO Auto-generated method stub
		String sqlDelete = "DELETE FROM [dbo].[Staff] where emp_id=?\r\n";
		try {
			PreparedStatement preparedStatement_Delete = con.con().prepareStatement(sqlDelete);
			preparedStatement_Delete.setString(1, p.getCode());
			preparedStatement_Delete.executeUpdate();
			con.con().close();
			preparedStatement_Delete.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e);
		}
	}

}
