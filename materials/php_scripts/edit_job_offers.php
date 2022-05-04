<?php
if(isset($_POST['job_offer_id']) && isset($_POST['title']) && isset($_POST['description']) && isset($_POST['start_date']) && isset($_POST['end_date']) && isset($_POST['salary_hour']) && isset($_POST['active'])){
    // Include the necessary files
    require_once "conn.php";
    require_once "validate.php";
    // Call validate, pass form data as parameter and store the returned value
    $job_offer_id = validate($_POST['job_offer_id']);
    $title = validate($_POST['title']);
    $description = validate($_POST['description']);
    $start_date = validate($_POST['start_date']);
    $end_date = validate($_POST['end_date']);
    $salary_hour = validate($_POST['salary_hour']);
    $active = validate($_POST['active']);
    // Create the SQL query string. We'll use md5() function for data security. It calculates and returns the MD5 hash of a string
    $sql = "UPDATE job_offers SET title = '" .$title. "', description = '" .$description. "', start_date = '" .$start_date. "',end_date = '" .$end_date. "', salary_hour = " .$salary_hour. ", active = '" .$active. "' 
    WHERE id = " .$job_offer_id. ";";
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