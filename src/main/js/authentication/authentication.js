import React from 'React';

class Authentication extends React.Component {
	constructor(props) {
		super(props);
		
		this.state = {
			is_authenticating: true,
			authenticated_user: null,
		};
	}
	
	componentDidMount() {
		fetch("/user/login", {
			method: "POST",
			headers: {
				'Content-Type': 'application/x-www-form-urlencoded'
			},
			body: "username=me&password=ME"
		})
		.then(data => {
			
			fetch("/api/user/current")
			.then(response => {
				if (response.status === 401) {
					return null;
				}
				
				return response.json();
			})
			.then(data => {
				this.setState({
					is_authenticating: false,
					authenticated_user: data
				})
			});
			
		})
	}
	
    render() {
		let bodyContent = null;
		
		if (this.state.is_authenticating) {
			bodyContent = (
				<div className="Authentication">
					<p>Checking authentication</p>
				</div>
			);
		} else {
			if (this.state.authenticated_user === null) {
				bodyContent = (
					<div className="Authentication">
						<a href="/user/login">Login</a>						
						<a href="/user/create">Create account</a>
					</div>
				);
			} else {
				bodyContent = (
					<div className="Authentication">
						<p>Logged in as { this.state.authenticated_user.username }</p>
						<a href="/user/logout">Logout</a>
					</div>
				);
			}
		}
		
        return bodyContent;
    }
}

export default Authentication;