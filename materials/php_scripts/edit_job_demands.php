<?php
if(isset($_POST['job_demand_id']) && isset($_POST['title']) && isset($_POST['description']) && isset($_POST['available_from'])&& isset($_POST['active'])){
    // Include the necessary files
    require_once "conn.php";
    require_once "validate.php";
    // Call validate, pass form data as parameter and store the returned value
    $job_demand_id = validate($_POST['job_demand_id']);
    $title = validate($_POST['title']);
    $description = validate($_POST['description']);
    $available_from = validate($_POST['available_from']);
    $active = validate($_POST['active']);
    // Create the SQL query string. We'll use md5() function for data security. It calculates and returns the MD5 hash of a string
    $sql = "UPDATE job_demands SET title = '" .$title. "', description = '" .$description. "', available_from = '" .$available_from. "', active = '" .$active. "' WHERE id = " .$job_demand_id. ";";
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