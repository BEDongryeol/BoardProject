package com.fastcampus.controller;

import com.fastcampus.blog.BlogService;
import com.fastcampus.blog.BlogVO;
import com.fastcampus.category.CategoryService;
import com.fastcampus.category.CategoryVO;
import com.fastcampus.post.PostService;
import com.fastcampus.post.PostVO;
import com.fastcampus.user.UserVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BlogController {

	final BlogService blogService;
	final CategoryService categoryService;
	final PostService postService;

	public BlogController(BlogService blogService, CategoryService categoryService, PostService postService) {
		this.blogService = blogService;
		this.categoryService = categoryService;
		this.postService = postService;
	}

	@RequestMapping("/")
	public String main(){
		return "forward:/index.jsp";
	}

	@RequestMapping("/blog")
	public String index(BlogVO vo, Model model) {
		if (vo.getSearchKeyword() == null && vo.getSearchCondition()==null) {
			return "redirect:/";
		}
		model.addAttribute("blogList", blogService.getBlogList(vo));
		model.addAttribute("search", vo);
		return "forward:/index.jsp";
	}

	@RequestMapping("/blog/create_view")
	public String blogCreateView(){
		return "blogcreate";
	}
	// 개인 블로그 생성
	@RequestMapping("/blog/create")
	public String blogCreate(BlogVO blogVO, HttpSession session){
		if (blogVO.getTitle() != null) {
			UserVO user = (UserVO) session.getAttribute("user");

			blogVO.setBlogId(user.getUserId());
			blogVO.setUserName(user.getUserName());

			session.setAttribute("user_blog", blogVO);
			blogService.registerBlog(blogVO);
			CategoryVO categoryVO = new CategoryVO();
			categoryVO.setBlogId(blogVO.getBlogId());
			categoryService.addDefaultCategory(categoryVO);
		}
		return "forward:/blog";
	}

	@RequestMapping("/blog/user")
	public String getBlog(BlogVO vo, HttpSession session, Model model){
		session.setAttribute("blog", blogService.getBlog(vo));

		CategoryVO categoryVO = new CategoryVO();
		categoryVO.setBlogId(vo.getBlogId());

		// blogId가 일치하는 카테고리들 가져오기
		List<CategoryVO> categoryVOList = new ArrayList<>();
		if (categoryService.getCategory(categoryVO) != null) {
			categoryVOList.addAll(categoryService.getCategory(categoryVO));
		}
		model.addAttribute("categories", categoryVOList);

		// 세션을 통해 카테고리별 분류인지 아닌지 확인
		if (session.getAttribute("byCategory") == null){
			List<PostVO> postVOList = new ArrayList<>();
			List<PostVO> postOfBlog = new ArrayList<>();
			for (CategoryVO category : categoryVOList) {
				PostVO post = new PostVO();
				post.setCategoryId(category.getCategoryId());
				if (!postVOList.contains(post)) {
					postVOList.add(post);
					if (postService.getPosts(post) != null) {
						postOfBlog.addAll(postService.getPosts(post));
					}
				}
			}
			model.addAttribute("posts", postOfBlog);
		} else {
			PostVO postVO = (PostVO) session.getAttribute("byCategory");
			List<PostVO> postList = postService.getPosts(postVO);
			model.addAttribute("posts", postList);
			session.setAttribute("byCategory", null);
		}
		return "blogmain";
	}

	@RequestMapping("/blog/delete")
	public String blogDelete(BlogVO vo, HttpSession session){
		blogService.deleteBlog(vo);
		session.setAttribute("user_blog", null);
		return "forward:/blog";
	}

	@RequestMapping("/blog/updateView")
	public String blogUpdateView(){
		return "blogmanagement";
	}

	@RequestMapping("/blog/update")
	public String blogUpdate(BlogVO vo, HttpSession session){
		blogService.updateBlog(vo);
		BlogVO blogVO = blogService.getBlog(vo);
		session.setAttribute("blog", blogVO);
		return "forward:/blog/setting";
	}

	@RequestMapping("/blog/basicsetting")
	public String basicSettingView(){

		return "basicsetting";
	}

	@RequestMapping("/blog/setting")
	public String basicSetting(BlogVO vo, HttpSession session){
		return "forward:/blog/updateView?blogId=" + vo.getBlogId();
	}
}
