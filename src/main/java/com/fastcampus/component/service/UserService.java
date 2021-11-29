package com.fastcampus.component.service;

import com.fastcampus.component.vo.BlogVO;
import com.fastcampus.component.vo.UserVO;

public interface UserService {

	UserVO getUser(UserVO vo);

	void insertUser(UserVO vo);

    void updateUser(UserVO vo);

	void deleteUser(UserVO vo);

	public BlogVO getUserBlog(UserVO vo);
}