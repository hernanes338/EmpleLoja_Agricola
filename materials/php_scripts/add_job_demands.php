<?php
if(isset($_POST['title']) && isset($_POST['description']) && isset($_POST['user_id'])&& isset($_POST['available_from'])){
    // Include the necessary files
    require_once "conn.php";
    require_once "validate.php";
    // Call validate, pass form data as parameter and store the returned value
    $title = validate($_POST['title']);
    $description = validate($_POST['description']);
    $user_id = validate($_POST['user_id']);
    $available_from = validate($_POST['available_from']);
    // Create the SQL query string. We'll use md5() function for data security. It calculates and returns the MD5 hash of a string
    $sql = "insert into job_demands (title, description, user_id, available_from) VALUES  
    ('" . $title . "','" . $description . "','" . $user_id ."','" . $available_from ."');";
    // Execute the query. Print "success" on a successful execution, otherwise "failure".
    if(!$conn->query($sql)){
        echo "failure";
    }else{
        echo "success";   
    }
} else {
    echo "parameters not set";
}
?>