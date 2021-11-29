package com.fastcampus.controller;

import javax.servlet.http.HttpSession;

import com.fastcampus.blog.BlogService;
import com.fastcampus.blog.BlogVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fastcampus.user.UserService;
import com.fastcampus.user.UserVO;

@Controller
public class UserController {
	
	final UserService userService;
	final BlogService blogService;

	public UserController(UserService userService, BlogService blogService) {
		this.userService = userService;
		this.blogService = blogService;
	}

	// 로그인 버튼 클릭
	@RequestMapping("/loginView.do")
	public String loginView() {
		return "bloglogin";
	}
	// bloglogin.jsp 에서 수행
	@RequestMapping("/login.do")
	public String login(UserVO vo, HttpSession session) {
		UserVO user = userService.getUser(vo);
		if (user != null) {
			// 로그인 성공한 경우 세션에 사용자 정보 저장
			session.setAttribute("user", user);
			// To Do: 로그인 성공한 사용자가 블로그를 소유한 사용자인지 조회하여, 세션에 등록한다.
			BlogVO blogVO = new BlogVO();
			blogVO.setBlogId(user.getUserId());
			BlogVO userBlog = blogService.getBlog(blogVO);
			if (userBlog != null) {
				session.setAttribute("user_blog", userBlog);
			}
		}
		// index 페이지로 이동
		return "redirect:/";
	}
	// 로그아웃
	@RequestMapping("/user/logout")
	public String logout(HttpSession session){
		session.invalidate();
		return "redirect:/";
	}

//	@RequestMapping("/insertUser.do")
//	public String loginView(UserVO vo) {
//		userService.insertUser(vo);
//		return "/";
//	}
}
