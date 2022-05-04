<?php
    // Include the necessary files
    require_once "conn.php";
    // Create the SQL query string
    $sql = "SELECT a.*, b.PHONE FROM job_demands AS a INNER JOIN users AS b ON a.USER_ID = b.USER_ID WHERE a.ACTIVE = 'Y';";
    // Execute the query
    $result = $conn->query($sql);

    while($row = mysqli_fetch_assoc($result)) {
        $jsonresult[] = $row; 
    }
    //$jsonresulttitle["job_demands"] = $jsonresult;
    //print(json_encode($jsonresulttitle));

    print(json_encode($jsonresult));
?>