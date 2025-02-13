package com.example.dynamicnavigationdrawer;

import android.os.Bundle;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.util.Pair;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ExpandableListView expandableListView; // ExpandableListView in drawer
    private CustomExpandableListAdapter listAdapter;
    List<Pair<String, Integer>> listDataHeader; // <-- Updated to Pair
    HashMap<Pair<String, Integer>, List<Pair<String, Integer>>> listDataChild; // <-- Updated to Pair
    private String userRole = "student"; //  <--  Simulated user role (can be "student" or "teacher")
    private int lastExpandedGroupPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // (1) Set the main layout (activity_main.xml)

        toolbar = findViewById(R.id.toolbar); // (2) Find the Toolbar from app_bar_main.xml
        setSupportActionBar(toolbar); // (3) Set the Toolbar as the Activity's ActionBar

        drawerLayout = findViewById(R.id.drawer_layout); // (4) Find the DrawerLayout from activity_main.xml
        expandableListView = findViewById(R.id.expandableListView); // (5) Find the ExpandableListView from activity_main_drawer.xml

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle( // (6) Set up the Drawer Toggle (hamburger icon)
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle); // (7) Add the toggle as a listener to the DrawerLayout
        toggle.syncState(); // (8) Sync the toggle state with the DrawerLayout

        // --- Dynamic Header Setup ---
        TextView headerTitleTextView = findViewById(R.id.header_title_textview); // Find header TextView by ID
        TextView headerSubtitleTextView = findViewById(R.id.header_subtitle_textview); // Find subtitle TextView by ID

        if (userRole.equals("student")) {
            headerTitleTextView.setText("Student Dashboard"); // Set student header title
            headerSubtitleTextView.setText("Welcome, Student!"); // Set student header subtitle
        } else if (userRole.equals("teacher")) {
            headerTitleTextView.setText("Teacher Portal"); // Set teacher header title
            headerSubtitleTextView.setText("Hello, Teacher!"); // Set teacher header subtitle
        }

        prepareListData(); // (9) Prepare the menu data (groups and children)

        listAdapter = new CustomExpandableListAdapter(this, listDataHeader, listDataChild); // (10) Create the adapter
        expandableListView.setAdapter(listAdapter); // (11) Set the adapter on the ExpandableListView

        // ... (12) Set up listeners for group and child clicks ...
        expandableListView.setOnGroupClickListener((parent, v, groupPosition, id) -> {
            Toast.makeText(getApplicationContext(),
                    "Group Clicked: " + listDataHeader.get(groupPosition),
                    Toast.LENGTH_SHORT).show();
            return false; // Allow group expansion
        });
        //--- Child Click Listener ---
        expandableListView.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
            Toast.makeText(
                            getApplicationContext(),
                            "Child Clicked: " + listDataHeader.get(groupPosition)
                                    + " -> "
                                    + listDataChild.get(
                                    listDataHeader.get(groupPosition)).get(
                                    childPosition), Toast.LENGTH_SHORT)
                    .show();
            // Handle child item click action here
            drawerLayout.closeDrawer(GravityCompat.START); // Close drawer after item selection
            return true; // Indicate click was handled
        });

        //--- Group Expand Listener (Modified for single expand) ---
        expandableListView.setOnGroupExpandListener(groupPosition -> {
            if (lastExpandedGroupPosition != -1 && groupPosition != lastExpandedGroupPosition) {
                // Collapse the previously expanded group if it's not the current group
                expandableListView.collapseGroup(lastExpandedGroupPosition);
            }
            lastExpandedGroupPosition = groupPosition; // Set current group as last expanded
            Toast.makeText(getApplicationContext(),
                    listDataHeader.get(groupPosition) + " Expanded",
                    Toast.LENGTH_SHORT).show();
        });

        //--- Group Collapse Listener (Modified to reset lastExpandedGroupPosition) ---
        expandableListView.setOnGroupCollapseListener(groupPosition -> {
            if (groupPosition == lastExpandedGroupPosition) {
                lastExpandedGroupPosition = -1; // Reset when the last expanded group is collapsed
            }
            Toast.makeText(getApplicationContext(),
                    listDataHeader.get(groupPosition) + " Collapsed",
                    Toast.LENGTH_SHORT).show();
        });
    }
    // ... (13) onBackPressed() - Handle back button to close drawer ...
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    // (14) Method to create the menu data
    //--- Prepare your data (Group and Child items) -  REPLACE WITH YOUR DATA ---
    private void prepareListData() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        String userRole = "student"; // Example user role - replace with your actual role logic

        // --- Common Menu Items (Always Present - Ordered) ---
        Pair<String, Integer> dashboardParent = new Pair<>("Dashboard", R.drawable.ic_menu_item3); // Replace with your icon
        Pair<String, Integer> profileParent = new Pair<>("Profile", R.drawable.ic_menu_item5); // Replace with your icon
        Pair<String, Integer> contactParent = new Pair<>("Contact", R.drawable.ic_menu_item4); // Replace with your icon
        Pair<String, Integer> signoutParent = new Pair<>("Sign Out", R.drawable.ic_menu_item1); // Replace with your icon


        listDataHeader.add(dashboardParent);    // 1. Dashboard
        // Role-based menus will be inserted here (conditionally)
        listDataHeader.add(profileParent);      // 2. Profile (Common)
        listDataHeader.add(contactParent);      // 3. Contact (Common)
        listDataHeader.add(signoutParent);      // 4. Sign Out (Common)

        listDataChild.put(dashboardParent, new ArrayList<>()); // Dashboard has no children
        listDataChild.put(profileParent, new ArrayList<>());   // Profile has no children
        listDataChild.put(contactParent, new ArrayList<>());   // Contact has no children
        listDataChild.put(signoutParent, new ArrayList<>());    // Sign Out has no children

        // --- Role-Specific Menu Items (Student Role) - Inserted in between common menus ---
        if (userRole.equals("student")) {
            // Parent menu items for students with Icons
            Pair<String, Integer> attendanceParent = new Pair<>("Attendance", R.drawable.ic_menu_parent); // Replace icon
            Pair<String, Integer> resultsParent = new Pair<>("Results", R.drawable.ic_menu_communicate); // Replace icon
            Pair<String, Integer> smsHistoryParent = new Pair<>("SMS History", R.drawable.ic_menu_gallery); // Replace icon
            Pair<String, Integer> diaryParent = new Pair<>("Diary", R.drawable.ic_menu_share); // Replace icon
            Pair<String, Integer> feePaymentParent = new Pair<>("Fee Payment", R.drawable.ic_menu_share); // Replace icon

            // Insert role-based menus after "Dashboard" (at index 1)
            listDataHeader.add(1, attendanceParent);
            listDataHeader.add(2, resultsParent); // Indices shift after insertion
            listDataHeader.add(3, smsHistoryParent);
            listDataHeader.add(4, diaryParent);
            listDataHeader.add(5, feePaymentParent);

            // Child menu items for students with Icons
            List<Pair<String, Integer>> resultsChildren = new ArrayList<>();
            resultsChildren.add(new Pair<>("Written Tests", R.drawable.ic_menu_item1)); // Replace icon
            resultsChildren.add(new Pair<>("Terminal Exam", R.drawable.ic_menu_item2)); // Replace icon

            listDataChild.put(attendanceParent, new ArrayList<>());
            listDataChild.put(smsHistoryParent, new ArrayList<>());
            listDataChild.put(diaryParent, new ArrayList<>());
            listDataChild.put(feePaymentParent, new ArrayList<>());
            listDataChild.put(resultsParent, resultsChildren);
//          listDataChild.put(feeHisotryParent, helpChildren);

        } else if (userRole.equals("teacher")) {
            // Parent menus for teachers with Icons
            Pair<String, Integer> classesParent = new Pair<>("Classes", R.drawable.ic_menu_item5); // Replace icon
            Pair<String, Integer> createAssignmentParent = new Pair<>("Create Assignment", R.drawable.ic_menu_item4); // Replace icon
            Pair<String, Integer> viewGradesParent = new Pair<>("View Grades", R.drawable.ic_menu_item3); // Replace icon


            // Insert role-based menus after "Dashboard" (at index 1)
            listDataHeader.add(1, classesParent);
            listDataHeader.add(2, createAssignmentParent);
            listDataHeader.add(3, viewGradesParent);


            // Child menus for teachers with Icons
            List<Pair<String, Integer>> classesChildren = new ArrayList<>();
            classesChildren.add(new Pair<>("Current Classes", R.drawable.ic_menu_item3)); // Replace icon
            classesChildren.add(new Pair<>("Past Classes", R.drawable.ic_menu_item2)); // Replace icon


            List<Pair<String, Integer>> viewGradesChildren = new ArrayList<>();
            viewGradesChildren.add(new Pair<>("Class Grades", R.drawable.ic_menu_item2)); // Replace icon
            viewGradesChildren.add(new Pair<>("All Students Grades", R.drawable.ic_menu_item4)); // Replace icon


            listDataChild.put(classesParent, classesChildren);
            listDataChild.put(viewGradesParent, viewGradesChildren);
            listDataChild.put(createAssignmentParent, new ArrayList<>()); // No children for "Create Assignment"

        }
    }
}