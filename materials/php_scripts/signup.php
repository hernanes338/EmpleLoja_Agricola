<?php
if(isset($_POST['name']) && isset($_POST['surname']) && isset($_POST['phone'])&& isset($_POST['email']) && isset($_POST['password'])&& isset($_POST['role'])){
    // Include the necessary files
    require_once "conn.php";
    require_once "validate.php";
    // Call validate, pass form data as parameter and store the returned value
    $name = validate($_POST['name']);
    $surname = validate($_POST['surname']);
    $phone = validate($_POST['phone']);
    $email = validate($_POST['email']);
    $password = validate($_POST['password']);
    $role = validate($_POST['role']);
    // Create the SQL query string. We'll use md5() function for data security. It calculates and returns the MD5 hash of a string
    $sql = "insert into users (name, surname, phone, email, password, role) VALUES  ('" . $name . "','" . $surname . "','" . $phone ."','" . $email ."','" . md5($password)."','" . $role ."');";
    // Execute the query. Print "success" on a successful execution, otherwise "failure".
    if(!$conn->query($sql)){
        echo "failure";
    }else{
        echo "success";   
    }
}
?>