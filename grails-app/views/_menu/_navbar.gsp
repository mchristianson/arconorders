<div id="Navbar" class="navbar navbar-fixed-top navbar-inverse">
	<div class="navbar-inner">
		<div class="container">
			<!-- .btn-navbar is used as the toggle for collapsed navbar content -->
			<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
				<span class="icon-bar"></span>
            	<span class="icon-bar"></span>
            	<span class="icon-bar"></span>
			</a>

			<a class="brand" href="${createLink(uri: '/')}">
				<img class="logo" src="${resource(dir:'images',file:'arcon_logo.png')}" alt="${meta(name:'app.name')}" />
			</a>

       		<div class="nav-collapse">
       		
       			<ul class="nav">
					<li class="dropdown">
						<a class="dropdown-toggle" data-toggle="dropdown" href="#">Browse <b class="caret"></b></a>
						<ul class="dropdown-menu">
		                    <li class="controller">
                                <g:link controller="arconOrder">Orders</g:link>
                                <g:link controller="product">Products</g:link>
                                <g:link controller="site">Sites</g:link>
                                <g:link controller="orderError">Errors</g:link>
                                <g:link controller="dubowSubmission">Submissions</g:link>
                                <g:link controller="user">Users</g:link>
                                <g:link controller="site" action="codes">Generate Codes</g:link>
						</ul>
					</li>
				</ul>

	  			<div class="pull-left">
					<%--Left-side entries--%>
	  			</div>

	  			<div class="pull-right">
                      <g:form url='[controller: "searchable", action: "index"]' id="searchableForm" name="searchableForm" method="get" class="navbar-search pull-left">
                          <g:textField name="q" value="${params.q}" size="50" class="search-query" placeholder="Search"/>
                      </g:form>
					<%--Right-side entries--%>
					<%--NOTE: the following menus are in reverse order due to "pull-right" alignment (i.e., right to left)--%>
					<g:render template="/_menu/config"/>
					<g:render template="/_menu/user"/><!-- NOTE: the renderDialog for the "Register" modal dialog MUST be placed outside the NavBar (at least for Bootstrap 2.1.1): see bottom of main.gsp -->
	  			</div>

			</div>
			
		</div>
	</div>
</div>
