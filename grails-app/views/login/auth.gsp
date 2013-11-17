<html>
<head>
	<meta name='layout' content='kickstart'/>
	<title><g:message code="springSecurity.login.title"/></title>
</head>

<body>
    <div class="container">
        <div class="row">
            <div class="span3" style="margin-left:40%;">
                <h2 class="form-signin-heading">Please sign in</h2>
                <form action='${postUrl}' method='POST' id='loginForm' class='cssform' autocomplete='off'>
                    <input name="j_username" maxlength="100" placeholder="Username" type="text" class="input-large input-block-level" id="id_username" />
                    <input name="j_password" maxlength="100" placeholder="Password" type="password" class="input-large input-block-level" id="id_password" />
                    <input type='submit' id="submit" value='${message(code: "springSecurity.login.button")}' class="btn btn-large btn-primary"/>
                </form>
            </div>
        </div>
    </div>


</body>
</html>
