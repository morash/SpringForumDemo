import React from 'react';

import { NavLink } from 'react-router-dom';

class CommentTree extends React.Component {
	constructor(props) {
		super(props);
		
		this.state = {
			comments: null
		}
		
		this.currentDepth = this.props.currentDepth === undefined ? 1 : this.props.currentDepth;
	}
	
	componentDidMount() {
		if (this.props.forPost !== undefined) {
			fetch("/api/board/~/post/" + this.props.forPost + "/comment/")
			.then(response => response.json())
			.then(data => {
				this.setState({
					comments: data
				})
			})
		} else if (this.props.forComment !== undefined) {
			fetch("/api/board/~/post/~/comment/" + this.props.forComment + "/children")
			.then(response => response.json())
			.then(data => {
				this.setState({
					comments: data
				})
			})
		}
	}
	
	render() {
		if (this.state.comments === null) {
			return (
				<div className={"CommentTree CommentTreeDepth"+this.currentDepth}>
					<p>Loading comment</p>
				</div>
			)
		}
		
		let commentList = [];
		
		this.state.comments.forEach(comment => {
			commentList.push(<CommentTreeItem comment={comment} currentDepth={this.currentDepth} maxDepth={this.props.maxDepth}/>)
		})
		
		return (
			<div className={"CommentTree CommentTreeDepth"+this.currentDepth}>
				{commentList}
			</div>
		)
	}
}

class CommentTreeItem extends React.Component {
	constructor(props) {
		super(props);
	}
	
	render() {
		let subTree = <NavLink to={"/board/" + this.props.comment.respondingToPost.board.name + "/post/" + this.props.comment.respondingToPost.id + "/comment/" + this.props.comment.id}>See more comments</NavLink>;
		
		if (this.props.currentDepth < this.props.maxDepth) {
			subTree = <CommentTree forComment={this.props.comment.id} currentDepth={this.props.currentDepth + 1} maxDepth={this.props.maxDepth}/>
		}
		
		return (
			<div className="CommentTreeItem">
				<p>{this.props.comment.contents}</p>
				{ this.props.comment.commentCount > 0 ? subTree : "" }
			</div>
		)
	}
}

export default CommentTree;