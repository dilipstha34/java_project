// Import necessary Swing components from swing package for building the GUI
import javax.swing.JFrame;       
import javax.swing.JPanel;        
import javax.swing.JTextField;    
import javax.swing.JPasswordField; 
import javax.swing.JCheckBox;     
import javax.swing.JButton;       
import javax.swing.JRadioButton;  
import javax.swing.JComboBox;     
import javax.swing.JLabel;        
import javax.swing.ImageIcon;     
import javax.swing.ButtonGroup;   
import javax.swing.JOptionPane;   
import javax.swing.JScrollPane;   
import javax.swing.JTextArea;     

// Import ArrayList from util package for dynamic storage of member objects
import java.util.ArrayList;       

// Import AWT components from awt package for basic GUI features
import java.awt.Font;            
import java.awt.Color;           
import java.awt.Cursor;          

// Import AWT event classes for handling user interactions
import java.awt.event.ActionListener; // Interface for responding to events like button clicks
import java.awt.event.ActionEvent;    // Represents an event triggered by a user action

// Import IO classes for file handling, enabling reading from and writing to files
import java.io.FileWriter;       // Writes data to a file
import java.io.FileReader;       // Reads data from a file
import java.io.IOException;      // Handles input/output exceptions


// This class, GymGUI, is designed to create a graphical user interface for managing gym memberships
// It includes functionalities such as adding members, marking attendance, and handling payments
// The following lines define the class and its implementation of the ActionListener interface

// GymGUI class implements ActionListener to respond to GUI events
public class GymGUI implements ActionListener 
{
    /*This ArrayList is used to store all gym members, both regular and premium
      It provides a dynamic way to manage member data throughout the application
      ArrayList to store GymMember objects (RegularMember and PremiumMember)*/
    private ArrayList<GymMember> membersObj;
    
    // Instance member variables for input collection
    int id;
    String name, location, phone, email, gender, dob, msd, trainer, referral;

    /* Main application frame and panels
       The frame serves as the main window of the application
       Panels are used to organize different sections of the interface*/
    private JFrame frame;
    private JPanel loginPanel, buttonPanel, mainPanel;
    private JPanel homePanel, addMemberPanel, regularPanel, premiumPanel, activateDeactivatePanel, markAttendancePanel, upgradePlanPanel,
    discountPanel, revertMemberPanel, payDuePanel;

    // Components for Login Panel
    private JTextField userTxt;
    private JPasswordField passwordTxt;
    private JCheckBox showPassword;
    private JButton loginButton;

    // Navigation Panel Buttons
    private JButton addMemberBtn, activateBtn, markAttendanceNavBtn, upgradeBtn, discountBtn, payDueNavBtn, revertMemberNavBtn, saveBtn, readBtn, logOutBtn;

    /*Components for "Add Member" Panel
      Buttons to switch between adding regular and premium members
      These allow the user to choose the type of member to add*/
    private JButton addRegularSwitchBtn, addPremiumSwitchBtn;

    // Components for "Add Regular Member" Panel
    // Frame for displaying regular member information
    // This frame will show details of all regular members when requested
    private JFrame rDisplayInfoFrame;
    // Text fields for inputting regular member details
    // These fields collect data such as ID, name, and contact information
    private JTextField rIdField, rNameField, rLocationField, rPhoneField, rEmailField, rReferralField, rPriceField;
    // Radio buttons for selecting gender
    // Allow the user to specify the gender of the regular member
    private JRadioButton rMaleBtn, rFemaleBtn;
    // Combo boxes for selecting date of birth and membership start date
    // Provide dropdown options for dates to ensure valid input is taken
    private JComboBox<String> rDobYear, rDobMonth, rDobDay, rMsYear, rMsMonth, rMsDay, rPlanBox;
    // Buttons for adding, displaying, and clearing regular member information
    // Facilitate the management of regular member data
    private JButton rAddMemberBtn, rDisplayBtn, rClearBtn;

    // Components for "Add Premium Member" Panel
    // Frame for displaying premium member information
    // Similar to the regular member display frame but for premium members
    private JFrame pDisplayInfoFrame;
    // Text fields for inputting premium member details
    // Include additional fields like trainer specific to premium members
    private JTextField pIdField, pNameField, pLocationField, pPhoneField, pEmailField, pTrainerField, pChargeField;
    // Radio buttons for selecting gender
    // Same purpose as in the regular member panel
    private JRadioButton pMaleBtn, pFemaleBtn;
    // Combo boxes for selecting date of birth and membership start date
    // Ensure consistent date input for premium members
    private JComboBox<String> pDobYear, pDobMonth, pDobDay, pMsYear, pMsMonth, pMsDay;
    // Buttons for adding, displaying, and clearing premium member information
    // Manage premium member data similarly to regular members
    private JButton pAddMemberBtn, pDisplayBtn, pClearBtn;

    // Components for "Activate/Deactivate Membership" Panel
    // Text field for entering member ID to activate or deactivate membership
    // Used to identify the member whose status will change
    private JTextField actIdField;
    // Buttons for activating and deactivating membership
    // Trigger the respective actions on the specified member
    private JButton activateMemberBtn, deactivateMemberBtn;

    // Components for "Mark Attendance" Panel
    // Text field for entering member ID to mark attendance
    // Identifies the member for attendance tracking
    private JTextField markIdField;
    // Buttons for marking attendance for regular and premium members
    // Differentiate between member types for attendance
    private JButton markRegularBtn, markPremiumBtn;

    // Components for "Upgrade Plan" Panel
    // Text field for entering member ID to upgrade plan
    // Specifies which member's plan will be upgraded
    private JTextField upIdField;
    // Combo box for selecting the new plan
    // Offers plan options for the upgrade
    private JComboBox<String> upPlanBox;
    // Button for upgrading the plan
    // Initiates the plan upgrade process
    private JButton upgradePlanBtn;

    // Components for "Calculate Discount" Panel
    // Text field for entering member ID to calculate discount
    // Identifies the premium member for discount calculation
    private JTextField discIdField, discAmountField;
    // Button for calculating the discount
    // Triggers the discount computation
    private JButton calcDiscountBtn;

    // Components for "Revert Member" Panel
    // Text field for entering member ID to revert member status
    // Targets the member to be removed or reverted
    private JTextField revertIdField;
    // Buttons for reverting regular and premium members
    // Handle reversion for each member type
    private JButton revertRegularBtn, revertPremiumBtn;

    // Components for "Pay Due" Panel
    // Text fields for entering member ID and amount to pay due
    // Collect payment details for premium members
    private JTextField payIdField, payAmountField;
    // Button for paying the due amount
    // Processes the payment
    private JButton payDueButton;

    // Arrays for combo boxes
    // These arrays provide predefined options for date and plan selections
    // They ensure consistency and validity in user inputs
    private String[] yearsArr = {"2025", "2024", "2023", "2022", "2021", "2020", "2019", "2018", "2017", "2016", "2015", "2010", "2000", "1995", "1990",
    "1980", "1970", "1960", "1950"};
    private String[] monthsArr = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    private String[] daysArr = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10",  "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", 
    "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
    private String[] planOptionsArr = {"Basic", "Standard", "Deluxe"};

    // Constructor explanation
    // The constructor initializes all GUI components and sets up the layout
    // It is the entry point for building the application's interface
    // Constructor of GymGUI class where all GUI is done
    public GymGUI() 
    {
        // Frame setup comments
        // Setting up the main window with a title and specific dimensions
        frame = new JFrame("Aldenaire Gym");
        frame.setSize(890, 910);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        // Initialize the ArrayList for member storage
        // This ensures we can start adding members immediately
        membersObj = new ArrayList<GymMember>();

        // Login panel configuration
        // The login panel is the first screen users see
        // It includes fields and buttons for authentication
        // Login Panel 
        loginPanel = new JPanel();
        loginPanel.setLayout(null);
        loginPanel.setBounds(0, 0, 890, 910);
        loginPanel.setBackground(Color.BLACK);

        // Adding the admin login title
        // This label informs the user of the login purpose
        JLabel adminLoginTxt = new JLabel("Admin Login");
        adminLoginTxt.setForeground(Color.WHITE);
        adminLoginTxt.setBounds(350, 50, 300, 50);
        adminLoginTxt.setFont(new Font("Arial", Font.BOLD, 36));
        loginPanel.add(adminLoginTxt);

        // Adding a login image
        // Enhances the visual appeal of the login screen
        ImageIcon loginImg = new ImageIcon("loginImg.png");
        JLabel loginImgLbl = new JLabel(loginImg);
        loginImgLbl.setBounds(200, 100, 550, 359);
        loginPanel.add(loginImgLbl);

        // Username label and field setup
        // Allows the user to input their username
        JLabel userLabel = new JLabel("Username");
        userLabel.setForeground(Color.WHITE);
        userLabel.setBounds(150, 500, 200, 35);
        userLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        loginPanel.add(userLabel);

        userTxt = new JTextField();
        userTxt.setBounds(360, 500, 350, 35);
        userTxt.setFont(new Font("Arial", Font.PLAIN, 25));
        loginPanel.add(userTxt);

        // Password label and field setup
        // Securely collects the user's password
        JLabel passLabel = new JLabel("Password");
        passLabel.setForeground(Color.WHITE);
        passLabel.setBounds(150, 600, 200, 35);
        passLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        loginPanel.add(passLabel);

        passwordTxt = new JPasswordField();
        passwordTxt.setBounds(360, 600, 350, 35);
        passwordTxt.setFont(new Font("Arial", Font.PLAIN, 25));
        loginPanel.add(passwordTxt);

        // Show password checkbox
        // Provides an option to toggle password visibility
        showPassword = new JCheckBox("Show Password");
        showPassword.setBounds(360, 660, 350, 35);
        showPassword.setFont(new Font("Arial", Font.PLAIN, 25));
        showPassword.setBackground(Color.BLACK);
        showPassword.setForeground(Color.WHITE);
        showPassword.addActionListener(this);
        loginPanel.add(showPassword);

        // Login button setup
        // Initiates the login process when clicked
        loginButton = new JButton("Login");
        loginButton.setBounds(360, 720, 200, 40);
        loginButton.setFont(new Font("Arial", Font.BOLD, 25));
        loginButton.setBackground(Color.GREEN);
        loginButton.addActionListener(this);
        buttonPointer(loginButton);
        loginPanel.add(loginButton);
        
        // Set login panel as the initial content
        // Displays the login screen when the app starts
        frame.setContentPane(loginPanel);

        // Button panel configuration
        // Contains navigation buttons for the main interface
        buttonPanel = new JPanel();
        buttonPanel.setLayout(null);
        buttonPanel.setBounds(0, 0, 200, 910);
        buttonPanel.setBackground(new Color(0x80490E));

        // Add member button
        // Navigates to the member addition panel
        addMemberBtn = new JButton("Add member");
        addMemberBtn.setBounds(10, 50, 180, 40);
        addMemberBtn.setFont(new Font("SansSerif", Font.BOLD, 15));
        addMemberBtn.setBackground(new Color(0xB4B8AC));
        addMemberBtn.addActionListener(this);
        buttonPointer(addMemberBtn);
        buttonPanel.add(addMemberBtn);

        // Activate/deactivate button
        // Accesses membership status controls
        activateBtn = new JButton("Activate/Deactivate");
        activateBtn.setBounds(10, 130, 180, 40);
        activateBtn.setFont(new Font("SansSerif", Font.BOLD, 15));
        activateBtn.setBackground(new Color(0xB4B8AC));
        activateBtn.addActionListener(this);
        buttonPointer(activateBtn);
        buttonPanel.add(activateBtn);

        // Mark attendance button
        // Opens the attendance marking panel
        markAttendanceNavBtn = new JButton("Mark Attendance");
        markAttendanceNavBtn.setBounds(10, 210, 180, 40);
        markAttendanceNavBtn.setFont(new Font("SansSerif", Font.BOLD, 15));
        markAttendanceNavBtn.setBackground(new Color(0xB4B8AC));
        markAttendanceNavBtn.addActionListener(this);
        buttonPointer(markAttendanceNavBtn);
        buttonPanel.add(markAttendanceNavBtn);

        // Upgrade plan button
        // Allows plan upgrades for regular members
        upgradeBtn = new JButton("Upgrade Plan");
        upgradeBtn.setBounds(10, 290, 180, 40);
        upgradeBtn.setFont(new Font("SansSerif", Font.BOLD, 15));
        upgradeBtn.setBackground(new Color(0xB4B8AC));
        upgradeBtn.addActionListener(this);
        buttonPointer(upgradeBtn);
        buttonPanel.add(upgradeBtn);

        // Calculate discount button
        // Computes discounts for premium members
        discountBtn = new JButton("Calculate Discount");
        discountBtn.setBounds(10, 370, 180, 40);
        discountBtn.setFont(new Font("SansSerif", Font.BOLD, 15));
        discountBtn.setBackground(new Color(0xB4B8AC));
        discountBtn.addActionListener(this);
        buttonPointer(discountBtn);
        buttonPanel.add(discountBtn);

        // Pay due button
        // Manages payments for premium members
        payDueNavBtn = new JButton("Pay Due");
        payDueNavBtn.setBounds(10, 450, 180, 40);
        payDueNavBtn.setFont(new Font("SansSerif", Font.BOLD, 15));
        payDueNavBtn.setBackground(new Color(0xB4B8AC));
        payDueNavBtn.addActionListener(this);
        buttonPointer(payDueNavBtn);
        buttonPanel.add(payDueNavBtn);

        // Revert member button
        // Handles member removal
        revertMemberNavBtn = new JButton("Revert Member");
        revertMemberNavBtn.setBounds(10, 530, 180, 40);
        revertMemberNavBtn.setFont(new Font("SansSerif", Font.BOLD, 15));
        revertMemberNavBtn.setBackground(new Color(0xB4B8AC));
        revertMemberNavBtn.addActionListener(this);
        buttonPointer(revertMemberNavBtn);
        buttonPanel.add(revertMemberNavBtn);
        
        // Save to file button
        // Exports member data to a file
        saveBtn = new JButton("Save to File");
        saveBtn.setBounds(10, 610, 180, 40);
        saveBtn.setFont(new Font("SansSerif", Font.BOLD, 15));
        saveBtn.setBackground(new Color(0xB4B8AC));
        saveBtn.addActionListener(this);
        buttonPointer(saveBtn);
        buttonPanel.add(saveBtn);
        
        // Read from file button
        // Imports member data from a file
        readBtn = new JButton("Read from File");
        readBtn.setBounds(10, 690, 180, 40);
        readBtn.setFont(new Font("SansSerif", Font.BOLD, 15));
        readBtn.setBackground(new Color(0xB4B8AC));
        readBtn.addActionListener(this);
        buttonPointer(readBtn);
        buttonPanel.add(readBtn);

        // Logout button
        // Returns to the login screen
        logOutBtn = new JButton("Log Out");
        logOutBtn.setBounds(10, 770, 180, 40);
        logOutBtn.setFont(new Font("SansSerif", Font.BOLD, 15));
        logOutBtn.setBackground(new Color(0xB4B8AC));
        logOutBtn.setForeground(Color.RED);
        logOutBtn.addActionListener(this);
        buttonPointer(logOutBtn);
        buttonPanel.add(logOutBtn);

        // Main panel setup
        // Holds all sub-panels for different functionalities
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBounds(0, 0, 690, 910);
        mainPanel.setBackground(Color.BLACK);

        // Home panel configuration
        // The default panel shown after login
        homePanel = new JPanel();
        homePanel.setLayout(null);
        homePanel.setBounds(0, 0, 690, 910);
        homePanel.setBackground(Color.BLACK);
        
        // Adding home images
        // Decorative elements for the home screen
        ImageIcon homeImg = new ImageIcon("homeImg.png");
        JLabel homeImgLbl = new JLabel(homeImg);
        homeImgLbl.setBounds(0, 370, 775, 500);
        ImageIcon logoHomeImg = new ImageIcon("logoImg.png");
        
        JLabel logoHomeImgLbl = new JLabel(logoHomeImg);
        logoHomeImgLbl.setBounds(125, 50, 450, 450);
        homePanel.add(homeImgLbl);
        homePanel.add(logoHomeImgLbl);
        mainPanel.add(homePanel);

        // Add member panel setup
        // Allows selection between regular and premium member addition
        addMemberPanel = new JPanel();
        addMemberPanel.setLayout(null);
        addMemberPanel.setBounds(0, 0, 690, 910);
        addMemberPanel.setBackground(Color.BLACK);
        
        JLabel addMemLabel = new JLabel("Add Member");
        addMemLabel.setForeground(Color.WHITE);
        addMemLabel.setFont(new Font("Arial", Font.BOLD, 37));
        addMemLabel.setBounds(225, 50, 400, 50);
        addMemberPanel.add(addMemLabel);
        
        addRegularSwitchBtn = new JButton("Add Regular Member");
        addRegularSwitchBtn.setBounds(175, 180, 325, 40);
        addRegularSwitchBtn.setFont(new Font("SansSerif", Font.BOLD, 20));
        addRegularSwitchBtn.addActionListener(this);
        buttonPointer(addRegularSwitchBtn);
        addMemberPanel.add(addRegularSwitchBtn);
        
        addPremiumSwitchBtn = new JButton("Add Premium Member");
        addPremiumSwitchBtn.setBounds(175, 270, 325, 40);
        addPremiumSwitchBtn.setFont(new Font("SansSerif", Font.BOLD, 20));
        addPremiumSwitchBtn.addActionListener(this);
        buttonPointer(addPremiumSwitchBtn);
        addMemberPanel.add(addPremiumSwitchBtn);
        mainPanel.add(addMemberPanel);

        // Regular member panel configuration
        // Form for adding regular members
        regularPanel = new JPanel();
        regularPanel.setLayout(null);
        regularPanel.setBounds(0, 0, 690, 910);
        regularPanel.setBackground(Color.BLACK);
        JLabel regHeading = new JLabel("Regular Member");
        regHeading.setForeground(Color.WHITE);
        regHeading.setFont(new Font("Arial", Font.BOLD, 37));
        regHeading.setBounds(225, 50, 400, 50);
        regularPanel.add(regHeading);
        
        // ID field setup
        // Collects the unique identifier for the member
        JLabel rIdLbl = new JLabel("Id");
        rIdLbl.setForeground(Color.WHITE);
        rIdLbl.setBounds(70, 120, 100, 30);
        rIdLbl.setFont(new Font("Arial", Font.PLAIN, 16));
        regularPanel.add(rIdLbl);
        rIdField = new JTextField();
        rIdField.setBounds(70, 150, 200, 24);
        regularPanel.add(rIdField);
        
        // Name field setup
        // Stores the member's full name
        JLabel rNameLbl = new JLabel("Name");
        rNameLbl.setForeground(Color.WHITE);
        rNameLbl.setBounds(350, 120, 100, 30);
        rNameLbl.setFont(new Font("Arial", Font.PLAIN, 16));
        regularPanel.add(rNameLbl);
        
        rNameField = new JTextField();
        rNameField.setBounds(350, 150, 200, 24);
        regularPanel.add(rNameField);
        
        // Location field setup
        // Records the member's location
        JLabel rLocLbl = new JLabel("Location");
        rLocLbl.setForeground(Color.WHITE);
        rLocLbl.setBounds(70, 190, 100, 30);
        rLocLbl.setFont(new Font("Arial", Font.PLAIN, 16));
        regularPanel.add(rLocLbl);
        
        rLocationField = new JTextField();
        rLocationField.setBounds(70, 220, 200, 24);
        regularPanel.add(rLocationField);
        
        // Phone field setup
        // Captures the member's contact number
        JLabel rPhoneLbl = new JLabel("Phone Number");
        rPhoneLbl.setForeground(Color.WHITE);
        rPhoneLbl.setBounds(350, 190, 150, 30);
        rPhoneLbl.setFont(new Font("Arial", Font.PLAIN, 16));
        regularPanel.add(rPhoneLbl);
        
        rPhoneField = new JTextField();
        rPhoneField.setBounds(350, 220, 200, 24);
        regularPanel.add(rPhoneField);
        
        // Email field setup
        // Stores the member's email address
        JLabel rEmailLbl = new JLabel("Email");
        rEmailLbl.setForeground(Color.WHITE);
        rEmailLbl.setBounds(70, 260, 100, 30);
        rEmailLbl.setFont(new Font("Arial", Font.PLAIN, 16));
        regularPanel.add(rEmailLbl);
        
        rEmailField = new JTextField();
        rEmailField.setBounds(70, 290, 350, 24);
        regularPanel.add(rEmailField);
        
        // Gender selection setup
        // Allows specification of member gender
        JLabel rGenderLbl = new JLabel("Gender");
        rGenderLbl.setForeground(Color.WHITE);
        rGenderLbl.setBounds(70, 330, 100, 30);
        rGenderLbl.setFont(new Font("Arial", Font.PLAIN, 16));
        regularPanel.add(rGenderLbl);
        
        rMaleBtn = new JRadioButton("Male");
        rFemaleBtn = new JRadioButton("Female");
        rMaleBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        rFemaleBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        rMaleBtn.setBackground(Color.BLACK);
        rFemaleBtn.setBackground(Color.BLACK);
        rMaleBtn.setForeground(Color.WHITE);
        rFemaleBtn.setForeground(Color.WHITE);
        rMaleBtn.setBounds(70, 360, 100, 30);
        rFemaleBtn.setBounds(180, 360, 100, 30);
        ButtonGroup rGenderBG = new ButtonGroup();
        rGenderBG.add(rMaleBtn);
        rGenderBG.add(rFemaleBtn);
        
        regularPanel.add(rMaleBtn);
        regularPanel.add(rFemaleBtn);
        
        // Date of birth setup
        // Collects the member's birth date
        JLabel rDobLbl = new JLabel("Date Of Birth (YYYY/MM/DD)");
        rDobLbl.setForeground(Color.WHITE);
        rDobLbl.setBounds(70, 400, 250, 30);
        rDobLbl.setFont(new Font("Arial", Font.PLAIN, 16));
        regularPanel.add(rDobLbl);
        
        rDobYear = new JComboBox(yearsArr);
        rDobMonth = new JComboBox(monthsArr);
        rDobDay = new JComboBox(daysArr);
        rDobYear.setBounds(70, 430, 80, 30);
        rDobMonth.setBounds(170, 430, 60, 30);
        rDobDay.setBounds(250, 430, 60, 30);
        
        regularPanel.add(rDobYear);
        regularPanel.add(rDobMonth);
        regularPanel.add(rDobDay);
        
        // Membership start date setup
        // Records when the membership begins
        JLabel rMsLbl = new JLabel("Member Start Date (YYYY/MM/DD)");
        rMsLbl.setForeground(Color.WHITE);
        rMsLbl.setBounds(70, 470, 300, 30);
        rMsLbl.setFont(new Font("Arial", Font.PLAIN, 16));
        regularPanel.add(rMsLbl);
        rMsYear = new JComboBox(yearsArr);
        rMsMonth = new JComboBox(monthsArr);
        rMsDay = new JComboBox(daysArr);
        rMsYear.setBounds(70, 500, 80, 30);
        rMsMonth.setBounds(170, 500, 60, 30);
        rMsDay.setBounds(250, 500, 60, 30);
        regularPanel.add(rMsYear);
        regularPanel.add(rMsMonth);
        regularPanel.add(rMsDay);
        
        // Referral field setup
        // Captures referral information
        JLabel rRefLbl = new JLabel("Referral");
        rRefLbl.setForeground(Color.WHITE);
        rRefLbl.setBounds(70, 540, 100, 30);
        rRefLbl.setFont(new Font("Arial", Font.PLAIN, 16));
        regularPanel.add(rRefLbl);
        rReferralField = new JTextField();
        rReferralField.setBounds(70, 570, 200, 24);
        regularPanel.add(rReferralField);
        
        // Display button
        // Shows all regular member details
        rDisplayBtn = new JButton("Display");
        rDisplayBtn.setBounds(70, 680, 175, 40);
        rDisplayBtn.setFont(new Font("Arial", Font.BOLD, 16));
        rDisplayBtn.setBackground(new Color(0xB4B8AC));
        rDisplayBtn.addActionListener(this);
        buttonPointer(rDisplayBtn);
        regularPanel.add(rDisplayBtn);
        
        // Clear button
        // Resets the form fields
        rClearBtn = new JButton("Clear");
        rClearBtn.setBounds(350, 680, 175, 40);
        rClearBtn.setFont(new Font("Arial", Font.BOLD, 16));
        rClearBtn.setBackground(new Color(0xB4B8AC));
        rClearBtn.addActionListener(this);
        buttonPointer(rClearBtn);
        regularPanel.add(rClearBtn);
        
        // Add member button
        // Submits the regular member data
        rAddMemberBtn = new JButton("Add member");
        rAddMemberBtn.setBounds(475, 800, 175, 40);
        rAddMemberBtn.setBackground(Color.GREEN);
        rAddMemberBtn.setFont(new Font("Arial", Font.BOLD, 16));
        rAddMemberBtn.addActionListener(this);
        buttonPointer(rAddMemberBtn);
        regularPanel.add(rAddMemberBtn);
        
        mainPanel.add(regularPanel);
        
        // Premium member panel configuration
        // Form for adding premium members
        premiumPanel = new JPanel();
        premiumPanel.setLayout(null);
        premiumPanel.setBounds(0, 0, 690, 910);
        premiumPanel.setBackground(Color.BLACK);
        JLabel preHeading = new JLabel("Premium Member");
        
        preHeading.setForeground(Color.WHITE);
        preHeading.setFont(new Font("Arial", Font.BOLD, 37));
        preHeading.setBounds(225, 50, 400, 50);
        premiumPanel.add(preHeading);
        
        // ID field for premium member
        JLabel pIdLbl = new JLabel("Id");
        pIdLbl.setForeground(Color.WHITE);
        pIdLbl.setBounds(70, 120, 100, 30);
        pIdLbl.setFont(new Font("Arial", Font.PLAIN, 16));
        premiumPanel.add(pIdLbl);
        
        pIdField = new JTextField();
        pIdField.setBounds(70, 150, 200, 24);
        premiumPanel.add(pIdField);
        
        // Name field for premium member
        JLabel pNameLbl = new JLabel("Name");
        pNameLbl.setForeground(Color.WHITE);
        pNameLbl.setBounds(350, 120, 100, 30);
        pNameLbl.setFont(new Font("Arial", Font.PLAIN, 16));
        premiumPanel.add(pNameLbl);
        
        pNameField = new JTextField();
        pNameField.setBounds(350, 150, 200, 24);
        premiumPanel.add(pNameField);
        
        // Location field for premium member
        JLabel pLocLbl = new JLabel("Location");
        pLocLbl.setForeground(Color.WHITE);
        pLocLbl.setBounds(70, 190, 100, 30);
        pLocLbl.setFont(new Font("Arial", Font.PLAIN, 16));
        premiumPanel.add(pLocLbl);
        
        pLocationField = new JTextField();
        pLocationField.setBounds(70, 220, 200, 24);
        premiumPanel.add(pLocationField);
        
        // Phone field for premium member
        JLabel pPhoneLbl = new JLabel("Phone Number");
        pPhoneLbl.setForeground(Color.WHITE);
        pPhoneLbl.setBounds(350, 190, 150, 30);
        pPhoneLbl.setFont(new Font("Arial", Font.PLAIN, 16));
        premiumPanel.add(pPhoneLbl);
        
        pPhoneField = new JTextField();
        pPhoneField.setBounds(350, 220, 200, 24);
        premiumPanel.add(pPhoneField);
        
        // Email field for premium member
        JLabel pEmailLbl = new JLabel("Email");
        pEmailLbl.setForeground(Color.WHITE);
        pEmailLbl.setBounds(70, 260, 100, 30);
        pEmailLbl.setFont(new Font("Arial", Font.PLAIN, 16));
        premiumPanel.add(pEmailLbl);
        
        pEmailField = new JTextField();
        pEmailField.setBounds(70, 290, 350, 24);
        premiumPanel.add(pEmailField);
        
        // Gender selection for premium member
        JLabel pGenderLbl = new JLabel("Gender");
        pGenderLbl.setForeground(Color.WHITE);
        pGenderLbl.setBounds(70, 330, 100, 30);
        pGenderLbl.setFont(new Font("Arial", Font.PLAIN, 16));
        premiumPanel.add(pGenderLbl);
        
        pMaleBtn = new JRadioButton("Male");
        pFemaleBtn = new JRadioButton("Female");
        pMaleBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        pFemaleBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        pMaleBtn.setBackground(Color.BLACK);
        pFemaleBtn.setBackground(Color.BLACK);
        pMaleBtn.setForeground(Color.WHITE);
        pFemaleBtn.setForeground(Color.WHITE);
        pMaleBtn.setBounds(70, 360, 100, 30);
        pFemaleBtn.setBounds(180, 360, 100, 30);
        ButtonGroup pGenderBG = new ButtonGroup();
        
        pGenderBG.add(pMaleBtn);
        pGenderBG.add(pFemaleBtn);
        premiumPanel.add(pMaleBtn);
        premiumPanel.add(pFemaleBtn);
        
        // Date of birth for premium member
        JLabel pDobLbl = new JLabel("Date Of Birth (YYYY/MM/DD)");
        pDobLbl.setForeground(Color.WHITE);
        pDobLbl.setBounds(70, 400, 250, 30);
        pDobLbl.setFont(new Font("Arial", Font.PLAIN, 16));
        premiumPanel.add(pDobLbl);
        
        pDobYear = new JComboBox(yearsArr);
        pDobMonth = new JComboBox(monthsArr);
        pDobDay = new JComboBox(daysArr);
        pDobYear.setBounds(70, 430, 80, 30);
        pDobMonth.setBounds(170, 430, 60, 30);
        pDobDay.setBounds(250, 430, 60, 30);
        premiumPanel.add(pDobYear);
        premiumPanel.add(pDobMonth);
        premiumPanel.add(pDobDay);
        
        // Membership start date for premium member
        JLabel pMsLbl = new JLabel("Member Start Date (YYYY/MM/DD)");
        pMsLbl.setForeground(Color.WHITE);
        pMsLbl.setBounds(70, 470, 300, 30);
        pMsLbl.setFont(new Font("Arial", Font.PLAIN, 16));
        premiumPanel.add(pMsLbl);
        
        pMsYear = new JComboBox(yearsArr);
        pMsMonth = new JComboBox(monthsArr);
        pMsDay = new JComboBox(daysArr);
        
        pMsYear.setBounds(70, 500, 80, 30);
        pMsMonth.setBounds(170, 500, 60, 30);
        pMsDay.setBounds(250, 500, 60, 30);
        
        premiumPanel.add(pMsYear);
        premiumPanel.add(pMsMonth);
        premiumPanel.add(pMsDay);
        
        // Trainer field for premium member
        // Specific to premium members for personal training
        JLabel tLbl = new JLabel("Trainer");
        tLbl.setForeground(Color.WHITE);
        tLbl.setBounds(70, 540, 100, 30);
        tLbl.setFont(new Font("Arial", Font.PLAIN, 16));
        premiumPanel.add(tLbl);
        
        pTrainerField = new JTextField();
        pTrainerField.setBounds(70, 570, 200, 24);
        premiumPanel.add(pTrainerField);
        
        // Charge field for premium member
        // Displays the fixed premium membership cost
        JLabel chargeLbl = new JLabel("Premium Charge");
        chargeLbl.setForeground(Color.WHITE);
        chargeLbl.setBounds(350, 540, 200, 30);
        chargeLbl.setFont(new Font("Arial", Font.PLAIN, 16));
        premiumPanel.add(chargeLbl);
        
        pChargeField = new JTextField("50000");
        pChargeField.setBounds(350, 570, 150, 24);
        pChargeField.setEditable(false);
        premiumPanel.add(pChargeField);
        
        // Display button for premium members
        pDisplayBtn = new JButton("Display");
        pDisplayBtn.setBounds(70, 650, 175, 40);
        pDisplayBtn.setFont(new Font("Arial", Font.BOLD, 18));
        pDisplayBtn.setBackground(new Color(0xB4B8AC));
        pDisplayBtn.addActionListener(this);
        buttonPointer(pDisplayBtn);
        premiumPanel.add(pDisplayBtn);
        
        // Clear button for premium members
        pClearBtn = new JButton("Clear");
        pClearBtn.setBounds(350, 650, 175, 40);
        pClearBtn.setFont(new Font("Arial", Font.BOLD, 18));
        pClearBtn.setBackground(new Color(0xB4B8AC));
        pClearBtn.addActionListener(this);
        buttonPointer(pClearBtn);
        premiumPanel.add(pClearBtn);
        
        // Add member button for premium members
        pAddMemberBtn = new JButton("Add member");
        pAddMemberBtn.setBounds(475, 800, 175, 40);
        pAddMemberBtn.setBackground(Color.GREEN);
        pAddMemberBtn.setFont(new Font("Arial", Font.BOLD, 18));
        pAddMemberBtn.addActionListener(this);
        buttonPointer(pAddMemberBtn);
        premiumPanel.add(pAddMemberBtn);
        
        mainPanel.add(premiumPanel);

        // Activate/deactivate panel setup
        // Manages membership status changes
        activateDeactivatePanel = new JPanel();
        activateDeactivatePanel.setLayout(null);
        activateDeactivatePanel.setBounds(0, 0, 690, 910);
        activateDeactivatePanel.setBackground(Color.BLACK);
        JLabel actHeading = new JLabel("Activate/Deactivate Membership");
        actHeading.setForeground(Color.WHITE);
        actHeading.setBounds(110, 50, 500, 50);
        actHeading.setFont(new Font("Arial", Font.BOLD, 30));
        activateDeactivatePanel.add(actHeading);
        
        // ID field for activation/deactivation
        JLabel actIdLbl = new JLabel("Member Id");
        actIdLbl.setForeground(Color.WHITE);
        actIdLbl.setBounds(100, 150, 100, 30);
        actIdLbl.setFont(new Font("Arial", Font.PLAIN, 16));
        activateDeactivatePanel.add(actIdLbl);
        
        actIdField = new JTextField();
        actIdField.setBounds(100, 180, 200, 24);
        activateDeactivatePanel.add(actIdField);
        
        // Activate button
        activateMemberBtn = new JButton("Activate");
        activateMemberBtn.setBounds(100, 250, 175, 40);
        activateMemberBtn.setFont(new Font("Arial", Font.BOLD, 18));
        activateMemberBtn.setBackground(Color.GREEN);
        activateMemberBtn.addActionListener(this);
        buttonPointer(activateMemberBtn);
        activateDeactivatePanel.add(activateMemberBtn);
        
        // Deactivate button
        deactivateMemberBtn = new JButton("Deactivate");
        deactivateMemberBtn.setBounds(360, 250, 175, 40);
        deactivateMemberBtn.setFont(new Font("Arial", Font.BOLD, 18));
        deactivateMemberBtn.setBackground(Color.RED);
        deactivateMemberBtn.setForeground(Color.WHITE);
        deactivateMemberBtn.addActionListener(this);
        buttonPointer(deactivateMemberBtn);
        activateDeactivatePanel.add(deactivateMemberBtn);
        mainPanel.add(activateDeactivatePanel);

        // Mark attendance panel setup
        // Tracks member attendance
        markAttendancePanel = new JPanel();
        markAttendancePanel.setLayout(null);
        markAttendancePanel.setBounds(0, 0, 690, 910);
        markAttendancePanel.setBackground(Color.BLACK);
        JLabel markHeading = new JLabel("Mark Attendance");
        markHeading.setForeground(Color.WHITE);
        markHeading.setFont(new Font("Arial", Font.BOLD, 37));
        markHeading.setBounds(225, 50, 400, 50);
        markAttendancePanel.add(markHeading);
        
        // ID field for attendance
        JLabel markIdLbl = new JLabel("Member Id");
        markIdLbl.setForeground(Color.WHITE);
        markIdLbl.setBounds(100, 150, 100, 30);
        markIdLbl.setFont(new Font("Arial", Font.PLAIN, 16));
        markAttendancePanel.add(markIdLbl);
        
        markIdField = new JTextField();
        markIdField.setBounds(100, 180, 200, 24);
        markAttendancePanel.add(markIdField);
        
        // Mark regular attendance button
        markRegularBtn = new JButton("Mark Regular");
        markRegularBtn.setBounds(100, 250, 175, 40);
        markRegularBtn.setFont(new Font("Arial", Font.BOLD, 18));
        markRegularBtn.setBackground(new Color(0xB4B8AC));
        markRegularBtn.addActionListener(this);
        buttonPointer(markRegularBtn);
        markAttendancePanel.add(markRegularBtn);
        
        // Mark premium attendance button
        markPremiumBtn = new JButton("Mark Premium");
        markPremiumBtn.setBounds(360, 250, 175, 40);
        markPremiumBtn.setFont(new Font("Arial", Font.BOLD, 18));
        markPremiumBtn.setBackground(new Color(0xB4B8AC));
        markPremiumBtn.addActionListener(this);
        buttonPointer(markPremiumBtn);
        markAttendancePanel.add(markPremiumBtn);
        
        mainPanel.add(markAttendancePanel);

        // Upgrade plan panel setup
        // Facilitates plan upgrades
        upgradePlanPanel = new JPanel();
        upgradePlanPanel.setLayout(null);
        upgradePlanPanel.setBounds(0, 0, 690, 910);
        upgradePlanPanel.setBackground(Color.BLACK);
        
        JLabel upHeading = new JLabel("Upgrade Plan");
        upHeading.setForeground(Color.WHITE);
        upHeading.setFont(new Font("Arial", Font.BOLD, 37));
        upHeading.setBounds(225, 50, 400, 50);
        upgradePlanPanel.add(upHeading);
        
        // ID field for upgrade
        JLabel upIdLbl = new JLabel("Member Id");
        upIdLbl.setForeground(Color.WHITE);
        upIdLbl.setBounds(100, 150, 100, 30);
        upIdLbl.setFont(new Font("Arial", Font.PLAIN, 16));
        upgradePlanPanel.add(upIdLbl);
        
        upIdField = new JTextField();
        upIdField.setBounds(100, 180, 200, 24);
        upgradePlanPanel.add(upIdField);
        
        // Plan selection for upgrade
        JLabel upPlanLbl = new JLabel("New Plan");
        upPlanLbl.setForeground(Color.WHITE);
        upPlanLbl.setBounds(100, 230, 100, 30);
        upPlanLbl.setFont(new Font("Arial", Font.PLAIN, 16));
        upgradePlanPanel.add(upPlanLbl);
        
        upPlanBox = new JComboBox(planOptionsArr);
        upPlanBox.setBounds(100, 260, 125, 30);
        upgradePlanPanel.add(upPlanBox);
        
        // Upgrade button
        upgradePlanBtn = new JButton("Upgrade");
        upgradePlanBtn.setBounds(100, 320, 175, 40);
        upgradePlanBtn.setFont(new Font("Arial", Font.BOLD, 18));
        upgradePlanBtn.setBackground(new Color(0xB4B8AC));
        upgradePlanBtn.addActionListener(this);
        buttonPointer(upgradePlanBtn);
        upgradePlanPanel.add(upgradePlanBtn);
        mainPanel.add(upgradePlanPanel);

        // Discount panel setup
        // Calculates discounts for premium members
        discountPanel = new JPanel();
        discountPanel.setLayout(null);
        discountPanel.setBounds(0, 0, 690, 910);
        discountPanel.setBackground(Color.BLACK);
        
        JLabel discHeading = new JLabel("Calculate Discount");
        discHeading.setForeground(Color.WHITE);
        discHeading.setFont(new Font("Arial", Font.BOLD, 37));
        discHeading.setBounds(225, 50, 400, 50);
        discountPanel.add(discHeading);
        
        // ID field for discount
        JLabel discIdLbl = new JLabel("Member Id");
        discIdLbl.setForeground(Color.WHITE);
        discIdLbl.setBounds(100, 150, 100, 30);
        discIdLbl.setFont(new Font("Arial", Font.PLAIN, 16));
        discountPanel.add(discIdLbl);
        
        discIdField = new JTextField();
        discIdField.setBounds(100, 180, 200, 24);
        discountPanel.add(discIdField);
        
        // Calculate discount button
        calcDiscountBtn = new JButton("Calculate");
        calcDiscountBtn.setBounds(100, 240, 175, 40);
        calcDiscountBtn.setFont(new Font("Arial", Font.BOLD, 18));
        calcDiscountBtn.setBackground(new Color(0xB4B8AC));
        calcDiscountBtn.addActionListener(this);
        buttonPointer(calcDiscountBtn);
        discountPanel.add(calcDiscountBtn);
        
        // Discount amount display
        JLabel dAmtLbl = new JLabel("Discount Amount");
        dAmtLbl.setForeground(Color.WHITE);
        dAmtLbl.setBounds(100, 300, 200, 30);
        dAmtLbl.setFont(new Font("Arial", Font.PLAIN, 16));
        discountPanel.add(dAmtLbl);
        discAmountField = new JTextField();
        discAmountField.setBounds(100, 330, 200, 24);
        discAmountField.setEditable(false);
        discountPanel.add(discAmountField);
        mainPanel.add(discountPanel);

        // Pay due panel setup
        // Manages due payments
        payDuePanel = new JPanel();
        payDuePanel.setLayout(null);
        payDuePanel.setBounds(0, 0, 690, 910);
        payDuePanel.setBackground(Color.BLACK);
        
        JLabel payHeading = new JLabel("Pay Due Amount");
        payHeading.setForeground(Color.WHITE);
        payHeading.setFont(new Font("Arial", Font.BOLD, 37));
        payHeading.setBounds(225, 50, 400, 50);
        payDuePanel.add(payHeading);
        
        // ID field for payment
        JLabel payIdLbl = new JLabel("Member Id");
        payIdLbl.setForeground(Color.WHITE);
        payIdLbl.setBounds(100, 150, 100, 30);
        payIdLbl.setFont(new Font("Arial", Font.PLAIN, 16));
        payDuePanel.add(payIdLbl);
        
        payIdField = new JTextField();
        payIdField.setBounds(100, 180, 200, 24);
        payDuePanel.add(payIdField);
        
        // Amount field for payment
        JLabel payAmtLbl = new JLabel("Amount:");
        payAmtLbl.setForeground(Color.WHITE);
        payAmtLbl.setBounds(100, 240, 100, 30);
        payAmtLbl.setFont(new Font("Arial", Font.PLAIN, 16));
        payDuePanel.add(payAmtLbl);
        
        payAmountField = new JTextField();
        payAmountField.setBounds(100, 270, 200, 24);
        payDuePanel.add(payAmountField);
        
        // Pay button
        payDueButton = new JButton("Pay Amount");
        payDueButton.setBounds(100, 330, 175, 40);
        payDueButton.setFont(new Font("Arial", Font.BOLD, 18));
        payDueButton.setBackground(new Color(0xB4B8AC));
        payDueButton.addActionListener(this);
        buttonPointer(payDueButton);
        payDuePanel.add(payDueButton);
        mainPanel.add(payDuePanel);
        
        // Revert member panel setup
        // Handles member reversion
        revertMemberPanel = new JPanel();
        revertMemberPanel.setLayout(null);
        revertMemberPanel.setBounds(0, 0, 690, 910);
        revertMemberPanel.setBackground(Color.BLACK);
        
        JLabel revHeading = new JLabel("Revert Member");
        revHeading.setForeground(Color.WHITE);
        revHeading.setFont(new Font("Arial", Font.BOLD, 37));
        revHeading.setBounds(225, 50, 400, 50);
        revertMemberPanel.add(revHeading);
        
        // ID field for reversion
        JLabel revIdLbl = new JLabel("Member Id");
        revIdLbl.setForeground(Color.WHITE);
        revIdLbl.setBounds(100, 150, 100, 30);
        revIdLbl.setFont(new Font("Arial", Font.PLAIN, 16));
        revertMemberPanel.add(revIdLbl);
        
        revertIdField = new JTextField();
        revertIdField.setBounds(100, 180, 200, 24);
        revertMemberPanel.add(revertIdField);
        
        // Revert regular button
        revertRegularBtn = new JButton("Revert Regular");
        revertRegularBtn.setBounds(100, 250, 175, 40);
        revertRegularBtn.setFont(new Font("Arial", Font.BOLD, 18));
        revertRegularBtn.setBackground(new Color(0xB4B8AC));
        revertRegularBtn.addActionListener(this);
        buttonPointer(revertRegularBtn);
        revertMemberPanel.add(revertRegularBtn);
        
        // Revert premium button
        revertPremiumBtn = new JButton("Revert Premium");
        revertPremiumBtn.setBounds(360, 250, 175, 40);
        revertPremiumBtn.setFont(new Font("Arial", Font.BOLD, 18));
        revertPremiumBtn.setBackground(new Color(0xB4B8AC));
        revertPremiumBtn.addActionListener(this);
        buttonPointer(revertPremiumBtn);
        revertMemberPanel.add(revertPremiumBtn);
        mainPanel.add(revertMemberPanel);

        // Final frame setup
        // Adds the main panel and makes the frame visible
        frame.add(mainPanel);
        frame.setVisible(true);
        hideAllSubPanels();
    }
    
    // Method to hide all sub-panels
    public void hideAllSubPanels() 
    {
        homePanel.setVisible(false); // Hiding the panel as setting visible to false
        addMemberPanel.setVisible(false); // Hiding the panel as setting visible to false
        regularPanel.setVisible(false); // Hiding the panel as setting visible to false
        premiumPanel.setVisible(false); // Hiding the panel as setting visible to false
        activateDeactivatePanel.setVisible(false); // Hiding the panel as setting visible to false
        markAttendancePanel.setVisible(false); // Hiding the panel as setting visible to false
        upgradePlanPanel.setVisible(false); // Hiding the panel as setting visible to false
        discountPanel.setVisible(false); // Hiding the panel as setting visible to false
        revertMemberPanel.setVisible(false); // Hiding the panel as setting visible to false
        payDuePanel.setVisible(false); // Hiding the panel as setting visible to false
    }

    // Button pointer method explanation
    // Enhances button usability by setting cursor and focus properties
    private void buttonPointer(JButton button) 
    {
        button.setFocusable(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    // Find member by ID method
    // Searches the ArrayList for a member with the given ID
    private GymMember searchMemId(int id) 
    {
        // Using a for-each loop to iterate through the ArrayList of members
        for (GymMember gm : membersObj)
        {
            if (gm.getId() == id)
            {
                return gm;
            }
        }
        return null;
    }

    // Duplicate ID check method
    // Verifies if an ID is already in use
    private boolean isDuplicateId(int id) 
    {
        return searchMemId(id) != null;
    }

    // Action listener implementation
    // Handles all button clicks and events in the GUI
    @Override
    public void actionPerformed(ActionEvent eve) 
    {
        Object btn = eve.getSource();

        // Login button handler
        // Authenticates the admin user
        if (btn == loginButton) 
        {
            String user = userTxt.getText();
            String pass = new String(passwordTxt.getPassword());
            if (user.equals("admin") && pass.equals("admin"))
            {
                JPanel container = new JPanel(null);
                container.setBounds(0, 0, 890, 910);
                container.add(buttonPanel);
                container.add(mainPanel);
                mainPanel.setBounds(200, 0, 690, 910);
                frame.setContentPane(container);
                frame.repaint();
                frame.revalidate();
                hideAllSubPanels();
                homePanel.setVisible(true);
            } 
            else if (user.equals("") || pass.equals("")) 
            {
                JOptionPane.showMessageDialog(frame, "Please fill up both text areas!", "Login Message", JOptionPane.INFORMATION_MESSAGE);
            } 
            else 
            {
                JOptionPane.showMessageDialog(frame, "Invalid username or password!", "Login Message", JOptionPane.ERROR_MESSAGE);
            }
        }
        // Show password checkbox handler
        // Toggles password visibility
        else if (btn == showPassword) 
        {
            if (showPassword.isSelected())
            {
                passwordTxt.setEchoChar((char) 0);
            } 
            else 
            {
                passwordTxt.setEchoChar('*');
            }
        }
        // Navigation button handlers
        // Switch between different panels
        if (btn == addMemberBtn) 
        {
            homePanel.setVisible(false); // Hiding the panel as setting visible to false
            regularPanel.setVisible(false); // Hiding the panel as setting visible to false
            premiumPanel.setVisible(false); // Hiding the panel as setting visible to false
            activateDeactivatePanel.setVisible(false); // Hiding the panel as setting visible to false
            markAttendancePanel.setVisible(false); // Hiding the panel as setting visible to false
            upgradePlanPanel.setVisible(false); // Hiding the panel as setting visible to false
            discountPanel.setVisible(false); // Hiding the panel as setting visible to false
            revertMemberPanel.setVisible(false); // Hiding the panel as setting visible to false
            payDuePanel.setVisible(false); // Hiding the panel as setting visible to false
            addMemberPanel.setVisible(true);
        }
        if (btn == activateBtn) 
        {
            hideAllSubPanels();
            activateDeactivatePanel.setVisible(true);
        }
        if (btn == markAttendanceNavBtn) 
        {
            hideAllSubPanels();
            markAttendancePanel.setVisible(true);
        }
        if (btn == upgradeBtn) 
        {
            hideAllSubPanels();
            upgradePlanPanel.setVisible(true);
        }
        if (btn == discountBtn) 
        {
            hideAllSubPanels();
            discountPanel.setVisible(true);
        }
        if (btn == payDueNavBtn) 
        {
            hideAllSubPanels();
            payDuePanel.setVisible(true);
        }
        if (btn == revertMemberNavBtn) 
        {
            hideAllSubPanels();
            revertMemberPanel.setVisible(true);
        }
        if (btn == logOutBtn) 
        {
            frame.getContentPane().removeAll();
            frame.setContentPane(loginPanel);
            frame.repaint();
            frame.revalidate();
        }
        if (btn == addRegularSwitchBtn) 
        {
            hideAllSubPanels();
            regularPanel.setVisible(true);
        }
        if (btn == addPremiumSwitchBtn) 
        {
            hideAllSubPanels();
            premiumPanel.setVisible(true);
        }
        
        // Add regular member handler
        // Validates and adds a regular member
        if (btn == rAddMemberBtn) 
        {
            if (rIdField.getText().isEmpty() || rNameField.getText().isEmpty() || rLocationField.getText().isEmpty() || rPhoneField.getText().isEmpty() ||
            rEmailField.getText().isEmpty() || rReferralField.getText().isEmpty() || (!rMaleBtn.isSelected() && !rFemaleBtn.isSelected())) 
            {
                JOptionPane.showMessageDialog(frame, "Please fill in all required fields for Regular Member.");
                return;
            }
            String phoneStr = rPhoneField.getText().trim();
            try 
            {
                Long.parseLong(phoneStr);
            } 
            catch (NumberFormatException e) 
            {
                JOptionPane.showMessageDialog(frame, "Phone number must contain number only.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (phoneStr.length() != 10) 
            {
                JOptionPane.showMessageDialog(frame, "Phone number must be of 10 digits.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try 
            {
                id = Integer.parseInt(rIdField.getText().trim());
                if (isDuplicateId(id)) 
                {
                    JOptionPane.showMessageDialog(frame, "Member with this ID already exists!");
                    return;
                }
                name = rNameField.getText().trim();
                location = rLocationField.getText().trim();
                phone = phoneStr;
                email = rEmailField.getText().trim();
                gender = (rMaleBtn.isSelected()) ? "Male" : "Female";
                dob = rDobYear.getSelectedItem() + "/" + rDobMonth.getSelectedItem() + "/" + rDobDay.getSelectedItem();
                msd = rMsYear.getSelectedItem() + "/" + rMsMonth.getSelectedItem() + "/" + rMsDay.getSelectedItem();
                referral = rReferralField.getText().trim();
                RegularMember rMemObj = new RegularMember(id, name, location, phone, email, gender, dob, msd, referral);
                // Upcasting: Adding RegularMember to ArrayList<GymMember>
                rMemObj.setPlan("Basic");
                membersObj.add(rMemObj);
                JOptionPane.showMessageDialog(frame, "Regular Member added successfully!");
            } 
            catch (NumberFormatException ex) 
            {
                JOptionPane.showMessageDialog(frame, "Character value detected. Please input number only!");
            }
        }
        // Display regular members handler
        // Shows all regular member details
        if (btn == rDisplayBtn)
        {
            if (membersObj.isEmpty()) 
            {
                JOptionPane.showMessageDialog(frame, "No members available!");
                return;
            }
            JFrame rDisplayInfoFrame = new JFrame("Regular Member Display");
            rDisplayInfoFrame.setSize(320, 730);
            rDisplayInfoFrame.setLayout(null);
            JTextArea textArea = new JTextArea();
            textArea.setEditable(false);
            textArea.setFont(new Font("Arial", Font.PLAIN, 14)); // Increased font size slightly
            String display = "";
            // Using a for-each loop to iterate through the ArrayList of members
            for (GymMember gm : membersObj) 
            {
                // Downcasting: Converting GymMember to RegularMember to access specific methods
                if (gm instanceof RegularMember) 
                {
                    RegularMember rm = (RegularMember) gm;
                    display += "ID: " + rm.getId() + "\n";
                    display += "Name: " + rm.getName() + "\n";
                    display += "Location: " + rm.getLocation() + "\n";
                    display += "Phone: " + rm.getPhone() + "\n";
                    display += "Email: " + rm.getEmail() + "\n";
                    display += "Gender: " + rm.getGender() + "\n";
                    display += "DOB: " + rm.getDOB() + "\n";
                    display += "Membership Start Date: " + rm.getMembershipStartDate() + "\n";
                    display += "Attendance: " + rm.getAttendance() + "\n";
                    display += "Loyalty Points: " + rm.getLoyaltyPoints() + "\n";
                    display += "Active Status: " + rm.getActiveStatus() + "\n";
                    display += "Plan: " + rm.getPlan() + "\n";
                    display += "Price: " + rm.getPrice() + "\n";
                    display += "Referral Source: " + rm.getReferralSource() + "\n";
                    if (!rm.getRemovalReason().isEmpty()) 
                    {
                        display += "Removal Reason: " + rm.getRemovalReason() + "\n";
                    }
                    display += "------------------------------------------\n";
                }
            }
            textArea.setText(display);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setBounds(10, 10, 300, 690); // Scroll bar spans top to bottom
            rDisplayInfoFrame.setResizable(false);
            rDisplayInfoFrame.add(scrollPane);
            rDisplayInfoFrame.setVisible(true);
        }
        // Clear regular member fields handler
        // Resets the regular member form
        else if (btn == rClearBtn)
        {
            rIdField.setText("");
            rNameField.setText("");
            rLocationField.setText("");
            rPhoneField.setText("");
            rEmailField.setText("");
            rReferralField.setText("");
            rMaleBtn.setSelected(false);
            rFemaleBtn.setSelected(false);
            rDobYear.setSelectedIndex(0);
            rDobMonth.setSelectedIndex(0);
            rDobDay.setSelectedIndex(0);
            rMsYear.setSelectedIndex(0);
            rMsMonth.setSelectedIndex(0);
            rMsDay.setSelectedIndex(0);
        }
        // Add premium member handler
        // Validates and adds a premium member
        else if (btn == pAddMemberBtn) 
        {
            if (pIdField.getText().isEmpty() || pNameField.getText().isEmpty() || pLocationField.getText().isEmpty() || pPhoneField.getText().isEmpty() ||
            pEmailField.getText().isEmpty() || pTrainerField.getText().isEmpty() || (!pMaleBtn.isSelected() && !pFemaleBtn.isSelected())) 
            {
                JOptionPane.showMessageDialog(frame, "Please fill in all required fields for Premium Member.");
                return;
            }
            String phoneStr = pPhoneField.getText().trim();
            try 
            {
                Long.parseLong(phoneStr);
            } 
            catch (NumberFormatException e) 
            {
                JOptionPane.showMessageDialog(frame, "Phone number must contain number only.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (phoneStr.length() != 10) 
            {
                JOptionPane.showMessageDialog(frame, "Phone number must be of 10 digits.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try 
            {
                id = Integer.parseInt(pIdField.getText().trim());
                if (isDuplicateId(id)) 
                {
                    JOptionPane.showMessageDialog(frame, "Member with this ID already exists!");
                    return;
                }
                name = pNameField.getText().trim();
                location = pLocationField.getText().trim();
                phone = phoneStr;
                email = pEmailField.getText().trim();
                gender = (pMaleBtn.isSelected()) ? "Male" : "Female";
                dob = pDobYear.getSelectedItem() + "/" + pDobMonth.getSelectedItem() + "/" + pDobDay.getSelectedItem();
                msd = pMsYear.getSelectedItem() + "/" + pMsMonth.getSelectedItem() + "/" + pMsDay.getSelectedItem();
                trainer = pTrainerField.getText().trim();
                PremiumMember pMemObj = new PremiumMember(id, name, location, phone, email, gender, dob, msd, trainer);
                // Upcasting: Adding PremiumMember to ArrayList<GymMember>
                membersObj.add(pMemObj);
                JOptionPane.showMessageDialog(frame, "Premium Member added successfully!");
            } 
            catch (NumberFormatException ex) 
            {
                JOptionPane.showMessageDialog(frame, "Character value detected. Please input number only!");
            }
        }
        // Display premium members handler
        // Shows all premium member details
        else if (btn == pDisplayBtn) 
        {
            if (membersObj.isEmpty()) 
            {
                JOptionPane.showMessageDialog(frame, "No members available!");
                return;
            }
            JFrame pDisplayInfoFrame = new JFrame("Premium Member Display");
            pDisplayInfoFrame.setSize(320, 730);
            pDisplayInfoFrame.setLayout(null);
            JTextArea textArea = new JTextArea();
            textArea.setEditable(false);
            textArea.setFont(new Font("Arial", Font.PLAIN, 14)); // Increased font size slightly
            String display = "";
            // Using a for-each loop to iterate through the ArrayList of members
            for (GymMember gm : membersObj) 
            {
                // Downcasting: Converting GymMember to PremiumMember to access specific methods
                if (gm instanceof PremiumMember) 
                {
                    PremiumMember pm = (PremiumMember) gm;
                    double remaining = pm.getPremiumCharge() - pm.getPaidAmount();
                    boolean full = pm.isFullPayment();
                    double discount = pm.getDiscountAmount();
                    double paidAmt = full ? (pm.getPremiumCharge() - discount) : pm.getPaidAmount();
                    display += "ID: " + pm.getId() + "\n";
                    display += "Name: " + pm.getName() + "\n";
                    display += "Location: " + pm.getLocation() + "\n";
                    display += "Phone: " + pm.getPhone() + "\n";
                    display += "Email: " + pm.getEmail() + "\n";
                    display += "Gender: " + pm.getGender() + "\n";
                    display += "DOB: " + pm.getDOB() + "\n";
                    display += "Membership Start Date: " + pm.getMembershipStartDate() + "\n";
                    display += "Attendance: " + pm.getAttendance() + "\n";
                    display += "Loyalty Points: " + pm.getLoyaltyPoints() + "\n";
                    display += "Active Status: " + pm.getActiveStatus() + "\n";
                    display += "Personal Trainer" + pm.getPersonalTrainer() + "\n";
                    display += "Premium Charge: " + pm.getPremiumCharge() + "\n";
                    display += "Paid Amount: " + paidAmt + "\n";
                    display += "Remaining Amount: " + (full ? 0 : remaining) + "\n";
                    display += "Discount Amount: " + discount + "\n";
                    display += "------------------------------------------\n";
                }
            }
            textArea.setText(display);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setBounds(10, 10, 300, 690); // Scroll bar spans top to bottom
            pDisplayInfoFrame.setResizable(false);
            pDisplayInfoFrame.add(scrollPane);
            pDisplayInfoFrame.setVisible(true);
        }
        // Clear premium member fields handler
        // Resets the premium member form
        else if (btn == pClearBtn) 
        {
            pIdField.setText("");
            pNameField.setText("");
            pLocationField.setText("");
            pPhoneField.setText("");
            pEmailField.setText("");
            pTrainerField.setText("");
            pMaleBtn.setSelected(false);
            pFemaleBtn.setSelected(false);
            pDobYear.setSelectedIndex(0);
            pDobMonth.setSelectedIndex(0);
            pDobDay.setSelectedIndex(0);
            pMsYear.setSelectedIndex(0);
            pMsMonth.setSelectedIndex(0);
            pMsDay.setSelectedIndex(0);
            pChargeField.setText("50000");
        }
        
        // Activate membership handler
        // Activates a member's status
        if (btn == activateMemberBtn) 
        {
            try 
            {
                int id = Integer.parseInt(actIdField.getText().trim());
                GymMember gm = searchMemId(id);
                if (gm != null) 
                {
                    gm.activeMembership();
                    JOptionPane.showMessageDialog(frame, "Membership activated!", "Status message", JOptionPane.INFORMATION_MESSAGE);
                } 
                else 
                {
                    JOptionPane.showMessageDialog(frame, "Member not found!", "Status message", JOptionPane.ERROR_MESSAGE);
                }
            } 
            catch (NumberFormatException ex) 
            {
                JOptionPane.showMessageDialog(frame, "Member Id can be number only!", "Status message", JOptionPane.WARNING_MESSAGE);
            }
        }
        // Deactivate membership handler
        // Deactivates a member's status
        if (btn == deactivateMemberBtn) 
        {
            try 
            {
                int id = Integer.parseInt(actIdField.getText().trim());
                GymMember gm = searchMemId(id);
                if (gm != null) 
                {
                    if (gm.getActiveStatus()) 
                    {
                        gm.deactivateMemberhship();
                        JOptionPane.showMessageDialog(frame, "Membership deactivated!", "Status message", JOptionPane.INFORMATION_MESSAGE);
                    } 
                    else 
                    {
                        JOptionPane.showMessageDialog(frame, "Membership is not active!", "Status message", JOptionPane.WARNING_MESSAGE);
                    }
                } 
                else 
                {
                    JOptionPane.showMessageDialog(frame, "Member not found!", "Status message", JOptionPane.ERROR_MESSAGE);
                }
            } 
            catch (NumberFormatException ex) 
            {
                JOptionPane.showMessageDialog(frame, "Member Id can be number only!", "Status message", JOptionPane.WARNING_MESSAGE);
            }
        }
        // Mark regular attendance handler
        // Records attendance for regular members
        if (btn == markRegularBtn) 
        {
            try 
            {
                int id = Integer.parseInt(markIdField.getText().trim());
                GymMember gm = searchMemId(id);
                // Downcasting to check if the member is a RegularMember
                if (gm != null && gm instanceof RegularMember) 
                {
                    if (gm.getActiveStatus()) 
                    {
                        gm.markAttendance();
                        JOptionPane.showMessageDialog(frame, "Attendance marked for Regular Member!", "Attendance message", JOptionPane.INFORMATION_MESSAGE);
                    } 
                    else 
                    {
                        JOptionPane.showMessageDialog(frame, "Member is not active!", "Attendance message", JOptionPane.WARNING_MESSAGE);
                    }
                } 
                else 
                {
                    JOptionPane.showMessageDialog(frame, "Regular Member not found!", "Attendance message", JOptionPane.ERROR_MESSAGE);
                }
            } 
            catch (NumberFormatException ex) 
            {
                JOptionPane.showMessageDialog(frame, "Invalid ID.", "Attendance message", JOptionPane.WARNING_MESSAGE);
            }
        }
        // Mark premium attendance handler
        // Records attendance for premium members
        if (btn == markPremiumBtn) 
        {
            try 
            {
                int id = Integer.parseInt(markIdField.getText().trim());
                GymMember gm = searchMemId(id);
                // Downcasting to check if the member is a PremiumMember
                if (gm != null && gm instanceof PremiumMember) 
                {
                    if (gm.getActiveStatus()) 
                    {
                        gm.markAttendance();
                        JOptionPane.showMessageDialog(frame, "Attendance marked for Premium Member!", "Attendance message", JOptionPane.INFORMATION_MESSAGE);
                    } 
                    else 
                    {
                        JOptionPane.showMessageDialog(frame, "Member is not active!", "Attendance message", JOptionPane.WARNING_MESSAGE);
                    }
                } 
                else 
                {
                    JOptionPane.showMessageDialog(frame, "Premium Member not found!", "Attendance message", JOptionPane.ERROR_MESSAGE);
                }
            } 
            catch (NumberFormatException ex) 
            {
                JOptionPane.showMessageDialog(frame, "Invalid ID.", "Attendance message", JOptionPane.WARNING_MESSAGE);
            }
        }
        // Upgrade plan handler
        // Upgrades a regular member's plan
        if (btn == upgradePlanBtn) 
        {
            try 
            {
                int id = Integer.parseInt(upIdField.getText().trim());
                GymMember gm = searchMemId(id);
                // Downcasting to RegularMember for plan upgrade functionality
                if (gm != null && gm instanceof RegularMember) 
                {
                    RegularMember rm = (RegularMember) gm;
                    String selectedPlan = (String) upPlanBox.getSelectedItem();
                    if (rm.getPlan().equalsIgnoreCase(selectedPlan)) 
                    {
                        JOptionPane.showMessageDialog(frame, "You are already subscribed to the " + selectedPlan + " plan.");
                        return;
                    }
                    if (rm.getAttendance() < 30) 
                    {
                        JOptionPane.showMessageDialog(frame, "Not enough attendance for upgrade! (Minimum 30 required)");
                        return;
                    }
                    String result = rm.upgradePlan(selectedPlan);
                    JOptionPane.showMessageDialog(frame, result);
                } 
                else 
                {
                    JOptionPane.showMessageDialog(frame, "Regular Member not found!");
                }
            } 
            catch (NumberFormatException ex) 
            {
                JOptionPane.showMessageDialog(frame, "Invalid ID.");
            }
        }
        // Calculate discount handler
        // Computes discount for premium members
        if (btn == calcDiscountBtn) 
        {
            try 
            {
                int id = Integer.parseInt(discIdField.getText().trim());
                GymMember gm = searchMemId(id);
                // Downcasting to PremiumMember for discount calculation
                if (gm != null && gm instanceof PremiumMember) 
                {
                    PremiumMember pm = (PremiumMember) gm;
                    pm.calculateDiscount();
                    discAmountField.setText(String.valueOf(pm.getDiscountAmount()));
                } 
                else 
                {
                    JOptionPane.showMessageDialog(frame, "Premium Member not found!");
                }
            } 
            catch (NumberFormatException ex) 
            {
                JOptionPane.showMessageDialog(frame, "Invalid ID.");
            }
        }
        // Revert regular member handler
        // Removes a regular member
        if (btn == revertRegularBtn) 
        {
            try 
            {
                int id = Integer.parseInt(revertIdField.getText().trim());
                GymMember gm = searchMemId(id);
                // Downcasting to RegularMember for reversion
                if (gm != null && gm instanceof RegularMember) 
                {
                    String reason = JOptionPane.showInputDialog(frame, "Enter removal reason:");
                    if (reason == null || reason.isEmpty()) 
                    {
                        JOptionPane.showMessageDialog(frame, "Removal reason is required.");
                        return;
                    }
                    RegularMember rm = (RegularMember) gm;
                    rm.revertRegularMember(reason);
                    JOptionPane.showMessageDialog(frame, "Regular Member reverted!");
                    
                } 
                else 
                {
                    JOptionPane.showMessageDialog(frame, "Regular Member not found!");
                }
            } 
            catch (NumberFormatException ex) 
            {
                JOptionPane.showMessageDialog(frame, "Invalid ID.");
            }
        }
        // Revert premium member handler
        // Removes a premium member
        if (btn == revertPremiumBtn) 
        {
            try 
            {
                int id = Integer.parseInt(revertIdField.getText().trim());
                GymMember gm = searchMemId(id);
                // Downcasting to PremiumMember for reversion
                if (gm != null && gm instanceof PremiumMember) 
                {
                    PremiumMember pm = (PremiumMember) gm;
                    pm.revertPremiumMember();
                    JOptionPane.showMessageDialog(frame, "Premium Member reverted!");
                    
                } 
                else 
                {
                    JOptionPane.showMessageDialog(frame, "Premium Member not found!");
                }
            } 
            catch (NumberFormatException ex) 
            {
                JOptionPane.showMessageDialog(frame, "Invalid ID.");
            }
        }
        // Pay due handler
        // Processes payments for premium members
        if (btn == payDueButton) 
    
        {
            try 
            {
                int id = Integer.parseInt(payIdField.getText().trim());
                double amt = Double.parseDouble(payAmountField.getText().trim());
                GymMember gm = searchMemId(id);
                // Downcasting to PremiumMember for payment processing
                    
                
                if (gm != null) 
                {
                    if (gm instanceof PremiumMember) 
                    {
                        PremiumMember pm = (PremiumMember) gm;
                        String result = pm.payDueAmount(amt);
                        JOptionPane.showMessageDialog(frame, "Payment processed: " + result);
                    } 
                    else 
                    {
                        JOptionPane.showMessageDialog(frame, "Pay Due Amount is applicable for Premium Members only.");
                    }
                } 
                else 
                {
                    JOptionPane.showMessageDialog(frame, "Member not found!");
                }
            } 
            catch (NumberFormatException ex) 
            {
                JOptionPane.showMessageDialog(frame, "Invalid ID or amount.");
            }
        }
        // Save to file handler
        // Exports member data to a text file
        if (btn == saveBtn) 
        {
            if (membersObj.isEmpty()) 
            {
                JOptionPane.showMessageDialog(frame, "No members to save!", "Save to File", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try 
            {
                // File handling: Using FileWriter to write member data to a file
                FileWriter writer = new FileWriter("MemberDetails.txt");
        
                // Header line, with Personal Trainer column added
                writer.write(String.format(
                    "%-5s %-15s %-15s %-15s %-25s %-20s %-10s %-10s %-10s %-15s %-12s %-20s %-12s %-15s %-15s\n",
                    "ID", "Name", "Location", "Phone", "Email",
                    "Membership Start", "Plan", "Price", "Attendance",
                    "Loyalty Points", "ActiveStatus", "Personal Trainer", "FullPayment", "DiscountAmt", "PaidAmt"
                ));
        
                // Using a for-each loop to iterate through the ArrayList of members
                for (GymMember gm : membersObj) 
                {
                    String plan;
                    double price;
                    String trainer;      // now included for both types
                    boolean fullPay;
                    double discount;
                    double totalPaid;
        
                    // Downcasting to determine member type and access specific attributes
                    if (gm instanceof RegularMember) 
                    {
                        RegularMember rm = (RegularMember) gm;
                        plan      = rm.getPlan();
                        price     = rm.getPrice();
                        trainer   = "N/A";               // no trainer for regular members
                        fullPay   = true;
                        discount  = 0.0;
                        totalPaid = price;
                    } 
                    else  // PremiumMember
                    {
                        PremiumMember pm = (PremiumMember) gm;
                        plan      = "Premium";
                        price     = pm.getPremiumCharge();
                        trainer   = pm.getPersonalTrainer();
                        fullPay   = pm.isFullPayment();
                        discount  = pm.getDiscountAmount();
                        totalPaid = fullPay 
                                    ? (price - discount) 
                                    : pm.getPaidAmount();
                                    
                    }
        
                    writer.write(String.format(
                        "%-5d %-15s %-15s %-15s %-25s %-20s %-10s %-10.2f %-10d %-15.2f %-12b %-20s %-12b %-15.2f %-15.2f\n",
                        gm.getId(),
                        gm.getName(),
                        gm.getLocation(),
                        gm.getPhone(),
                        gm.getEmail(),
                        gm.getMembershipStartDate(),
                        plan,
                        price,
                        gm.getAttendance(),
                        gm.getLoyaltyPoints(),
                        gm.getActiveStatus(),
                        trainer,
                        fullPay,
                        discount,
                        totalPaid
                    ));
                }

                writer.close();
                JOptionPane.showMessageDialog(frame, "Member details saved to file!", "Save to File", JOptionPane.INFORMATION_MESSAGE);
            } 
            catch (IOException ex) 
            {
                JOptionPane.showMessageDialog(frame, "Error saving to file!", "Save to File", JOptionPane.ERROR_MESSAGE);
            }
        }

        // Read from file handler
        // Imports member data from a text file
        if (btn == readBtn) 
        {
            try 
            {
                // File handling: Using FileReader to read member data from a file
                FileReader reader = new FileReader("MemberDetails.txt");
                String readData = "";
                int c;
                // Using a while loop to read the file character by character
                while ((c = reader.read()) != -1) 
                {
                    readData += (char) c;
                }
                reader.close();
    
                // Display in a new frame with monospaced font for alignment
                JFrame readDisplayFrame = new JFrame("Member Details from File");
                readDisplayFrame.setSize(1300, 610);
                readDisplayFrame.setLayout(null);
                readDisplayFrame.setResizable(false);
                JTextArea textArea = new JTextArea();
                textArea.setEditable(false);
                textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
                textArea.setText(readData);
    
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setBounds(10, 10, 1270, 550);
    
                readDisplayFrame.add(scrollPane);
                readDisplayFrame.setVisible(true);
            } 
            catch (IOException ex) 
            {
                JOptionPane.showMessageDialog(frame, "Error reading file!", "Read from File", JOptionPane.ERROR_MESSAGE);
            }
        } 
    }     
    // Main method
    // Entry point of the application
    public static void main(String[] args) 
    {
        new GymGUI();
    }
}