package in.co.rays.project_3.controller;

/**
 * ORS View Provide Loose Coupling
 * 
 * @author Anshul Prajapati
 *
 */
public interface ORSView {
	public String APP_CONTEXT = "/project_3";

	public String PAGE_FOLDER = "/jsp";

	public String JAVA_DOC_VIEW = APP_CONTEXT + "/doc/index.html";

	public String ERROR_VIEW = PAGE_FOLDER + "/ErrorView404.jsp";

	public String MARKSHEET_VIEW = PAGE_FOLDER + "/MarksheetView.jsp";

	public String MARKSHEET_LIST_VIEW = PAGE_FOLDER + "/MarksheetListView.jsp";
	public String GET_MARKSHEET_VIEW = PAGE_FOLDER + "/GetMarksheetView.jsp";
	public String USER_VIEW = PAGE_FOLDER + "/UserView.jsp";
	public String USER_LIST_VIEW = PAGE_FOLDER + "/UserListView.jsp";
	public String COLLEGE_VIEW = PAGE_FOLDER + "/CollegeView.jsp";
	public String COLLEGE_LIST_VIEW = PAGE_FOLDER + "/CollegeListView.jsp";
	public String STUDENT_VIEW = PAGE_FOLDER + "/StudentView.jsp";
	public String STUDENT_LIST_VIEW = PAGE_FOLDER + "/StudentListView.jsp";
	public String ROLE_VIEW = PAGE_FOLDER + "/RoleView.jsp";
	public String ROLE_LIST_VIEW = PAGE_FOLDER + "/RoleListView.jsp";
	public String USER_REGISTRATION_VIEW = PAGE_FOLDER + "/UserRegistrationView.jsp";
	public String LOGIN_VIEW = PAGE_FOLDER + "/LoginView.jsp";
	public String WELCOME_VIEW = PAGE_FOLDER + "/Welcome.jsp";
	public String CHANGE_PASSWORD_VIEW = PAGE_FOLDER + "/ChangePasswordView.jsp";
	public String MY_PROFILE_VIEW = PAGE_FOLDER + "/MyProfileView.jsp";
	public String FORGET_PASSWORD_VIEW = PAGE_FOLDER + "/ForgetPasswordView.jsp";
	public String MARKSHEET_MERIT_LIST_VIEW = PAGE_FOLDER + "/MarksheetMeritListView.jsp";

	public String FACULTY_VIEW = PAGE_FOLDER + "/FacultyView.jsp";
	public String FACULTY_LIST_VIEW = PAGE_FOLDER + "/FacultyListView.jsp";
	public String COURSE_VIEW = PAGE_FOLDER + "/CourseView.jsp";
	public String COURSE_LIST_VIEW = PAGE_FOLDER + "/CourseListView.jsp";
	public String TIMETABLE_VIEW = PAGE_FOLDER + "/TimeTableView.jsp";
	public String TIMETABLE_LIST_VIEW = PAGE_FOLDER + "/TimeTableListView.jsp";
	public String SUBJECT_VIEW = PAGE_FOLDER + "/SubjectView.jsp";
	public String SUBJECT_LIST_VIEW = PAGE_FOLDER + "/SubjectListView.jsp";
	public String PRODUCT_VIEW = PAGE_FOLDER + "/ProductView.jsp";
	public String PRODUCT_LIST_VIEW = PAGE_FOLDER + "/ProductListView.jsp";

	public String EMPLOYEE_VIEW = PAGE_FOLDER + "/EmployeeView.jsp";
	public String EMPLOYEE_LIST_VIEW = PAGE_FOLDER + "/EmployeeListView.jsp";

	public String CONTACT_VIEW = PAGE_FOLDER + "/ContactView.jsp";
	public String CONTACT_LIST_VIEW = PAGE_FOLDER + "/ContactListView.jsp";

	public String BATCH_VIEW = PAGE_FOLDER + "/BatchView.jsp";
	public String BATCH_LIST_VIEW = PAGE_FOLDER + "/BatchListView.jsp";

	public String LIBRARY_VIEW = PAGE_FOLDER + "/LibraryView.jsp";
	public String LIBRARY_LIST_VIEW = PAGE_FOLDER + "/LibraryListView.jsp";

	public String JOB_APPLICATION_VIEW = PAGE_FOLDER + "/JobApplicationView.jsp";
	public String JOB_APPLICATION_LIST_VIEW = PAGE_FOLDER + "/JobApplicationListView.jsp";

	public String PAYMENT_VIEW = PAGE_FOLDER + "/PaymentView.jsp";
	public String PAYMENT_LIST_VIEW = PAGE_FOLDER + "/PaymentListView.jsp";

	public String STOCK_VIEW = PAGE_FOLDER + "/StockView.jsp";
	public String STOCK_LIST_VIEW = PAGE_FOLDER + "/StockListView.jsp";

	public String TOUR_GUIDE_BOOKING_VIEW = PAGE_FOLDER + "/TourGuideBookingView.jsp";
	public String TOUR_GUIDE_BOOKING_LIST_VIEW = PAGE_FOLDER + "/TourGuideBookingListView.jsp";
	
	public String CLAIM_VIEW = PAGE_FOLDER + "/ClaimView.jsp";
	public String CLAIM_LIST_VIEW = PAGE_FOLDER + "/ClaimListView.jsp";

	public String BRANCH_VIEW = PAGE_FOLDER + "/BranchView.jsp";
	public String BRANCH_LIST_VIEW = PAGE_FOLDER + "/BranchListView.jsp";

	public String DONATIONCAMP_VIEW = PAGE_FOLDER + "/DonationCampView.jsp";
	public String DONATIONCAMP_LIST_VIEW = PAGE_FOLDER + "/DonationCampListView.jsp";

	public String CURRENCY_VIEW = PAGE_FOLDER + "/CurrencyView.jsp";
	public String CURRENCY_LIST_VIEW = PAGE_FOLDER + "/CurrencyListView.jsp";

	
	public String ERROR_CTL = APP_CONTEXT + "/ErrorCtl";

	public String MARKSHEET_CTL = APP_CONTEXT + "/ctl/MarksheetCtl";
	public String MARKSHEET_LIST_CTL = APP_CONTEXT + "/ctl/MarksheetListCtl";
	public String USER_CTL = APP_CONTEXT + "/ctl/UserCtl";
	public String USER_LIST_CTL = APP_CONTEXT + "/ctl/UserListCtl";
	public String COLLEGE_CTL = APP_CONTEXT + "/ctl/CollegeCtl";
	public String COLLEGE_LIST_CTL = APP_CONTEXT + "/ctl/CollegeListCtl";
	public String STUDENT_CTL = APP_CONTEXT + "/ctl/StudentCtl";
	public String STUDENT_LIST_CTL = APP_CONTEXT + "/ctl/StudentListCtl";
	public String ROLE_CTL = APP_CONTEXT + "/ctl/RoleCtl";
	public String ROLE_LIST_CTL = APP_CONTEXT + "/ctl/RoleListCtl";
	public String USER_REGISTRATION_CTL = APP_CONTEXT + "/UserRegistrationCtl";
	public String LOGIN_CTL = APP_CONTEXT + "/LoginCtl";
	public String WELCOME_CTL = APP_CONTEXT + "/WelcomeCtl";

	public String FACULTY_CTL = APP_CONTEXT + "/ctl/FacultyCtl";
	public String FACULTY_LIST_CTL = APP_CONTEXT + "/ctl/FacultyListCtl";
	public String COURSE_CTL = APP_CONTEXT + "/ctl/CourseCtl";
	public String COURSE_LIST_CTL = APP_CONTEXT + "/ctl/CourseListCtl";
	public String SUBJECT_CTL = APP_CONTEXT + "/ctl/SubjectCtl";
	public String SUBJECT_LIST_CTL = APP_CONTEXT + "/ctl/SubjectListCtl";
	public String TIMETABLE_CTL = APP_CONTEXT + "/ctl/TimeTableCtl";
	public String TIMETABLE_LIST_CTL = APP_CONTEXT + "/ctl/TimeTableListCtl";
	public String PRODUCT_CTL = APP_CONTEXT + "/ctl/ProductCtl";
	public String PRODUCT_LIST_CTL = APP_CONTEXT + "/ctl/ProductListCtl";
	public String CONTACT_CTL = APP_CONTEXT + "/ctl/ContactCtl";
	public String CONTACT_LIST_CTL = APP_CONTEXT + "/ctl/ContactListCtl";

	public String GET_MARKSHEET_CTL = APP_CONTEXT + "/ctl/GetMarksheetCtl";
	public String CHANGE_PASSWORD_CTL = APP_CONTEXT + "/ctl/ChangePasswordCtl";
	public String MY_PROFILE_CTL = APP_CONTEXT + "/ctl/MyProfileCtl";
	public String FORGET_PASSWORD_CTL = APP_CONTEXT + "/ForgetPasswordCtl";
	public String MARKSHEET_MERIT_LIST_CTL = APP_CONTEXT + "/ctl/MarksheetMeritListCtl";
	
	public String EMPLOYEE_CTL = APP_CONTEXT + "/ctl/EmployeeCtl";
	public String EMPLOYEE_LIST_CTL = APP_CONTEXT + "/ctl/EmployeeListCtl";

	public String BATCH_CTL = APP_CONTEXT + "/ctl/BatchCtl";
	public String BATCH_LIST_CTL = APP_CONTEXT + "/ctl/BatchListCtl";

	public String LIBRARY_CTL = APP_CONTEXT + "/ctl/LibraryCtl";
	public String LIBRARY_LIST_CTL = APP_CONTEXT + "/ctl/LibraryListCtl";

	public String JOB_APPLICATION_CTL = APP_CONTEXT + "/ctl/JobApplicationCtl";
	public String JOB_APPLICATION_LIST_CTL = APP_CONTEXT + "/ctl/JobApplicationListCtl";

	public String PAYMENT_CTL = APP_CONTEXT + "/ctl/PaymentCtl";
	public String PAYMENT_LIST_CTL = APP_CONTEXT + "/ctl/PaymentListCtl";

	public String STOCK_CTL = APP_CONTEXT + "/ctl/StockCtl";
	public String STOCK_LIST_CTL = APP_CONTEXT + "/ctl/StockListCtl";
	
	public String CLAIM_CTL = APP_CONTEXT + "/ctl/ClaimCtl";
	public String CLAIM_LIST_CTL = APP_CONTEXT + "/ctl/ClaimListCtl";

	public String BRANCH_CTL = APP_CONTEXT + "/ctl/BranchCtl";
	public String BRANCH_LIST_CTL = APP_CONTEXT + "/ctl/BranchListCtl";

	public String TOUR_GUIDE_BOOKING_CTL = APP_CONTEXT + "/ctl/TourGuideBookingCtl";
	public String TOUR_GUIDE_BOOKING_LIST_CTL = APP_CONTEXT + "/ctl/TourGuideBookingListCtl";

	public String DONATIONCAMP_CTL = APP_CONTEXT + "/ctl/DonationCampCtl";
	public String DONATIONCAMP_LIST_CTL = APP_CONTEXT + "/ctl/DonationCampListCtl";

	public String CURRENCY_CTL = APP_CONTEXT + "/ctl/CurrencyCtl";
	public String CURRENCY_LIST_CTL = APP_CONTEXT + "/ctl/CurrencyListCtl";

}
