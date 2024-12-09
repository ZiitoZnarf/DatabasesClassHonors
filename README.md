# Running the application

There will be two methods, one with the command line, and the other will be with Intellij.

---

## Command Line Method

### 1. Clone the Repository

Run the following command:

```sh
git clone https://github.com/ZiitoZnarf/SER322Honors.git
```



### 2. Navigate to the `src/` Directory

After cloning, change into the `src/` directory of the project:

```sh
cd SER322Honors/src/
```



### 3. Compile the `PartPickerStart` File

To compile the `PartPickerStart.java` file, use the following command:

```sh
javac -cp .:SER322Honors/mysql-connector-j-8.4.0.jar PartPickerStart.java
```

If the command above doesn't work, replace `<path/to/mysql-connector-java-<VERSION>.jar>` with the absolute path to the `mysql-connector-java` file:

```sh
javac -cp .:<path/to/mysql-connector-java-<VERSION>.jar> PartPickerStart.java
```



### 4. Run the `PartPickerStart` File

To run `PartPickerStart`, replace the following placeholders with your specific details:

- `<path/to/mysql-connector-java-<VERSION>.jar>`: The absolute path to your MySQL connector.
- `<username>`: Your MySQL username.
- `<password>`: Your MySQL password.

Then, run the command:

```sh
java -cp .:<path/to/mysql-connector-java-<VERSION>.jar> PartPickerStart.java jdbc:mysql://localhost:3306/ <username> <password> src/pc_builder_db_init.sql
```

---


## IntelliJ IDEA Method

### 1. Clone the repository:

 Open IntelliJ IDEA, and use the following steps to clone the repository:

 - Open IntelliJ IDEA and go to `File > New > Project from Version Control`.
 - In the "URL" field, enter the URL of the repository:

   ```sh
   git clone https://github.com/ZiitoZnarf/SER322Honors.git
   ```

 - Click `Clone`.



### 2. Add MySQL connector file to the project (if not already there).

 Open IntelliJ IDEA, and use the following steps to clone the repository:

 - Go to `File > New > Project Structure`
 - Look at the left side bar of `Project Settings` and go to `Modules`
 - If you do not see the MySQL connector jar file, add it by:
   - Click the `+` and choose `JARs or Directories`
   - Go to the project's root `SER322Honors/` and add the jar file.
    


### 3. Create Configuration to run the application

- Locate the `PartPickerStart.java` file
- Go to `Run > Edit Configurations...`
  - Click the `+` and add a new `Application`
  - Name it whatever you'd like.
  - Change the Java SDK to match the project if you haven't already done so.
  - For `Main class` type `PartPickerStart`
  - Replace the following placeholders with your specific details
    - `<username>`: Your MySQL username.
    - `<password>`: Your MySQL password.
  ```sh
  jdbc:mysql://localhost:3306/
  <username>
  <password>
  src/pc_builder_db_init.sql
  ```
  - Add all these arguments to the `Program arguments`
  - Ensure the `Working directory` of the configuration is `path/to/SER322Honors`
  - Click Apply
 

 
### 4. Run your Configuration

If everything was set up correctly, it should run `PartPickerStart`. 



