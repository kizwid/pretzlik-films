<?php

$name = $_POST['name'];
$from = $_POST['email'];
$subject = $_POST['subject'];
$message = $_POST['message'];

$to = 'your@email-address.com'; // your email address
$valid_referers = array(
		'http://your-website.com/',	// the URL to your page without www prefix
		'http://www.your-website.com/'	// the URL to your page with www prefix
);

$headers = 'From:' .$name. '<' .$from. ">\r\n" .
			'Reply-To: '.$from."\r\n" .
			'X-Mailer: PHP/' . phpversion();

if ($_SERVER['REQUEST_METHOD'] == 'POST' && in_array($_SERVER['HTTP_REFERER'], $valid_referers)) {

	if (!mail($to, $subject, $message, $headers)) {
		exit;
	};
	
	die('ok');

};

?>