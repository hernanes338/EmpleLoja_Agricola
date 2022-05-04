<?php
    if(isset($_POST['user_id'])){
    // Include the necessary files
    require_once "conn.php";
    require_once "validate.php";
    // Call validate, pass form data as parameter and store the returned value
    $user_id = validate($_POST['user_id']);
    // Create the SQL query string
    $sql = "SELECT * FROM job_offers WHERE user_id = '$user_id' AND active = 'Y';";
    // Execute the query
    $result = $conn->query($sql);
    if($result->num_rows > 0){

    while($row = mysqli_fetch_assoc($result)) {
        $jsonresult[] = $row; 
    }
    //$jsonresulttitle['users'] = $jsonresult;
    //print(json_encode($jsonresulttitle));

    print(json_encode($jsonresult));
} else {
    echo "jobs not found";
}
} else {
    echo "parameters not set";
}
?>