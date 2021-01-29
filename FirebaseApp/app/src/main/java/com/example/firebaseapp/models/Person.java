package com.example.firebaseapp.models;

public class Person {
	public String fname,lname,address;

	public Person() {
	}

	public Person(String fname, String lname, String address) {
		this.fname = fname;
		this.lname = lname;
		this.address = address;
	}

	@Override
	public String toString() {
		return "Person{" + "fname='" + fname + '\'' + ", lname='" + lname + '\'' + ", address='" + address + '\'' + '}';
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
