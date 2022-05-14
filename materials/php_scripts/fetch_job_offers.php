<?php
    // Include the necessary files
    require_once "conn.php";
    // Create the SQL query string
    $sql = "SELECT a.*, b.PHONE FROM job_offers AS a INNER JOIN users AS b ON a.USER_ID = b.USER_ID WHERE a.ACTIVE = 'Y';";
    // Execute the query
    $result = $conn->query($sql);
    if($result->num_rows > 0){

    while($row = mysqli_fetch_assoc($result)) {
        $jsonresult[] = $row; 
    }
    
    print(json_encode($jsonresult));
} else {
    echo "jobs not found";
}
?>