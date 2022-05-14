<?php
    if(isset($_POST['email'])){
    // Include the necessary files
    require_once "conn.php";
    require_once "validate.php";
    // Call validate, pass form data as parameter and store the returned value
    $email = validate($_POST['email']);
    // Create the SQL query string
    $sql = "SELECT * FROM users WHERE email = '$email';";
    // Execute the query
    $result = $conn->query($sql);
    if($result->num_rows > 0){

    while($row = mysqli_fetch_assoc($result)) {
        $jsonresult[] = $row; 
    }
    print(json_encode($jsonresult));
} else {
    echo "user not found";
}
} else {
    echo "parameters not set";
}
?>