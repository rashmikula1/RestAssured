package apiEngine.model.request;

public class PostPatientRequest {
	public String FirstName;
    public String LastName;
    public long ContactNumber;
    public String Email;
    public String Allergy;
    public String FoodCategory;
    public String DateOfBirth;
	public PostPatientRequest(String firstName, String lastName, long contactNumber, String email, String allergy,
			String foodCategory, String dateOfBirth) {
		super();
		FirstName = firstName;
		LastName = lastName;
		ContactNumber = contactNumber;
		Email = email;
		Allergy = allergy;
		FoodCategory = foodCategory;
		DateOfBirth = dateOfBirth;
	}
	@Override
	public String toString() {
		return "PostPatientRequest [FirstName=" + FirstName + ", LastName=" + LastName + ", ContactNumber="
				+ ContactNumber + ", Email=" + Email + ", Allergy=" + Allergy + ", FoodCategory=" + FoodCategory
				+ ", DateOfBirth=" + DateOfBirth + "]";
	}
    
	
	
}
