package com.fastcampus.user;

import com.fastcampus.blog.BlogVO;

public interface UserService {

	UserVO getUser(UserVO vo);

	void insertUser(UserVO vo);

    void updateUser(UserVO vo);

	void deleteUser(UserVO vo);

}