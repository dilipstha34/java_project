public class PremiumMember extends GymMember //RegularMember extends GymMember
{
   //attributes and access modifier as private 
   private final double premiumCharge=50000;
   private String personalTrainer;
   private boolean isFullPayment;
   private double paidAmount;
   private double discountAmount;
   
   // Constructor of class PremiumMember accepts nine parameters
   public PremiumMember(int id, String name, String location, String phone, String email, String gender, String DOB, String membershipStartDate, 
   String personalTrainer)
   {
      super(id, name, location, phone, email, gender, DOB, membershipStartDate); // A call is made to the superclass constructor with eight parameters
      this.personalTrainer = personalTrainer;
      this.isFullPayment = false;
      this.paidAmount = 0.0;
      this.discountAmount = 0.0;
   }
   
   public double getPremiumCharge()
   {
       return this.premiumCharge;
   }
   
   public String getPersonalTrainer()
   {
       return this.personalTrainer;
       
   }
   
   public boolean isFullPayment()
   {
       return this.isFullPayment;
   }
   
   public double getPaidAmount()
   {
       return this.paidAmount;
   }
   
   public double getDiscountAmount()
   {
       return this.discountAmount;
   }
   
   //a method markAttendance() method created to override abstract class
   @Override
   public void markAttendance() 
   {
       super.attendance = 30; //Attendance increase by 1 every time markAttendace() is called
       super.loyaltyPoints += 300; // Premium members get 10 loyaltyPoints each at a time markAttendance() is called
   }
   
   //a method payDueAmount() of return type String is created to pay the due amount with paidAmount as a parameter
   public String payDueAmount(double paidAmount) 
   {
       if (this.isFullPayment == true) // Check if full payment has already been made
       {
           return "Payment is already fully completed.";
       }
    
       if ((this.paidAmount + paidAmount) > premiumCharge) // Check if adding this payment would exceed the premium charge
       {
           double excess = (this.paidAmount + paidAmount) - premiumCharge;
           return "Excess amount detected of Rs. " + excess;
       }
    
       // Update the paid amount
       this.paidAmount += paidAmount;
       double remainingAmount = premiumCharge - this.paidAmount;
   
       // Check if the full payment has been achieved
       if (remainingAmount == 0) 
       {
           this.isFullPayment = true;
           return "Payment successful. No remaining amount.";
       } 
       else 
       {
           return "Payment successful. Remaining amount: Rs. " + remainingAmount;
       }
    }
  
   /* calculateDiscount() method is created
      method is used to calculate discount amounts based on payment status */
   public void calculateDiscount() 
   {        
       if (this.isFullPayment) 
       {
           this.discountAmount = 0.10 * premiumCharge;
           System.out.println("Discounted amount is Rs.: " + this.discountAmount);
       } 
       else 
       {
           System.out.println("No discount is given as payment is not full.");
       }    
   }  
   
   // a method name revertPremiumMember()
   public void revertPremiumMember() 
   {
       super.resetMember();
       this.personalTrainer = ""; 
       this.isFullPayment = false;
       this.paidAmount = 0.0;
       this.discountAmount = 0.0;
   }
    
   // display method created to display the details of the PremiumMember
   @Override //Method of parent class used so override is used
   public void display() 
   {
       super.display();
       System.out.println("Personal Trainer: " + personalTrainer);
       System.out.println("Paid Amount: " + paidAmount);
       System.out.println("Full Payment: " + isFullPayment);

       double remainingAmount = premiumCharge - this.paidAmount;
       System.out.println("Remaining Amount: Rs. " + remainingAmount);

       if (this.isFullPayment == true) 
       {
            System.out.println("Discount Amount: Rs. " + discountAmount);
       }
       System.out.println("");
    }
}