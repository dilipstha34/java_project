public class RegularMember extends GymMember //RegularMember extends GymMember
{
    //attributes and access modifier as private
    private final int attendanceLimit = 30; //value 30 is stored as constant in variable attendanceLimit
    private boolean isEligibleForUpgrade;
    private String removalReason;
    private String referralSource;
    private String plan;
    private double price;
    
    //Constructor of class RegularMember accepting following attributes
    public RegularMember(int id, String name, String location, String phone, String email, String gender, String DOB, String membershipStartDate, 
    String referralSource)
    {
        super(id, name, location, phone, email, gender, DOB, membershipStartDate); //super keyword used to access the parent class attributes
        this.isEligibleForUpgrade = false;
        this.removalReason = "";
        this.referralSource = referralSource;
        this.plan = "basic";
        this.price = 6500;
    }
    
    // Getter/Accessor method
    public int getAttendanceLimit()
    {
        return this.attendanceLimit;
    }
    
    public boolean getIsEligibleForUpgrade()
    {
        return this.isEligibleForUpgrade;
    }
    
    public String getRemovalReason()
    {
        return this.removalReason;
    }
    
    public String getReferralSource()
    {
        return this.referralSource;
    }
    
    public String getPlan()
    {
        return this.plan;
    }
    
    public double getPrice()
    {
        return this.price;
    }
    
    //Overriding method markAttendace() of parent class
    @Override
    public void markAttendance()
    {
        super.attendance++; //attendance increase by 1 every time this method is called
        super.loyaltyPoints += 5; //loyaltyPoints incease by 5 every time this method is called
        if (super.attendance >= attendanceLimit) // Check if the member is eligible for an upgrade based on attendance
        {
            this.isEligibleForUpgrade = true;
        }
    }
    
    //getPlanPrice() method is created with return type double and using switch case for the returning following  plan’s price and returns -1 as default
    public double getPlanPrice(String plan) 
    {
        switch (plan.toLowerCase())  //toLowerCase() method set all the gym plan to lower case
        {
            case "basic":
                return 6500;
            case "standard":
                return 12500;
            case "deluxe":
                return 18500;
            default:
                return -1;
        }
    }
    
    public void setPlan(String plan) {
        this.plan = plan;
    }
    
    /* upgradePlan() method is created with return type String
       used to upgrade the plan subscribed by the member */ 
    public String upgradePlan(String plan) 
    {
        if (this.plan.equalsIgnoreCase(plan)) // Validate if the same plan is being chosen. And equalsIgnoreCase() ignore the case of letters
        {       
            return "You are already subscribed to the " + plan + " plan.";
        }
    
        if (super.attendance >= attendanceLimit) // Check if the member is eligible for an upgrade based on attendance
        {
            this.isEligibleForUpgrade = true;
        }
    
        if (!isEligibleForUpgrade) 
        {
            return "Not eligible for upgrade yet. Attendance must reach " + attendanceLimit + ".";
        }
    
        // Retrieve the new plan's price
        double newPrice = getPlanPrice(plan);
        if (newPrice == -1) 
        {
            return "Invalid plan selected.";
        }
    
        // Update the plan and its price if eligible for plan upgrade
        this.plan = plan;
        this.price = newPrice;
        return "Plan upgraded to " + plan + ". New price: Rs. " + newPrice;
    }
    
    //revertRegularMember() method is created which accepts removalReason as a parameter
    public void revertRegularMember(String removalReason) 
    {
        super.resetMember(); //A super class method resetMember() is called
        this.isEligibleForUpgrade = false;
        this.plan = "basic";
        this.price = 6500;
        this.removalReason = removalReason;
    }
    
    //Using same method as in GymMember class using "@Override" keyword
    @Override
    public void display() 
    {
        super.display();
        System.out.println("Plan: " + plan);
        System.out.println("Price: " + price);
        if (!removalReason.isEmpty()) // if removalReason value is not empty then removalReason is displayed
        {
            System.out.println("Removal Reason: " + removalReason);
        }
        System.out.println("");
    }
}