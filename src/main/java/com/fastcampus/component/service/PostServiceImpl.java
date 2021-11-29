package com.fastcampus.component.service;

import com.fastcampus.component.dao.PostDAO;
import com.fastcampus.component.vo.PostVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService{

    private final PostDAO postDAO;

    public PostServiceImpl(PostDAO postDAO) {
        this.postDAO = postDAO;
    }

    @Override
    public void createPost(PostVO vo) {
        postDAO.createPost(vo);
    }

    @Override
    public PostVO getPost(PostVO vo) {
        return postDAO.getPost(vo);
    }

    @Override
    public List<PostVO> getPosts(PostVO vo) {
        return postDAO.getPosts(vo);
    }

    @Override
    public void updatePost(PostVO vo) {
        postDAO.updatePost(vo);
    }

    @Override
    public void deletePost(PostVO vo) {
        postDAO.deletePost(vo);
    }
}
