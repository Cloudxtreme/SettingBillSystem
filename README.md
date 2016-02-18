# SettingBillSystem

## Intro
Setting Bill System for Electric Industry. A Graduation Design Work with Java Web. Used EJB + JSP + Servlet to implement MVC. It's an improvement design for the original system.

![Screenshot](https://github.com/tonghuashuo/SettingBillSystem/blob/master/screenshot/02-select.jpg)

For demostrtion's convenience. All css, js files are not compressed so you can read the source code. (I AM AWARED that compression is necessary in product environment.) Some JavaScript code snippets remained in JSP file because they are too small. Not necessary for another HTTP request.

The whole project is based on Ajax and backend API, making it a SPA (Single Page Application). Cut off a lot of network traffic, and speed up page response.

There're some HTML5 features applied to this project. Such as File API, Drag & Drop. You can experience it by adding a new setting bill. Choose "import from file" and drag `project_folder/config/import.csv` into the browser.

![Screenshot](https://github.com/tonghuashuo/SettingBillSystem/blob/master/screenshot/04-import-from-file.jpg)

The project is born with responsive design. It works perfectly on PC and tablet. Phone is not recommended due to the complexity of data on such a small screen, though it still works, just not as perfect as PC and tablet.

![Screenshot](https://github.com/tonghuashuo/SettingBillSystem/blob/master/screenshot/05-mobile.jpg)

## Setup
1. Clone this repository to your local machine.
2. Import the project into Eclipse/MyEclipse.
3. Create a MySQL database named "SettingBill". Use `project_folder/config/settingbill.sql` to restore all the tables and datas.
4. Edit `project_folder/WebRoot/db.json`. Replace the MySQL connection info with your own. (otherwise it won't connect to MySQL)
5. Deploy the project to Tomcat.
6. After deployment, check `/your_tomcat_location/webapps/SettingBillSystem/WEB-INF/lib` to see whether the following files are in position. if not, copy them from `project_folder/lib`.
    - mysql-connector-java-bin.jar 
    - fastjson.jar

# Demo
You can login with username/password: 1/111111.
