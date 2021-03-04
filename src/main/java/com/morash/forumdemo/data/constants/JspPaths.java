/**
 * 
 */
package com.morash.forumdemo.data.constants;

/**
 * @author Michael
 *
 */
public class JspPaths {
	public static final String HOME_PAGE = "home";
	
	public static final String BOARD_ROOT = "/board";
	public static final String BOARD_INDEX = BOARD_ROOT + "/index";
	public static final String BOARD_CREATE = BOARD_ROOT + "/create";
	public static final String BOARD_VIEW = BOARD_ROOT + "/view";
	
	public static final String POST_ROOT = "/post";
	public static final String POST_CREATE = POST_ROOT + "/create";
	public static final String POST_VIEW = POST_ROOT + "/view";
	
	public static final String COMMENT_ROOT = "/comment";
	public static final String COMMENT_CREATE_FOR_POST = COMMENT_ROOT + "/create/respondToPost";
	public static final String COMMENT_CREATE_FOR_COMMENT = COMMENT_ROOT + "/create/respondToComment";
	public static final String COMMENT_VIEW = COMMENT_ROOT + "/view";
	
	public static final String USER_ROOT = "/user";
	public static final String USER_CREATE = USER_ROOT + "/create";
	public static final String USER_LOGIN = USER_ROOT + "/login";
	public static final String USER_VIEW = USER_ROOT + "/view";
}
