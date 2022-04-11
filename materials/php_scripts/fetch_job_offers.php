<?php
    // Include the necessary files
    require_once "conn.php";
    // Create the SQL query string
    $sql = "SELECT * FROM users;";
    // Execute the query
    $result = $conn->query($sql);

    while($row = mysqli_fetch_assoc($result)) {
        $jsonresult[] = $row; 
    }
    print(json_encode($jsonresult));
?>