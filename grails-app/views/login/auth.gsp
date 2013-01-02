<html>
<head>
	<meta name='layout' content='kickstart'/>
	<title><g:message code="springSecurity.login.title"/></title>
</head>

<body>
    <div class="container">
        <div class="row">
            <div class="span6" style="margin-left:300px;">
                <form action='${postUrl}' method='POST' id='loginForm' class='cssform' autocomplete='off'>
                    <fieldset>
                        <div class="control-group">
                            <label class="control-label" for="id_username">Username</label>
                            <div class="controls">
                                <input name="j_username" maxlength="100" placeholder="Enter your username..." type="text" class="input-large" id="id_username" />
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label" for="id_password">Password</label>
                            <div class="controls">
                                <input name="j_password" maxlength="100" placeholder="Enter your password..." type="password" class="input-large" id="id_password" />
                            </div>
                        </div>
                        <input type='submit' id="submit" value='${message(code: "springSecurity.login.button")}' class="btn"/>
                    </fieldset>
                </form>
            </div>
        </div>
    </div>


</body>
</html>
