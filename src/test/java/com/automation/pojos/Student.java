package com.automation.pojos;

/**
 * {
 *     "studentId": 11625,
 *     "firstName": "Mevlana",
 *     "lastName": "Rumi",
 *     "batch": 15,
 *     "joinDate": "01/01/3321",
 *     "birthDate": "01/01/1207",
 *     "password": "1234",
 *     "subject": "Math",
 *     "gender": "Males",
 *     "admissionNo": "1122",
 *     "major": "CS",
 *     "section": "101010",
 *     "contact": {
 *         "contactId": 11645,
 *         "phone": "240-123-1231",
 *         "emailAddress": "james_bond@gmail.com",
 *         "premanentAddress": "7925 Jones Branch Dr"
 *     },
 *     "company": {
 *         "companyId": 11565,
 *         "companyName": "Cybertek",
 *         "title": "SDET",
 *         "startDate": "02/02/2020",
 *         "address": {
 *             "addressId": 11585,
 *             "street": "7925 Jones Branch Dr",
 *             "city": "McLean",
 *             "state": "Virginia",
 *             "zipCode": 22102
 *         }
 *     }
 * }
 */

public class Student {

    private int studentId;
    private String firstName;
    private String lastName;
    private int batch;
    private String joinDate;
    private String birthDate;
    private String password;
    private String subject;
    private String gender;
    private String admissionNo;
    private String major;
    private String section;
    private Contact contact;
    private Company company;



    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBatch(int batch) {
        this.batch = batch;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAdmissionNo(String admissionNo) {
        this.admissionNo = admissionNo;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
    public int getStudentId() {
        return studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getBatch() {
        return batch;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getPassword() {
        return password;
    }

    public String getSubject() {
        return subject;
    }

    public String getGender() {
        return gender;
    }

    public String getAdmissionNo() {
        return admissionNo;
    }

    public String getMajor() {
        return major;
    }

    public String getSection() {
        return section;
    }

    public Contact getContact() {
        return contact;
    }

    public Company getCompany() {
        return company;
    }



    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", batch=" + batch +
                ", joinDate='" + joinDate + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", password='" + password + '\'' +
                ", subject='" + subject + '\'' +
                ", gender='" + gender + '\'' +
                ", admissionNo='" + admissionNo + '\'' +
                ", major='" + major + '\'' +
                ", section='" + section + '\'' +
                ", contact=" + contact +
                ", company=" + company +
                '}';
    }
}
