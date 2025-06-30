public abstract class GymMember //Abstract Class GymMember
{
    //attributes of GymMember and access modifier as protected
    protected int id;
    protected String name;
    protected String location;
    protected String phone;
    protected String email;
    protected String gender;
    protected String DOB;
    protected String membershipStartDate;
    protected int attendance; 
    protected double loyaltyPoints;
    protected boolean activeStatus;
    
    //Constructor of class GymMember accepting following attributes 
    public GymMember(int id, String name, String location, String phone, String email, String gender, String DOB, String membershipStartDate)
    {
        this.id = id;
        this.name = name;
        this.location = location;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.DOB = DOB;
        this.membershipStartDate = membershipStartDate;
        this.attendance = 0;
        this.loyaltyPoints = 0.0;
        this.activeStatus = false; //Membership is deactivated by default i.e activeStatus set to false
    }   
    
    //Each attributes with its corresponding accessor/getter method
    public int getId()
    {
        return this.id;
    }
        
    public String getName()
    {
        return this.name;
    }
    
    public String getLocation()
    {
        return this.location;
    }
    
    public String getPhone()
    {
        return this.phone;
    }
    
    public String getEmail()
    {
        return this.email;
    }
    
    public String getGender()
    {
        return this.gender;
    }
    
    public String getDOB()
    {
        return this.DOB;
    }
    
    public String getMembershipStartDate()
    {
        return this.membershipStartDate;
    }
    
    public int getAttendance()
    {
        return this.attendance;
    }
    
    public double getLoyaltyPoints()
    {
        return this.loyaltyPoints;
    }
    
    public boolean getActiveStatus()
    {
        return this.activeStatus;
    }
    
    /* Abstract method markAttendance() is created
     * and abstract method does not contain body*/
    public abstract void markAttendance();
    
    /* Concrete method activateMembership() is created and activeStatus is set to true,
       when the members want to activate or renew their membership*/
    public void activeMembership()
    {
        this.activeStatus = true;
    }
    
    /* Concrete method deactivateMembership() is created and activeStatus is set to false by using if else condition
       when the members want to deactivate or pause their membership*/
    public void deactivateMemberhship()
    {
        if(this.activeStatus == true) //membership must be activated first in order to deactivate membership
        {
            this.activeStatus = false;
        }
        else //else membership cannot be deactivated
        {
            System.out.println("Membership is not actived.");
        }
    }
    
    
    /* resetMember() method is created in order to set values to default,
       i.e. activeStatus to false, attendance to zero and loyaltyPoints to zero.*/
    public void resetMember() 
    {
        this.activeStatus = false;
        this.attendance = 0;
        this.loyaltyPoints = 0.0;
    }
    
    //display() method is created for suitable output
    public void display()
    {
        System.out.println("ID of member is: "+ id);
        System.out.println("Name of member is: "+ name);
        System.out.println("Location of member is: "+ location);
        System.out.println("Phone Number of member is: "+ phone);
        System.out.println("Email of member is: "+ email);
        System.out.println("Gender of member is: "+ gender);
        System.out.println("DOB: "+ DOB);
        System.out.println("Membership Start Date: "+ membershipStartDate);
        System.out.println("Attendace: "+ attendance);
        System.out.println("Loyalty Points: "+ loyaltyPoints);
        System.out.println("Active Status: "+ activeStatus);
    }
}