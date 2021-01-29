package com.example.profile;

public class Profile  {
	private String firstName,lastName,pseudo;

	public Profile(String firstName, String lastName, String pseudo) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.pseudo = pseudo;
	}

	@Override
	public String toString() {
		return "Profile{" + "firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", pseudo='" + pseudo + '\'' + '}';
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPseudo() {
		return pseudo;
	}
}
