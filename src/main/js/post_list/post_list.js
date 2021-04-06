import React from 'react';

import { NavLink } from 'react-router-dom';

class PostList extends React.Component {
	constructor(props) {
		super(props);
		
		this.state = {
			postList: null
		}
		
		if (this.props.boardName === undefined) {
			console.log("PostList instantiated without boardName");
		}
	}
	
	componentDidMount() {
		fetch("/api/board/" + this.props.boardName + "/post/")
		.then(response => response.json())
		.then(data => {
			this.setState({
				postList: data
			})
		})
	}
	
	render() {
		if (this.state.postList === null) {
			return (
				<div className="PostList">
					<p>Loading post list</p>
				</div>
			)
		}
		
		let postList = [];
		
		this.state.postList.forEach(post => {
			postList.push(<PostListItem post={post}/>)
		})
		
		return (
			<div className="PostList">
				{postList}
			</div>
		)
	}
}

class PostListItem extends React.Component {
	constructor(props) {
		super(props);
		
		if (this.props.post === undefined) {
			console.log("PostListItem instantiated without post");
		}
	}
	
	render() {
		return (
			<div className="PostListItem">
				<h3><NavLink to={ "/board/" + this.props.post.board.name + "/post/" + this.props.post.id }>{ this.props.post.title }</NavLink></h3>
				<p>{ this.props.post.contents }</p>
			</div>
		)
	}
}

export default PostList;