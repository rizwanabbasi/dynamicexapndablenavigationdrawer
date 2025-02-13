# dynamicexapndablenavigationdrawer
Dynamic Expandable Navigation Drawer using Android Java

  ## Description

This Android project provides a dynamic and customizable Expandable Navigation Drawer.  The Navigation Drawer menu structure and header content adapt based on user roles (e.g., student, teacher, admin) or any other dynamic condition you define.  It features:

Dynamic Menu Structure: Menus are generated programmatically, allowing for role-based or data-driven menu items.
Expandable Menu Groups: Organize menu items into expandable parent groups with child items.
Dynamic Icons: Specify different icons for each parent and child menu item.
Common Menu Items: Include always-present menu items (like Dashboard, Profile, Contact, Sign Out) irrespective of user roles.
Customizable Appearance: Easily customize colors, styles, and layouts.
Clean and Organized Code: Well-structured code for easy integration and modification.
This project is designed to be easily integrated into existing Android applications requiring a flexible and dynamic navigation system.

Features
Role-Based Menus: Display different menu options based on user roles or application state.
Expandable List: Utilizes Android's ExpandableListView for a hierarchical menu structure.
Dynamic Icons: Supports setting unique icons for each menu item (parent and child).
Common Menu Items: Allows for persistent menu items across all roles/conditions.
Easy Customization: Provides clear instructions and modular code for customization.
Ready-to-Integrate Files: Includes all necessary layout, Java adapter, and resource files.
Detailed Manual: Comprehensive manual in text format (copy-pasteable to PDF or MS Word) for integration and customization guidance.
Files Included
To integrate this dynamic Navigation Drawer, you need to copy the following files into your Android project:

Layout Files (res/layout folder):

activity_main.xml: Main activity layout with DrawerLayout structure.
app_bar_main.xml: Layout for the app bar (Toolbar) and the main content area.
activity_main_drawer.xml: Layout for the Navigation Drawer's content, containing the ExpandableListView.
nav_header_main.xml: Layout for the Navigation Drawer header (dynamic header content).
list_group.xml: Layout for parent menu items in the ExpandableListView (groups).
list_item.xml: Layout for child menu items in the ExpandableListView (items within groups).
Java Adapter File:

CustomExpandableListAdapter.java: Custom adapter class for managing the ExpandableListView and displaying menu items.
Drawable Resource Files (res/drawable folder):

side_nav_bar.xml (Optional): Background for the Navigation Drawer header.
ic_menu_item_default_icon.xml (Optional): Default icon for parent menu items (can be removed if you use dynamic icons for all).
ic_child_menu_item_default_icon.xml (Optional): Default icon for child menu items (can be removed if you use dynamic icons for all).
ic_expand_more.xml: Icon for the "expand" indicator (down arrow).
ic_expand_less.xml: Icon for the "collapse" indicator (up arrow).
(Your Menu Icons): Place all your menu icons (e.g., .png, .xml vector drawables) in this folder.
Menu Resource File (res/menu folder):

activity_main_drawer.xml (Potentially present): Menu resource file (may be created by default in Navigation Drawer Activities, but menu structure is primarily defined in Java code).
Values Resources (res/values folder):

colors.xml: Color definitions for the Navigation Drawer and app theme.
dimens.xml (Optional): Dimension values, especially for nav_header_main.xml.
strings.xml: String resources for header text, accessibility, etc.
themes/themes.xml (Optional): Custom styles for Toolbar/AppBar.
MainActivity.java (or your Activity):

You will integrate code snippets into your MainActivity.java (or the Activity where you want to use the drawer) to handle drawer setup, dynamic menu population, and event handling.
Integration Steps
Follow these steps to integrate the Dynamic Expandable Navigation Drawer into your Android project:

Create Folders: In your project's res directory, create layout, drawable, menu, and values folders if they don't exist.
Copy Layout Files: Copy all XML layout files from the layout folder to your project's res/layout folder.
Copy Java Adapter: Copy CustomExpandableListAdapter.java to your project's Java source directory, ensuring the correct package declaration.
Copy Drawables: Copy all drawable XML and image files from the drawable folder to your project's res/drawable folder. Make sure to add all your menu icons to this folder.
Copy Menu XML: Copy activity_main_drawer.xml (if provided) to your project's res/menu folder.
Copy Values Resources: Copy the contents of colors.xml, dimens.xml, and strings.xml to your project's res/values files, merging carefully to avoid overwriting existing resources.
Modify MainActivity.java:
Add import android.util.Pair; at the top of your MainActivity.java file.
Set setContentView() in onCreate() to your main layout file (e.g., R.layout.activity_main).
Declare necessary member variables in your MainActivity class (see manual for details).
Integrate the code from the example onCreate() method into your MainActivity's onCreate(), adapting findViewById() calls.
Add the prepareListData() method to your MainActivity to define your dynamic menu structure.
Add the onBackPressed() method for drawer behavior.
Ensure all necessary import statements are present, including androidx.appcompat.app.ActionBarDrawerToggle; and android.util.Pair;.
Update IDs: If you modify layout file names or IDs, update findViewById() calls in MainActivity.java and CustomExpandableListAdapter.java.
Dependencies: Check your build.gradle (Module: app) file for necessary dependencies (e.g., Material Design components).
Clean and Rebuild: Clean and rebuild your project (Build > Clean Project, then Build > Rebuild Project).
Dynamic Menu Customization (prepareListData() Method)
The prepareListData() method in MainActivity.java is where you define your dynamic menu structure.

listDataHeader: A List<Pair<String, Integer>> holding parent menu items (groups). Each Pair contains the title (String) and icon resource ID (Integer).
listDataChild: A HashMap<Pair<String, Integer>, List<Pair<String, Integer>>> holding child menu items. Keys are parent Pair objects, and values are List<Pair<String, Integer>> of child Pair objects.
Example prepareListData() Structure:

Java

private void prepareListData() {
    listDataHeader = new ArrayList<>();
    listDataChild = new HashMap<>();

    String userRole = "student"; // Replace with your actual user role logic

    // --- Common Menu Items ---
    Pair<String, Integer> dashboardParent = new Pair<>("Dashboard", R.drawable.ic_menu_dashboard);
    Pair<String, Integer> contactParent = new Pair<>("Contact", R.drawable.ic_menu_contact);
    Pair<String, Integer> logoutParent = new Pair<>("Sign Out", R.drawable.ic_menu_logout);

    listDataHeader.add(dashboardParent);
    listDataHeader.add(contactParent);
    listDataHeader.add(logoutParent);

    listDataChild.put(dashboardParent, new ArrayList<>());
    listDataChild.put(contactParent, new ArrayList<>());
    listDataChild.put(logoutParent, new ArrayList<>());

    // --- Role-Specific Menus (Student) ---
    if (userRole.equals("student")) {
        Pair<String, Integer> coursesParent = new Pair<>("Courses", R.drawable.ic_menu_courses);
        // ... add student-specific menus and children ...
        listDataHeader.add(coursesParent);
        // ... populate listDataChild for student menus ...
    }
    // --- Role-Specific Menus (Teacher) ---
    else if (userRole.equals("teacher")) {
        // ... add teacher-specific menus and children ...
    }
}
Customizing Menu Items:

Add Parent Menu: Create a Pair<String, Integer> for the parent (title and icon resource ID) and add it to listDataHeader.
Add Child Menus: Create a List<Pair<String, Integer>> for child items (each child is also a Pair) and put it in listDataChild with the parent Pair as the key.
Update Menu Items: Modify the Pair objects in listDataHeader and listDataChild as needed.
Remove Menu Items: Remove Pair objects from listDataHeader and listDataChild.
Conditional Menus: Use if/else if/else or switch statements based on user roles or other conditions to define different menu structures.
Dynamic Icons
This Navigation Drawer supports dynamic icons for both parent and child menu items.

Pair<String, Integer>: Menu data is stored using android.util.Pair objects. The first element is the menu item title (String), and the second element is the icon's drawable resource ID (Integer).
prepareListData(): In your prepareListData() method, create Pair objects for each menu item, specifying the appropriate icon resource ID (e.g., R.drawable.ic_menu_courses).
CustomExpandableListAdapter: The CustomExpandableListAdapter is updated to retrieve the icon resource ID from the Pair objects in getGroupView() and getChildView() and set the icon dynamically using imageView.setImageResource(iconResourceId).
Customization
Beyond dynamic menus and icons, you can further customize:

Colors: Modify colors in res/values/colors.xml (e.g., colorPrimary, navigationDrawerBackground, navigationDrawerTextColorPrimary).
Dimensions: Adjust dimensions in res/values/dimens.xml (if used in layouts like nav_header_main.xml).
Styles: Customize styles for the Toolbar/AppBar in res/values/themes/themes.xml.
Layouts: Modify the layout files (activity_main_drawer.xml, nav_header_main.xml, list_group.xml, list_item.xml) to change the visual appearance of the drawer and menu items.
Usage
After integration, the Navigation Drawer will be accessible from your main activity.  The menu items will be dynamically populated based on the logic in your prepareListData() method.  Users can expand parent menu items to view child items and navigate through your application.

License
This project is licensed under the MIT License.

Author
Rizwan Abbasi
