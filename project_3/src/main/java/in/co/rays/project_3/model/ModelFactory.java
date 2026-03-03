package in.co.rays.project_3.model;

import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * ModelFactory decides which model implementation run
 * 
 * @author malay dongre
 *
 */
public final class ModelFactory {

	private static ResourceBundle rb = ResourceBundle.getBundle("in.co.rays.project_3.bundle.system");
	private static final String DATABASE = rb.getString("DATABASE");
	private static ModelFactory mFactory = null;
	private static HashMap modelCache = new HashMap();

	private ModelFactory() {

	}

	public static ModelFactory getInstance() {
		if (mFactory == null) {
			mFactory = new ModelFactory();
		}
		return mFactory;
	}

	public ProductModelInt getProductModel() {
		ProductModelInt productModel = (ProductModelInt) modelCache.get("productModel");
		if (productModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				productModel = new ProductModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				productModel = new ProductModelHibImp();
			}
			modelCache.put("productModel", productModel);
		}
		return productModel;
	}

	public MarksheetModelInt getMarksheetModel() {
		MarksheetModelInt marksheetModel = (MarksheetModelInt) modelCache.get("marksheetModel");
		if (marksheetModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				marksheetModel = new MarksheetModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				marksheetModel = new MarksheetModelJDBCImpl();
			}
			modelCache.put("marksheetModel", marksheetModel);
		}
		return marksheetModel;
	}

	public CollegeModelInt getCollegeModel() {
		CollegeModelInt collegeModel = (CollegeModelInt) modelCache.get("collegeModel");
		if (collegeModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				collegeModel = new CollegeModelHibImp();

			}
			if ("JDBC".equals(DATABASE)) {
				collegeModel = new CollegeModelJDBCImpl();
			}
			modelCache.put("collegeModel", collegeModel);
		}
		return collegeModel;
	}

	public RoleModelInt getRoleModel() {
		RoleModelInt roleModel = (RoleModelInt) modelCache.get("roleModel");
		if (roleModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				roleModel = new RoleModelHibImp();

			}
			if ("JDBC".equals(DATABASE)) {
				roleModel = new RoleModelJDBCImpl();
			}
			modelCache.put("roleModel", roleModel);
		}
		return roleModel;
	}

	public UserModelInt getUserModel() {

		UserModelInt userModel = (UserModelInt) modelCache.get("userModel");
		if (userModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				userModel = new UserModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				userModel = new UserModelJDBCImpl();
			}
			modelCache.put("userModel", userModel);
		}

		return userModel;
	}

	public StudentModelInt getStudentModel() {
		StudentModelInt studentModel = (StudentModelInt) modelCache.get("studentModel");
		if (studentModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				studentModel = new StudentModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				studentModel = new StudentModelJDBCImpl();
			}
			modelCache.put("studentModel", studentModel);
		}

		return studentModel;
	}

	public CourseModelInt getCourseModel() {
		CourseModelInt courseModel = (CourseModelInt) modelCache.get("courseModel");
		if (courseModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				courseModel = new CourseModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				courseModel = new CourseModelJDBCImpl();
			}
			modelCache.put("courseModel", courseModel);
		}

		return courseModel;
	}

	public TimetableModelInt getTimetableModel() {

		TimetableModelInt timetableModel = (TimetableModelInt) modelCache.get("timetableModel");

		if (timetableModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				timetableModel = new TimetableModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				timetableModel = new TimetableModelJDBCImpl();
			}
			modelCache.put("timetableModel", timetableModel);
		}

		return timetableModel;
	}

	public SubjectModelInt getSubjectModel() {
		SubjectModelInt subjectModel = (SubjectModelInt) modelCache.get("subjectModel");
		if (subjectModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				subjectModel = new SubjectModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				subjectModel = new SubjectModelJDBCImpl();
			}
			modelCache.put("subjectModel", subjectModel);
		}

		return subjectModel;
	}

	public FacultyModelInt getFacultyModel() {
		FacultyModelInt facultyModel = (FacultyModelInt) modelCache.get("facultyModel");
		if (facultyModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				facultyModel = new FacultyModelHibImp();
			}
			if ("JDBC".equals(DATABASE)) {
				facultyModel = new FacultyModelJDBCImpl();
			}
			modelCache.put("facultyModel", facultyModel);
		}

		return facultyModel;
	}

	public ContactModelInt getContactModel() {
		ContactModelInt ContactModel = (ContactModelInt) modelCache.get("ContactModel");
		if (ContactModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				ContactModel = new ContactModelHibImpl();
			}
			if ("JDBC".equals(DATABASE)) {
				ContactModel = new ContactModelJDBCImpl();
			}
			modelCache.put("contactModel", ContactModel);
		}

		return ContactModel;
	}


	public EmployeeModelInt getEmployeeModel() {
		// TODO Auto-generated method stub
		EmployeeModelInt EmployeeModel = (EmployeeModelInt) modelCache.get("EmployeeModel");
		if (EmployeeModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				EmployeeModel = new EmployeeModelHibImpl();
			}
			if ("JDBC".equals(DATABASE)) {
				EmployeeModel = new EmployeeModelHibImpl();
			}
			modelCache.put("employeeModel", EmployeeModel);
		}

		return EmployeeModel;

	}
	
	public BatchModelInt getBatchModel() {
		// TODO Auto-generated method stub
		BatchModelInt BatchModel = (BatchModelInt) modelCache.get("BatchModel");
		if (BatchModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				BatchModel = new BatchModelHibImpl();
			}
			if ("JDBC".equals(DATABASE)) {
				BatchModel = new BatchModelHibImpl();
			}
			modelCache.put("BatchModel", BatchModel);
		}

		return BatchModel;

	}

	public LibraryModelInt getLibraryModel() {
		// TODO Auto-generated method stub
		LibraryModelInt LibraryModel = (LibraryModelInt) modelCache.get("LibraryModel");
		if (LibraryModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				LibraryModel = new LibraryModelHibImpl();
			}
			if ("JDBC".equals(DATABASE)) {
				LibraryModel = new LibraryModelHibImpl();
			}
			modelCache.put("LibraryModel", LibraryModel);
		}

		return LibraryModel;

	}

	public JobApplicationModelInt getJobApplicationModel() {
		
		// TODO Auto-generated method stub
		JobApplicationModelInt JobApplicationModel = (JobApplicationModelInt) modelCache.get("JobApplicationModel");
		if (JobApplicationModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				JobApplicationModel = new JobApplicationModelHibImpl();
			}
			if ("JDBC".equals(DATABASE)) {
				JobApplicationModel = new JobApplicationModelHibImpl();
			}
			modelCache.put("JobApplicationModel", JobApplicationModel);
		}

		return JobApplicationModel;

	}
	
     public PaymentModelInt getPaymentModel() {
		
	PaymentModelInt PaymentModel = (PaymentModelInt) modelCache.get("PaymentModel");
		if (PaymentModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				PaymentModel = new PaymentModelHibImpl();
			}
			if ("JDBC".equals(DATABASE)) {
				PaymentModel = new PaymentModelHibImpl();
			}
			modelCache.put("PaymentModel", PaymentModel);
		}

		return PaymentModel;

	}


     public StockModelInt getStockModel() {
		
    	 StockModelInt StockModel = (StockModelInt) modelCache.get("StockModel");
		if (StockModel == null) {
			if ("Hibernate".equals(DATABASE)) {
				StockModel = new StockModelHibImpl();
			}
			if ("JDBC".equals(DATABASE)) {
				StockModel = new StockModelHibImpl();
			}
			modelCache.put("StockModel", StockModel);
		}

		return StockModel;

	}

}
