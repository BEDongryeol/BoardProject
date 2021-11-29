package com.fastcampus.component.service;


import com.fastcampus.component.vo.PostVO;

import java.util.List;

public interface PostService {

    // 게시글 생성
    void createPost(PostVO vo);

    public PostVO getPost(PostVO vo);

    public List<PostVO> getPosts(PostVO vo);

    // 게시글 수정
    void updatePost(PostVO vo);

    // 게시글 삭제
    void deletePost(PostVO vo);
}
