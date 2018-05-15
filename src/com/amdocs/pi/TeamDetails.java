package com.amdocs.pi;

public class TeamDetails {

	private String managerName;
	private String aspectDesc;
	private String competencyDesc;
	private int grade;
	private String employeeName;
	private int employeeId;

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getAspectDesc() {
		return aspectDesc;
	}

	public void setAspectDesc(String aspectDesc) {
		this.aspectDesc = aspectDesc;
	}

	public String getCompetencyDesc() {
		return competencyDesc;
	}

	public void setCompetencyDesc(String competencyDesc) {
		this.competencyDesc = competencyDesc;
	}

}
